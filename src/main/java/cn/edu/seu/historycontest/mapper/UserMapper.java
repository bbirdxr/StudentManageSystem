package cn.edu.seu.historycontest.mapper;

import cn.edu.seu.historycontest.config.CacheConfig;
import cn.edu.seu.historycontest.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.CacheNamespace;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2020-08-28
 */
@Repository
@CacheNamespace(implementation = CacheConfig.class, eviction = CacheConfig.class)
public interface UserMapper extends BaseMapper<User> {

}
