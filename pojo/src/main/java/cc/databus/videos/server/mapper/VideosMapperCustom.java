package cc.databus.videos.server.mapper;

import cc.databus.videos.server.vo.VideosVO;
import cc.databus.videos.utils.MyMapper;

import java.util.List;

public interface VideosMapperCustom extends MyMapper<VideosVO> {
    public List<VideosVO> getVideos();
}
