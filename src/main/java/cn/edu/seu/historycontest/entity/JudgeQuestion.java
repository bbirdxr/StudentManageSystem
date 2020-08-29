package cn.edu.seu.historycontest.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@TableName("hc_judge_question")
public class JudgeQuestion implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String question;

    private Integer answer;

    @JsonIgnore
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @JsonIgnore
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;


}
