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
     * Persist User instance
     * @param user given user instance
     */
     void saveUser(Users user);

}
