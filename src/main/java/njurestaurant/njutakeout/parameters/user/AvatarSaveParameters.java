package njurestaurant.njutakeout.parameters.user;

public class AvatarSaveParameters {
    private String avatarUrl;

    public AvatarSaveParameters() {
    }

    public AvatarSaveParameters(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
