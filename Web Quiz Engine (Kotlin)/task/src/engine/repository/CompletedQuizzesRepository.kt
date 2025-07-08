package engine.repository

import engine.model.quizzes.CompletedQuiz
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CompletedQuizzesRepository : JpaRepository<CompletedQuiz, Int>{
  fun findByUserEmail(userEmail: String, pageable: Pageable): Page<CompletedQuiz>
}
