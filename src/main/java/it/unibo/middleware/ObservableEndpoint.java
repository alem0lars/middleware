package it.unibo.middleware;

import it.unibo.exceptions.InvalidConnectionString;
import it.unibo.interfaces.IObservableEndpoint;
import it.unibo.interfaces.ISender;
import it.unibo.interfaces.IToken;
import it.unibo.logging.LoggingFacility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class ObservableEndpoint implements IObservableEndpoint {

  protected final List<ISender> publishers;
  private final LoggingFacility loggingFacility;

  // { Construction

  /**
   * Create a new endpoint which is observable
   */
  public ObservableEndpoint(LoggingFacility loggingFacility) {
    this.loggingFacility = loggingFacility;
    this.publishers = new ArrayList<>();
  }

  // }

  // { IObservableEndpoint behaviour

  /**
   * @see IObservableEndpoint#addPublisher(ISender)
   */
  @Override
  public void addPublisher(ISender sender) {
    loggingFacility.log(Level.INFO, String.format("Added the publisher: %s", sender));
    this.publishers.add(sender);
  }

  /**
   * @see IObservableEndpoint#deletePublishers()
   */
  @Override
  public void deletePublishers() {
    loggingFacility.log(Level.INFO, "Removed all publishers");
    this.publishers.removeAll(this.publishers);
  }

  /**
   * @see IObservableEndpoint#deletePublisher(ISender)
   */
  @Override
  public void deletePublisher(ISender sender) {
    loggingFacility.log(Level.INFO, String.format("Removed the publisher: %s", sender));
    this.publishers.remove(sender);
  }

  /**
   * @see IObservableEndpoint#notifyPublishers(IToken)
   */
  @Override
  public void notifyPublishers(IToken token) throws InterruptedException, IOException, InvalidConnectionString {
    for (ISender sender : this.publishers) {
      sender.publish(this, token);
    }
  }

  /**
   * @see IObservableEndpoint#countPublishers()
   */
  @Override
  public int countPublishers() {
    return this.publishers.size();
  }

  // }

}
