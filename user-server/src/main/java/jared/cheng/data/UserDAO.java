package jared.cheng.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author jared
 * @since 2023/3/12
 */
@Repository
public interface UserDAO extends CrudRepository<UserDO, Integer> {

    List<UserDO> findByUserName(String userName);
}
