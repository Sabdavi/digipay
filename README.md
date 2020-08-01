# digipay

These project consists of 3 services including: card-service,payment-service and sms-service. there
are also 3 external services naming NotificationServices,MellatPaymentService and SamanPaymentService
which theri api's are callde with internal services.
For gaining maximum isolation, each service has own its database. there are totally 4 databases in
the project:

cardManagement:
CREATE TABLE `card` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `bank_name` varchar(45) DEFAULT NULL,
  `owner_name` varchar(45) DEFAULT NULL,
  `card_number` varchar(45) DEFAULT NULL,
  `expiration_date` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

paymentService:
CREATE TABLE `transaction` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `source` bigint(20) NOT NULL,
  `destination` varchar(45) NOT NULL,
  `amount` decimal(10,0) NOT NULL,
  `date` date DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL,
  `description` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `source` (`source`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=latin1;

smsService:
CREATE TABLE `sms` (
  `id` int(11) NOT NULL,
  `message` varchar(45) DEFAULT NULL,
  `target` varchar(45) DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

In addition to these databases, we have eventuate schema. In these project I used Eventuate.io
(https://eventuate.io/) framework for communicating between microservices that provides several useful
libraries for messaging and transaction management. Eventuate CDC is a part of framework that ensures transactional
messaging between a producer and consumer and requires eventuate schema with following tables:

create table events (
event_id varchar(200) PRIMARY KEY,
event_type varchar(200),
event_data varchar(1000) NOT NULL,
entity_type VARCHAR(1000) NOT NULL,
entity_id VARCHAR(200) NOT NULL,
triggering_event VARCHAR(1000),
metadata VARCHAR(1000),
published TINYINT DEFAULT 0
);

CREATE INDEX events_idx ON events(entity_type, entity_id, event_id);
CREATE INDEX events_published_idx ON events(published, event_id);
create table entities (
entity_type VARCHAR(200),
entity_id VARCHAR(200),
entity_version VARCHAR(1000) NOT NULL,
PRIMARY KEY(entity_type, entity_id)
);

CREATE INDEX entities_idx ON events(entity_type, entity_id);
create table snapshots (
entity_type VARCHAR(200),
entity_id VARCHAR(200),
entity_version VARCHAR(200),
snapshot_type VARCHAR(1000) NOT NULL,
snapshot_json VARCHAR(1000) NOT NULL,
triggering_events VARCHAR(1000),
PRIMARY KEY(entity_type, entity_id, entity_version)
);

CREATE TABLE message (
id varchar(200) NOT NULL,
destination varchar(1000) NOT NULL,
headers varchar(1000) NOT NULL,
payload varchar(1000) NOT NULL,
published smallint(6) DEFAULT '0',
creation_time bigint(20) DEFAULT NULL,
PRIMARY KEY (id),
KEY message_published_idx (published,id)
);

create table received_messages (
consumer_id varchar(200) NOT NULL,
message_id varchar(200) NOT NULL,
creation_time bigint(20) DEFAULT NULL,
PRIMARY KEY (consumer_id, message_id)
);

create table cdc_monitoring (
reader_id VARCHAR(200) PRIMARY KEY,
last_time BIGINT
);

CREATE TABLE offset_store(client_name VARCHAR(255) NOT NULL PRIMARY KEY, serialized_offset VARCHAR(255));
ALTER TABLE received_messages MODIFY creation_time BIGINT;

Steps for runnig the project:
#1.first run Zookeeper: zookeeper-server-start.sh /opt/kafka_2.12-2.5.0/config/zookeeper.properties 

#2.run apache Kafka: kafka-server-start.sh config/server.properties

#3.run your database service (in my case MySql): mysqld_safe --user=mysql 

#4.run CDC service jar: java -jar eventuate-tram-cdc-mysql-service-0.21.3.RELEASE.jar --spring-config-location=./application.properties

here is a properties file:

spring.datasource.url=jdbc:mysql://localhost:3306/eventuate?autoReconnect=true&useSSL=false
spring.datasource.username=root
spring.datasource.password=saeid
spring.datasource.driver.class.name=com.mysql.jdbc.Driver
eventuatelocal.cdc.reader.name = reader
eventuatelocal.cdc.leadership.lock.path=/eventuatelocal/cdc/leader/1
eventuate.database.schema=eventuate
eventuatelocal.cdc.source.table.name=message
eventuatelocal.kafka.bootstrap.servers=localhost:9092
eventuatelocal.zookeeper.connection.string=localhost:2181
eventuate.cdc.type=EventuateTram
spring.profiles.active=EventuatePolling

 



 
