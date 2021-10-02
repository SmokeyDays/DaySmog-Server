package top.smokeydays.web.daysmogserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import top.smokeydays.web.daysmogserver.datatype.BlogArticle;
import top.smokeydays.web.daysmogserver.datatype.DSUser;


@Controller
@RequestMapping(path = "/blog")
public class BlogArticleController {

    private BlogArticleMapper blogArticleMapper;

    @Autowired
    public void setBlogArticleController(BlogArticleMapper blogArticleMapper){
        this.blogArticleMapper = blogArticleMapper;
    }

    @GetMapping(path = "/request-article")
    public @ResponseBody BlogArticle requestArticle (@RequestParam int articleId){
        BlogArticle ret = new BlogArticle();
        return ret;
    }

    @PostMapping(path = "/post-article",consumes = "application/json")
    public @ResponseBody String postArticle (@RequestBody BlogArticle nowArticle){
        System.out.println(nowArticle.toString());
//        if(nowArticle.getDesc() == null && nowArticle.getText().length() > 0){
//            nowArticle.setDesc(nowArticle.getText().substring(0,Math.min(10,nowArticle.getText().length())));
//            System.out.println("Over");
//        }
        System.out.println(nowArticle.toString());
        blogArticleMapper.insert(nowArticle);
        return "Posted Successfully";
    }

}
