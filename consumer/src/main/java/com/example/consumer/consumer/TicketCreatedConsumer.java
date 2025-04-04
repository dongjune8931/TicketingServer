package com.example.consumer.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.example.consumer.domain.FailedEvent;
import com.example.consumer.domain.Ticket;
import com.example.consumer.repository.FailedEventRepository;
import com.example.consumer.repository.TicketRepository;

@Component
public class TicketCreatedConsumer {

	private final TicketRepository ticketRepository;
	private final FailedEventRepository failedEventRepository;
	private final Logger logger = LoggerFactory.getLogger(TicketCreatedConsumer.class);

	public TicketCreatedConsumer(TicketRepository ticketRepository, FailedEventRepository failedEventRepository) {
		this.ticketRepository = ticketRepository;
		this.failedEventRepository = failedEventRepository;
	}

	@KafkaListener(topics="ticket_create",groupId = "group_1")
	public void listener(Long userId){
		try{
			ticketRepository.save(new Ticket(userId));
		}catch(Exception e){
			logger.error("failed to create coupon:"+userId);
			failedEventRepository.save(new FailedEvent(userId));
		}
	}
}
