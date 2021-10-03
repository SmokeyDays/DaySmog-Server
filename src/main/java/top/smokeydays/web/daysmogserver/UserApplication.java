package top.smokeydays.web.daysmogserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import top.smokeydays.web.daysmogserver.datatype.UserSession;

import java.util.concurrent.ConcurrentHashMap;

@SpringBootApplication
public class UserApplication {

    public static void main(String[] args){
        SpringApplication.run(UserApplication.class, args);
    }
}
