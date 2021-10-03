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


@Data
@AllArgsConstructor
@NoArgsConstructor
class LoginRespond {
    /*
    typeCode: 状态码
        0: 登录成功
        1: 密码错误
        2: 用户不存在
     */
    private Integer typeCode;
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
    public @ResponseBody LoginRespond userLogin (@RequestBody DSUser nowUser){
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
