package com.lhsystems.interview;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.time.LocalDateTime;
import java.util.List;

public class Flight {
    private String flightNumber;
    private String origin;
    private String destination;
    private LocalDateTime departureTime;
    private int availableSeats;
    private List<Booking> bookings;

    public Flight(String flightNumber, String origin, String destination, LocalDateTime departureTime, int availableSeats, List<Booking> bookings) {
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
        this.departureTime = departureTime;
        this.availableSeats = availableSeats;
        this.bookings = bookings;
    }


    public Booking bookSeat(int seatNumber, Passenger passenger) throws BookingException {
        Booking booking = new Booking(this,0, new Passenger("Test Doe", "test@gmail.com")); //empty constructor would be better
        List<Booking> bookings = this.getBookings();

        if(this.getBookings().size() == 0){

            booking.setFlight(this);
            booking.setPassenger(passenger);
            booking.setSeatNumber(seatNumber);

            this.setAvailableSeats(this.getAvailableSeats() - 1);
        } else {
            for (int j = 0; j < this.getBookings().size(); j++) {
                if (this.getAvailableSeats() > 0 && this.getBookings().get(j).getSeatNumber() != seatNumber) {
                    booking.setFlight(this);
                    booking.setPassenger(passenger);
                    booking.setSeatNumber(seatNumber);
                    this.setAvailableSeats(this.getAvailableSeats() - 1);

                }
                else if (this.getAvailableSeats() == 0 || this.getBookings().get(j).getSeatNumber() == seatNumber) {
                    throw new BookingException("Seat already booked, or flight is fully booked!");
                }
            }
        }

        bookings.add(booking);
        this.setBookings(bookings);

        //This method should book a seat with the specified seat number for the specified Passenger object.
        return booking;
    }

    public void cancelBooking(Booking booking) {
        for (int j = 0; j < this.getBookings().size(); j++) {
            if(this.getBookings().get(j).equals(booking)){
                this.getBookings().remove(booking);
            }
        }

        booking.getFlight().setAvailableSeats(booking.getFlight().getAvailableSeats() +1);
        //This method should cancel the given Booking.

    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Flight flight = (Flight) o;

        return new EqualsBuilder().append(availableSeats, flight.availableSeats).append(flightNumber, flight.flightNumber).append(origin, flight.origin).append(destination, flight.destination).append(departureTime, flight.departureTime).append(bookings, flight.bookings).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(flightNumber).append(origin).append(destination).append(departureTime).append(availableSeats).append(bookings).toHashCode();
    }


    @Override
    public String toString() {
        return "Flight{" +
                "flightNumber='" + flightNumber + '\'' +
                '}';
    }
}
