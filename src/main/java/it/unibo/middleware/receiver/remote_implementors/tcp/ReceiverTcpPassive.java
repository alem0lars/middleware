package it.unibo.middleware.receiver.remote_implementors.tcp;

import it.unibo.exceptions.InvalidConnectionString;
import it.unibo.logging.LoggingFacility;
import it.unibo.middleware.receiver.remote_implementors.RemoteReceiverImplementor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;


public class ReceiverTcpPassive extends RemoteReceiverImplementor {

  private int port;
  private String lastTokenData;
  private ServerSocket connectionSocket;

  private Thread acceptorThread;
  private Set<Thread> receiversThreads;

  private boolean calledReceive;
  private Semaphore calledReceiveMutex;
  private Semaphore waitForReceive;

  public ReceiverTcpPassive(int port, final LoggingFacility loggingFacility) throws IOException {
    super(loggingFacility);
    this.port = port;
    this.connectionSocket = new ServerSocket(this.port);
    this.receiversThreads = new HashSet<>();
    this.waitForReceive = new Semaphore(0);
    this.calledReceiveMutex = new Semaphore(1);
    this.calledReceive = true; // Initially allow receives

    this.acceptorThread = new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          while (!Thread.interrupted()) {
            final Socket connSocket = connectionSocket.accept();
            loggingFacility.log(Level.INFO, "Accepted a new connection");
            Thread receiverThread = new Thread(new Runnable() {
              @Override
              public void run() {
                try {
                  BufferedReader reader = new BufferedReader(new InputStreamReader(connSocket.getInputStream()));
                  while (!Thread.interrupted()) {
                    try {
                      ReceiverTcpPassive.this.lastTokenData = reader.readLine();
                      try {
                        calledReceiveMutex.acquire();
                        if (calledReceive) {
                          waitForReceive.release();
                          calledReceive = false;
                        }
                        calledReceiveMutex.release();
                      } catch (InterruptedException ignored) {
                      }
                    } catch (IOException ignored) {
                    }
                  }
                } catch (IOException ignored) {
                }
                try {
                  connSocket.close();
                } catch (IOException ignored) {
                }
              }
            });
            receiversThreads.add(receiverThread);
            receiverThread.start();
          }
        } catch (IOException ignored) {
        }
      }
    });

    this.acceptorThread.start();
  }

  /**
   * @see RemoteReceiverImplementor#receive()
   * It blocks until another token has been received
   */
  @Override
  public void receive() throws InterruptedException, IOException, InvalidConnectionString {
    this.waitForReceive.acquire();
    this.calledReceive = true;
    this.loggingFacility.log(Level.INFO, "Received a new token");
  }

  /**
   * @see RemoteReceiverImplementor#getLastTokenData()
   */
  @Override
  public String getLastTokenData() {
    return this.lastTokenData;
  }

  @Override
  public void releaseResources() throws IOException {
    this.connectionSocket.close();
    this.acceptorThread.interrupt();
    for (Thread receiverThread : this.receiversThreads) {
      receiverThread.interrupt();
    }
  }
}
