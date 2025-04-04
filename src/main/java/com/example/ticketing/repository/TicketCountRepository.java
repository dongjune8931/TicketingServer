package com.example.ticketing.repository;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TicketCountRepository {

	private final RedisTemplate<String, String> redisTemplate;

	public TicketCountRepository(RedisTemplate<String, String> redisTemplate){
		this.redisTemplate=redisTemplate;
	}

	public Long increment(){
		return redisTemplate
			.opsForValue()
			.increment("ticket_count");
	}
}
