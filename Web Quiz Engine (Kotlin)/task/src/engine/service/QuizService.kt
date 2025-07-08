package engine.service

import engine.model.AnswerEvaluationResponse
import engine.model.Quiz
import engine.model.QuizDTO
import engine.model.QuizResponseDTO
import engine.model.quizzes.CompletedQuiz
import engine.model.quizzes.CompletedQuizDTO
import engine.model.quizzes.toDTO
import engine.model.toDTO
import engine.model.toEntity
import engine.model.toResponseDTO
import engine.repository.CompletedQuizzesRepository
import engine.repository.QuizRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.sql.Date
import java.time.LocalDateTime

@Service
class QuizService(
  val repo: QuizRepository,
  private val completedQuizzesRepo: CompletedQuizzesRepository
) {


  fun getQuiz(id: Int): QuizResponseDTO = repo.findById(id).orElseThrow().toResponseDTO()


  fun solveQuiz(id: Int, userAnswer: List<Int>, userEmail: String): AnswerEvaluationResponse {
    val quiz = repo.findById(id).orElseThrow()
    val isCorrect = quiz.answer.sorted() == userAnswer.sorted()
    println("Solving quiz ${quiz.answer} ${userAnswer}")

    return if (isCorrect) {
      completedQuizzesRepo.save(
        CompletedQuiz(
          quizId = quiz.id,
          userEmail = userEmail,
          completedAt = LocalDateTime.now()
        )
      )
      AnswerEvaluationResponse(true, "Congratulations, you're right!")
    } else {
      AnswerEvaluationResponse(false, "Wrong answer! Please, try again.")
    }
  }

  fun deleteQuiz(id: Int, currentEmail: String) {
    val quiz = repo.findById(id).orElseThrow {
      ResponseStatusException(HttpStatus.NOT_FOUND)
    }
    if (quiz.authorEmail != currentEmail)
      throw ResponseStatusException(HttpStatus.FORBIDDEN)

    repo.delete(quiz)
  }


  fun createQuiz(
    title: String,
    text: String,
    options: List<String>,
    answer: List<Int>,
    currentEmail: String
  ): QuizResponseDTO {
    val quiz = QuizDTO(title = title, text = text, options = options, answer = answer).toEntity(currentEmail)
    repo.save(quiz)
    return quiz.toResponseDTO()
  }

  fun getQuizzesByPage(page: Int, size: Int = 10): Page<QuizResponseDTO> {
    val pageable = PageRequest.of(page, size)
    return repo.findAll(pageable).map { it.toResponseDTO() }
  }


  fun getCompletedQuizzes(page: Int, size: Int = 10, userEmail: String): Page<CompletedQuizDTO> {
    val pageable = PageRequest.of(page, size, Sort.by("completedAt").descending())
    return completedQuizzesRepo.findByUserEmail(userEmail, pageable).map { it.toDTO() }
  }
}
