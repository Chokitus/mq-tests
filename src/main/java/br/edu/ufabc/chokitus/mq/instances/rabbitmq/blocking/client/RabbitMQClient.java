package br.edu.ufabc.chokitus.mq.instances.rabbitmq.blocking.client;

import com.rabbitmq.client.Channel;

public interface RabbitMQClient {
	Channel getChannel();
}
