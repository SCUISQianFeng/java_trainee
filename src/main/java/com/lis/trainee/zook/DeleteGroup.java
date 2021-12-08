package com.lis.trainee.zook;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;

import java.util.List;

/**
 * DeleteGroup
 *
 * @author Lis
 * @version 1.0
 * @date 2021-12-8 15:42
 */
public class DeleteGroup extends ConnectionWatcher {

	public void delete(String groupName) throws KeeperException,
			InterruptedException {
		String path = "/" + groupName;

		try {
			List<String> children = zk.getChildren(path, false);
			for (String child : children) {
				zk.delete(path + "/" + child, -1);
			}
			zk.delete(path, -1);
		} catch (KeeperException.NoNodeException e) {
			System.out.printf("Group %s does not exist\n", groupName);
			System.exit(1);
		}
	}

	public static void main(String[] args) throws Exception {
		DeleteGroup deleteGroup = new DeleteGroup();
		deleteGroup.connect("192.168.137.128");
		deleteGroup.delete("zoo");
		deleteGroup.close();
	}
}
