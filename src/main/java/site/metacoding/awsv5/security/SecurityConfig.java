package site.metacoding.awsv5.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/actuator/**").permitAll() // 헬스체크 요청 경로에 대한 접근을 허용합니다.
                .anyRequest().authenticated() // 다른 요청에 대해서는 인증이 필요합니다.
                .and()
                .httpBasic(); // 기본 인증을 활성화합니다. (선택적)
        return http.build();
    }
}
