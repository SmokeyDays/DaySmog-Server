package top.smokeydays.web.daysmogserver;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import top.smokeydays.web.daysmogserver.datatype.DSUser;

import java.time.Instant;
import java.util.Date;


@Controller
@RequestMapping(path = "/demo")
public class UserController {

    private UserRepository userRepository;

    @Autowired
    public void setUserController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @PostMapping(path = "/add",consumes = "application/json")
    public @ResponseBody String addNewUser (@RequestBody DSUser nowUser){
        userRepository.insert(nowUser);
        return "Registered Successfully";
    }


}
