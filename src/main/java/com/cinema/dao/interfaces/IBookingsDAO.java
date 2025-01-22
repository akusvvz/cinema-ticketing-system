package com.cinema.dao.interfaces;

import com.cinema.entities.Bookings;
import java.util.List;

public interface IBookingsDAO {
    List<Bookings> getAllBookings();
    Bookings getBookingById(int id);
    boolean addBooking(Bookings booking);
    boolean updateBooking(Bookings booking);
    boolean deleteBooking(int id);
}
