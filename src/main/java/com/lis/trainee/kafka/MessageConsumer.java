package com.lis.trainee.kafka;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Arrays;
import java.util.Collection;
import java.util.Properties;

/**
 * MessageConsumer
 *
 * @author Lis
 * @version 1.0
 * @date 2021-12-8 17:20
 */
public class MessageConsumer {
	private static final String TOPIC = "javatopic";
	private static final String BROKER_LIST = "192.168.137.128:9092";
	private static KafkaConsumer<String, String> kafkaConsumer = null;

	static {
		Properties properties = initConfit();
		kafkaConsumer = new KafkaConsumer<String, String>(properties);
		// 订阅主题
		kafkaConsumer.subscribe(Arrays.asList(TOPIC), new ConsumerRebalanceListener() {
			@Override
			public void onPartitionsRevoked(Collection<TopicPartition> collection) {
				// 而在发生异常的时候，手动提交 kafkaConsumer.commitSync()
				kafkaConsumer.commitSync();
			}

			@Override
			public void onPartitionsAssigned(Collection<TopicPartition> collection) {

			}
		});
	}

	private static Properties initConfit() {
		Properties properties = new Properties();
		properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BROKER_LIST);
		properties.put(ConsumerConfig.GROUP_ID_CONFIG, "test");
		properties.put(ConsumerConfig.CLIENT_ID_CONFIG, "test");
		properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		return properties;
	}

	public static void main(String[] args) {
//		try {
//			while (true) {
//				// 拉取消息
//				ConsumerRecords<String, String> records = kafkaConsumer.poll(100);
//				for (ConsumerRecord record : records) {
//					try {
//						System.out.println(record.value());
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			kafkaConsumer.close();
//		}
		try{
			while(true){
				ConsumerRecords<String,String> records =
						kafkaConsumer.poll(100);
				for(ConsumerRecord record:records){
					try{
						System.out.println(record.value());
					}catch(Exception e){
						e.printStackTrace();
					}
				}
				//正常消费时，异步提交kafkaConsumer.commitAsync()。即使偶尔失败，也会被后续成功的提交覆盖掉
				kafkaConsumer.commitAsync();
			}

		}catch(Exception e){
			e.printStackTrace();
		}finally {
			try{
				kafkaConsumer.commitSync();
			}finally {
				kafkaConsumer.close();
			}
		}
	}
}
