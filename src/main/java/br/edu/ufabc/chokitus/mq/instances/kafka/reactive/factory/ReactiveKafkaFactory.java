package br.edu.ufabc.chokitus.mq.instances.kafka.reactive.factory;

import br.edu.ufabc.chokitus.mq.instances.kafka.reactive.client.ReactiveKafkaProducer;
import br.edu.ufabc.chokitus.mq.instances.kafka.reactive.client.ReactiveKafkaReceiver;
import br.edu.ufabc.chokitus.mq.instances.kafka.reactive.config.ReactiveKakfaConfig;
import br.edu.ufabc.chokitus.reactive.mq.factory.ReactiveFactory;
import reactor.core.publisher.Mono;
import reactor.kafka.receiver.ReceiverOptions;

import java.util.Map;

public class ReactiveKafkaFactory extends ReactiveFactory<ReactiveKakfaConfig, ReactiveKafkaReceiver, ReactiveKafkaProducer> {
	public ReactiveKafkaFactory(final ReactiveKakfaConfig config) {
		super(config);
	}

	@Override
	public ReactiveKafkaReceiver createReceiver(final Map<String, String> config) throws Exception {
		return new ReactiveKafkaReceiver(this::baseReceiverOptions);
	}

	@Override
	public ReactiveKafkaProducer createProducer(final Map<String, String> config) throws Exception {
		new ReactiveKafkaProducer()
		return null;
	}

	private ReceiverOptions<String, byte[]> baseReceiverOptions() {
		return ReceiverOptions.create(generalConfig.asConsumerProperties());
	}

	@Override
	public Mono<Void> close() {
		return null;
	}
}
