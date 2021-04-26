package group10.server.security;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class JWTUtil {
    public static String getJWTToken(String username, long userId) {

        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        Date now = new Date();
        JwtBuilder builder = Jwts
                .builder()
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setId(Long.toString(userId))
                .setSubject(username)
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS512,
                        TextCodec.BASE64.decode(JWTConstants.DECODE_ON_SIGN)
                );
        if (JWTConstants.EXPIRES_IN > 0) {
            Date expirationDate = new Date(System.currentTimeMillis() + JWTConstants.EXPIRES_IN);
            builder.setExpiration(expirationDate);
        }
        return builder.compact();
    }
}
