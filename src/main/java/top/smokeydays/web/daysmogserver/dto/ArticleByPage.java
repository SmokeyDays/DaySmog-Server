package top.smokeydays.web.daysmogserver.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.smokeydays.web.daysmogserver.dao.BlogArticle;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleByPage {

    private List<BlogArticle> articleList;
}
