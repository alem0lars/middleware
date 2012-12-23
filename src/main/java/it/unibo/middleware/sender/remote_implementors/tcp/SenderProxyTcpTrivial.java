package it.unibo.middleware.sender.remote_implementors.tcp;

import it.unibo.exceptions.InvalidConnectionString;
import it.unibo.logging.LoggingFacility;
import it.unibo.middleware.RemoteConnectorType;
import it.unibo.middleware.sender.remote_implementors.RemoteSenderImplementor;
import it.unibo.middleware.sender.remote_implementors.RemoteSenderImplementorProxy;

import java.io.IOException;

public class SenderProxyTcpTrivial extends RemoteSenderImplementorProxy {

  // { Construction

  public SenderProxyTcpTrivial(RemoteConnectorType senderType,
                               String connectionString,
                               LoggingFacility loggingFacility) {
    super(senderType, connectionString, loggingFacility);
  }

  // }

  /**
   * @see RemoteSenderImplementorProxy#createSenderImplementor()
   */
  @Override
  protected RemoteSenderImplementor createSenderImplementor()
      throws InvalidConnectionString, IOException, InterruptedException {
    RemoteSenderImplementor sender = null;
    int port;

    switch (this.senderType) {
      case PASSIVE_CONNECTOR:
        try {
          port = Integer.parseInt(this.connectionString);
        } catch (Exception exc) {
          throw new InvalidConnectionString();
        }

        sender = new SenderTcpPassive(port, this.loggingFacility);
        break;
      case ACTIVE_CONNECTOR:
        String address = this.connectionString.split(":")[0];
        try {
          port = Integer.parseInt(this.connectionString.split(":")[1]);
        } catch (Exception exc) {
          throw new InvalidConnectionString();
        }

        sender = new SenderTcpActive(address, port, this.loggingFacility);
        break;
    }
    return sender;
  }

  /**
   * @see RemoteSenderImplementorProxy#isSendDenied()
   */
  @Override
  protected boolean isSendDenied() {
    // No permission control policies.
    return true;
  }

  /**
   * @see RemoteSenderImplementorProxy#applyQosPolicies()
   */
  @Override
  protected void applyQosPolicies() {
    // No policies specified.
  }

  /**
   * @see RemoteSenderImplementorProxy#cleanupSend()
   */
  @Override
  protected void cleanupSend() {
    // There is no cleanup to do.
  }
}
