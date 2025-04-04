package com.example.ticketing.service;

import static org.assertj.core.api.AssertionsForClassTypes.*;


import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.ticketing.repository.TicketRepository;

@SpringBootTest
class TicketingServiceTest {

	@Autowired
	private TicketingService ticketingService;

	@Autowired
	private TicketRepository ticketRepository;

	@Test
	public void OnceTicket(){
		ticketingService.apply_v3(1L);
		long count= ticketRepository.count();
		assertThat(count).isEqualTo(1);
	}

	@Test
	public void ManyTicket() throws InterruptedException {
		int threadCount=1000;
		ExecutorService executorService= Executors.newFixedThreadPool(32);
		CountDownLatch latch=new CountDownLatch(threadCount);

		for(int i=0;i<threadCount;i++){
			long userId=i;
			executorService.submit(()->{
				try{
					ticketingService.apply_v3(userId);
				}finally {
					latch.countDown();
				}
			});
		}
		latch.await();;

		Thread.sleep(10000);
		long count= ticketRepository.count();
		assertThat(count).isEqualTo(100);
	}
}