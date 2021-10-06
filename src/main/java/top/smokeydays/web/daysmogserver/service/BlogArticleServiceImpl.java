package top.smokeydays.web.daysmogserver.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.smokeydays.web.daysmogserver.dao.BlogArticle;
import top.smokeydays.web.daysmogserver.mapper.BlogArticleMapper;

@Service
public class BlogArticleServiceImpl extends ServiceImpl<BlogArticleMapper, BlogArticle> implements BlogArticleService {

}
