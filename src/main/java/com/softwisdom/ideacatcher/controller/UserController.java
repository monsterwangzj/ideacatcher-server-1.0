package com.softwisdom.ideacatcher.controller;

import com.softwisdom.ideacatcher.enums.UserStatusEnum;
import com.softwisdom.ideacatcher.model.User;
import com.softwisdom.ideacatcher.result.CommonJsonResult;
import com.softwisdom.ideacatcher.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

@Controller
@RequestMapping("/user")
public class UserController {

    @Resource(name = "userService")
    private UserService userService;

    @RequestMapping(value = "/register")
    public ModelAndView register() {
        ModelAndView mv = new ModelAndView();
        mv.addObject("message", "HelloMVC");
        mv.setViewName("users");
        return mv;
    }

    @RequestMapping(value = "/update")
    public ModelAndView update() {
        ModelAndView mv = new ModelAndView();
        mv.addObject("message", "HelloMVC");
        mv.setViewName("users");
        return mv;
    }

    @ResponseBody
    @RequestMapping(value = "/login")
    public CommonJsonResult login(String username, String password) {
        CommonJsonResult commonJsonResult = null;
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            commonJsonResult = setResponseResult(-100, "用户名或密码为空");
        } else {
            boolean loginSucc = false;
            User user = userService.findUserByUsernamenPassword(username, password);
            if (user == null) {
                commonJsonResult = setResponseResult(-110, "用户名或密码错误");
            } else if (user.getStatus() == UserStatusEnum.USER_STATUS_FREEZE.getCode()) {
                commonJsonResult = setResponseResult(-120, "用户已冻结");
            } else {
                // 新建session TODO
                commonJsonResult = setResponseResult(0, "登录成功");
            }
        }

        return commonJsonResult;
    }

    @RequestMapping(value = "/logout")
    public ModelAndView logout() {
        int c = userService.userCount();

        ModelAndView mv = new ModelAndView();
        mv.addObject("message", c);
        mv.setViewName("users");
        return mv;
    }

    private CommonJsonResult setResponseResult(int code, String desc) {
        CommonJsonResult commonJsonResult = new CommonJsonResult();
        commonJsonResult.setSuccess(true);
        commonJsonResult.setCode(code);
        commonJsonResult.setDesc(desc);

        return commonJsonResult;
    }

}
