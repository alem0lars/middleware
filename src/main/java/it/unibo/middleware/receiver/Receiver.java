package it.unibo.middleware.receiver;

import it.unibo.interfaces.IObserverEndpoint;
import it.unibo.interfaces.IReceiver;
import it.unibo.interfaces.IToken;
import it.unibo.logging.LoggingFacility;

import java.util.ArrayList;
import java.util.List;

public abstract class Receiver implements IReceiver {

  protected final LoggingFacility loggingFacility;
  protected List<IToken> pool;
  protected List<IObserverEndpoint> subscribers;

  // { Construction

  /**
   * Create a new receiver
   */
  public Receiver(LoggingFacility loggingFacility) {
    this.loggingFacility = loggingFacility;
    this.pool = new ArrayList<>();
    this.subscribers = new ArrayList<>();
  }

  // }

  // { Receiver behaviour

  /**
   * @see Receiver#subscribe(IObserverEndpoint)
   */
  @Override
  public void subscribe(IObserverEndpoint endpoint) {
    this.subscribers.add(endpoint);
  }

  /**
   * @see Receiver#deleteSubscribers()
   */
  @Override
  public void deleteSubscribers() {
    this.subscribers.removeAll(this.subscribers);
  }

  /**
   * @see Receiver#deleteSubscriber(IObserverEndpoint)
   */
  @Override
  public void deleteSubscriber(IObserverEndpoint endpoint) {
    this.subscribers.add(endpoint);
  }

  /**
   * @see Receiver#notifySubscribers(IToken)
   */
  @Override
  public void notifySubscribers(IToken token) {
    for (IObserverEndpoint endpoint : this.subscribers) {
      endpoint.update(this, token);
    }
  }

  /**
   * @see Receiver#countSubscribers()
   */
  @Override
  public int countSubscribers() {
    return this.subscribers.size();
  }

  // }

}
