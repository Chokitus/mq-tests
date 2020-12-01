package br.edu.ufabc.chokitus.mq.instances.pulsar.reactive.message;

import br.edu.ufabc.chokitus.reactive.mq.message.ReactiveMessage;
import reactor.core.publisher.Mono;

public class ReactivePulsarMessage extends ReactiveMessage<byte[]> {

	public ReactivePulsarMessage(final byte[] message,
	                             final Mono<Void> ack) {
		super(message, ack);
	}

	public ReactivePulsarMessage(final byte[] message) {
		super(message);
	}

	@Override
	public byte[] getBytes() {
		return message;
	}
}
