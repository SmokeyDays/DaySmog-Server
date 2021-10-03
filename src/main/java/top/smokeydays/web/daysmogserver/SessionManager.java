package top.smokeydays.web.daysmogserver;

import top.smokeydays.web.daysmogserver.datatype.UserSession;
import top.smokeydays.web.daysmogserver.datatype.UserToken;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;
import java.util.UUID;

public class SessionManager {

    static ConcurrentHashMap<String, UserSession> sessionSaver = new ConcurrentHashMap<>();

    public static String generateSession(String userName){
        if(userName == null){
            System.out.println("Error: Invalid User Name.");
            return null;
        }
        /* 生成时间戳 */
        Instant nowInstant = Instant.now();
        /* 检验 Session 是否已经存在且未过期 */
        UserSession pastUserSession = sessionSaver.get(userName);
        if(pastUserSession != null && pastUserSession.getInstant().isAfter(nowInstant)){
            System.out.println("Time Stamp:"+pastUserSession.getInstant().toString()+" and "+nowInstant.toString());
            return pastUserSession.getEncryption();
        };
        /* 生成新 Session */
        String nowSession = UUID.randomUUID().toString();
        /* 处理时间戳 */
        nowInstant = nowInstant.plus(Duration.ofDays(1));

        UserSession userSession = new UserSession(nowSession,nowInstant);
        sessionSaver.put(userName,userSession);
        return nowSession;
    }

    /*
    返回状态码。
        0: 正常
        1: 用户未登录
        2: Session 错误
        3: Session 已过期
     */
    public static int checkSession(UserToken userToken){
        UserSession pastUserSession = sessionSaver.get(userToken.getUserName());
        Instant nowInstant = Instant.now();
        if(pastUserSession == null){
            return 1;
        }
        if(!pastUserSession.getEncryption().equals(userToken.getEncryption())){
            return 2;
        }
        if(!pastUserSession.getInstant().isAfter(nowInstant)){
            return 3;
        }
        return 0;
    }

}
