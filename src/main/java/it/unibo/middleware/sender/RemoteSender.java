package it.unibo.middleware.sender;

import it.unibo.exceptions.InvalidConnectionString;
import it.unibo.interfaces.IRemoteSender;
import it.unibo.interfaces.ITranscoder;
import it.unibo.interfaces.IRemoteSenderImplementor;
import it.unibo.interfaces.IToken;
import it.unibo.logging.LoggingFacility;

import java.io.IOException;

public class RemoteSender extends Sender implements IRemoteSender {

  protected IRemoteSenderImplementor implementor;
  private final ITranscoder transcoder;

  // { Construction

  /**
   * Create a new remote sender abstraction
   *
   * @param implementor The remote sender implementor
   */
  public RemoteSender(IRemoteSenderImplementor implementor, ITranscoder transcoder, LoggingFacility loggingFacility) {
    super(loggingFacility);
    this.implementor = implementor;
    this.transcoder = transcoder;
  }

  // }

  // { Sender behaviour

  /**
   * @see Sender#send(IToken)
   * It delegates the send operation to the implementor
   */
  @Override
  protected void send(IToken token)
      throws InterruptedException, InvalidConnectionString, IOException {
    this.implementor.send(this.transcoder.encode(token));
  }

  // }

}
