package top.smokeydays.web.daysmogserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import top.smokeydays.web.daysmogserver.datatype.DSUser;


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
    public @ResponseBody String userLogin (@RequestBody DSUser nowUser){
        System.out.println(nowUser.toString());

        if(true){
            return "Login Successfully";
        }else{
            return "Login Failed";
        }

    }

}
