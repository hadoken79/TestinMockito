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
class Test12BDD {
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
    void should_CountAvailablePlaces_WhenGivenRoomListOfOneElement(){
        //given
        given(roomServiceMock.getAvailableRooms())//alternatives to match BDD style
                .willReturn(Collections.singletonList(new Room("Room 1", 2)));
        int expected = 2;

        //when
        int actual = this.bookingService.getAvailablePlaceCount();

        //then
        assertEquals(expected, actual);

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
        then(paymentServiceMock).should(times(1)).pay(bookingRequest, 400.0);//also BDD instead of verify use then.should
        verifyNoMoreInteractions(paymentServiceMock);
    }

}