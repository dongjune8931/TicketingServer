package com.example.ticketing.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ticketing.domain.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

}
