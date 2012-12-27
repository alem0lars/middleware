package it.unibo.interfaces;

/**
 * Minimal unit of information that can be sent in the middleware
 */
public interface IToken {

  /**
   * @return the payload of the token
   */
  public abstract String getValue();
}
