package cc.databus.video.server.service.impl;

import cc.databus.common.UUIDGenerator;
import cc.databus.video.server.service.UserService;
import cc.databus.videos.server.mapper.UsersMapper;
import cc.databus.videos.server.pojo.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersMapper userMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean queryUsernameIfExist(String username) {
        Users user = new Users();
        user.setUsername(username);
        return Objects.isNull(userMapper.selectOne(user));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveUser(Users user) {
        user.setId(UUIDGenerator.generate());
        userMapper.insert(user);
    }
}
