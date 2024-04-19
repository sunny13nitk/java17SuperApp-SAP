package java17superApp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@Order(1) // needs to have higher priority than CAP security config
public class AuthSecurityConfig
{

    @Bean
    public SecurityFilterChain appFilterChain(HttpSecurity http) throws Exception
    {
        return http.securityMatcher(AntPathRequestMatcher.antMatcher("/authorize/**")).csrf(c -> c.disable())
                .authorizeHttpRequests(r -> r.anyRequest().permitAll()).build();
    }

}