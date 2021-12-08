package com.lis.trainee.zook;

import org.apache.zookeeper.KeeperException;

import java.util.List;

/**
 * ListGroup
 *
 * @author Lis
 * @version 1.0
 * @date 2021-12-8 15:28
 */
public class ListGroup extends ConnectionWatcher {
  public void list(String groupName) throws InterruptedException, KeeperException {
    String path = "/" + groupName;
    try {
      List<String> children = zk.getChildren(path, false);
      if (children.isEmpty()) {
        System.out.printf("No members in group %s\n", groupName);
        System.exit(1);
      }
      for (String child : children) {
        System.out.println(child);
      }
    } catch (KeeperException.NoNodeException e) {
      System.out.printf("Group %s does not exist\n", groupName);
      System.exit(1);
    }
  }

  public static void main(String[] args) throws Exception {
    ListGroup listGroup = new ListGroup();
    listGroup.connect("192.168.137.128");
    listGroup.list("zoo");
    listGroup.close();
  }
}
