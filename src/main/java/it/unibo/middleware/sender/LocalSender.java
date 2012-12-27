package it.unibo.middleware.sender;

import it.unibo.interfaces.ILocalReceiver;
import it.unibo.interfaces.ILocalSender;
import it.unibo.interfaces.IToken;
import it.unibo.logging.LoggingFacility;

public class LocalSender extends Sender implements ILocalSender {

  protected ILocalReceiver receiver;

  // { Construction

  /**
   * Create a receiver that sends tokens to the provided receiver
   *
   * @param receiver The local receiver
   */
  public LocalSender(ILocalReceiver receiver, LoggingFacility loggingFacility) {
    super(loggingFacility);
    this.receiver = receiver;
  }

  // }

  // { Sender behaviour

  /**
   * @see Sender#send(IToken)
   */
  @Override
  protected void send(IToken token) {
    this.receiver.add(token);
  }

  // }

}
