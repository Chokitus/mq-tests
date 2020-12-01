package br.edu.ufabc.chokitus.mq.instances.kafka.blocking.client;

import br.edu.ufabc.chokitus.mq.benchmark.ConfigurationProperties;
import br.edu.ufabc.chokitus.mq.instances.kafka.blocking.message.KafkaMessage;
import org.apache.kafka.clients.producer.ProducerRecord;

import br.edu.ufabc.chokitus.mq.client.AbstractProducer;

public class KafkaProducer
		extends AbstractProducer<org.apache.kafka.clients.producer.KafkaProducer<String, byte[]>, KafkaMessage> {

	public KafkaProducer(final org.apache.kafka.clients.producer.KafkaProducer<String, byte[]> client,
			final ConfigurationProperties properties) {
		super(client, properties);
	}

	@Override
	public void closeImpl() throws Exception {
		client.flush();
		client.close();
	}

	@Override
	public void startImpl(final ConfigurationProperties properties) throws Exception {
		// Unnecessary
	}

	/**
	 * This send is actually assync, so this have to be taking into account when
	 * extending this class' functionality.
	 */
	@Override
	protected KafkaMessage sendImpl(final KafkaMessage message) throws Exception {
		client.send(new ProducerRecord<>(message.getDestination(), message.getBody()));

		client.flush(); // Required to send, but adds high overhead, might change this behaviour later

		return new KafkaMessage(new byte[0], message.getDestination(), message.getProperties());
	}

}
