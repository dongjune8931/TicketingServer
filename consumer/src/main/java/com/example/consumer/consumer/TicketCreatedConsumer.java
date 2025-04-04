package com.example.consumer.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.example.consumer.domain.Ticket;
import com.example.consumer.repository.TicketRepository;

@Component
public class TicketCreatedConsumer {

	private final TicketRepository ticketRepository;

	public TicketCreatedConsumer(TicketRepository ticketRepository) {
		this.ticketRepository = ticketRepository;
	}

	@KafkaListener(topics="ticket_create",groupId = "group_1")
	public void listener(Long userId){
		ticketRepository.save(new Ticket(userId));
	}
}
