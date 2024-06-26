package codes.aliahmad.springauth.common.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum ExceptionType
{
  USER_ALREADY_EXISTS("User already Exists", HttpStatus.CONFLICT),
  ROLE_NOT_FOUND("Role not found", HttpStatus.NOT_FOUND),
  USER_NOT_FOUND("User not found", HttpStatus.NOT_FOUND),
  INVALID_TOKEN("Token is invalid", HttpStatus.BAD_REQUEST),
  ACCESS_DENIED("Access denied", HttpStatus.FORBIDDEN);

  private String message;
  private HttpStatus status;
  }
