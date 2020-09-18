package cn.edu.seu.historycontest.service.impl;

import cn.edu.seu.historycontest.entity.Department;
import cn.edu.seu.historycontest.mapper.DepartmentMapper;
import cn.edu.seu.historycontest.service.DepartmentService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2020-09-09
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements DepartmentService {

    @Override
    public String getNameById(Integer id) {
        Department department = getById(id);
        if (department != null)
            return department.getName();
        else
            return "无法识别";
    }

    @Override
    public Integer getIdByName(String name) {
        QueryWrapper<Department> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", name);
        Department department = getOne(queryWrapper);
        return department.getId();
    }

    @Override
    public Integer getIdBySid(String sid) {
        QueryWrapper<Department> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("prefix", sid.substring(0, 2));
        Department department = getOne(queryWrapper);
        if (department != null)
            return department.getId();
        return -1;
    }
}
