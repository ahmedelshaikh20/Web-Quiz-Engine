package engine.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.validation.constraints.*

data class QuizDTO(
  val id: Int? = null,

  @field:NotEmpty(message = "Title is required")
  val title: String,

  @field:NotEmpty(message = "Text is required")
  val text: String,

  @field:NotEmpty(message = "Options must not be empty")
  @field:Size(min = 2, message = "Options must contain at least 2 items")
  val options: List<@NotBlank(message = "Each option must not be blank") String>,

  // Optional field: can be missing or empty
  val answer: List<Int> = emptyList()
)

fun QuizDTO.toEntity(authorEmail: String): Quiz = Quiz(
  title   = title,
  text    = text,
  options = options,
  answer  = answer,
  authorEmail = authorEmail// store e-mail only or map later
)
