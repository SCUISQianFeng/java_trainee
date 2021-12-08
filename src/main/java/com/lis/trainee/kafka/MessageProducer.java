package com.lis.trainee.kafka;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * MessageProducer
 *
 * @author Lis
 * @version 1.0
 * @date 2021-12-8 17:03
 */
public class MessageProducer {
	private static final String TOPIC = "javatopic";
	private static final String BROKER_LIST = "192.168.137.128:9092";
	private static KafkaProducer<String, String> producer = null;

	static {
		Properties configs = initConfig();
		producer = new KafkaProducer<String, String>(configs);
	}

	private static Properties initConfig() {
		Properties properties = new Properties();
		properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BROKER_LIST);
		//*   0，不等服务器响应，直接返回发送成功。速度最快，但是丢了消息是无法知道的
		//*   1，leader副本收到消息后返回成功
		//*   all，所有参与的副本都复制完成后返回成功。这样最安全，但是延迟最高。
		properties.put(ProducerConfig.ACKS_CONFIG, "all");
		properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		return properties;
	}

	public static void main(String[] args) {
		try {
			String message = "hello world";
			ProducerRecord<String, String> record = new ProducerRecord<String, String>(TOPIC, message);
			producer.send(record, new Callback() {
				@Override
				public void onCompletion(RecordMetadata recordMetadata, Exception e) {
					if (null == e) {
						System.out.println("Perfect!");
					}
					if (null != recordMetadata) {
						System.out.print("offset:" + recordMetadata.offset() + ";partition:" + recordMetadata.partition());
					}
				}
			}).get();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			producer.close();
		}
	}
}
