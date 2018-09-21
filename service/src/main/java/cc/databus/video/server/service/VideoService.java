package cc.databus.video.server.service;

import cc.databus.videos.server.pojo.Videos;
import cc.databus.videos.utils.PagedResult;

public interface VideoService {
    /**
     * 保存视频到数据库
     * @param videos 视频
     */
    void saveVideo(Videos videos);

    void updateCover(String videoId, String coverPath);

    /**
     * 分页查询视频列表
     * @param page
     * @param pageSize
     * @return
     */
    PagedResult getAllVideos(Integer page, Integer pageSize);
}
