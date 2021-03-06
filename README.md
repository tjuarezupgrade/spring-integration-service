# spring-integration-service
Spectrum consumer - Kafka publisher PoC

## Run

In order to test this service, firs you need to run kafka and rabbitMQ. Use the following `docker-compose` file and run `docker-compose up`:

```yaml
version: '2'

networks:
  kafka-connect-network:
    driver: bridge

services:
  zookeeper:
    image: confluentinc/cp-zookeeper:5.3.2
    networks: 
      - kafka-connect-network
    ports:
      - '31000:31000'
    environment:
      ZOOKEEPER_CLIENT_PORT: 2181
      ZOOKEEPER_TICK_TIME: 2000
      KAFKA_JMX_HOSTNAME: "localhost"
      KAFKA_JMX_PORT: 31000

  kafka:
    image: confluentinc/cp-enterprise-kafka:5.3.2
    networks: 
      - kafka-connect-network
    ports:
      - '9092:9092'
      - '31001:31001'
    depends_on:
      - zookeeper
    environment:
      KAFKA_BROKER_ID: 1
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
      KAFKA_LISTENER_SECURITY_PROTOCOL_MAP: PLAINTEXT:PLAINTEXT,PLAINTEXT_HOST:PLAINTEXT
      KAFKA_INTER_BROKER_LISTENER_NAME: PLAINTEXT
      KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://kafka:29092,PLAINTEXT_HOST://localhost:9092
      KAFKA_AUTO_CREATE_TOPICS_ENABLE: "true"
      KAFKA_METRIC_REPORTERS: io.confluent.metrics.reporter.ConfluentMetricsReporter
      KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1
      KAFKA_GROUP_INITIAL_REBALANCE_DELAY_MS: 100
      CONFLUENT_METRICS_REPORTER_BOOTSTRAP_SERVERS: kafka:29092
      CONFLUENT_METRICS_REPORTER_ZOOKEEPER_CONNECT: zookeeper:2181
      CONFLUENT_METRICS_REPORTER_TOPIC_REPLICAS: 1
      CONFLUENT_METRICS_ENABLE: 'false'
      CONFLUENT_SUPPORT_CUSTOMER_ID: 'anonymous'
      KAFKA_JMX_HOSTNAME: "localhost"
      KAFKA_JMX_PORT: 31001

  schema-registry:
    image: confluentinc/cp-schema-registry:5.3.2
    depends_on:
      - zookeeper
      - kafka
    networks: 
      - kafka-connect-network
    ports:
      - '8081:8081'
      - '31002:31002'
    environment:
      SCHEMA_REGISTRY_HOST_NAME: schema-registry
      SCHEMA_REGISTRY_KAFKASTORE_CONNECTION_URL: zookeeper:2181
      SCHEMA_REGISTRY_JMX_HOSTNAME: "localhost"
      SCHEMA_REGISTRY_JMX_PORT: 31002

  rabbitmq:
    image: rabbitmq
    environment:
      RABBITMQ_DEFAULT_USER: guest
      RABBITMQ_DEFAULT_PASS: guest
      RABBITMQ_DEFAULT_VHOST: "/"
    networks: 
      - kafka-connect-network
    ports:
      - '15672:15672'
      - '5672:5672'
```

To check the output (kafka messages in `spectrum-queue` topic), dowload the confluent utilities and run:

```
./kafka-console-consumer --bootstrap-server localhost:9092 \
                         --topic spectrum-topic \
                         --from-beginning \ 
                         --property print.key=true
```
