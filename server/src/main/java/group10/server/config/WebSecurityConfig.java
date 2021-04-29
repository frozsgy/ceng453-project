package group10.server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, JWTConstants.LOGIN_PATH).permitAll()
                .antMatchers(HttpMethod.POST, JWTConstants.REGISTER_PATH).permitAll()
                .antMatchers(HttpMethod.POST, JWTConstants.REQUEST_CODE_PATH).permitAll()
                .antMatchers(HttpMethod.POST, JWTConstants.UPDATE_PW_PATH).permitAll()
                .anyRequest().authenticated();
    }
}
