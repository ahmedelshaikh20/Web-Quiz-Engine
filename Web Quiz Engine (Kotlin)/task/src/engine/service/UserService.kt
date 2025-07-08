package engine.service

import engine.model.registeration.RegistrationRequest
import engine.model.registeration.toUser
import engine.repository.UserRepository
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(private val repo: UserRepository , private val passwordEncoder: PasswordEncoder): UserDetailsService {


  fun registerUser(registrationRequest: RegistrationRequest) {
    if(userAlreadyRegistered(registrationRequest.email)){
      throw IllegalStateException("User with email ${registrationRequest.email} already exists")
    }

    val user = registrationRequest.toUser(passwordEncoder)
    repo.save(user)
  }


  fun userAlreadyRegistered(email : String): Boolean {
    return repo.existsByEmail(email)
  }


  override fun loadUserByUsername(email: String): UserDetails {
    val u = repo.findByEmail(email)
      ?: throw UsernameNotFoundException("User $email not found")

    return User
      .withUsername(u.email)
      .password(u.hashedPassword)
      .roles("USER")
      .build()
  }
}
