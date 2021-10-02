package top.smokeydays.web.daysmogserver.datatype;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("BlogArticle")
public class BlogArticle {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField(value = "title")
    private String title;

    @TableField(value = "desc")
    private String desc;

    @TableField(value = "text")
    private String text;

    @TableField(value = "author")
    private String author;

    @TableField(value = "tags")
    private String tags;
}