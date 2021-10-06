package top.smokeydays.web.daysmogserver.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import top.smokeydays.web.daysmogserver.dao.BlogArticle;

import java.time.Instant;

@Data
@NoArgsConstructor
public class ArticleResponse<T> {
    private T body;
    private int total;
    public ArticleResponse(T body, int total){
        this.body = body;this.total = total;
    }
}
