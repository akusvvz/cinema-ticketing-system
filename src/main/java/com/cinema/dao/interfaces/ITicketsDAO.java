package com.cinema.dao.interfaces;

import com.cinema.entities.Tickets;
import java.util.List;

public interface ITicketsDAO {
    List<Tickets> getAllTickets();
    Tickets getTicketById(int id);
    int generateUniqueTicketId();
    int saveTicket(int showtimeId, int seatId, double price);
    boolean updateTicket(Tickets ticket);
    boolean deleteTicket(int id);
}
