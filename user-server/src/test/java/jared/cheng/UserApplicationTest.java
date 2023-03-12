package jared.cheng;

import jared.cheng.data.UserDAO;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author jared
 * @since 2023/3/12
 */
@SpringBootTest(classes = UserApplication.class)
public class UserApplicationTest {

    UserDAO userDAO;
}
