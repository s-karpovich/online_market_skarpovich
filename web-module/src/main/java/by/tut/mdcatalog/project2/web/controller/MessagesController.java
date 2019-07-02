package by.tut.mdcatalog.project2.web.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MessagesController implements ErrorController {

    @GetMapping("/success")
    public String success() { return "messages/success"; }

    @RequestMapping("/error")
    public String handleError() { return "/messages/error"; }

    @Override
    public String getErrorPath() { return "/messages/error"; }

    @GetMapping("/403")
    public String error403() { return "/messages/403"; }

    @GetMapping("/404")
    public String error404() { return "/messages/404"; }
}
