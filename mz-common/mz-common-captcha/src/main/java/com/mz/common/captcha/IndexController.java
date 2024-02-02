package com.mz.common.captcha;

import org.springframework.web.bind.annotation.GetMapping;

public class IndexController {

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/slider")
    public String slider() {
        return "slider";
    }

    @GetMapping("/rotate")
    public String rotate() {
        return "rotate";
    }

    @GetMapping("/concat")
    public String concat() {
        return "concat";
    }

    @GetMapping("/word-click")
    public String wordClick() {
        return "word-click";
    }

    @GetMapping("/bt-image")
    public String btImage() {
        return "bt-image";
    }

}
