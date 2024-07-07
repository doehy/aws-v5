package site.metacoding.awsv5.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder encode() {
        return new BCryptPasswordEncoder();
    }



    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.authorizeRequests()
                .antMatchers("/members/**").access("hasRole('ADMIN') or hasRole('MEMBER')")
                .antMatchers("/admin/**").access("hasRole('ADMIN')")
                .antMatchers("/static/css/**").permitAll()
                .antMatchers("/static/js/**").permitAll()
                .antMatchers("/static/file/**").permitAll()
                .anyRequest().permitAll();

        return http.build();
    }
}
