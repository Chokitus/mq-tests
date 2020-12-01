package br.edu.ufabc.chokitus.mq.instances.rabbitmq.reactive.message;

import br.edu.ufabc.chokitus.reactive.mq.message.ReactiveMessage;
import com.rabbitmq.client.Delivery;
import reactor.core.publisher.Mono;
import reactor.rabbitmq.AcknowledgableDelivery;

public class ReactiveRMQMessage extends ReactiveMessage<Delivery> {
	public ReactiveRMQMessage(final Delivery message) {
		super(message);
	}

	public ReactiveRMQMessage(final Delivery message,
	                          final Mono<Void> ack) {
		super(message, ack);
	}

	public static ReactiveRMQMessage autoAck(final Delivery message) {
		return new ReactiveRMQMessage(message);
	}

	public static ReactiveRMQMessage manualAck(final AcknowledgableDelivery message) {
		return new ReactiveRMQMessage(message, Mono.fromRunnable(message::ack));
	}

	@Override
	public byte[] getBytes() {
		return message.getBody();
	}
}
