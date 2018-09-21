package cc.databus.video.server.service.impl;

import cc.databus.video.server.service.VideoService;
import cc.databus.videos.server.mapper.VideosMapper;
import cc.databus.videos.server.mapper.VideosMapperCustom;
import cc.databus.videos.server.pojo.Videos;
import cc.databus.videos.server.vo.VideosVO;
import cc.databus.videos.utils.PagedResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideosMapper videosMapper;

    @Autowired
    private VideosMapperCustom videosMapperCustom;


    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveVideo(Videos videos) {
        videosMapper.insertSelective(videos);
    }

    @Override
    public void updateCover(String videoId, String coverPath) {
        Videos videos = new Videos();
        videos.setId(videoId);
        videos.setCoverPath(coverPath);
        System.out.printf("video=%s,cover=%s\n", videoId, coverPath);
        videosMapper.updateByPrimaryKeySelective(videos);
    }

    @Override
    public PagedResult getAllVideos(Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<VideosVO> list = videosMapperCustom.getVideos();
        PageInfo<VideosVO> pageList = new PageInfo<>(list);
        PagedResult pagedResult = new PagedResult();
        pagedResult.setPage(page);
        pagedResult.setTotal(pageList.getPages());
        pagedResult.setRows(list);
        pagedResult.setRecords(pageList.getTotal());

        return pagedResult;
    }
}
