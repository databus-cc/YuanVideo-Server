package cc.databus.video.server.service.impl;

import cc.databus.common.UUIDGenerator;
import cc.databus.video.server.service.UserService;
import cc.databus.videos.server.mapper.UsersMapper;
import cc.databus.videos.server.pojo.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

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
        return Objects.nonNull(userMapper.selectOne(user));
    }

    @Override
    public Users queryUserForLogin(String username, String password) {
        Example userExample = new Example(Users.class);
        Example.Criteria criteria = userExample.createCriteria();
        criteria.andEqualTo("username", username)
                .andEqualTo("password", password);

        return userMapper.selectOneByExample(userExample);
    }


    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveUser(Users user) {
        user.setId(UUIDGenerator.generate());
        userMapper.insert(user);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateUser(Users user) {
        Example userExample = new Example(Users.class);
        Example.Criteria criteria = userExample.createCriteria();
        criteria.andEqualTo("id", user.getId());
        userMapper.updateByExampleSelective(user, userExample);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Users getUser(String userId) {
        Example example = new Example(Users.class);
        example.createCriteria()
                .andEqualTo("id", userId);
        return userMapper.selectOneByExample(example);
    }
}
