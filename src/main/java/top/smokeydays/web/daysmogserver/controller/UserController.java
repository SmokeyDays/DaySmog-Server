package top.smokeydays.web.daysmogserver.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import top.smokeydays.web.daysmogserver.dto.LoginRespond;
import top.smokeydays.web.daysmogserver.tools.SessionManager;
import top.smokeydays.web.daysmogserver.mapper.UserMapper;
import top.smokeydays.web.daysmogserver.dao.DSUser;


@Controller
@RequestMapping(path = "/user")
public class UserController {

    private UserMapper userMapper;

    @Autowired
    public void setUserController(UserMapper userMapper){
        this.userMapper = userMapper;
    }

    @PostMapping(path = "/register",consumes = "application/json")
    public @ResponseBody String userRegister (@RequestBody DSUser nowUser){
        System.out.println(nowUser.toString());
        /* 搜索用户并检验是否存在 */
        QueryWrapper<DSUser> wrapper = Wrappers.query();
        wrapper.likeRight("name",nowUser.getName());
        DSUser pastUser = userMapper.selectOne(wrapper);
        if(pastUser != null){
            return "Register Failed: User Already Existed";
        }else{
            nowUser.setPermission(2);
            userMapper.insert(nowUser);
            return "Registered Successfully";
        }
    }

    @PostMapping(path = "/login",consumes = "application/json")
    public @ResponseBody
    LoginRespond userLogin (@RequestBody DSUser nowUser){
        System.out.println(nowUser.toString());
        /* 搜索用户 */
        QueryWrapper<DSUser> wrapper = Wrappers.query();
        wrapper.likeRight("name",nowUser.getName());
        DSUser pastUser = userMapper.selectOne(wrapper);
        /* 生成返回对象 */
        LoginRespond loginRespond = new LoginRespond();
        if(pastUser == null){
            loginRespond.setTypeCode(2);
            return loginRespond;
        }else if(pastUser.getPasswd().equals(nowUser.getPasswd())){
            loginRespond.setTypeCode(0);
            loginRespond.setRespondSession(SessionManager.generateSession(pastUser.getName()));
            return loginRespond;
        }else{
            loginRespond.setTypeCode(1);
            return loginRespond;
        }

    }

}
