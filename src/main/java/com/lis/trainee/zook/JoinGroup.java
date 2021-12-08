package com.lis.trainee.zook;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;

/**
 * JoinGroup
 *
 * @author Lis
 * @version 1.0
 * @date 2021-12-8 15:33
 */
public class JoinGroup extends ConnectionWatcher {
  public void join(String groupName, String memberName)
      throws KeeperException, InterruptedException {
    String path = "/" + groupName + "/" + memberName;
    String createPath =
        zk.create(path, null /*data*/, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
    System.out.println("Created " + createPath);
  }

  public static void main(String[] args) throws Exception {
    JoinGroup joinGroup = new JoinGroup();
    joinGroup.connect("192.168.137.128");
    joinGroup.join("zoo", "goat");
    // stay alive until process is killed or thread is interrupted
    Thread.sleep(Long.MAX_VALUE);
  }
}
