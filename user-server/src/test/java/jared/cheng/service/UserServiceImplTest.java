package jared.cheng.service;

import jared.cheng.data.UserDAO;
import jared.cheng.data.UserDO;
import jared.cheng.exception.UserError;
import jared.cheng.exception.UserException;
import jared.cheng.resource.UserRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

/**
 * @author jared
 * @since 2023/3/12
 */
public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl svc;

    @Mock
    private UserDAO userDAO;

    @Spy
    private BlockingQueue<UserDO> queue = new ArrayBlockingQueue<>(100);

    @Captor
    private ArgumentCaptor<UserDO> daoArgCaptor;

    AutoCloseable mocks;

    @BeforeEach
    void setUp() {
        mocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        mocks.close();
    }

    @Test
    void createUser_SuccessToPersist_UserReturned() {
        // assign
        final UserRequest req = new UserRequest();
        final String userName = "Helen";
        req.setUserName(userName);

        given(userDAO.findByUserName(userName)).willReturn(null);
        given(userDAO.save(any(UserDO.class))).willAnswer(i -> {
            final UserDO result = i.getArgument(0, UserDO.class);
            result.setScore(0);
            return result;
        });

        // act
        final UserDO user = svc.createUser(req);

        // assert
        assertThat(user).isNotNull();
        then(userDAO).should().findByUserName(userName);
        then(userDAO).should().save(daoArgCaptor.capture());
        assertThat(daoArgCaptor.getValue()).isNotNull()
                .extracting(UserDO::getUserName, UserDO::getScore)
                .containsExactly(userName, 0);
        then(queue).should().offer(user);
    }

    @Test
    void createUser_UserAlreadyExist_ExceptionThrown() {
        // assign
        final UserRequest req = new UserRequest();
        final String userName = "Helen";
        req.setUserName(userName);

        given(userDAO.findByUserName(userName)).willReturn(new ArrayList<>(){
            {
                this.add(new UserDO());
            }
        });

        // act & assert
        assertThatThrownBy(() -> svc.createUser(req))
                .isInstanceOf(UserException.class)
                .extracting("error", "message")
                .containsExactly(UserError.USER_ALREADY_EXISTS, UserError.USER_ALREADY_EXISTS.getErrorMessage());
    }
}
