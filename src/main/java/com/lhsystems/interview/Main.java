package com.lhsystems.interview;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws BookingException {
        Passenger p1 = new Passenger("Mr. passenger", "passenger.mister@gmail.com");
        Passenger p4 = new Passenger("Mr. passenger", "passenger.MR@gmail.com");
        Passenger p2 = new Passenger("Jr. passenger", "passenger.junior@gmail.com");
        Passenger p3 = new Passenger("Mrs. passenger", "passenger.mrs@gmail.com");
        //Booking(Flight flight, int seatNumber, Passenger passenger)
        List<Booking> bookings1 = new ArrayList<>();
        List<Booking> bookings2 = new ArrayList<>();
        List<Flight> flights = new ArrayList<>();


        //Flight(String flightNumber, String origin, String destination, LocalDateTime departureTime, int availableSeats, List<Booking> bookings)
        Flight flight1 = new Flight("LH123", "BUD", "FRA", LocalDateTime.of(2023, 2, 5, 10, 0, 0), 20, new ArrayList<>());
        Flight flight2 = new Flight("LH124", "FRA", "BUD", LocalDateTime.of(2023, 2, 6, 10, 0, 0), 20, bookings1);
        Flight flight3 = new Flight("LH125", "BEL", "HOR", LocalDateTime.of(2023, 2, 6, 10, 0, 0), 10, new ArrayList<>());

        flights.add(flight3);
        flights.add(flight2);
        flights.add(flight1);

        //Booking booking = new Booking(flight1, 2, p1);
        //Booking booking2 = new Booking(flight2, 2, p1);

        //bookings1.add(booking);
        //bookings2.add(booking2);

        BookingSystem bookingSystem = new BookingSystem(flights);

        //System.out.println("------------------------------");
        //System.out.println("size: "+flights.size());
        //System.out.println(bookingSystem.getBookingsForPassenger(p1.getEmail()));
        //bookingSystem.printFlights();
        System.out.println();
        System.out.println("------------new method test-------------");
        System.out.println("Booking: "+bookingSystem.bookFlight(flight1, 1, p1));
        System.out.println("Booking: "+bookingSystem.bookFlight(flight1, 11, p4));
        System.out.println();
        // seat taken
        //try {
            System.out.println("Booking: "+bookingSystem.bookFlight(flight1, 2, p2));
        System.out.println();
        //} catch (BookingException e) {
            //e.printStackTrace();
        //}
        System.out.println("Booking: "+bookingSystem.bookFlight(flight1, 4, p3));
        System.out.println();
        System.out.println("Booking: "+bookingSystem.bookFlight(flight3, 4, p1));
        System.out.println("Booking: "+bookingSystem.bookFlight(flight3, 14, p4));
        System.out.println("Booking: "+bookingSystem.bookFlight(flight2, 14, p4));

        System.out.println();
        System.out.println("-----------stat-------------");
        bookingSystem.findMostBookedPassenger();
        //bookingSystem.totalBookings();
    }


}
