package com.softwisdom.ideacatcher.controller;

import com.softwisdom.ideacatcher.enums.UserStatusEnum;
import com.softwisdom.ideacatcher.model.User;
import com.softwisdom.ideacatcher.result.LoginJsonResult;
import com.softwisdom.ideacatcher.service.UserService;
import com.softwisdom.ideacatcher.util.RedisUtil;
import com.softwisdom.ideacatcher.util.ResponseUtil;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/user")
public class UserController {

    @Resource(name = "userService")
    private UserService userService;

//    public static int VIDEO_TOTAL_NUMBER = 800000;
//    public static Map<String, Long> videoMapBuffer = Collections.synchronizedMap(new LRUMap(VIDEO_TOTAL_NUMBER));

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
    @RequestMapping(value = "/login", produces = "text/html;charset=UTF-8")
    public void login(String username, String password, HttpServletResponse resp) {
        LoginJsonResult loginJsonResult = null;
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            loginJsonResult = setResponseResult(-100, null, "用户名或密码为空");
        } else {
            boolean loginSucc = false;
            User user = userService.findUserByUsernamenPassword(username, password);
            if (user == null) {
                loginJsonResult = setResponseResult(-110, null, "用户名或密码不正确");
            } else if (user.getStatus() == UserStatusEnum.USER_STATUS_FREEZE.getCode()) {
                loginJsonResult = setResponseResult(-120, null, "用户已冻结");
            } else {
                Long userId = user.getId();
                RedisUtil.setLong("user-" + userId, 1000L); // 登录session

                loginJsonResult = setResponseResult(0, userId.toString(), "登录成功");
            }
        }

        ResponseUtil.sendMessageNoCache(resp, JSONObject.fromObject(loginJsonResult).toString());
    }

    @RequestMapping(value = "/logout")
    public ModelAndView logout() {
        int c = userService.userCount();

        ModelAndView mv = new ModelAndView();
        mv.addObject("message", c);
        mv.setViewName("users");
        return mv;
    }

    private LoginJsonResult setResponseResult(int code, String loginToken, String desc) {
        LoginJsonResult loginJsonResult = new LoginJsonResult();
        loginJsonResult.setSuccess(true);
        loginJsonResult.setLoginToken(loginToken);
        loginJsonResult.setCode(code);
        loginJsonResult.setDesc(desc);

        return loginJsonResult;
    }

}
