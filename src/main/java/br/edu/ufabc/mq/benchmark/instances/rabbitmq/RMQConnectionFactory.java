package br.edu.ufabc.mq.benchmark.instances.rabbitmq;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import br.edu.ufabc.mq.connection.ConnectionFactoryWrapper;
import br.edu.ufabc.mq.exception.MessageQueueException;
import br.edu.ufabc.mq.utils.PropertyUtils;

public class RMQConnectionFactory extends ConnectionFactoryWrapper<ConnectionFactory, Connection, RMQClient> {

	public RMQConnectionFactory(final Map<String, Object> properties) throws MessageQueueException {
		super(properties);
	}

	@Override
	protected ConnectionFactory getFactoryImpl() {
		final ConnectionFactory factory = new ConnectionFactory();
		factory.setHost((String) properties.get(PropertyUtils.HOST));
		factory.setPort((int) properties.get(PropertyUtils.PORT));
		return factory;
	}

	@Override
	protected Connection getConnectionImpl() throws IOException, TimeoutException {
		return factory.newConnection();
	}

	@SuppressWarnings("unchecked")
	@Override
	protected RMQClient getNewClientImpl(final Connection connection, final Map<String, Object> clientProperties) throws IOException {
		final Channel channel = connection.createChannel();
		channel.queueDeclare(
				(String) clientProperties.getOrDefault(RMQClient.QUEUE_PROPERTY, ""),
				(Boolean) clientProperties.getOrDefault(RMQClient.DURABLE_PROPERTY, true),
				(Boolean) clientProperties.getOrDefault(RMQClient.EXCLUSIVE_PROPERTY, false),
				(Boolean) clientProperties.getOrDefault(RMQClient.AUTO_DELETE_PROPERTY, false),
				(Map<String, Object>) clientProperties.getOrDefault(RMQClient.ARGUMENTS_PROPERTY, null));
		return new RMQClient(channel);
	}

}
