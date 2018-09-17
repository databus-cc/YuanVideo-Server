package cc.databus.video.server.service;

import cc.databus.videos.server.pojo.Videos;

public interface VideoService {
    /**
     * 保存视频到数据库
     * @param videos 视频
     */
    void saveVideo(Videos videos);
}
