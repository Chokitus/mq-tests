package br.edu.ufabc.chokitus.mq.instances.rabbitmq.blocking.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import java.io.IOException;

import br.edu.ufabc.chokitus.mq.benchmark.ConfigurationProperties;
import br.edu.ufabc.chokitus.mq.instances.rabbitmq.blocking.client.RabbitMQProducer;
import br.edu.ufabc.chokitus.mq.instances.rabbitmq.blocking.client.RabbitMQReceiver;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import br.edu.ufabc.chokitus.mq.factory.AbstractClientFactory;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
public class RabbitMQClientFactory extends AbstractClientFactory<RabbitMQReceiver, RabbitMQProducer> {

	private final ConnectionFactory factory;

	private final List<Connection> connections = new ArrayList<>();

	/**
	 *
	 * @throws TimeoutException
	 * @throws IOException
	 */
	public RabbitMQClientFactory(final ConfigurationProperties clientFactoryProperties, final ConnectionFactory factory) throws IOException, TimeoutException {
		super(clientFactoryProperties);
		this.factory = factory;
		connections.add(getConnection());
		connections.add(getConnection());
	}

	@Override
	protected RabbitMQReceiver createConsumerImpl(final ConfigurationProperties receiverProperties) throws Exception {
		final Channel channel = connections.get(0).createChannel();
		return new RabbitMQReceiver(channel, receiverProperties);
	}

	@Override
	protected RabbitMQProducer createProducerImpl(final ConfigurationProperties producerProperties) throws Exception {
		final Channel channel = connections.get(1).createChannel();
		return new RabbitMQProducer(channel, producerProperties);
	}

	private Connection getConnection() throws IOException, TimeoutException {
		return factory.newConnection();
	}

	@Override
	protected void closeImpl() throws Exception {
		for (final Connection connection : connections) {
			if(connection.isOpen()) {
				connection.close();
			}
		}
	}

}
