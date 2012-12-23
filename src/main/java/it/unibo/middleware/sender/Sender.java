package it.unibo.middleware.sender;

import it.unibo.exceptions.InvalidConnectionString;
import it.unibo.interfaces.IObservableEndpoint;
import it.unibo.interfaces.ISender;
import it.unibo.interfaces.IToken;
import it.unibo.logging.LoggingFacility;

import java.io.IOException;

public abstract class Sender implements ISender {

  protected final LoggingFacility loggingFacility;

  protected Sender(LoggingFacility loggingFacility) {
    this.loggingFacility = loggingFacility;
  }

  // { ISenderChannel behaviour

  /**
   * @see ISender#publish(IObservableEndpoint, IToken)
   */
  @Override
  public void publish(IObservableEndpoint source, IToken token)
      throws InterruptedException, IOException, InvalidConnectionString {
    this.send(token);
  }

  // }

  /**
   * Send the token
   *
   * @param token The token to be sent
   */
  protected abstract void send(IToken token)
      throws InterruptedException, InvalidConnectionString, IOException;

}
