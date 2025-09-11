package ar.edu.ceniit.demo.user.aplication.entitys.exceptions;

public class UserAlreadyExistsException extends Exception {
  public UserAlreadyExistsException(String message) {
    super(message);
  }

  public UserAlreadyExistsException() {
    super();
  }

  public UserAlreadyExistsException(String message, Throwable cause) {
    super(message, cause);
  }

  public UserAlreadyExistsException(Throwable cause) {
    super(cause);
  }

  protected UserAlreadyExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}

