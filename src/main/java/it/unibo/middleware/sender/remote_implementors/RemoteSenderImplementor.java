package it.unibo.middleware.sender.remote_implementors;

import it.unibo.interfaces.IRemoteSenderImplementor;
import it.unibo.logging.LoggingFacility;

public abstract class RemoteSenderImplementor implements
    IRemoteSenderImplementor {

  protected final LoggingFacility loggingFacility;

  // { Construction

  public RemoteSenderImplementor(LoggingFacility loggingFacility) {
    this.loggingFacility = loggingFacility;
  }

  // }

}
