package top.smokeydays.web.daysmogserver;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.smokeydays.web.daysmogserver.datatype.BlogArticle;

@Mapper
public interface BlogArticleMapper extends BaseMapper<BlogArticle> {

}
