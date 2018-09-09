package cc.databus.video.server.rest;

import cc.databus.common.JsonResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
public class RegisterController {
    @PostMapping("/")
    public JsonResponse register() {

        // 1. username and password not null and empty
        // 2. username not exist
        // 3. save user and register info

        return JsonResponse.ok();
    }
}
