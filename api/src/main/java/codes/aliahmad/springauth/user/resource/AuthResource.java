package codes.aliahmad.springauth.user.resource;


import codes.aliahmad.springauth.user.dto.request.SignInRequest;
import codes.aliahmad.springauth.user.dto.request.SignUpRequest;
import codes.aliahmad.springauth.user.dto.response.JwtResponse;
import codes.aliahmad.springauth.user.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthResource
{

  private final AuthService authService;


  @PostMapping("/signin")
  public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody SignInRequest signInRequest)
  {
    return ResponseEntity.ok(authService.authenticateUser(signInRequest));
  }

  @PostMapping("/signup")
  public ResponseEntity<JwtResponse> registerUser(@Valid @RequestBody SignUpRequest signUpRequest)
  {
    return new ResponseEntity<>(authService.registerUser(signUpRequest), HttpStatus.OK);
  }

  @GetMapping("/logout")
  public ResponseEntity<HttpStatus> logout(@RequestHeader(value = "Authorization") String token)
  {
    authService.logout(token);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/refresh")
  public ResponseEntity<JwtResponse> refreshAuth(@AuthenticationPrincipal UserDetails userDetails)
  {
    return new ResponseEntity<>(authService.refreshAuth(userDetails.getUsername()), HttpStatus.OK);
  }

}
