package it.unibo.middleware.sender.remote_implementors;

import it.unibo.exceptions.InvalidConnectionString;
import it.unibo.interfaces.IRemoteSenderImplementor;
import it.unibo.logging.LoggingFacility;
import it.unibo.middleware.RemoteConnectorType;

import java.io.IOException;
import java.util.logging.Level;

/**
 * Virtual, Protection and Smart proxy for a remote sender
 * (see Proxy DP types)
 */
public abstract class RemoteSenderImplementorProxy implements
    IRemoteSenderImplementor {

  protected RemoteSenderImplementor sender;
  protected final RemoteConnectorType senderType;
  protected final String connectionString;
  protected final LoggingFacility loggingFacility;

  // { Construction

  /**
   * Create a proxy for a sender implementor
   */
  public RemoteSenderImplementorProxy(RemoteConnectorType senderType, String connectionString, LoggingFacility loggingFacility) {
    this.senderType = senderType;
    this.connectionString = connectionString;
    this.loggingFacility = loggingFacility;
    this.sender = null;
  }

  // }

  /**
   * @see IRemoteSenderImplementor#send(String)
   */
  @Override
  public void send(String tokenData)
      throws IOException, InvalidConnectionString, InterruptedException {
    if (this.sender == null) {
      this.sender = this.createSenderImplementor();
    }
    if (!this.isSendDenied()) {
      this.applyQosPolicies();
      this.sender.send(tokenData);
      this.cleanupSend();
    } else {
      this.loggingFacility.log(Level.INFO, String.format("Send denied for the token: %s", tokenData));
    }
  }

  /**
   * @see IRemoteSenderImplementor#releaseResources()
   */
  @Override
  public void releaseResources() throws IOException {
    if (!(this.sender == null)) {
      this.loggingFacility.log(Level.INFO, "The resources were already released.");
      this.sender.releaseResources();
    }
    this.sender = null;
  }

  /**
   * @return the created sender
   */
  protected abstract RemoteSenderImplementor createSenderImplementor()
      throws InvalidConnectionString, IOException, InterruptedException;

  /**
   * @return the send has been denied
   * Normally the send operation isn't denied, but if we want to implement
   * some permissions control we can:
   * <ul>
   *   <li>
   *     return false if there aren't sufficient permissions for the send
   *     operation
   *   </li>
   *   <li>
   *     otherwise return true
   *   </li>
   * </ul>
   */
  protected abstract boolean isSendDenied();

  /**
   * The concrete proxy implements here its quality of service policies
   */
  protected abstract void applyQosPolicies();

  /**
   * Cleanup between successive send operations.
   * This cleanup is always performed after a non-denied send operation to
   * perform some cleanup.
   */
  protected abstract void cleanupSend();
}
