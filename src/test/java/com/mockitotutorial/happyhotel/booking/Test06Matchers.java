package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.*;


class Test06Matchers {
    private BookingService bookingService;

    private PaymentService paymentServiceMock;
    private RoomService roomServiceMock;
    private BookingDAO bookingDAOMock;
    private MailSender mailSenderMock;

    @BeforeEach
    void setup(){
        this.paymentServiceMock = mock(PaymentService.class);
        this.roomServiceMock = mock(RoomService.class);
        this.bookingDAOMock = mock(BookingDAO.class);
        this.mailSenderMock = mock(MailSender.class);


        this.bookingService = new BookingService(paymentServiceMock, roomServiceMock, bookingDAOMock, mailSenderMock);
    }

    @Test
    void should_notCompleteBooking_when_PriceToHigh(){
        //given
        BookingRequest bookingRequest = new BookingRequest(
                "1",
                LocalDate.of(2020, 01, 01),
                LocalDate.of(2020, 01, 05),2, true);

        when(this.paymentServiceMock.pay(any(),anyDouble()))//with this matchers no explicit arguments have be be provided. any() is for Objects, anyDouble() for this double primitive
                .thenThrow(BusinessException.class);        //the arguments can be mixed, matchers and real, but then feg. eq(400.0) as equals has to be used

        // when(this.paymentServiceMock.pay(any(),eq(400.0)))
        //      .thenThrow(BusinessException.class);

        //when
        Executable executable = () -> bookingService.makeBooking(bookingRequest);

        //then
        assertThrows(BusinessException.class, executable);

    }



}