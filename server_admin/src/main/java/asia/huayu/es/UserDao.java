package asia.huayu.es;


import asia.huayu.entity.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @author RainZiYu
 * @Date 2023/1/13
 */
@Repository
public interface UserDao extends ElasticsearchRepository<User, Long> {
}
