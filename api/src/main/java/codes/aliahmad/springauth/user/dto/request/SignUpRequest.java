package codes.aliahmad.springauth.user.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequest
{
  @NotBlank(message = "Email can't be empty")
  private String email;
  @NotBlank(message = "Password can't be empty")
  private String password;
}
