package jared.cheng.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.GenerationType.SEQUENCE;

/**
 * @author jared
 * @since 2023/3/12
 */
@Entity
@Table(name = "USER_INFO")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserDO {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "USER_ID")
    @JsonIgnore
    private Integer userId;

    @Column(name = "USER_NAME", nullable = false)
    private String userName;

    @Column(name = "SCORE")
    private Integer score;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDO userDO = (UserDO) o;
        return Objects.equals(userId, userDO.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
}
