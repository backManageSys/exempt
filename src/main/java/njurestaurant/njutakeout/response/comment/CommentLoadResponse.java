package njurestaurant.njutakeout.response.comment;

import njurestaurant.njutakeout.response.Response;

import java.util.List;

public class CommentLoadResponse extends Response {
    List<CommentItem> comments;

    public CommentLoadResponse() {
    }

    public CommentLoadResponse(List<CommentItem> comments) {
        this.comments = comments;
    }

    public List<CommentItem> getComments() {
        return comments;
    }

    public void setComments(List<CommentItem> comments) {
        this.comments = comments;
    }
}
