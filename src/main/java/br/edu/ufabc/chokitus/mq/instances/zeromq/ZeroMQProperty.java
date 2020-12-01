package br.edu.ufabc.chokitus.mq.instances.zeromq;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ZeroMQProperty {
	SOCKET_URL("socket_url"), SOCKET_TYPE("socket_type");

	private final String value;
}
