package it.unibo.middleware.receiver.remote_implementors.tcp;


import it.unibo.exceptions.InvalidConnectionString;
import it.unibo.logging.LoggingFacility;
import it.unibo.middleware.RemoteConnectorType;
import it.unibo.middleware.receiver.remote_implementors.RemoteReceiverImplementor;
import it.unibo.middleware.receiver.remote_implementors.RemoteReceiverImplementorProxy;

import java.io.IOException;

public class ReceiverProxyTcpTrivial extends RemoteReceiverImplementorProxy {

  public ReceiverProxyTcpTrivial(RemoteConnectorType receiverType,
                                 String connectionString,
                                 LoggingFacility loggingFacility) {
    super(receiverType, connectionString, loggingFacility);
  }

  /**
   * @see RemoteReceiverImplementorProxy#createReceiverImplementor()
   */
  @Override
  protected RemoteReceiverImplementor createReceiverImplementor()
      throws InvalidConnectionString, IOException, InterruptedException {
    RemoteReceiverImplementor receiver = null;
    int port;

    switch (this.receiverType) {
      case PASSIVE_CONNECTOR:
        try {
          port = Integer.parseInt(this.connectionString);
        } catch (Exception exc) {
          throw new InvalidConnectionString();
        }

        receiver = new ReceiverTcpPassive(port, this.loggingFacility);
        break;
      case ACTIVE_CONNECTOR:
        String address = this.connectionString.split(":")[0];
        try {
          port = Integer.parseInt(this.connectionString.split(":")[1]);
        } catch (Exception exc) {
          throw new InvalidConnectionString();
        }

        receiver = new ReceiverTcpActive(address, port, this.loggingFacility);
        break;
    }
    return receiver;
  }

  /**
   * @see RemoteReceiverImplementorProxy#isReceiveDenied()
   */
  @Override
  protected boolean isReceiveDenied() {
    // No permission control policies.
    return true;
  }

  /**
   * @see RemoteReceiverImplementorProxy#applyQosPolicies()
   */
  @Override
  protected void applyQosPolicies() {
    // No policies specified.
  }

  /**
   * @see RemoteReceiverImplementorProxy#cleanupReceive()
   */
  @Override
  protected void cleanupReceive() {
    // There is no cleanup to do.
  }

}
