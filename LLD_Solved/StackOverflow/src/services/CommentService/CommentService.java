package services.CommentService;

// Comment Service Implementation
class CommentService implements ICommentService {
    private IQuestionService questionService;
    private IUserService userService;

    public CommentService(IQuestionService questionService, IUserService userService) {
        this.questionService = questionService;
        this.userService = userService;
    }

    @Override
    public void addComment(int questionId, int userId, String comment) {
        User user = userService.getUserById(userId);
        if (user == null) {
            System.out.println("User not found.");
            return;
        }
        Question question = questionService.getAllQuestions()
                .stream()
                .filter(q -> q.id == questionId)
                .findFirst()
                .orElse(null);
        if (question == null) {
            System.out.println("Question not found.");
            return;
        }
        question.addComment(comment);
        System.out.println("Comment added to question: " + questionId);
    }
}