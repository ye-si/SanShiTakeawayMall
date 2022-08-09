package cn.ye.reggie.controller;

import cn.ye.reggie.common.Result;
import cn.ye.reggie.entity.User;
import cn.ye.reggie.service.UserService;
import cn.ye.reggie.utils.SMSUtils;
import cn.ye.reggie.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/sendMsg")
    public Result<String> sendMsg(@RequestBody User user, HttpSession session) {
        // 获取手机号
        String phone = user.getPhone();
        if (!(StringUtils.isEmpty(phone))) {
            // 生成随机的4为验证码
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            // 调用API
            SMSUtils.sendMessage("外卖","",phone,code);
            // 保存到session
            session.setAttribute(phone,code);

            return Result.success("发送成功");
        }
        return Result.error("发送失败");
    }
}
