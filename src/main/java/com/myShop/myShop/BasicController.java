package com.myShop.myShop;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.ZonedDateTime;

@Controller
public class BasicController {

    @GetMapping("/url")
    @ResponseBody
    String url(){
        return ZonedDateTime.now().toString();
    }


}
