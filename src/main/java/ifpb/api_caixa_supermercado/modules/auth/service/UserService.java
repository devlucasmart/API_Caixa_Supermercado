package ifpb.api_caixa_supermercado.modules.auth.service;

import ifpb.api_caixa_supermercado.modules.auth.entity.User;
import ifpb.api_caixa_supermercado.modules.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final PasswordEncoder encoder;

    public void createUser(User user) {
        var pass = user.getPassword();
        //criptografando antes de salvar no banco
        user.setPassword(encoder.encode(pass));
        repository.save(user);
    }
}