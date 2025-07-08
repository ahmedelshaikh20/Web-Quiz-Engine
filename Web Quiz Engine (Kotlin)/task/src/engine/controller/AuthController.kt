package engine.controller

import engine.model.registeration.RegistrationRequest
import engine.service.UserService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController(private val userService: UserService) {

    @PostMapping("/api/register")
    fun signup(@Valid @RequestBody request: RegistrationRequest): ResponseEntity<String> {
      try {
        userService.registerUser(request)
        return ResponseEntity.ok("User registered successfully")}
      catch (e: Exception) {
        return ResponseEntity.badRequest().body(e.message)
      }
    }
}
