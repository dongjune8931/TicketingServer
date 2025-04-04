package com.example.ticketing.service;

import org.springframework.stereotype.Service;

import com.example.ticketing.domain.Ticket;
import com.example.ticketing.repository.TicketRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TicketingService {

	private final TicketRepository ticketRepository;

	/**
	 * 레이스 컨디션 발생 : 두 개 이상의 스레드에서 공유 데이터의 액세스를 할 때 발생하는 문제
	 * 싱글 스레드로 하면 해결 되지만 트래픽이 몰리는 우리 서비스에서는 성능이 안좋아짐
	 * 그럼 락?? nono -> 티켓 갯수에 대한 정합성을 지켜야 하는데 락을 활용하면 발급된 쿠폰 개수를 가져오는것 부터
	 * 쿠폰을 생성할 때까지 락을 걸어야하는데 ,,,
	 * 이 또한 락을 거는 구간이 길어져 성능이 저하될 거임.
	 */
	public void apply_v1(Long userId){
		long count= ticketRepository.count();
		if(count>100){
			return;
		}

		ticketRepository.save(new Ticket(userId));
	}



}
