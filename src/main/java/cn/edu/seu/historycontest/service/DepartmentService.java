package cn.edu.seu.historycontest.service;

import cn.edu.seu.historycontest.entity.Department;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2020-09-09
 */
public interface DepartmentService extends IService<Department> {

    String getNameById(Integer id);
    Integer getIdByName(String name);

}
