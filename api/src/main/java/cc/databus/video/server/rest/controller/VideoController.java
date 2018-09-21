package cc.databus.video.server.rest.controller;

import cc.databus.common.JsonResponse;
import cc.databus.common.UUIDGenerator;
import cc.databus.video.server.service.VideoService;
import cc.databus.video.util.VideoStatus;
import cc.databus.videos.server.pojo.Videos;
import cc.databus.videos.utils.PagedResult;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import static cc.databus.video.server.rest.controller.UserController.FILE_SPACE;

@Api(value = "视频接口", tags = {"视频controller"})
@RequestMapping("/videos")
@RestController
public class VideoController {

    @Autowired
    private VideoService videoService;

    @PostMapping(value = "/showAll")
    public JsonResponse showAll(Integer page) {
        if (page == null) {
            page = 1;
        }


        PagedResult result = videoService.getAllVideos(page, 5);
        return JsonResponse.ok(result);
    }

    @ApiOperation(value = "上传视频封面", notes = "上传视频封面的接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户Id", required = true, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "bgmId", value = "背景音乐Id", required = true, dataType = "String", paramType = "form"),
    })
    @PostMapping(value = "/uploadCover", headers = {"content-type=multipart/form-data"})
    public JsonResponse uploadCover(String userId, String videoId, @ApiParam(value = "短视频封面", required = true) MultipartFile file) {
        if (StringUtils.isBlank(userId)) {
            return JsonResponse.badRequest("用户Id不能是空。");
        }

        if (StringUtils.isBlank(videoId)) {
            return JsonResponse.badRequest("视频Id不能是空。");
        }
        String coverName = file.getOriginalFilename();
        try {
            File dir = Paths.get(FILE_SPACE, userId, "video", "cover").toFile();
            if (!dir.exists()) {
                dir.mkdirs();
            }
            IOUtils.copy(file.getInputStream(), new FileOutputStream(new File(dir, coverName)));
        }
        catch (IOException e) {
            e.printStackTrace();
            return JsonResponse.badRequest("上传视频封面失败 - " + ExceptionUtils.getMessage(e));
        }


        videoService.updateCover(videoId, coverName);

        return JsonResponse.ok();
    }

    @ApiOperation(value = "上传视频", notes = "上传视频的接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户Id", required = true, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "bgmId", value = "背景音乐Id", required = false, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "duration", value = "视频长度", required = true, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "width", value = "视频宽度", required = true, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "height", value = "视频高度", required = true, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "desc", value = "视频描述", required = false, dataType = "String", paramType = "form"),
    })
    @PostMapping(value = "/", headers = {"content-type=multipart/form-data"})
    public JsonResponse uploadVideo(String userId,
                                   String bgmId,
                                   double duration,
                                   int width,
                                   int height,
                                   String desc,
                                   @ApiParam(value = "短视频", required = true) MultipartFile file) {

        if (StringUtils.isBlank(userId)) {
            return JsonResponse.badRequest("用户id不能为空");
        }

        if (file == null) {
            return JsonResponse.badRequest("上传出错.");
        }

        FileOutputStream fos = null;
        InputStream inputStream = null;

        String fileName = null;
        try {
            fileName = file.getOriginalFilename();
            if (StringUtils.isNotBlank(fileName)) {
                Path directoryPath = Paths.get(FILE_SPACE, userId, "video");
                File directoryFile = directoryPath.toFile();
                if (!directoryFile.exists()) {
                    directoryFile.mkdir();
                }

                File outputFile = new File(directoryFile, fileName);

                fos = new FileOutputStream(outputFile);
                inputStream = file.getInputStream();
                IOUtils.copy(inputStream, fos);
                fos.flush();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            JsonResponse.badRequest("上传出错 - " + ExceptionUtils.getRootCauseMessage(e));
        }
        finally {
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(fos);
        }

        // 保存视频到数据库
        Videos videos = new Videos();
        videos.setId(UUIDGenerator.generate());
        videos.setUserId(userId);
        videos.setAudioId(bgmId);
        videos.setVideoPath(fileName);
        videos.setVideoSeconds((float) duration);
        videos.setVideoHeight(height);
        videos.setVideoWidth(width);
        videos.setVideoDesc(desc);
        videos.setStatus(VideoStatus.OK.value);
        videos.setCreateTime(new Date());

        videoService.saveVideo(videos);

        return JsonResponse.ok(videos.getId());
    }
}
