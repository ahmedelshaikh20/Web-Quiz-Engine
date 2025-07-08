package engine.model.quizzes

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "completed_quizzes")
data class CompletedQuiz(
  @Id @GeneratedValue val id: Int = 0,
  val quizId: Int? = null,
  val userEmail: String,
  val completedAt: LocalDateTime
)
