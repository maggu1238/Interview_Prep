package services.QuestionService;

public interface IQuestionService {
    void addQuestion(int authorId, String title, String content);
    void removeQuestion(int questionId, int adminId);
    void upvoteQuestion(int questionId);
    List<Question> getAllQuestions();
}
