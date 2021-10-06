package top.smokeydays.web.daysmogserver.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import top.smokeydays.web.daysmogserver.dao.BlogArticle;
import top.smokeydays.web.daysmogserver.dao.DSUser;
import top.smokeydays.web.daysmogserver.dto.ArticleResponse;
import top.smokeydays.web.daysmogserver.dto.PermissionChecker;
import top.smokeydays.web.daysmogserver.dto.UserToken;
import top.smokeydays.web.daysmogserver.mapper.BlogArticleMapper;
import top.smokeydays.web.daysmogserver.service.BlogArticleService;
import top.smokeydays.web.daysmogserver.service.BlogArticleServiceImpl;
import top.smokeydays.web.daysmogserver.tools.SessionManager;

import java.util.List;

@Controller
@RequestMapping(path = "/blog")
public class BlogArticleController {

    private BlogArticleMapper blogArticleMapper;
    @Autowired
    public void setBlogArticleController(BlogArticleMapper blogArticleMapper){
        this.blogArticleMapper = blogArticleMapper;
    }

    private BlogArticleServiceImpl blogArticleServiceImpl;
    @Autowired
    public void setBlogArticleService(BlogArticleServiceImpl blogArticleServiceImpl){
        this.blogArticleServiceImpl=blogArticleServiceImpl;
    }

    @GetMapping(path = "/get-article")
    public @ResponseBody ArticleResponse<BlogArticle> getArticle (@RequestParam int articleId){
        ArticleResponse<BlogArticle> articleResponse = new ArticleResponse(blogArticleMapper.selectById(articleId), blogArticleServiceImpl.count());
        return articleResponse;
    }

    @GetMapping(path = "/get-article-by-page")
    public @ResponseBody ArticleResponse<List<BlogArticle>> getArticleByPage (@RequestParam int current,@RequestParam int size,@RequestParam String keyword){
        /* 生成页面对象 */
        IPage<BlogArticle> page = new Page<>(current,size);
        /* 根据条件筛选 */
        QueryWrapper<BlogArticle> wrapper = Wrappers.query();
        wrapper.orderByAsc("id").eq("invalid",0);
        if(keyword != null && (!keyword.equals(""))) {
            wrapper.and(i -> i.like("title", keyword).or().like("text", keyword).or().like("description", keyword));
        }

        page = blogArticleMapper.selectPage(page,wrapper);
        ArticleResponse<List<BlogArticle>> articleResponse = new ArticleResponse(page.getRecords(), blogArticleServiceImpl.count(wrapper));
        return articleResponse;
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
        /* 自动填充摘要、作者、人气和可用性 */
        BlogArticle blogArticle = permissionChecker.getBlogArticle();
        if(blogArticle.getDescription() == null && blogArticle.getText().length() > 0){
            blogArticle.setDescription(blogArticle.getText().substring(0,Math.min(10,blogArticle.getText().length())));
            System.out.println("Over");
        }
        if(blogArticle.getAuthor() == null){
            blogArticle.setAuthor(permissionChecker.getUserToken().getUserName());
        }
        blogArticle.setInvalid(0);
        /* 如果未传入 id 则新建，否则修改 */
        if(blogArticle.getId() == null){
            System.out.println(blogArticle.toString());
            blogArticleMapper.insert(blogArticle);
            return "Posted Successfully";
        }else{
            blogArticleMapper.updateById(blogArticle);
            return "Updated Successfully";
        }
    }

    /* 逻辑删除 */
    @DeleteMapping(path = "/delete-article")
    public @ResponseBody int deleteArticle (@RequestParam Integer articleId,@RequestParam String encryption,@RequestParam String userName){
        UserToken userToken = new UserToken(encryption,userName);
        /* 是否登录，以及用户权限是否足够，同时返回错误码 */
        int checkerCode = SessionManager.checkSession(userToken);
        if(checkerCode != 0){
            return checkerCode;
        }
        /* 逻辑删除 */
        BlogArticle blogArticle = blogArticleMapper.selectById(articleId);
        blogArticle.setInvalid(1);
        blogArticleMapper.updateById(blogArticle);
        return 0;
    }

}
