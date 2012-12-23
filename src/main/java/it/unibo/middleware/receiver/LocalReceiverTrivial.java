package it.unibo.middleware.receiver;

import it.unibo.interfaces.ILocalReceiver;
import it.unibo.interfaces.IToken;
import it.unibo.logging.LoggingFacility;

public class LocalReceiverTrivial extends LocalReceiver implements ILocalReceiver {

  // { Construction

  /**
   * Create a new local receiver (trivial)
   */
  public LocalReceiverTrivial(LoggingFacility loggingFacility) {
    super(loggingFacility);
  }

  // }

  // { ILocalReceiver behaviour

  /**
   * @see ILocalReceiver#getLastToken()
   */
  @Override
  public IToken getLastToken() {
    return this.pool.get(this.pool.size() - 1);
  }

  /**
   * @see ILocalReceiver#add(IToken)
   */
  @Override
  public void add(IToken token) {
    this.pool.add(token);
    this.notifySubscribers(token);
  }

  // }

}
