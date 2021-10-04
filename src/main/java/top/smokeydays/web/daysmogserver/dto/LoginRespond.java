package top.smokeydays.web.daysmogserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRespond {
    /*
    typeCode: 状态码
        0: 登录成功
        1: 密码错误
        2: 用户不存在
     */
    private Integer typeCode;
    private String respondSession;
}
