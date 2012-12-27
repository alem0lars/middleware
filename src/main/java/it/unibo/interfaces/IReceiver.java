package it.unibo.interfaces;

public interface IReceiver {

  /**
   * Wait for a new notification in the channel and notify the subscribers when
   * a new token is available in the channel
   */
  public abstract void receive();

  /**
   * @return the last token received in the channel
   */
  public abstract IToken getLastToken();

  /**
   * Subscribe the provided endpoint in the channel
   *
   * @param endpoint the observer to be subscribed
   */
  public abstract void subscribe(IObserverEndpoint endpoint);

  /**
   * Delete all subscribers from the channel
   */
  public abstract void deleteSubscribers();

  /**
   * Delete the endpoint from the subscribed endpoints
   *
   * @param endpoint The enpoint to unsubscribed
   */
  public abstract void deleteSubscriber(IObserverEndpoint endpoint);

  /**
   * Notify the endpoint (subscribers) for the availability of a new token in
   * the channel
   *
   * @param token The new token available in the channel
   */
  public abstract void notifySubscribers(IToken token);

  /**
   * @return the number of subscribers registered in the channel
   */
  public abstract int countSubscribers();
}
