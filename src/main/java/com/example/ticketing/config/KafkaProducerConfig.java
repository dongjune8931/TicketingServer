package com.example.ticketing.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
public class KafkaProducerConfig {

	@Bean
	public ProducerFactory<String,Long> producerFactory(){ // 손쉽게 설정값들을 설정할 수 있도록 해줌,
		Map<String,Object> config =new HashMap<>(); //설정값들을 넣어줄 맵

		config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092"); //서버 정보 추가
		config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);//Key Serializer 클래스 정보 추가
		config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, LongSerializer.class);//Value Serializer

		return new DefaultKafkaProducerFactory<>(config);
	}

	//카프카 토픽에 데이터를 전송하기 위해 사용할 카프카 템플릿 생성
	@Bean
	public KafkaTemplate<String, Long> kafkaTemplate(){
		return new KafkaTemplate<>(producerFactory());
	}


}