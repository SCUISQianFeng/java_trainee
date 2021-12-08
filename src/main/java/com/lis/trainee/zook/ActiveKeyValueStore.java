package com.lis.trainee.zook;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Stat;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * ActiveKeyValueStore
 *
 * @author Lis
 * @version 1.0
 * @date 2021-12-8 15:46
 */
public class ActiveKeyValueStore  extends ConnectionWatcher{

	private static final Charset CHARSET = Charset.forName("UTF-8");
	public void write(String path, String value) throws InterruptedException, KeeperException {
		Stat stat = zk.exists(path, false);
		if (stat == null) {
			// 可以写入数据
			zk.create(path, value.getBytes(CHARSET), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		} else {
			zk.setData(path, value.getBytes(CHARSET), -1);
		}
	}

	public String read(String path, Watcher watcher) throws InterruptedException, KeeperException {
		byte[] data = zk.getData(path, watcher, null/*data*/);
		return new String(data, CHARSET);
	}
}
