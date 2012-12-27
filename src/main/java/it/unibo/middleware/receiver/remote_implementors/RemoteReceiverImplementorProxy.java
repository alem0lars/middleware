package it.unibo.middleware.receiver.remote_implementors;

import it.unibo.exceptions.InvalidConnectionString;
import it.unibo.interfaces.IRemoteReceiverImplementor;
import it.unibo.logging.LoggingFacility;
import it.unibo.middleware.RemoteConnectorType;

import java.io.IOException;
import java.util.logging.Level;

public abstract class RemoteReceiverImplementorProxy implements
    IRemoteReceiverImplementor {

  protected RemoteReceiverImplementor receiver;
  protected final RemoteConnectorType receiverType;
  protected final String connectionString;
  protected final LoggingFacility loggingFacility;

  // { Construction

  /**
   * Create a proxy for a receiver implementor
   */
  public RemoteReceiverImplementorProxy(RemoteConnectorType receiverType,
                                        String connectionString,
                                        LoggingFacility loggingFacility) {
    this.loggingFacility = loggingFacility;
    this.receiverType = receiverType;
    this.connectionString = connectionString;
    this.receiver = null;
  }

  // }

  @Override
  public void receive() throws InterruptedException, IOException, InvalidConnectionString {
    if (this.receiver == null) {
      this.receiver = this.createReceiverImplementor();
    }
    if (!this.isReceiveDenied()) {
      this.applyQosPolicies();
      this.receiver.receive();
      this.cleanupReceive();
    } else {
      this.loggingFacility.log(Level.INFO, "Receive denied");
    }
  }

  @Override
  public String getLastTokenData() {
    if (this.receiver == null) {
      return null;
    }
    return this.receiver.getLastTokenData();
  }

  /**
   * @see IRemoteReceiverImplementor#releaseResources()
   */
  @Override
  public void releaseResources() throws IOException {
    if (!(this.receiver == null)) {
      this.loggingFacility.log(Level.WARNING, "The resources were already released.");
      this.receiver.releaseResources();
    }
    this.receiver = null;
  }

  /**
   * @return the created sender
   */
  protected abstract RemoteReceiverImplementor createReceiverImplementor()
      throws InvalidConnectionString, IOException, InterruptedException;

  /**
   * @return the receive has been denied
   * Normally the receive operation isn't denied, but if we want to implement
   * some permissions control we can:
   * <ul>
   *   <li>
   *     return false if there aren't sufficient permissions for the receive
   *     operation
   *   </li>
   *   <li>
   *     otherwise return true
   *   </li>
   * </ul>
   */
  protected abstract boolean isReceiveDenied();

  /**
   * The concrete proxy implements here its quality of service policies
   */
  protected abstract void applyQosPolicies();

  /**
   * Cleanup between successive receive operations.
   * This cleanup is always performed after a non-denied receive operation to
   * perform some cleanup.
   */
  protected abstract void cleanupReceive();
}
