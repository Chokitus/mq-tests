package br.edu.ufabc.chokitus.mq.instances.pulsar.blocking.factory;

import br.edu.ufabc.chokitus.mq.benchmark.ConfigurationProperties;
import br.edu.ufabc.chokitus.mq.instances.pulsar.blocking.config.PulsarProperty;
import br.edu.ufabc.chokitus.mq.instances.pulsar.blocking.client.PulsarProducer;
import br.edu.ufabc.chokitus.mq.instances.pulsar.blocking.client.PulsarReceiver;
import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;

import br.edu.ufabc.chokitus.mq.factory.AbstractClientFactory;

public class PulsarClientFactory extends AbstractClientFactory<PulsarReceiver, PulsarProducer> {

	final PulsarClient pulsarClient;

	public PulsarClientFactory(final ConfigurationProperties clientFactoryProperties, final PulsarClient pulsarClient) {
		super(clientFactoryProperties);
		this.pulsarClient = pulsarClient;
	}

	@Override
	protected void closeImpl() throws Exception {
		pulsarClient.close();
	}

	@Override
	protected PulsarReceiver createConsumerImpl(final ConfigurationProperties consumerProperties) throws Exception {
		final Consumer<byte[]> consumer = pulsarClient.newConsumer()
													  .topic((String) consumerProperties.get(PulsarProperty.TOPIC_NAME.getValue()))
													  .subscriptionName((String) consumerProperties.get(PulsarProperty.SUBSCRIPTION_NAME.getValue()))
													  .subscribe();
		return new PulsarReceiver(consumer, consumerProperties);
	}

	@Override
	protected PulsarProducer createProducerImpl(final ConfigurationProperties producerProperties) throws Exception {
		final Producer<byte[]> producer = pulsarClient.newProducer()
													  .topic((String) producerProperties.get(PulsarProperty.TOPIC_NAME.getValue()))
													  .create();
		return new PulsarProducer(producer, producerProperties);
	}

}
