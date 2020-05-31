package br.edu.ufabc.chokitus.mq.instances.rabbitmq;

import java.util.Map;

import com.rabbitmq.client.Channel;

import br.edu.ufabc.chokitus.mq.client.AbstractProducer;

public class RabbitMQProducer extends AbstractProducer<Channel, RabbitMQMessage> implements RabbitMQClient {

	public RabbitMQProducer(final Channel client, final Map<String, Object> properties) {
		super(client, properties);
	}

	@Override
	public void closeImpl() throws Exception {
		client.close();
	}

	@Override
	public void startImpl(final Map<String, Object> properties) throws Exception {
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
