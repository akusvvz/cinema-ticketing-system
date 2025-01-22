package com.cinema.dao.interfaces;

import com.cinema.entities.Tickets;
import java.util.List;

public interface ITicketsDAO {
    List<Tickets> getAllTickets();
    Tickets getTicketById(int id);
    List<Tickets> getTicketsByBookingId(int bookingId);
    boolean addTicket(Tickets ticket);
    boolean updateTicket(Tickets ticket);
    boolean deleteTicket(int id);
}