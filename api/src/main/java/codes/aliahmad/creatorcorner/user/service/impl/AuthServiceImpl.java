package codes.aliahmad.creatorcorner.user.service.impl;

import codes.aliahmad.creatorcorner.common.exception.BusinessException;
import codes.aliahmad.creatorcorner.common.exception.ExceptionType;
import codes.aliahmad.creatorcorner.user.dto.request.SignInRequest;
import codes.aliahmad.creatorcorner.user.dto.request.SignUpRequest;
import codes.aliahmad.creatorcorner.user.dto.response.JwtResponse;
import codes.aliahmad.creatorcorner.user.entity.Role;
import codes.aliahmad.creatorcorner.user.entity.User;
import codes.aliahmad.creatorcorner.user.model.ERole;
import codes.aliahmad.creatorcorner.user.security.helper.JwtHelper;
import codes.aliahmad.creatorcorner.user.security.model.UserDetailsModel;
import codes.aliahmad.creatorcorner.user.service.AuthService;
import codes.aliahmad.creatorcorner.user.service.RoleService;
import codes.aliahmad.creatorcorner.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService
{
  private final UserService userService;
  private final RoleService roleService;
  private final PasswordEncoder encoder;
  private final AuthenticationManager authenticationManager;
  private final JwtHelper jwtHelper;
//  private final SessionService sessionService;

  @Override
  public JwtResponse registerUser(SignUpRequest signUpRequest)
  {
    if (userService.existByEmail(signUpRequest.getEmail()))
    {
      throw new BusinessException(ExceptionType.USER_ALREADY_EXISTS);
    }

    Role role = roleService.findByName(ERole.USER);

    // Create new user's account
    User userToSave = User.builder().email(signUpRequest.getEmail())
            .password(encoder.encode(signUpRequest.getPassword()))
            .role(role)
            .build();

    User _ = userService.save(userToSave);

    return authenticateUser(signUpRequest.getEmail(), signUpRequest.getPassword());
  }

  public JwtResponse authenticateUser(SignInRequest signInRequest)
  {
    return authenticateUser(signInRequest.getEmail(), signInRequest.getPassword());
  }

  private JwtResponse authenticateUser(String email, String password)
  {
    Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(email, password));


    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtHelper.generateJwtToken(authentication);
    UserDetailsModel userDetails = (UserDetailsModel) authentication.getPrincipal();

    String role = userDetails.getAuthorities()
            .stream()
            .findFirst()
            .map(GrantedAuthority::getAuthority)
            .orElse(ERole.USER.getValue());

    return new JwtResponse(jwt, userDetails.getUsername(), role);
  }

}
