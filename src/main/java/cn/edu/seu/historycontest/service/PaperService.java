package cn.edu.seu.historycontest.service;

import cn.edu.seu.historycontest.entity.Paper;
import cn.edu.seu.historycontest.payload.DetailedPaper;
import cn.edu.seu.historycontest.security.UserPrincipal;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2020-08-28
 */
public interface PaperService extends IService<Paper> {
    DetailedPaper generatePaper(UserPrincipal userPrincipal);
    void calibrateTime(Long userId, Date time);
    DetailedPaper getDetailedPaper(Long userId);
}
