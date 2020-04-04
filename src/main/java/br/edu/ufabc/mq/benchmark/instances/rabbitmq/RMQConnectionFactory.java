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
		return factory;
	}

	@Override
	protected Connection getConnectionImpl() throws IOException, TimeoutException {
		return factory.newConnection();
	}

	@Override
	protected RMQClient getNewClientImpl(final Connection connection) throws IOException {
		final Channel channel = connection.createChannel();
		return new RMQClient(channel);
	}

}
