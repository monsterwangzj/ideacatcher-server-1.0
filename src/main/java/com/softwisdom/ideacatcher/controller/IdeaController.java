package com.softwisdom.ideacatcher.controller;

import com.softwisdom.ideacatcher.service.IdeaService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

@Controller
@RequestMapping("/idea")
public class IdeaController {

    @Resource(name = "ideaService")
    private IdeaService ideaService;

    @RequestMapping(value = "/save", method = RequestMethod.GET)
    public ModelAndView save() {
        ModelAndView mv = new ModelAndView();
        mv.addObject("message", "HelloMVC");
        mv.setViewName("users");
        return mv;
    }

}
