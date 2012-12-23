package it.unibo.middleware.sender.remote_implementors.tcp;


import it.unibo.logging.LoggingFacility;
import it.unibo.middleware.sender.remote_implementors.RemoteSenderImplementor;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SenderTcpActive extends RemoteSenderImplementor {

  private String address;
  private int port;
  private Socket socket;
  private DataOutputStream writer;

  // { Construction

  public SenderTcpActive(String address, int port, LoggingFacility loggingFacility)
      throws IOException {
    super(loggingFacility);
    // TODO: Implement
    // this.address = address;
    // this.port = port;
    // this.socket = new Socket(InetAddress.getByName(this.address), this.port);
    // this.writer = new DataOutputStream(this.socket.getOutputStream());
  }

  // }

  /**
   * @see RemoteSenderImplementor#send(String)
   */
  @Override
  public void send(String tokenData) throws IOException {
    // TODO: Implement
    // this.writer.writeBytes(String.format("%s\n", tokenData));
    // this.loggingFacility.log(Level.INFO, String.format("Sent the token: %s", tokenData));
  }

  /**
   * @see RemoteSenderImplementor#releaseResources()
   */
  @Override
  public void releaseResources() throws IOException {
    this.socket.close();
  }
}
