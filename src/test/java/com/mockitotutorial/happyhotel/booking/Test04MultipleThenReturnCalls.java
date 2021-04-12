package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class Test04MultipleThenReturnCalls {
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
    void should_CountAvailablePlaces_When_CalledMultipleTimes(){
        //given
        when(roomServiceMock.getAvailableRooms())
                .thenReturn(Collections.singletonList(new Room("Room 1", 2)))
                .thenReturn(Collections.emptyList());


        int expectedFirst = 2;
        int expectedSecond = 0;

        //when
        int actualFirst = this.bookingService.getAvailablePlaceCount();
        int actualSecond = this.bookingService.getAvailablePlaceCount();

        //then
        assertAll(
                () -> assertEquals(expectedFirst, actualFirst),
                () -> assertEquals(expectedSecond, actualSecond)
        );

    }





}