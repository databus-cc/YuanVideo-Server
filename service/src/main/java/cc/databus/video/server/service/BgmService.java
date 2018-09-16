package cc.databus.video.server.service;

import cc.databus.videos.server.pojo.Bgm;

import java.util.List;

/**
 * Bgm (background music) service
 */
public interface BgmService {
    /**
     * get the bgm list
     * @return list of bgms
     */
    List<Bgm> queryBgmList();
}
