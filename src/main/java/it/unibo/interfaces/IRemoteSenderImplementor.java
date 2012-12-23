package it.unibo.interfaces;

import it.unibo.exceptions.InvalidConnectionString;

import java.io.IOException;

public interface IRemoteSenderImplementor {

  /**
   * Implementation for the send operation
   *
   * @param tokenData The data of the token to be sent
   */
  public abstract void send(String tokenData) throws IOException, InvalidConnectionString, InterruptedException;

  /**
   * Release all of the resources used
   */
  public abstract void releaseResources() throws IOException;
}
