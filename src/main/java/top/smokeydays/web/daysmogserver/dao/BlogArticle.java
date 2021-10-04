package top.smokeydays.web.daysmogserver.dao;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("blog_article")
public class BlogArticle {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField(value = "title")
    private String title;

    @TableField(value = "description")
    private String description;

    @TableField(value = "text")
    private String text;

    @TableField(value = "author")
    private String author;

    @TableField(value = "tags")
    private String tags;

    @TableField(value = "popularity")
    private int popularity;

    @TableField(value = "invalid")
    private int invalid;
}