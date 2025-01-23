package services.QuestionService;

// Question Service Implementation
class QuestionService implements IQuestionService {
    private Map<Integer, Question> questions = new HashMap<>();
    private int questionIdCounter = 1;
    private IUserService userService;

    public QuestionService(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public void addQuestion(int authorId, String title, String content) {
        if (userService.getUserById(authorId) == null) {
            System.out.println("Author not found.");
            return;
        }
        Question question = new Question(questionIdCounter++, title, content, authorId);
        questions.put(question.id, question);
        System.out.println("Question added: " + title);
    }

    @Override
    public void removeQuestion(int questionId, int adminId) {
        User admin = userService.getUserById(adminId);
        if (admin == null || !admin.role.equals("Admin")) {
            System.out.println("Only admins can remove questions.");
            return;
        }
        if (questions.containsKey(questionId)) {
            questions.remove(questionId);
            System.out.println("Question removed: " + questionId);
        } else {
            System.out.println("Question not found.");
        }
    }

    @Override
    public void upvoteQuestion(int questionId) {
        if (questions.containsKey(questionId)) {
            questions.get(questionId).upvote();
            System.out.println("Question upvoted: " + questionId);
        } else {
            System.out.println("Question not found.");
        }
    }

    @Override
    public List<Question> getAllQuestions() {
        return new ArrayList<>(questions.values());
    }
}