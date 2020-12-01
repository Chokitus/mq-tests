package br.edu.ufabc.chokitus.mq.instances.kafka.reactive.client;

import br.edu.ufabc.chokitus.mq.instances.kafka.reactive.message.ReactiveKafkaMessage;
import br.edu.ufabc.chokitus.reactive.mq.client.ReactiveReceiver;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Supplier;

public class ReactiveKafkaReceiver implements ReactiveReceiver<ReactiveKafkaMessage> {

	private final Map<String, KafkaReceiver<String, byte[]>> topicToReceiver = new ConcurrentHashMap<>();
	private final Function<String, KafkaReceiver<String, byte[]>> receiverBuilder;

	public ReactiveKafkaReceiver(final Supplier<ReceiverOptions<String, byte[]>> baseReceiverOptions) {
		receiverBuilder = topic -> KafkaReceiver.create(
						baseReceiverOptions.get().subscription(Collections.singletonList(topic)));
	}

	@Override
	public Flux<ReactiveKafkaMessage> consume(final String queue) {
		return topicToReceiver.computeIfAbsent(queue, receiverBuilder)
		                      .receive()
		                      .map(record -> new ReactiveKafkaMessage(
		                      				record.value(),
						                      record.receiverOffset().commit()));
	}

	@Override
	public Flux<ReactiveKafkaMessage> consumeAutoAck(final String queue) {
		return topicToReceiver.computeIfAbsent(queue, receiverBuilder)
		                      .receiveAutoAck()
		                      .flatMap(Function.identity())
		                      .map(record -> new ReactiveKafkaMessage(record.value()));
	}

	@Override
	public Mono<Void> close() {
		return Mono.empty(); // Automatically closed
	}
}
