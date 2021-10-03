package top.smokeydays.web.daysmogserver.datatype;

import lombok.Data;

import java.time.Instant;

@Data
public class UserToken {
    private String encryption;

    private String userName;

    public UserToken(String nowSession,String nowUserName){
        encryption = nowSession;userName = nowUserName;
    }
}
