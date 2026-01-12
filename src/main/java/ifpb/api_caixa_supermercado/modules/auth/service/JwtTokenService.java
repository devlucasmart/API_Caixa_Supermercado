package ifpb.api_caixa_supermercado.modules.auth.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
public class JwtTokenService {

    @Value("${jwt.secret.key}")
    private String secretKey; // Injetado do application.yml ou variável de ambiente

    @Value("${jwt.issuer}")
    private String issuer; // Injetado do application.yml ou variável de ambiente

    @Value("${jwt.expiration.hours}")
    private int expirationHours; // Valor padrão 4 horas

    @Value("${jwt.timezone}")
    private String timezone; // Valor padrão America/Sao_Paulo

    public String generateToken(UserDetailsImpl user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            return JWT.create()
                    .withIssuer(issuer)
                    .withIssuedAt(creationDate())
                    .withExpiresAt(expirationDate())
                    .withSubject(user.getUsername())
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new JWTCreationException("Erro ao gerar token.", exception);
        }
    }

    public String getSubjectFromToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            return JWT.require(algorithm)
                    .withIssuer(issuer)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception){
            throw new JWTVerificationException("Token inválido ou expirado.");
        }
    }

    private Instant creationDate() {
        return ZonedDateTime.now(ZoneId.of(timezone)).toInstant();
    }

    private Instant expirationDate() {
        return ZonedDateTime.now(ZoneId.of(timezone)).plusHours(expirationHours).toInstant();
    }
}