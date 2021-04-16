package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


class Test07VerifyingBehavior {
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
    void should_invokePayment_when_prepaid(){
        //given
        BookingRequest bookingRequest = new BookingRequest(
                "1",
                LocalDate.of(2020, 01, 01),
                LocalDate.of(2020, 01, 05),2, true);

        //when
        bookingService.makeBooking(bookingRequest);

        //then
        verify(paymentServiceMock).pay(bookingRequest, 400.0);
        //optional can be checked how often e method is called
        //verify(paymentServiceMock, times(2)).pay(bookingRequest, 400.0);
        verifyNoMoreInteractions(paymentServiceMock);//make sure no other method is called after pay
    }

    @Test
    void should_NotinvokePayment_when_Notprepaid(){
        //given
        BookingRequest bookingRequest = new BookingRequest(
                "1",
                LocalDate.of(2020, 01, 01),
                LocalDate.of(2020, 01, 05),2, false);

        //when
        bookingService.makeBooking(bookingRequest);

        //then
        verify(paymentServiceMock, never()).pay(any(), anyDouble());
    }
}