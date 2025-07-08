package engine.controller

import engine.model.AnswerBody
import engine.model.AnswerEvaluationResponse
import engine.model.QuizDTO
import engine.model.QuizResponseDTO
import engine.model.quizzes.CompletedQuiz
import engine.model.quizzes.CompletedQuizDTO
import engine.service.QuizService
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
class QuizServiceController(private val quizService: QuizService) {

  @GetMapping("/api/quizzes/{id}")
  fun getQuizById(@PathVariable id: Int): ResponseEntity<QuizResponseDTO> {
    return try {
      val quiz = quizService.getQuiz(id)
      ResponseEntity.ok(quiz)
    } catch (e: NoSuchElementException) {
      ResponseEntity.notFound().build()
    }
  }

//  @GetMapping("/api/quizzes")
//  fun getAllQuizzes(pageable: Pageable): ResponseEntity<Page<QuizResponseDTO>> {
//    val quizzes = quizService.getQuizzes(pageable)
//    return ResponseEntity.ok(quizzes)
//  }

  @GetMapping("/api/quizzes")
  fun getAllQuizzesByPage(@RequestParam page: Int): ResponseEntity<Page<QuizResponseDTO>> {
    val quizzes = quizService.getQuizzesByPage(page)
    return ResponseEntity.ok(quizzes)
  }

  @PostMapping("/api/quizzes")
  fun createQuiz(@Valid @RequestBody quizDTO: QuizDTO, principal: Principal): ResponseEntity<QuizResponseDTO> {
    val quiz = quizService.createQuiz(
      title = quizDTO.title,
      text = quizDTO.text,
      options = quizDTO.options,
      answer = quizDTO.answer,
      principal.name
    )
    return ResponseEntity.status(HttpStatus.OK).body(quiz)
  }

  @PostMapping("/api/quizzes/{id}/solve")
  fun solveAnswer(
    @PathVariable id: Int,
    @RequestBody answerBody: AnswerBody,
    principal: Principal
  ): ResponseEntity<AnswerEvaluationResponse> {
    return try {
      val response = quizService.solveQuiz(id, answerBody.answer, principal.name)
      ResponseEntity.ok(response)
    } catch (e: NoSuchElementException) {
      ResponseEntity.notFound().build()
    }
  }

  @DeleteMapping("api/quizzes/{id}")
  fun deleteQuiz(@PathVariable id: Int, principal: Principal): ResponseEntity<String> {
    try {
      quizService.deleteQuiz(id, principal.name)
      return ResponseEntity.status(204).build()

    } catch (e: NoSuchElementException) {
      return ResponseEntity.notFound().build()
    }
  }

  @GetMapping("/api/quizzes/completed")
  fun getCompletedQuizzes(@RequestParam page: Int, principal: Principal): ResponseEntity<Page<CompletedQuizDTO>> {
    try {
        val quizzes = quizService.getCompletedQuizzes(page , userEmail = principal.name)
      return ResponseEntity.ok(quizzes)
    }catch (e: NoSuchElementException) {
      return ResponseEntity.notFound().build()
    }
  }


}
