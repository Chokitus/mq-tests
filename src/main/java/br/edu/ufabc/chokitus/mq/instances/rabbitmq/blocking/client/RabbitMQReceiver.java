package br.edu.ufabc.chokitus.mq.instances.rabbitmq.blocking.client;

import br.edu.ufabc.chokitus.mq.benchmark.ConfigurationProperties;
import br.edu.ufabc.chokitus.mq.client.AbstractReceiver;
import br.edu.ufabc.chokitus.mq.instances.rabbitmq.blocking.message.RabbitMQMessage;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.GetResponse;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Objects;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class RabbitMQReceiver extends AbstractReceiver<Channel, RabbitMQMessage> implements RabbitMQClient {

	public RabbitMQReceiver(final Channel client, final ConfigurationProperties properties) {
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
	protected RabbitMQMessage instantiateEmptyMessage() {
		return new RabbitMQMessage(null, null, null);
	}

	@Override
	protected RabbitMQMessage consumeImpl(final String property) throws Exception {
		final GetResponse message = client.basicGet(property, true);
		return Objects.isNull(message) ? empty : new RabbitMQMessage(message.getBody(), property, null);
	}

	@Override
	public Channel getChannel() {
		return super.getClient();
	}

}
