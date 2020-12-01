package br.edu.ufabc.chokitus.mq.instances.kafka.reactive.client;

import br.edu.ufabc.chokitus.reactive.mq.client.ReactiveProducer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ReactiveKafkaProducer implements ReactiveProducer {

	

	@Override
	public Mono<Void> produce(final byte[] message,
	                          final String destination) {
		return null;
	}

	@Override
	public Mono<Void> produce(final Flux<byte[]> messages,
	                          final String destination) {
		return null;
	}

	@Override
	public Mono<Void> close() {
		return null;
	}
}
