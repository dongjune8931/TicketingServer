package com.example.consumer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.consumer.domain.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

}
