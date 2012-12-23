package it.unibo.middleware.receiver;

import it.unibo.interfaces.ILocalReceiver;
import it.unibo.logging.LoggingFacility;

public abstract class LocalReceiver extends Receiver implements ILocalReceiver {

  // { Construction

  /**
   * Create a new local receiver
   */
  public LocalReceiver(LoggingFacility loggingFacility) {
    super(loggingFacility);
  }

  // }

  // { ILocalReceiver behaviour

  /**
   * @see ILocalReceiver#receive()
   */
  @Override
  public void receive() {
    this.notifySubscribers(this.getLastToken());
  }

  // }

}
