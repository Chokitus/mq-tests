package br.edu.ufabc.mq.benchmark.instances.rabbitmq;

import java.util.Map;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.GetResponse;

import br.edu.ufabc.mq.client.AbstractConsumer;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class RabbitMQConsumer extends AbstractConsumer<Channel, RabbitMQMessage> implements RabbitMQClient {

	public RabbitMQConsumer(final Channel client, final Map<String, Object> properties) {
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
	protected RabbitMQMessage consumeImpl(final String property) throws Exception {
		final GetResponse message = client.basicGet(property, true);
		return new RabbitMQMessage(message == null ? new byte[0] : message.getBody(), property, null);
	}

	@Override
	public Channel getChannel() {
		return super.getClient();
	}

}
