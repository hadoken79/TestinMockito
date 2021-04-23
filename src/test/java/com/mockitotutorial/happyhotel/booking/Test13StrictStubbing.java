package com.mockitotutorial.happyhotel.booking;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.*;


@ExtendWith(MockitoExtension.class)//since JUnit 5
class Test13StrictStubbing {
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


    @Test
    void should_invokePayment_when_prepaid(){
        //given
        BookingRequest bookingRequest = new BookingRequest(
                "1",
                LocalDate.of(2020, 01, 01),
                LocalDate.of(2020, 01, 05),2, false);
        lenient().when(paymentServiceMock.pay(eq(bookingRequest),anyDouble())).thenReturn("1");//this mock is never called, cause prepaid is false.
        //lenient can be applied when strict stubbing is turned on(from @ExtendWith) and still a mock is created, that is never used
        //without lenient an exception would be thrown. Strict Stubbing helps keeping tests clean and free from unused assumptions

        //when
        bookingService.makeBooking(bookingRequest);

        //then
        //no exception is thrown
    }

}