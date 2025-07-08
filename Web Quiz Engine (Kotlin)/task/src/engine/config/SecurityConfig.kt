package engine.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
@Configuration
class SecurityConfig {
  @Bean
  fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()


  @Bean
  fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
    http {
      csrf { disable() }

      authorizeHttpRequests {
        authorize("/api/register", permitAll)
        authorize("/api/quizzes/**", authenticated)
        authorize(anyRequest, permitAll)
      }

      httpBasic { }
    }
    return http.build()
  }
}
