package top.smokeydays.web.daysmogserver.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class UserSession {
    private String encryption;

    private Instant instant;

    public UserSession(String nowSession,Instant nowInstant){
        encryption = nowSession;instant = nowInstant;
    }

}
