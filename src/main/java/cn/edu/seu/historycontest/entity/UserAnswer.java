package cn.edu.seu.historycontest.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author ${author}
 * @since 2020-08-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("hc_user_answer")
public class UserAnswer implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long uid;

    /**
     * 以","分割
     */
    private String choiceAnswer;

    private String judgeAnswer;

    /**
     * "-1"代表未评分
     */
    private Integer score;


}
