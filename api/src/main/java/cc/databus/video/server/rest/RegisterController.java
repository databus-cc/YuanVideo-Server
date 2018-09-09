package cc.databus.video.server.rest;

import cc.databus.common.JsonResponse;
import cc.databus.common.MD5Utils;
import cc.databus.video.server.service.UserService;
import cc.databus.videos.server.pojo.Users;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private UserService userService;

    @PostMapping("/")
    public JsonResponse register(@RequestBody Users user) {
        // 1. username and password not null and empty
        if (StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword())) {
            return JsonResponse.badRequest("用户名和密码不能为空");
        }

        // 2. username not exist
        if (userService.queryUsernameIfExist(user.getUsername())) {
            return JsonResponse.badRequest(String.format("用户名%s已存在", user.getUsername()));
        }

        // 3. save user and register info
        user.setNickname(user.getUsername());
        user.setPassword(MD5Utils.md5(user.getPassword()));
        user.setFansCounts(0);
        user.setReceiveLikeCounts(0);
        user.setFollowCounts(0);

        userService.saveUser(user);
        return JsonResponse.ok(user);
    }
}
