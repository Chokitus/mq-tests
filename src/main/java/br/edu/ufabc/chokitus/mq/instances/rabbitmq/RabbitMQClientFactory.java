package br.edu.ufabc.chokitus.mq.instances.rabbitmq;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import br.edu.ufabc.chokitus.mq.factory.AbstractClientFactory;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
public class RabbitMQClientFactory extends AbstractClientFactory<RabbitMQConsumer, RabbitMQProducer> {

	private final ConnectionFactory factory;

	private final List<Connection> connections = new ArrayList<>();

	/**
	 *
	 * @throws TimeoutException
	 * @throws IOException
	 */
	public RabbitMQClientFactory(final Map<String, Object> clientFactoryProperties, final ConnectionFactory factory) throws IOException, TimeoutException {
		super(clientFactoryProperties);
		this.factory = factory;
		connections.add(getConnection());
	}

	@Override
	protected RabbitMQConsumer createConsumerImpl(final Map<String, Object> receiverProperties) throws Exception {
		final Channel channel = connections.get(0).createChannel();
		return new RabbitMQConsumer(channel, receiverProperties);
	}

	@Override
	protected RabbitMQProducer createProducerImpl(final Map<String, Object> producerProperties) throws Exception {
		final Channel channel = connections.get(0).createChannel();
		return new RabbitMQProducer(channel, producerProperties);
	}

	private Connection getConnection() throws IOException, TimeoutException {
		if(connections.isEmpty()) {
			connections.add(factory.newConnection());
		}
		return connections.get(0);
	}

	@Override
	protected void closeImpl() throws Exception {
		for (final Connection connection : connections) {
			connection.close();
		}
	}

}
