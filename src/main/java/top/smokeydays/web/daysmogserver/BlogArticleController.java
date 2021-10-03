package top.smokeydays.web.daysmogserver;

import com.baomidou.mybatisplus.core.toolkit.Constants;
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

    @GetMapping(path = "/get-article")
    public @ResponseBody BlogArticle getArticle (@RequestParam int articleId){
        BlogArticle ret = new BlogArticle();
        ret = blogArticleMapper.selectById(articleId);
        return ret;
    }

    @PostMapping(path = "/post-article",consumes = "application/json")
    public @ResponseBody String postArticle (@RequestBody BlogArticle nowArticle){
        System.out.println(nowArticle.toString());
        /* 自动填充摘要 */
        if(nowArticle.getDescription() == null && nowArticle.getText().length() > 0){
            nowArticle.setDescription(nowArticle.getText().substring(0,Math.min(10,nowArticle.getText().length())));
            System.out.println("Over");
        }
        /* 如果未传入 id 则新建，否则修改 */
        if(nowArticle.getId() == null){
            System.out.println(nowArticle.toString());
            blogArticleMapper.insert(nowArticle);
            return "Posted Successfully";
        }else{
            blogArticleMapper.updateById(nowArticle);
            return "Updated Successfully";
        }
    }

    @DeleteMapping(path = "/delete-article")
    public @ResponseBody String deleteArticle (@RequestParam int articleId){
        blogArticleMapper.deleteById(articleId);
        return "Delete Successfully";
    }

}
