package engine.model

import java.util.Date

data class QuizResponseDTO(
  val id: Int?,
  val title: String,
  val text: String,
  val options: List<String>,
)

fun Quiz.toResponseDTO(): QuizResponseDTO {
  return QuizResponseDTO(
    id = this.id,
    title = this.title,
    text = this.text,
    options = this.options
  )
}
