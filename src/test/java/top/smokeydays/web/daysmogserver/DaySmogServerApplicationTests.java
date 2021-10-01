package top.smokeydays.web.daysmogserver;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@RestController
@EnableAutoConfiguration
public class DaySmogServerApplicationTests {

    @RequestMapping("/")
    String Home(){
        return "Hello World";
    }
    public static void main(String[] args){
        SpringApplication.run(DaySmogServerApplicationTests.class, args);
    }
}
