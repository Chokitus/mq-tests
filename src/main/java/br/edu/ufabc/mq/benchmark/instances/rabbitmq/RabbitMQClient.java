package br.edu.ufabc.mq.benchmark.instances.rabbitmq;

import com.rabbitmq.client.Channel;

public interface RabbitMQClient {
	Channel getChannel();
}
