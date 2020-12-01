package br.edu.ufabc.chokitus.mq.instances.pulsar.reactive.factory;

import br.edu.ufabc.chokitus.mq.instances.pulsar.reactive.client.ReactivePulsarProducer;
import br.edu.ufabc.chokitus.mq.instances.pulsar.reactive.client.ReactivePulsarReceiver;
import br.edu.ufabc.chokitus.mq.instances.pulsar.reactive.config.ReactivePulsarConfig;
import br.edu.ufabc.chokitus.reactive.mq.factory.ReactiveFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import reactor.core.publisher.Mono;

import java.util.Map;

import static br.edu.ufabc.chokitus.mq.instances.pulsar.reactive.config.ReactivePulsarConfig.Properties.SUBSCRIPTION_NAME;
import static br.edu.ufabc.chokitus.mq.instances.pulsar.reactive.config.ReactivePulsarConfig.Properties.TOPIC_NAME;

@Slf4j
public class ReactivePulsarFactory extends ReactiveFactory<ReactivePulsarConfig, ReactivePulsarReceiver, ReactivePulsarProducer> {

	private final PulsarClient pulsarClient;

	public ReactivePulsarFactory(final ReactivePulsarConfig config) throws PulsarClientException {
		super(config);
		this.pulsarClient = PulsarClient.builder().serviceUrl(config.getServiceUrl()).build();
	}

	@Override
	public ReactivePulsarReceiver createReceiver(final Map<String, String> config) throws PulsarClientException {
		return new ReactivePulsarReceiver(topicName -> buildConsumer(topicName, pulsarClient));
	}

	@Override
	public ReactivePulsarProducer createProducer(final Map<String, String> config) {
		return new ReactivePulsarProducer(topicName -> buildProducer(topicName, pulsarClient))
						.init(config.get(TOPIC_NAME.v()));
	}

	private static Producer<byte[]> buildProducer(final String topicName,
	                                              final PulsarClient pulsarClient) {
		try {
			return pulsarClient.newProducer()
			                   .topic(topicName)
			                   .create();
		} catch (final PulsarClientException e) {
			log.error("Error while creating producer to topic [{}]!", topicName, e);
			throw new RuntimeException(e);
		}
	}

	private static Consumer<byte[]> buildConsumer(final String topicName,
	                                              final PulsarClient pulsarClient) {
		try {
			return pulsarClient.newConsumer()
			                   .topic(topicName)
			                   .subscriptionName(topicName) // Limitation added for simplicity
			                   .subscribe();
		} catch (final PulsarClientException e) {
			log.error("Error while creating consumer to topic [{}]!", topicName, e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public Mono<Void> close() {
		return Mono.fromFuture(pulsarClient::closeAsync);
	}
}
