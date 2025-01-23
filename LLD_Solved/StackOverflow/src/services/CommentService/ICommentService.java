package services.CommentService;

interface ICommentService {
    void addComment(int questionId, int userId, String comment);
}