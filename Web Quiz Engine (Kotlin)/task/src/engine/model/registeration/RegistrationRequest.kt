package engine.model.registeration

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.springframework.security.crypto.password.PasswordEncoder

data class RegistrationRequest(
  @field:Email(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "Email must be in the form user@example.com")
  @field:NotBlank(message = "Email must not be blank")
  val email: String,

  @field:Size(min = 5, message = "Password must be at least 5 characters long")
  @field:NotBlank(message = "Password must not be blank")
  val password: String
)


fun RegistrationRequest.toUser(passwordEncoder: PasswordEncoder): User {
  return User(email = email, hashedPassword = passwordEncoder.encode(password))
}
