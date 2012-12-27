package it.unibo.middleware.receiver.remote_implementors.tcp;


import it.unibo.exceptions.InvalidConnectionString;
import it.unibo.logging.LoggingFacility;
import it.unibo.middleware.receiver.remote_implementors.RemoteReceiverImplementor;

import java.io.IOException;
import java.net.Socket;

public class ReceiverTcpActive extends RemoteReceiverImplementor {

  private String address;
  private int port;
  private Socket socket;
  private String lastTokenData;

  public ReceiverTcpActive(String address, int port, LoggingFacility loggingFacility)
      throws IOException {
    super(loggingFacility);
    // TODO: Implement
    // this.address = address;
    // this.port = port;
    // this.socket = new Socket(InetAddress.getByName(this.address), this.port);
  }

  @Override
  public void receive() throws InterruptedException, IOException, InvalidConnectionString {
    // TODO: Implement
    // BufferedReader reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
    // final String data = reader.readLine();
    // this.lastTokenData = data;
  }

  @Override
  public String getLastTokenData() {
    return this.lastTokenData;
  }

  @Override
  public void releaseResources() throws IOException {
    this.socket.close();
  }
}
