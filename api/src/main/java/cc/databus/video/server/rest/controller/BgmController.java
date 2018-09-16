package cc.databus.video.server.rest.controller;

import cc.databus.common.JsonResponse;
import cc.databus.video.server.service.BgmService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Api(value = "背景音乐接口", tags = {"背景音乐controller"})
@RequestMapping("/bgm")
@RestController
public class BgmController {
    @Autowired
    private BgmService bgmService;

    @ApiOperation(value = "获取背景音乐列表", notes = "获取背景音乐列表的接口")
    @GetMapping("/")
    public JsonResponse getAll() {
        return JsonResponse.ok(bgmService.queryBgmList());
    }
}
