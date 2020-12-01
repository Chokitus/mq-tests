package br.edu.ufabc.chokitus.mq.instances.pulsar.reactive.client;

import br.edu.ufabc.chokitus.mq.instances.pulsar.reactive.message.ReactivePulsarMessage;
import br.edu.ufabc.chokitus.reactive.mq.client.ReactiveReceiver;
import lombok.RequiredArgsConstructor;
import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Message;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;


@RequiredArgsConstructor
public class ReactivePulsarReceiver implements ReactiveReceiver<ReactivePulsarMessage> {

	private final Map<String, Consumer<byte[]>> topicToConsumer = new ConcurrentHashMap<>();
	private final Function<String, Consumer<byte[]>> consumerBuilder;

	@Override
	public Flux<ReactivePulsarMessage> consume(final String queue) {
		final Consumer<byte[]> consumer = get(queue);
		return Mono.fromFuture(consumer::batchReceiveAsync)
		           .flatMapMany(Flux::fromIterable)
		           .map(msg -> buildMessage(msg, consumer));
	}

	private Consumer<byte[]> get(final String queue) {
		return topicToConsumer.computeIfAbsent(queue, consumerBuilder);
	}

	@Override
	public Flux<ReactivePulsarMessage> consumeAutoAck(final String queue) {
		final Consumer<byte[]> consumer = get(queue);
		return Mono.fromFuture(consumer::batchReceiveAsync)
		           .flatMapMany(batch -> Mono.fromFuture(consumer.acknowledgeAsync(batch))
		                                     .thenMany(Flux.fromIterable(batch)))
		           .map(this::buildMessageNoAck);
	}

	@Override
	public Mono<Void> close() {
		return Flux.fromIterable(topicToConsumer.values()).flatMap(consumer -> Mono.fromFuture(consumer.closeAsync())).then();
	}

	private ReactivePulsarMessage buildMessage(final Message<byte[]> message,
	                                           final Consumer<byte[]> consumer) {
		return new ReactivePulsarMessage(message.getValue(),
		                                 Mono.fromFuture(() -> consumer.acknowledgeAsync(message)));
	}

	private ReactivePulsarMessage buildMessageNoAck(final Message<byte[]> message) {
		return new ReactivePulsarMessage(message.getValue());
	}
}
