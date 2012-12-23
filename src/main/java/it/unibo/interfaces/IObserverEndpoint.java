package it.unibo.interfaces;


public interface IObserverEndpoint {

  /**
   * Update the observer
   *
   * @param source the source of the update operation
   * @param token  the token for whom the observer has been updated
   */
  public abstract void update(IReceiver source, IToken token);
}
