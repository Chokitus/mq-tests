package br.edu.ufabc.chokitus.mq.instances.rabbitmq;

import com.rabbitmq.client.Channel;

public interface RabbitMQClient {
	Channel getChannel();
}
