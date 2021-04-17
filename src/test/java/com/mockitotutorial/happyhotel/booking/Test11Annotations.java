package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)//since JUnit 5
class Test11Annotations {
    @InjectMocks
    private BookingService bookingService;
    @Mock
    private PaymentService paymentServiceMock;
    @Mock
    private RoomService roomServiceMock;
    @Mock
    private BookingDAO bookingDAOMock;
    @Mock //if spy needed -> @Spy
    private MailSender mailSenderMock;
    @Captor
    private ArgumentCaptor<Double> doubleCaptor;

//    @BeforeEach
//    void setup(){
//        this.paymentServiceMock = mock(PaymentService.class);
//        this.roomServiceMock = mock(RoomService.class);
//        this.bookingDAOMock = mock(BookingDAO.class);
//        this.mailSenderMock = mock(MailSender.class);
//
//
//        this.bookingService = new BookingService(paymentServiceMock, roomServiceMock, bookingDAOMock, mailSenderMock);
//        this.doubleCaptor = ArgumentCaptor.forClass(Double.class);
//    }

    @Test
    void shouldPayCorrectPrice_when_InputOk(){
        //given
        BookingRequest bookingRequest = new BookingRequest(
                "1",
                LocalDate.of(2020, 01, 01),
                LocalDate.of(2020, 01, 05),3, true);

        //when
        bookingService.makeBooking(bookingRequest);

        //then
        verify(paymentServiceMock).pay(eq(bookingRequest), doubleCaptor.capture());
        double capturedArgument = doubleCaptor.getValue();
        System.out.println("passedPriceValue to payMethod=" + capturedArgument);
        assertEquals(600.0, capturedArgument);

    }

    @Test
    void shouldPayCorrectPrices_when_MultipleInput(){
        //given
        BookingRequest bookingRequest = new BookingRequest(
                "1",
                LocalDate.of(2020, 01, 01),
                LocalDate.of(2020, 01, 05),3, true);

        BookingRequest bookingRequest2 = new BookingRequest(
                "1",
                LocalDate.of(2020, 01, 01),
                LocalDate.of(2020, 01, 02),2, true);
        List<Double> expectedValues = Arrays.asList(600.0, 100.0);

        //when
        bookingService.makeBooking(bookingRequest);
        bookingService.makeBooking(bookingRequest2);

        //then
        verify(paymentServiceMock, times(2)).pay(any(), doubleCaptor.capture());
        List<Double> capturedArguments = doubleCaptor.getAllValues();
        capturedArguments.stream().forEach(price -> System.out.println("passed VAl=" + price));
        assertEquals(expectedValues, capturedArguments);
    }

}