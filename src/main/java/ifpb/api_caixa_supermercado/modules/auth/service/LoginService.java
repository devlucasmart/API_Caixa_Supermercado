package ifpb.api_caixa_supermercado.modules.auth.service;

import ifpb.api_caixa_supermercado.modules.auth.dto.Login;
import ifpb.api_caixa_supermercado.modules.auth.dto.Sessao;
import ifpb.api_caixa_supermercado.modules.auth.entity.User;
import ifpb.api_caixa_supermercado.modules.auth.repository.UserRepository;
import ifpb.api_caixa_supermercado.modules.auth.security.JWTCreator;
import ifpb.api_caixa_supermercado.modules.auth.security.JWTObject;
import ifpb.api_caixa_supermercado.modules.auth.security.SecurityConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final PasswordEncoder encoder;
    private final SecurityConfig securityConfig;
    private final UserRepository repository;

    public Sessao logar(Login login) {
        User user = repository.findByUsername(login.getUsername());
        if (user != null) {
            boolean passwordOk = encoder.matches(login.getPassword(), user.getPassword());
            if (!passwordOk) {
                throw new RuntimeException("Senha inválida para o login: " + login.getUsername());
            }
            //Estamos enviando um objeto Sessão para retornar mais informações do usuário
            Sessao sessao = new Sessao();
            sessao.setLogin(user.getUsername());

            JWTObject jwtObject = new JWTObject();
            jwtObject.setIssuedAt(new Date(System.currentTimeMillis()));
            jwtObject.setExpiration((new Date(System.currentTimeMillis() + SecurityConfig.EXPIRATION)));
            jwtObject.setRoles(user.getRoles());
            sessao.setToken(JWTCreator.create(SecurityConfig.PREFIX, SecurityConfig.KEY, jwtObject));
            return sessao;
        } else {
            throw new RuntimeException("Erro ao tentar fazer login");
        }
    }
}
