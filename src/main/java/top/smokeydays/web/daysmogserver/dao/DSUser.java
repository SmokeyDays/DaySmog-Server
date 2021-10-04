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
@TableName("DSUser")
public class DSUser {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField(value = "name")
    private String name;

    @TableField(value = "passwd")
    private String passwd;

    /*
    permission:
        0: All Permission Including Post
        1: Comment Only
        2: Read Only
     */
    @TableField(value = "permission")
    private Integer permission;

}