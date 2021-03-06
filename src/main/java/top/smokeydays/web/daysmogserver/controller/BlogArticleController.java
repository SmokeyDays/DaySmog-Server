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
import top.smokeydays.web.daysmogserver.mapper.UserMapper;
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

    private UserMapper userMapper;
    @Autowired
    public void setUserController(UserMapper userMapper){
        this.userMapper = userMapper;
    }

    @GetMapping(path = "/get-article")
    public @ResponseBody ArticleResponse<BlogArticle> getArticle (@RequestParam int articleId){
        ArticleResponse<BlogArticle> articleResponse = new ArticleResponse(blogArticleMapper.selectById(articleId), blogArticleServiceImpl.count());
        return articleResponse;
    }

    @GetMapping(path = "/get-all-article")
    public @ResponseBody List<BlogArticle> getArticle (){
        List<BlogArticle> articleResponse = blogArticleServiceImpl.list();
        return articleResponse;
    }

    @GetMapping(path = "/get-article-by-page")
    public @ResponseBody ArticleResponse<List<BlogArticle>> getArticleByPage (@RequestParam int current,@RequestParam int size,@RequestParam String keyword){
        /* ?????????????????? */
        IPage<BlogArticle> page = new Page<>(current,size);
        /* ?????????????????? */
        QueryWrapper<BlogArticle> wrapper = Wrappers.query();
        wrapper.orderByAsc("id").eq("invalid",0);
        if(keyword != null && (!keyword.equals(""))) {
            wrapper.and(i -> i.like("title", keyword).or().like("text", keyword).or().like("description", keyword).or().like("tags", keyword));
        }

        page = blogArticleMapper.selectPage(page,wrapper);
        ArticleResponse<List<BlogArticle>> articleResponse = new ArticleResponse(page.getRecords(), blogArticleServiceImpl.count(wrapper));
        return articleResponse;
    }

    @PostMapping(path = "/post-article",consumes = "application/json")
    public @ResponseBody String postArticle (@RequestBody PermissionChecker permissionChecker){
        System.out.println(permissionChecker.toString());
        /* ????????????????????????????????????????????????????????????????????? */
        int checkerCode = SessionManager.checkSession(permissionChecker.getUserToken());
        QueryWrapper<DSUser> wrapper = Wrappers.query();
        wrapper.likeRight("name", permissionChecker.getUserToken().getUserName());
        if(checkerCode == 0 && userMapper.selectOne(wrapper).getPermission() > 0){
            checkerCode = 4;
        }
        if(checkerCode != 0){
            switch(checkerCode){
                case 1:
                    return "Error: Not Login";
                case 2:
                    return "Error: Session Wrong";
                case 3:
                    return "Error: Session Expired";
                case 4:
                    return "Error: Permission Not Enough";
                default:
                    return "Unknown Error";
            }
        }
        /* ???????????????????????????????????????????????? */
        BlogArticle blogArticle = permissionChecker.getBlogArticle();
        if(blogArticle.getDescription() == null && blogArticle.getText().length() > 0){
            blogArticle.setDescription(blogArticle.getText().substring(0,Math.min(200,blogArticle.getText().length())));
            System.out.println("Over");
        }
        if(blogArticle.getAuthor() == null){
            blogArticle.setAuthor(permissionChecker.getUserToken().getUserName());
        }
        blogArticle.setInvalid(0);
        /* ??????????????? id ???????????????????????? */
        if(blogArticle.getId() == null){
            System.out.println(blogArticle.toString());
            blogArticleMapper.insert(blogArticle);
            return "Posted Successfully";
        }else{
            blogArticleMapper.updateById(blogArticle);
            return "Updated Successfully";
        }
    }

    /* ???????????? */
    @DeleteMapping(path = "/delete-article")
    public @ResponseBody int deleteArticle (@RequestParam Integer articleId,@RequestParam String encryption,@RequestParam String userName){
        UserToken userToken = new UserToken(encryption,userName);
        /* ????????????????????????????????????????????????????????????????????? */
        int checkerCode = SessionManager.checkSession(userToken);
        QueryWrapper<DSUser> wrapper = Wrappers.query();
        wrapper.likeRight("name", userName);
        if(checkerCode == 0 && userMapper.selectOne(wrapper).getPermission() > 0){
            checkerCode = 4;
        }
        if(checkerCode != 0){
            return checkerCode;
        }
        /* ???????????? */
        BlogArticle blogArticle = blogArticleMapper.selectById(articleId);
        blogArticle.setInvalid(1);
        blogArticleMapper.updateById(blogArticle);
        return 0;
    }

}
