package top.smokeydays.web.daysmogserver;

import com.baomidou.mybatisplus.core.toolkit.Constants;
import lombok.Data;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import top.smokeydays.web.daysmogserver.*;
import top.smokeydays.web.daysmogserver.datatype.BlogArticle;
import top.smokeydays.web.daysmogserver.datatype.UserSession;
import top.smokeydays.web.daysmogserver.datatype.UserToken;

@Data
class PermissionChecker {
    private BlogArticle blogArticle;
    private UserToken userToken;
}

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
    public @ResponseBody String postArticle (@RequestBody PermissionChecker permissionChecker){
        System.out.println(permissionChecker.toString());
        /* 是否登录，以及用户权限是否足够，同时返回错误码 */
        int checkerCode = SessionManager.checkSession(permissionChecker.getUserToken());
        if(checkerCode != 0){
            switch(checkerCode){
                case 1:
                    return "Error: Not Login";
                case 2:
                    return "Error: Session Wrong";
                case 3:
                    return "Error: Session Expired";
            }
        }
        /* 自动填充摘要和作者 */
        if(permissionChecker.getBlogArticle().getDescription() == null && permissionChecker.getBlogArticle().getText().length() > 0){
            permissionChecker.getBlogArticle().setDescription(permissionChecker.getBlogArticle().getText().substring(0,Math.min(10,permissionChecker.getBlogArticle().getText().length())));
            System.out.println("Over");
        }
        if(permissionChecker.getBlogArticle().getAuthor() == null){
            permissionChecker.getBlogArticle().setAuthor(permissionChecker.getUserToken().getUserName());
        }
        /* 如果未传入 id 则新建，否则修改 */
        if(permissionChecker.getBlogArticle().getId() == null){
            System.out.println(permissionChecker.getBlogArticle().toString());
            blogArticleMapper.insert(permissionChecker.getBlogArticle());
            return "Posted Successfully";
        }else{
            blogArticleMapper.updateById(permissionChecker.getBlogArticle());
            return "Updated Successfully";
        }
    }

    @DeleteMapping(path = "/delete-article")
    public @ResponseBody int deleteArticle (@RequestParam Integer articleId,@RequestParam String encryption,@RequestParam String userName){
        UserToken userToken = new UserToken(encryption,userName);
        /* 是否登录，以及用户权限是否足够，同时返回错误码 */
        int checkerCode = SessionManager.checkSession(userToken);
        if(checkerCode != 0){
            return checkerCode;
        }
        blogArticleMapper.deleteById(articleId);
        return 0;
    }

}
