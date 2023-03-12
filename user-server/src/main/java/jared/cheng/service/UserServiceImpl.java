package jared.cheng.service;

import jared.cheng.data.UserDAO;
import jared.cheng.data.UserDO;
import jared.cheng.exception.UserError;
import jared.cheng.exception.UserException;
import jared.cheng.resource.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * @author jared
 * @since 2023/3/12
 */
@Service
public class UserServiceImpl implements IUserService{

    private final UserDAO userDAO;
    private final BlockingQueue<UserDO> redisDataQueue;

    public UserServiceImpl(@Autowired UserDAO userDAO,
                           @Autowired @Qualifier("redisDataQueue") BlockingQueue<UserDO> redisDataQueue) {
        this.userDAO = userDAO;
        this.redisDataQueue = redisDataQueue;
    }

    @Override
    public UserDO createUser(UserRequest userRequest) {
        final List<UserDO> queryResult = userDAO.findByUserName(userRequest.getUserName());
        if (CollectionUtils.isEmpty(queryResult)) {
            final UserDO user = new UserDO();
            user.setUserName(userRequest.getUserName());
            final UserDO result = userDAO.save(user);
            this.enqueue(result);
            return result;
        } else {
            throw new UserException(UserError.USER_ALREADY_EXISTS);
        }
    }

    @Override
    public UserDO updateScore(UserRequest request) {
        final List<UserDO> queryResult = userDAO.findByUserName(request.getUserName());
        if (CollectionUtils.isEmpty(queryResult)) {
            throw new UserException(UserError.USER_NOT_FOUND);
        } else {
            final UserDO user = queryResult.get(0);
            user.setScore(request.getScore());
            final UserDO result = userDAO.save(user);
            this.enqueue(result);
            return result;
        }
    }

    private void enqueue(UserDO userDO) {
        try {
            redisDataQueue.offer(userDO);
        } catch (Exception e) {
            // just print to console at here, ignore system in-sync error
            // in real production, we need log it in somewhere like ES, Error table, etc
            // then, we need run remedy batch regularly to remedy data into Redis
            System.out.println("failed to add Redis queue" + e.getMessage());
        }
    }
}
