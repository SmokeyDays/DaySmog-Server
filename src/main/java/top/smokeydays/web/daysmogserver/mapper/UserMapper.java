package top.smokeydays.web.daysmogserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.smokeydays.web.daysmogserver.dao.DSUser;

@Mapper
public interface UserMapper extends BaseMapper<DSUser> {

}
