package br.edu.ufabc.chokitus.mq.instances.pulsar;

import java.util.Map;

import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;

import br.edu.ufabc.mq.factory.AbstractClientFactory;

public class PulsarClientFactory extends AbstractClientFactory<PulsarConsumer, PulsarProducer> {

	final PulsarClient pulsarClient;

	public PulsarClientFactory(final Map<String, Object> clientFactoryProperties, final PulsarClient pulsarClient) {
		super(clientFactoryProperties);
		this.pulsarClient = pulsarClient;
	}

	@Override
	protected void closeImpl() throws Exception {
		pulsarClient.close();
	}

	@Override
	protected PulsarConsumer createConsumerImpl(final Map<String, Object> consumerProperties) throws Exception {
		final Consumer<byte[]> consumer = pulsarClient.newConsumer()
													  .topic((String) consumerProperties.get(PulsarProperty.TOPIC_NAME.getValue()))
													  .subscribe();
		return new PulsarConsumer(consumer, consumerProperties);
	}

	@Override
	protected PulsarProducer createProducerImpl(final Map<String, Object> producerProperties) throws Exception {
		final Producer<byte[]> producer = pulsarClient.newProducer()
													  .topic((String) producerProperties.get(PulsarProperty.TOPIC_NAME.getValue()))
													  .create();
		return new PulsarProducer(producer, producerProperties);
	}

}
