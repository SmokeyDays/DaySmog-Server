package top.smokeydays.web.daysmogserver;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import top.smokeydays.web.daysmogserver.datatype.DSUser;
/*
typeCode: 状态码
    0: 登录成功
    1: 密码错误
    2: 用户不存在

 */


@Data
@AllArgsConstructor
@NoArgsConstructor
class LoginRespond {
    private int typeCode;
    private String respondSession;
}

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
        userMapper.insert(nowUser);
        return "Registered Successfully";
    }

    @PostMapping(path = "/login",consumes = "application/json")
    public @ResponseBody LoginRespond userLogin (@RequestBody DSUser nowUser){
        System.out.println(nowUser.toString());
        /* 条件构造器 */
        QueryWrapper<DSUser> wrapper = Wrappers.query();
        wrapper.likeRight("name",nowUser.getName());
        LoginRespond loginRespond = new LoginRespond();
        DSUser pastUser = userMapper.selectOne(wrapper);
        if(pastUser == null){
            loginRespond.setTypeCode(2);
            return loginRespond;
        }else if(pastUser.getPassword().equals(nowUser.getPassword())){
            loginRespond.setTypeCode(0);
            loginRespond.setRespondSession(SessionManager.generateSession(pastUser.getName()));
            return loginRespond;
        }else{
            loginRespond.setTypeCode(1);
            return loginRespond;
        }

    }

}
