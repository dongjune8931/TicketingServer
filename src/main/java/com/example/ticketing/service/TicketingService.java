package com.example.ticketing.service;

import org.springframework.stereotype.Service;

import com.example.ticketing.domain.Ticket;
import com.example.ticketing.producer.TicketCreateProducer;
import com.example.ticketing.repository.TicketCountRepository;
import com.example.ticketing.repository.TicketRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TicketingService {

	private final TicketRepository ticketRepository;
	private final TicketCountRepository ticketCountRepository;
	private final TicketCreateProducer ticketCreateProducer;

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

	/**
	 * Redis를 사용한 카운트 관리. (높은 동시성 환경에서 레이스 컨디션 환경 방지)
	 * 레디스에 저장된 카운트를 원자적으로 증가시키며, 이를 통해 여러 스레드가 동시에 카운트를 증가시켜도 문제가 발생하지 않도록 보장
	 * 동시성 처리 최적화
	 *
	 * 문제점: 현재는 발급이 가능하면 RDB에 저장함
	 * 쿠폰의 개수가 많아질수록 RDB에 부하가 갈거임,,
	 * 짧은 시간에 많은 요청이 오면 부하 발생할거고 그만큼 서비스 지연됨
	 * 단기간에 많은 요청이 들어와 RDB의 CPU 사용량이 높아지고 그로인한 서비스 오류
	 */
	public void apply_v2(Long userId){
		Long count = ticketCountRepository.increment();
		if(count>100){
			return;
		}
		ticketRepository.save(new Ticket(userId));

	}

	public void apply_v3(Long userId){
		Long count= ticketCountRepository.increment();
		if(count>100){
			return;
		}
		ticketCreateProducer.create(userId);

	}

}
