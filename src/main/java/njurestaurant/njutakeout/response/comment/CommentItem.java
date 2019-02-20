package njurestaurant.njutakeout.response.comment;

public class CommentItem {
    private String avatarUrl;
    private String date;
    private String content;

    public CommentItem() {
    }

    public CommentItem(String avatarUrl, String date, String content) {
        this.avatarUrl = avatarUrl;
        this.date = date;
        this.content = content;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
