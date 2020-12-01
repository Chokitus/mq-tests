# README em Construção


## Credits for docker images and scripts
### Apache ActiveMQ Artemis
- [Victor Romero](https://github.com/vromero)

Found on https://github.com/vromero/activemq-artemis-docker, simple adjustment to docker-compose by me.

---
### Apache Kafka
- [Wurstmeister](https://github.com/wurstmeister)

Found on https://github.com/wurstmeister/kafka-docker, after adapting `docker-compose`.

---

### RocketMQ
Adapted from [medium](https://medium.com/hepsiburadatech/implementing-highly-available-rabbitmq-cluster-on-docker-swarm-using-consul-based-discovery-45c4e7919634), using original image integrated with haproxy.

---

### Apache Pulsar

- [Apache](https://github.com/apache)

Directly from [Apache Pulsar's main repository](https://github.com/apache/pulsar), adding network and exposing two ports.

---

### Apache RocketMQ

- [Apache](https://github.com/apache)
  
Directly from [Apache RocketMQ's Docker repository](https://github.com/apache/rocketmq-docker), but requiring several changes on Dockerfile due to undocumented errors.