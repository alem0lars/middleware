package it.unibo.middleware.receiver;

import it.unibo.exceptions.InvalidConnectionString;
import it.unibo.interfaces.IRemoteReceiver;
import it.unibo.interfaces.ITranscoder;
import it.unibo.interfaces.IRemoteReceiverImplementor;
import it.unibo.interfaces.IToken;
import it.unibo.logging.LoggingFacility;

import java.io.IOException;
import java.util.logging.Level;

public class RemoteReceiver extends Receiver implements IRemoteReceiver {

  protected final IRemoteReceiverImplementor implementor;
  private Thread receiverThread;
  private ITranscoder transcoder;

  // { Construction

  /**
   * Create a new remote receiver abstraction
   *
   * @param implementor The remote receiver implementor
   */
  public RemoteReceiver(IRemoteReceiverImplementor implementor, ITranscoder transcoder, LoggingFacility loggingFacility) {
    super(loggingFacility);
    this.implementor = implementor;
    this.transcoder = transcoder;

    receiverThread = new Thread(new Runnable() {
      @Override
      public void run() {
        while (!Thread.interrupted()) {
          try {
            RemoteReceiver.this.implementor.receive();
            IToken lastToken = RemoteReceiver.this.getLastToken();
            RemoteReceiver.this.notifySubscribers(lastToken);
          } catch (InterruptedException | InvalidConnectionString | IOException ignored) {
          }
        }
      }
    });
  }

  // }

  // { IRemoteReceiver behaviour

  /**
   * @see IRemoteReceiver#receive()
   * It delegates the receive operation to the implementor
   */
  @Override
  public void receive() {
    this.loggingFacility.log(Level.INFO, "Starting to receive");
    receiverThread.start();
  }

  /**
   * @see IRemoteReceiver#receive()
   * It delegates the getLastToken operation to the implementor
   */
  @Override
  public IToken getLastToken() {
    String lastTokenData = this.implementor.getLastTokenData();
    return this.transcoder.decode(lastTokenData);
  }

  // }

}
