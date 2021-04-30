package group10.server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author Alperen Caykus, Mustafa Ozan Alpay
 * Config class that is responsible of Spring Security.
 */
@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * Whitelist for URL's that can be reached without authentication.
     * Used by Swagger and other libraries.
     */
    private static final String[] AUTH_WHITELIST = {
            "/h2-console",
            "/h2-console/**",
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            "/graphiql",
            "/api/graphql",
    };

    /**
     * Spring Security Filter for Authentication.
     * @param http - HttpSecurity
     * @throws Exception depending on the HttpSecurity type.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(AUTH_WHITELIST).permitAll()
                .antMatchers(HttpMethod.POST, JWTConstants.LOGIN_PATH).permitAll()
                .antMatchers(HttpMethod.POST, JWTConstants.REGISTER_PATH).permitAll()
                .antMatchers(HttpMethod.POST, JWTConstants.REQUEST_CODE_PATH).permitAll()
                .antMatchers(HttpMethod.PUT, JWTConstants.UPDATE_PW_PATH).permitAll()
                .anyRequest().authenticated();
    }
}
