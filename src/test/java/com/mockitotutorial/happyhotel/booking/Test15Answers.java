package com.mockitotutorial.happyhotel.booking;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.anyDouble;
import static org.mockito.BDDMockito.mockStatic;


@ExtendWith(MockitoExtension.class)//since JUnit 5
class Test15Answers {
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
    void shouldCalculate_correctPrice(){
        //given
        try(MockedStatic<CurrencyConverter> mockedConverter = mockStatic(CurrencyConverter.class)){
            //to mock static methods, change in pom
            // "<artifactId>mockito-core</artifactId>" to
            //"<artifactId>mockito-inline</artifactId>"
            BookingRequest bookingRequest = new BookingRequest(
                    "1",
                    LocalDate.of(2020, 01, 01),
                    LocalDate.of(2020, 01, 05),2, false);

            double expected = 400 * 0.8;
            mockedConverter.when(() -> CurrencyConverter.toEuro(anyDouble()))
                    .thenAnswer(inv -> (double)inv.getArgument(0) * 0.8);

            ;

            //when
            double actual = bookingService.calculatePriceEuro(bookingRequest);


            //then
            assertEquals(expected, actual);

        }



    }

}