package com.example.ticketing.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class TicketCreateProducer {

	private final KafkaTemplate<String,Long> kafkaTemplate;

	public TicketCreateProducer(KafkaTemplate<String, Long> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	public void create(Long userId){
		kafkaTemplate.send("ticket_create",userId);
	}
}
