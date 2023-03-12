package jared.cheng.service;

import jared.cheng.data.UserDO;
import jared.cheng.resource.UserRequest;

/**
 * Interface for user application service layer
 *
 * @author jared
 * @since 2023/3/12
 */
public interface IUserService {

    UserDO createUser(UserRequest userRequest);
    UserDO updateScore(UserRequest userRequest);
}
