package ifpb.api_caixa_supermercado.modules.auth.controller;

import ifpb.api_caixa_supermercado.modules.auth.entity.User;
import ifpb.api_caixa_supermercado.modules.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/users")
public class UserController {
    private final UserService service;

    @PostMapping
    public void postUser(@RequestBody User user) {
        service.createUser(user);
    }
}
