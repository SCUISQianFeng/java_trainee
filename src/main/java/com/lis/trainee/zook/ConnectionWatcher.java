package com.lis.trainee.zook;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * ConnetionWatcher
 *
 * @author Lis
 * @version 1.0
 * @date 2021-12-8 15:21
 */
public class ConnectionWatcher implements Watcher {

  private static final int SESSION_TIMEOUT = 5000;

  protected ZooKeeper zk;

  private CountDownLatch connectedSignal = new CountDownLatch(1);

  public void connect(String hosts) throws IOException, InterruptedException {
    zk = new ZooKeeper(hosts, SESSION_TIMEOUT, this);
    connectedSignal.await();
  }

  public void close() throws InterruptedException {
    zk.close();
  }

  @Override
  public void process(WatchedEvent watchedEvent) {
    if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
      connectedSignal.countDown();
    }
  }


}
