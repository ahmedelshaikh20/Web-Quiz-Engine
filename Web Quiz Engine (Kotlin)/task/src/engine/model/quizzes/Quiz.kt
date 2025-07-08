package engine.model

import jakarta.persistence.*
import org.springframework.security.core.userdetails.User
import java.sql.Date

@Entity
@Table(name = "quizzes")
data class Quiz(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Int? = null,

  @Column(nullable = false)
  val title: String,

  @Column(nullable = false)
  val text: String,

  @ElementCollection
  @CollectionTable(name = "quiz_options", joinColumns = [JoinColumn(name = "quiz_id")])
  @Column(name = "option_text")
  val options: List<String>,

  @ElementCollection
  @CollectionTable(name = "quiz_answers", joinColumns = [JoinColumn(name = "quiz_id")])
  @Column(name = "answer_index")
  val answer: List<Int>,

  @Column(nullable = false)
  val authorEmail: String


)


fun Quiz.toDTO(): QuizDTO {
  return QuizDTO(
    id = this.id,
    title = this.title,
    text = this.text,
    options = this.options
  )
}
