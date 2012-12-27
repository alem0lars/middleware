package it.unibo.middleware.sender.remote_implementors.tcp;

import it.unibo.logging.LoggingFacility;
import it.unibo.middleware.sender.remote_implementors.RemoteSenderImplementor;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;


public class SenderTcpPassive extends RemoteSenderImplementor {

  private int port;
  private ConcurrentLinkedQueue<String> dataQueue;
  private Set<Socket> sockets;
  private ServerSocket connectionSocket;

  private Thread acceptorThread;
  private Thread senderThread;
  private Semaphore socketsSemaphore;

  // { Construction

  public SenderTcpPassive(int port, LoggingFacility loggingFacility)
      throws InterruptedException, IOException {
    super(loggingFacility);
    this.port = port;
    this.dataQueue = new ConcurrentLinkedQueue<>();
    this.connectionSocket = new ServerSocket(this.port);
    this.sockets = new HashSet<>();
    this.socketsSemaphore = new Semaphore(1);

    this.acceptorThread = new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          while (!Thread.interrupted()) {
            try {
              socketsSemaphore.acquire();
              sockets.add(connectionSocket.accept());
              socketsSemaphore.release();
            } catch (InterruptedException ignored) {
            }
          }
        } catch (IOException ignored) {
        }
      }
    });

    this.senderThread = new Thread(new Runnable() {
      @Override
      public void run() {
        while (!Thread.interrupted()) {
          String data = dataQueue.remove();
          if (data != null) {
            try {
              socketsSemaphore.acquire();
              for (Socket socket : sockets) {
                try {
                  OutputStreamWriter outputStream = new OutputStreamWriter(socket.getOutputStream());
                  outputStream.write(data);
                } catch (IOException exc1) {
                  try {
                    socket.getOutputStream().close();
                    socket.close();
                  } catch (IOException ignored) {
                  }
                }
              }
              socketsSemaphore.release();
            } catch (InterruptedException ignored) {
            }
          }
        }
      }
    });

    this.acceptorThread.start();
    this.senderThread.start();
  }

  // }

  /**
   * @see RemoteSenderImplementor#send(String)
   */
  @Override
  public void send(String tokenData) {
    try {
      this.socketsSemaphore.acquire();
      this.dataQueue.add(String.format("%s\n", tokenData));
      this.socketsSemaphore.release();
    } catch (InterruptedException ignored) {
    }
  }

  /**
   * @see RemoteSenderImplementor#releaseResources()
   */
  @Override
  public void releaseResources() throws IOException {
    this.acceptorThread.interrupt();
    this.senderThread.interrupt();
    this.connectionSocket.close();
    for (Socket socket : this.sockets) {
      socket.close();
    }
  }
}
