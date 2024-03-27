package com.playfieldsync.repositories.ticket;

import com.playfieldsync.entities.ticket.Ticket;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;

@Registered
public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
