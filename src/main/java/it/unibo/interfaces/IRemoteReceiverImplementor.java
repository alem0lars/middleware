package it.unibo.interfaces;

import it.unibo.exceptions.InvalidConnectionString;

import java.io.IOException;

public interface IRemoteReceiverImplementor {

  /**
   * Implementation for the receive operation
   *
   * @see IReceiver#receive()
   */
  public abstract void receive() throws InterruptedException, IOException, InvalidConnectionString;

  /**
   * @return the data of the last token available
   */
  public abstract String getLastTokenData();

  /**
   * Release all of the resources used
   */
  public abstract void releaseResources() throws IOException;
}
