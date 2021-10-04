package top.smokeydays.web.daysmogserver.tools;

import org.springframework.beans.factory.annotation.Autowired;
import top.smokeydays.web.daysmogserver.mapper.BlogArticleMapper;

public class AdminManager {

    private BlogArticleMapper blogArticleMapper;
    @Autowired
    public void setBlogArticleController(BlogArticleMapper blogArticleMapper){
        this.blogArticleMapper = blogArticleMapper;
    }

    public void deleteArticleCompeletely(int articleId){
        blogArticleMapper.deleteById(articleId);
    }

}
