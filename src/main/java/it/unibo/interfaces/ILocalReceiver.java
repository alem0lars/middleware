package it.unibo.interfaces;

public interface ILocalReceiver extends IReceiver {

  /**
   * Add the provided token into the pool
   *
   * @param token The token to be added into the pool
   */
  public abstract void add(IToken token);
}
