package group10.server.config;

import io.jsonwebtoken.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * {@inheritDoc}
 *
 * @author Alperen Caykus, Mustafa Ozan Alpay
 */
public class JWTAuthorizationFilter extends OncePerRequestFilter {

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        try {
            if (checkJWTToken(request)) {
                Claims claims = validateToken(request);
                if (claims.get("authorities") != null) {
                    setUpSpringAuthentication(claims);
                } else {
                    SecurityContextHolder.clearContext();
                }
            } else {
                SecurityContextHolder.clearContext();
            }
            chain.doFilter(request, response);
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
        }
    }

    /**
     * Given HTTP Request, extracts the Bearer token.
     *
     * @param request HTTP request that the token is going to be extracted
     * @return Claims that token carries
     */
    private Claims validateToken(HttpServletRequest request) {
        String jwtToken = request.getHeader(JWTConstants.AUTH_HEADER).replace(JWTConstants.TOKEN_PREFIX, "");
        return Jwts.parser().setSigningKey(JWTConstants.SECRET_KEY.getBytes()).parseClaimsJws(jwtToken).getBody();
    }

    /**
     * Authentication method in Spring flow.
     * Authenticates or does not authenticate the token with the given Claims
     *
     * @param claims Claims that the token carries.
     */
    private void setUpSpringAuthentication(Claims claims) {
        @SuppressWarnings("unchecked")
        List<String> authorities = (List) claims.get("authorities");

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(claims.getSubject(), null,
                authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
        SecurityContextHolder.getContext().setAuthentication(auth);

    }

    /**
     * Given a request checks if Bearer token header exists.
     *
     * @param request HTTP request that the token is investigated
     * @return True Bearer header exists, false otherwise
     */
    private boolean checkJWTToken(HttpServletRequest request) {
        String authenticationHeader = request.getHeader(JWTConstants.AUTH_HEADER);
        return authenticationHeader != null && authenticationHeader.startsWith(JWTConstants.TOKEN_PREFIX);
    }

}
