package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)

public class Gasmeter {
    private static final long serialVersionUID = 1L;

//    @TableField("F_CUSTOMERCODE")
    private String F_CUSTOMERCODE;

    private String F_KEY;
}
