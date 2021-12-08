package com.lis.trainee.zook;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * CreateGroup
 *
 * @author Lis
 * @version 1.0
 * @date 2021-12-8 14:51
 */
public class CreateGroup implements Watcher {

  private static final int SESSION_TIMEOUT = 5000;
  private ZooKeeper zk;
  private CountDownLatch connectedSignal = new CountDownLatch(1);

  public void connect(String hosts) throws IOException, InterruptedException {
    zk = new ZooKeeper(hosts, SESSION_TIMEOUT, this);
    connectedSignal.await();
  }

  @Override
  public void process(WatchedEvent watchedEvent) {
    if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
      connectedSignal.countDown();
    }
  }

  public void create(String groupName) throws InterruptedException, KeeperException {
    String path = "/" + groupName;
    String createPath =
        zk.create(path, null /*data*/, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    System.out.println("Created " + createPath);
  }

  public void close() throws InterruptedException {
    zk.close();
  }

  public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
    CreateGroup createGroup = new CreateGroup();
    createGroup.connect("192.168.137.128");
    createGroup.create("zoo");
    createGroup.close();
  }
}
