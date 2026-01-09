package ifpb.api_caixa_supermercado.modules.auth.controller;

import ifpb.api_caixa_supermercado.modules.auth.dto.Login;
import ifpb.api_caixa_supermercado.modules.auth.dto.Sessao;
import ifpb.api_caixa_supermercado.modules.auth.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequiredArgsConstructor
public class LoginController {
    private final LoginService service;

    @PostMapping("api/login")
    public Sessao login(@RequestBody Login login) {
        return service.logar(login);
    }
}