package cc.databus.video.server.service;

import cc.databus.videos.server.pojo.Users;

public interface UserService {

    /**
     * Check if the username already exist
     * @param username given username
     * @return true if already exist
     */
     boolean queryUsernameIfExist(String username);

    /**
     * Query the user by username and password
     * @param username username
     * @param password password
     * @return User whose username and password match given one.
     */
     Users queryUserForLogin(String username, String password);

    /**
     * Persist User instance
     * @param user given user instance
     */
     void saveUser(Users user);

}
