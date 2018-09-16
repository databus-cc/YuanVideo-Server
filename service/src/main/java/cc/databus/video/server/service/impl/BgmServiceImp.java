package cc.databus.video.server.service.impl;

import cc.databus.video.server.service.BgmService;
import cc.databus.videos.server.mapper.BgmMapper;
import cc.databus.videos.server.pojo.Bgm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BgmServiceImp implements BgmService {

    @Autowired
    private BgmMapper bgmMapper;

    @Override
    public List<Bgm> queryBgmList() {
        return bgmMapper.selectAll();
    }
}
