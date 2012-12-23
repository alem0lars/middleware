package it.unibo.interfaces;

import it.unibo.exceptions.InvalidConnectionString;

import java.io.IOException;

public interface IObservableEndpoint {

  /**
   * Add the provided sender to the registered publishers
   *
   * @param sender the sender to be registered
   */
  public abstract void addPublisher(ISender sender);

  /**
   * Delete all registered publishers
   */
  public abstract void deletePublishers();

  /**
   * Delete the provided sender from the registered publishers
   *
   * @param sender the sender to be deleted from the registered publishers
   */
  public abstract void deletePublisher(ISender sender);

  /**
   * Notify all publishers for the availability of the provided token
   *
   * @param token the token that will be published in the registered publishers
   */
  public abstract void notifyPublishers(IToken token) throws InterruptedException, IOException, InvalidConnectionString;

  /**
   * @return the current number of registered publishers
   */
  public abstract int countPublishers();
}
