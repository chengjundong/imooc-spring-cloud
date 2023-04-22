package jared.cheng.resource;

import java.util.List;

/**
 * @author jared
 * @since 2023/4/22
 */
public class AsyncUserRequest {

    private List<UserRequest> users;

    public List<UserRequest> getUsers() {
        return users;
    }

    public void setUsers(List<UserRequest> users) {
        this.users = users;
    }
}
