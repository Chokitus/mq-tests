package br.edu.ufabc.chokitus.mq.instances.kafka.reactive.message;

import br.edu.ufabc.chokitus.reactive.mq.message.ReactiveMessage;
import reactor.core.publisher.Mono;

public class ReactiveKafkaMessage extends ReactiveMessage<byte[]> {
	public ReactiveKafkaMessage(final byte[] message,
	                            final Mono<Void> ack) {
		super(message, ack);
	}

	public ReactiveKafkaMessage(final byte[] message) {
		super(message);
	}

	@Override
	public byte[] getBytes() {
		return message;
	}
}
