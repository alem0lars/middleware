package it.unibo.interfaces;

import it.unibo.exceptions.InvalidConnectionString;

import java.io.IOException;

public interface ISender {

  /**
   * Publish the provided token
   *
   * @param source The source of the token
   * @param token  The token to be published
   */
  public abstract void publish(IObservableEndpoint source, IToken token)
      throws InterruptedException, IOException, InvalidConnectionString;
}
