package it.unibo.logging;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggingFacility {
  Logger logger;

  public LoggingFacility(String name) {
    this.logger = Logger.getLogger(name);
    this.enableDebug();
  }

  public synchronized void log(Level level, String format, Object... args) {
    if (logger.isLoggable(level)) {
      logger.log(level, String.format(format, args));
    }
  }

  public synchronized void disableDebug() {
    this.logger.setLevel(Level.WARNING);
  }

  public synchronized void enableDebug() {
    this.logger.setLevel(Level.INFO);
  }
}
