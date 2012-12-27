package it.unibo.middleware.receiver.remote_implementors;

import it.unibo.interfaces.IRemoteReceiverImplementor;
import it.unibo.logging.LoggingFacility;

public abstract class RemoteReceiverImplementor implements
    IRemoteReceiverImplementor {

  protected final LoggingFacility loggingFacility;

  // { Construction

  public RemoteReceiverImplementor(LoggingFacility loggingFacility) {
    this.loggingFacility = loggingFacility;
  }

  // }

}
