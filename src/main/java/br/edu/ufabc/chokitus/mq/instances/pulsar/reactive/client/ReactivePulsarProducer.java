package br.edu.ufabc.chokitus.mq.instances.pulsar.reactive.client;

import br.edu.ufabc.chokitus.reactive.mq.client.ReactiveProducer;
import lombok.RequiredArgsConstructor;
import org.apache.pulsar.client.api.Producer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

@RequiredArgsConstructor
public class ReactivePulsarProducer implements ReactiveProducer {

	final Map<String, Producer<byte[]>> topicToProducer = new ConcurrentHashMap<>();
	final Function<String, Producer<byte[]>> producerBuilder;

	@Override
	public Mono<Void> produce(final byte[] message,
	                          final String destination) {
		final Producer<byte[]> producer = topicToProducer.computeIfAbsent(destination, producerBuilder);
		return Mono.fromFuture(producer.sendAsync(message))
		           .then();
	}

	@Override
	public Mono<Void> produce(final Flux<byte[]> messages,
	                          final String destination) {
		return messages.flatMap(msg -> produce(msg, destination))
		               .then();
	}

	@Override
	public Mono<Void> close() {
		return Flux.fromIterable(topicToProducer.values())
		           .flatMap(producer -> Mono.fromFuture(producer.closeAsync()))
		           .then();
	}

	public ReactivePulsarProducer init(final String topic) {
		topicToProducer.put(topic, producerBuilder.apply(topic));
		return this;
	}
}
