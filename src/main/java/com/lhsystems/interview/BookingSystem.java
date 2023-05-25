package com.lhsystems.interview;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class BookingSystem {

    private final List<Flight> flights;

    public BookingSystem(List<Flight> flights) {
        this.flights = flights;
    }

    public List<Flight> findAvailableFlights(String origin, String destination, LocalDateTime departureTime) {
        List<Flight> availableFlights = new ArrayList<>();
        for (int i = 0; i < flights.size(); i++) {
            if(flights.get(i).getOrigin().equals(origin) && flights.get(i).getDestination().equals(destination) && flights.get(i).getDepartureTime().isAfter(departureTime) && flights.get(i).getAvailableSeats() > 0){
                flights.get(i).setAvailableSeats(flights.get(i).getAvailableSeats() - 1);
                availableFlights.add(flights.get(i));
            }
        }
        // returns a list of available flights based on the specified origin, destination, and departure time with at least one available seat
        return availableFlights;
    }

    //Booking(Flight flight, int seatNumber, Passenger passenger)
    public Booking bookFlight(Flight flight, int seatNumber, Passenger passenger) throws BookingException {
        // books the specified flight and seat for the specified passenger
        // throws a BookingException if the seat is already booked or if the flight is fully booked

        List<Booking> bookings = flight.getBookings();
        Booking booking = new Booking(flight,0,new Passenger("Test Doe", "test@gmail.com"));

        if(flight.getBookings().size() == 0){

            booking.setFlight(flight);
            booking.setPassenger(passenger);
            booking.setSeatNumber(seatNumber);

            flight.setAvailableSeats(flight.getAvailableSeats() - 1);
        } else {
            for (int j = 0; j < flight.getBookings().size(); j++) {
                if (flight.getAvailableSeats() > 0 && flight.getBookings().get(j).getSeatNumber() != seatNumber) {
                    booking.setFlight(flight);
                    booking.setPassenger(passenger);
                    booking.setSeatNumber(seatNumber);
                    flight.setAvailableSeats(flight.getAvailableSeats() - 1);

                }
                else if (flight.getAvailableSeats() == 0 || flight.getBookings().get(j).getSeatNumber() == seatNumber) {
                    throw new BookingException("Seat already booked, or flight is fully booked!");
                }
            }
        }

        bookings.add(booking);
        flight.setBookings(bookings);

        return  booking;
    }

    public void cancelBooking(Booking booking) {
        for (int i = 0; i < flights.size(); i++) {
            if (flights.get(i).getBookings().size() != 0) {
                for (int j = 0; j < flights.get(i).getBookings().size(); j++) {
                    if (flights.get(i).getBookings().get(j).equals(booking)) {
                        List<Booking> bookings = flights.get(i).getBookings();
                        bookings.remove(flights.get(i).getBookings().get(j));
                    }
                }
            }
        }
        booking.getFlight().setAvailableSeats(booking.getFlight().getAvailableSeats() +1);
        // cancels the specified booking associated with one of the Flight objects from the BookingSystem

    }

    public float getBookedPercentageForFlight(String flightNumber) {
        float result = 0;
        float seatsPer;
        float bookings;
        for (int i = 0; i < flights.size(); i++) {
            if(flights.get(i).getFlightNumber().equals(flightNumber)){
                seatsPer = flights.get(i).getAvailableSeats()/100f;
                bookings = flights.get(i).getBookings().size();
                result = bookings/seatsPer;
            }
        }
        // returns the booked seat percentage of all bookings associated with the given flight.
        return result;
    }

    public List<Booking> getBookingsForPassenger(String email) {

        List<Booking> bookings = new ArrayList<>();

        for (int i = 0; i < flights.size(); i++) {
            if(flights.get(i).getBookings().size() != 0) {
                for (int j = 0; j < flights.get(i).getBookings().size(); j++) {
                    if (flights.get(i).getBookings().get(j).getPassenger().getEmail().equals(email)) {
                        bookings.add(flights.get(i).getBookings().get(j));
                    }
                }
            }
        }
        // returns a list of all bookings associated with the given passenger email address, regardless of the flight.
        return bookings;
    }

    // check:
    public void printFlights() {
        for (int i = 0; i < flights.size(); i++) {
            System.out.println(flights.get(i).getFlightNumber());
        }
    }

    // Comparator for compare two Passenger objects
    Comparator<Passenger> passengerNameComparator = Comparator.comparing(Passenger::getName);

    public Passenger findMostBookedPassenger() {
        Map<Passenger, Integer> passengerStatistics = new TreeMap<>(passengerNameComparator);
        List<Passenger> passengers = new ArrayList<>();
        for (int i = 0; i < flights.size(); i++) {
            if (flights.get(i).getBookings().size() != 0) {
                for (int j = 0; j < flights.get(i).getBookings().size(); j++) {
                    if (passengerStatistics.containsKey(flights.get(i).getBookings().get(j).getPassenger())) {

                        Integer count = passengerStatistics.get(flights.get(i).getBookings().get(j).getPassenger());
                        count++;
                        passengerStatistics.put(flights.get(i).getBookings().get(j).getPassenger(), count);

                    } else {
                        passengerStatistics.put(flights.get(i).getBookings().get(j).getPassenger(), 1);
                    }
                }
            }
        }
        Set<Map.Entry<Passenger, Integer>> entrySet = passengerStatistics.entrySet();
        // check:
        for (Map.Entry<Passenger, Integer> entry : entrySet) {
            System.out.println(entry.getKey() + " --> " + entry.getValue() + " db");
        }

        //comparingByValue:
        List<Map.Entry<Passenger, Integer>> collect = passengerStatistics.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue())).collect(Collectors.toList());

        for(int i = 0; i < collect.size(); i++){
            passengers.add(collect.get(i).getKey());
        }

        System.out.println(passengers);

        // We would like to reward the passenger with the most booked seats. Therefor we need to find the passenger that has the most booked seats in our bookingsystem
        // returns which passenger has the most amount of Bookings?
        return passengers.get(0);
    }

    public List<Flight> getFlights() {
        return flights;
    }

    // check:
    public void totalBookings(){
        int count = 0;
        for (int i = 0; i < flights.size(); i++) {
            System.out.println("number: "+flights.get(i).getFlightNumber());
            System.out.println("size: "+flights.get(i).getBookings().size());
            count += flights.get(i).getBookings().size();
        }
        System.out.println("Total bookings: "+count);
    }
}
