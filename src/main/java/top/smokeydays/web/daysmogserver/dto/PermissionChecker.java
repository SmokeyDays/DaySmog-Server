package top.smokeydays.web.daysmogserver.dto;

import lombok.Data;
import top.smokeydays.web.daysmogserver.dao.BlogArticle;

@Data
public class PermissionChecker {
    private BlogArticle blogArticle;
    private UserToken userToken;
}
