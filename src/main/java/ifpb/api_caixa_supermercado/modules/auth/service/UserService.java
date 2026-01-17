package ifpb.api_caixa_supermercado.modules.auth.service;

import ifpb.api_caixa_supermercado.modules.auth.dto.CreateUserDto;
import ifpb.api_caixa_supermercado.modules.auth.dto.LoginUserDto;
import ifpb.api_caixa_supermercado.modules.auth.dto.RecoveryJwtTokenDto;
import ifpb.api_caixa_supermercado.modules.auth.entity.Role;
import ifpb.api_caixa_supermercado.modules.auth.entity.User;
import ifpb.api_caixa_supermercado.modules.auth.enums.Position;
import ifpb.api_caixa_supermercado.modules.auth.enums.RoleName;
import ifpb.api_caixa_supermercado.modules.auth.repository.UserRepository;
import ifpb.api_caixa_supermercado.modules.auth.security.WebSecurityConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WebSecurityConfig securityConfiguration;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);


    public RecoveryJwtTokenDto authenticateUser(LoginUserDto loginUserDto) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginUserDto.email(), loginUserDto.password());

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return new RecoveryJwtTokenDto(jwtTokenService.generateToken(userDetails));
    }

    public void createUser(CreateUserDto createUserDto) {
        logger.info("[UserService] - createUser - Criando usuário com email: {}", createUserDto.email());

        Position position = Position.fromPosition(createUserDto.position());
        RoleName role = position.getRole();

        User newUser = User.builder()
                .name(createUserDto.name())
                .email(createUserDto.email())
                .phone(createUserDto.phone())
                .password(securityConfiguration.passwordEncoder().encode(createUserDto.password()))
                .roles(List.of(Role.builder().name(role).build()))
                .build();

        userRepository.save(newUser);
    }
}