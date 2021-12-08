package com.lis.trainee.kafka;

import kafka.admin.AdminUtils;
import kafka.utils.ZkUtils;
import org.apache.kafka.common.security.JaasUtils;

import java.util.Properties;

/**
 * TopicProcessor
 *
 * @author Lis
 * @version 1.0
 * @date 2021-12-8 16:37
 */
public class TopicProcessor {
	private static final String ZK_CONNECT = "192.168.137.128:2181";
	private static final int SESSION_TIMEOUT = 30000;
	private static final int CONNECT_OUT = 30000;

	/**
	 * 新建kaifa主题
	 *
	 * @param topicName       订阅主题名称
	 * @param partitionNumber 分区数量
	 * @param replicaNumber   副本数量
	 * @param properties      配置信息
	 */
	public static void createTopic(
			String topicName, int partitionNumber, int replicaNumber, Properties properties) {
		ZkUtils zkUtils = null;
		try {
			zkUtils =
					ZkUtils.apply(ZK_CONNECT, SESSION_TIMEOUT, CONNECT_OUT, JaasUtils.isZkSecurityEnabled());
			if (!AdminUtils.topicExists(zkUtils, topicName)) {
				// 指定zk上没有对应的topic，则新建
				AdminUtils.createTopic(zkUtils, topicName, partitionNumber, replicaNumber, properties);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			zkUtils.close();
		}
	}

	public static void main(String[] args) {
		createTopic("javatopic", 1, 1, new Properties());
	}
}
