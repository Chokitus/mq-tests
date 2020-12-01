package br.edu.ufabc.chokitus.mq.instances.rabbitmq.blocking.client;

import br.edu.ufabc.chokitus.mq.benchmark.ConfigurationProperties;
import br.edu.ufabc.chokitus.mq.instances.rabbitmq.blocking.message.RabbitMQMessage;
import br.edu.ufabc.chokitus.mq.instances.rabbitmq.blocking.config.RabbitMQProperty;
import com.rabbitmq.client.Channel;

import br.edu.ufabc.chokitus.mq.client.AbstractProducer;

public class RabbitMQProducer extends AbstractProducer<Channel, RabbitMQMessage> implements RabbitMQClient {

	public RabbitMQProducer(final Channel client, final ConfigurationProperties properties) {
		super(client, properties);
	}

	@Override
	public void closeImpl() throws Exception {
		client.close();
	}

	@Override
	public void startImpl(final ConfigurationProperties properties) throws Exception {
		// Não necessário
	}

	@Override
	protected RabbitMQMessage sendImpl(final RabbitMQMessage message) throws Exception {
		client.basicPublish(RabbitMQProperty.DEFAULT_EXCHANGE.getValue(), message.getDestination(), null, message.getBody());
		return new RabbitMQMessage(null, message.getDestination(), null);
	}

	@Override
	public Channel getChannel() {
		return super.getClient();
	}

}
