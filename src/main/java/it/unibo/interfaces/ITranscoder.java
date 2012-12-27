package it.unibo.interfaces;


public interface ITranscoder {

  /**
   * Decode the provided string in a token
   */
  public abstract IToken decode(String data);

  /**
   * Encode the provided token in a string
   */
  public abstract String encode(IToken token);
}
