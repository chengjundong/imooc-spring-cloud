package jared.cheng.data;

import jared.cheng.UserApplication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

/**
 * @author jared
 * @since 2023/3/12
 */
@Sql(value = {"classpath:/schema.sql", "classpath:/data.sql"})
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = UserApplication.class)
public class UserDAOTest {

    @Autowired
    private UserDAO userDAO;

    @Test
    void testFindAll() {
        assertThat(userDAO.findAll()).isNotEmpty().hasSize(2);
    }

    @Test
    @Rollback
    void testSave() {
        final UserDO user = new UserDO();
        user.setUserId(200);
        user.setUserName("Pogba");
        user.setScore(10);

        userDAO.save(user);

        final List<UserDO> results = userDAO.findByUserName("Pogba");
        assertThat(results).hasSize(1)
                .extracting(UserDO::getUserName, UserDO::getScore)
                .containsExactly(tuple("Pogba", 10));
    }

    @Test
    @Rollback
    void testUpdate() {
        final List<UserDO> results = userDAO.findByUserName("Jared");
        assertThat(results).hasSize(1)
                .extracting(UserDO::getUserId, UserDO::getUserName, UserDO::getScore)
                .containsExactly(tuple(1, "Jared", 300));

        // update score
        final UserDO user = results.get(0);
        user.setScore(500);
        userDAO.save(user);

        assertThat(userDAO.findByUserName("Jared")).hasSize(1)
                .extracting(UserDO::getUserId, UserDO::getUserName, UserDO::getScore)
                .containsExactly(tuple(1, "Jared", 500));
    }
}
