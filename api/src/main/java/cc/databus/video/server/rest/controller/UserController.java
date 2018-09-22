package cc.databus.video.server.rest.controller;

import cc.databus.common.JsonResponse;
import cc.databus.video.server.service.UserService;
import cc.databus.videos.server.pojo.Users;
import cc.databus.videos.server.vo.UserVO;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@Api(value = "用户相关业务的接口", tags = {"用户相关业务的controller"})
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    public static final String FILE_SPACE = "/Users/jianyuan/Personal/Codes/yuanvideo/api/space";

    /**
     * user face image server pathL:
     * <code>{ServerUrl}/face/{imageName}</code>
     * @param userId
     * @param files
     * @return
     */
    @PostMapping("/uploadFace")
    public JsonResponse uploadFace(String userId, @RequestParam("file") MultipartFile[] files) {

        if (StringUtils.isBlank(userId)) {
            return JsonResponse.badRequest("用户id不能为空");
        }

        if (files == null || files.length == 0) {
            return JsonResponse.badRequest("上传出错.");
        }

        FileOutputStream fos = null;
        InputStream inputStream = null;

        String fileName = null;
        try {
            fileName = files[0].getOriginalFilename();
            if (StringUtils.isNotBlank(fileName)) {
                Path directoryPath = Paths.get(FILE_SPACE, "face");
                File directoryFile = directoryPath.toFile();
                if (!directoryFile.exists()) {
                    directoryFile.mkdirs();
                }

                File outputFile = new File(directoryFile, fileName);

                fos = new FileOutputStream(outputFile);
                inputStream = files[0].getInputStream();
                IOUtils.copy(inputStream, fos);
                fos.flush();
            }
        }
        catch (IOException e) {
            // TODO: need a more
            e.printStackTrace();
            JsonResponse.badRequest("上传出错 - " + ExceptionUtils.getRootCauseMessage(e));
        }
        finally {
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(fos);
        }

        if (StringUtils.isNotBlank(fileName)) {
            Users user = new Users();
            user.setId(userId);
            user.setFaceImage(fileName);

            userService.updateUser(user);
        }

        return JsonResponse.ok(fileName);
    }


    @GetMapping("/{userId}")
    public JsonResponse getUser(@PathVariable String userId) {
        if (StringUtils.isBlank(userId)) {
            return JsonResponse.badRequest("用户id不能是空");
        }
        Users user =  userService.getUser(userId);

        if (user == null) {
            return JsonResponse.badRequest("用户不存在");
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        userVO.setPassword("");

        return JsonResponse.ok(userVO);
    }


}
