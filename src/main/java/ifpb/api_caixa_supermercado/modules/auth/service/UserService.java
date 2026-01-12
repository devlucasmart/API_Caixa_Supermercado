package ifpb.api_caixa_supermercado.modules.auth.service;

import ifpb.api_caixa_supermercado.modules.auth.dto.CreateUserDto;
import ifpb.api_caixa_supermercado.modules.auth.dto.LoginUserDto;
import ifpb.api_caixa_supermercado.modules.auth.dto.RecoveryJwtTokenDto;
import ifpb.api_caixa_supermercado.modules.auth.entity.Role;
import ifpb.api_caixa_supermercado.modules.auth.entity.User;
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


    // Método responsável por autenticar um usuário e retornar um token JWT
    public RecoveryJwtTokenDto authenticateUser(LoginUserDto loginUserDto) {
        // Cria um objeto de autenticação com o email e a senha do usuário
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginUserDto.email(), loginUserDto.password());

        // Autentica o usuário com as credenciais fornecidas
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        // Obtém o objeto UserDetails do usuário autenticado
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        // Gera um token JWT para o usuário autenticado
        return new RecoveryJwtTokenDto(jwtTokenService.generateToken(userDetails));
    }

    // Método responsável por criar um usuário
    public void createUser(CreateUserDto createUserDto) {

        logger.info("[UserServicde] - createUser - Criando usuário com email: {}", createUserDto.email());

        // Cria um novo usuário com os dados fornecidos
        User newUser = User.builder()
                .email(createUserDto.email())
                // Codifica a senha do usuário com o algoritmo bcrypt
                .password(securityConfiguration.passwordEncoder().encode(createUserDto.password()))
                // Atribui ao usuário uma permissão específica
                .roles(List.of(Role.builder().name(createUserDto.role()).build()))
                .build();

        // Salva o novo usuário no banco de dados
        userRepository.save(newUser);
    }
}