package top.smokeydays.web.daysmogserver;

import org.springframework.data.repository.CrudRepository;
import top.smokeydays.web.daysmogserver.datatype.DSUser;

public interface UserRepository extends CrudRepository<DSUser, Long> {

}
