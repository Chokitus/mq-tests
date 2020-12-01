package br.edu.ufabc.chokitus.mq.instances.rabbitmq.reactive.client;

import br.edu.ufabc.chokitus.reactive.mq.client.ReactiveProducer;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.rabbitmq.OutboundMessage;
import reactor.rabbitmq.Sender;

import java.util.function.Function;

@RequiredArgsConstructor
public class ReactiveRMQProducer implements ReactiveProducer {

	private final Sender sender;

	@Override
	public Mono<Void> close() {
		return Mono.fromRunnable(sender::close);
	}

	@Override
	public Mono<Void> produce(final byte[] message,
	                          final String destination) {
		return sender.send(Mono.just(new OutboundMessage("", destination, message)));
	}

	@Override
	public Mono<Void> produce(final Flux<byte[]> messages,
	                          final String destination) {
		return sender.send(messages.map(toDestination(destination)));
	}

	public static Function<byte[], OutboundMessage> toDestination(final String destination) {
		return bytes -> new OutboundMessage("", destination, bytes);
	}
}
