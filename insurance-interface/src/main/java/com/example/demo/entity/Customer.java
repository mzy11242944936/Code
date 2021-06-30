package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("T_CUSTOMER")
public class Customer implements Serializable {
    private static final long serialVersionUID = 1L;

//    @TableId("F_KEY")
//    private String fKey;

    @TableField("F_CUSTOMERCODE")
    private String fCustomercode;
//    private Gasmeter gasmeter;

    @TableField("F_CUSTOMERNAME")
    private String fCustomername;

    /**
     * 电话
     */
    @TableField("F_PHONE")
    private String fPhone;

    /**
     * 具体位置（行政区+道/乡+村+所属小区+楼号+门栋号+房间号）
     */
    @TableField("F_ADDRESS")
    private String fAddress;

    /**
     * 证件类型
     */
    @TableField("F_CARDTYPE")
    private String fCardtype;
    /**
     * 证件号
     */
    @TableField("F_CARDNO")
    private String fCardno;

    @TableField("F_FLAG")
    private String fFlag;

}
