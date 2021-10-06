package top.smokeydays.web.daysmogserver.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserToken {
    private String encryption;

    private String userName;

    public UserToken(String nowSession,String nowUserName){
        encryption = nowSession;userName = nowUserName;
    }
}
