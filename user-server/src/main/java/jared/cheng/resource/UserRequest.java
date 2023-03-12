package jared.cheng.resource;

/**
 * @author jared
 * @since 2023/3/12
 */
public class UserRequest {

    private String userName;
    private Integer score;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
