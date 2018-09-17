package cc.databus.video.server.service.impl;

import cc.databus.common.UUIDGenerator;
import cc.databus.video.server.service.VideoService;
import cc.databus.videos.server.mapper.VideosMapper;
import cc.databus.videos.server.pojo.Videos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideosMapper videosMapper;


    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveVideo(Videos videos) {
        videos.setId(UUIDGenerator.generate());
        videosMapper.insertSelective(videos);
    }
}
