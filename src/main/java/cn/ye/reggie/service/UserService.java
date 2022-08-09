package cn.ye.reggie.service;

import cn.ye.reggie.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

public interface UserService extends IService<User> {
    public void sendMsg(String to,String subject,String context);

}
