package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.*;


class Test03ReturnCustomValues {
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
    void should_CountAvailablePlaces_WhenGivenRoomListOfOneElement(){
        //given
        when(roomServiceMock.getAvailableRooms())
                .thenReturn(Collections.singletonList(new Room("Room 1", 2)));
        int expected = 2;

        //when
        int actual = this.bookingService.getAvailablePlaceCount();

        //then
        assertEquals(expected, actual);

    }

    @Test
    void should_CountAvailablePlaces_When_MultipleRoomsAreAvailable(){
        //given
        List<Room> roomList = Arrays.asList(
                new Room("Room 1", 2),
                new Room("Room 2", 1),
                new Room("Room 3", 2)
        );

        when(roomServiceMock.getAvailableRooms())
                .thenReturn(roomList);
        int expected = 5;

        //when
        int actual = this.bookingService.getAvailablePlaceCount();

        //then
        assertEquals(expected, actual);

    }



}