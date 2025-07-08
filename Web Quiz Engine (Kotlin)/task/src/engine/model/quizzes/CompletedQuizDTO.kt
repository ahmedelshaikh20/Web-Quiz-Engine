package engine.model.quizzes

import java.time.LocalDateTime

data class CompletedQuizDTO(val id : Int?, val completedAt: LocalDateTime)



fun CompletedQuiz.toDTO() : CompletedQuizDTO {
  return CompletedQuizDTO(
    id = quizId,
    completedAt = completedAt
  )
}
