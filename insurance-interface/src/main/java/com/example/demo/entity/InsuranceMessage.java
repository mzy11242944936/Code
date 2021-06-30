package com.example.demo.entity;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("T_INSURANCE")
public class InsuranceMessage implements Serializable {
    private static final long serialVersionUID = 1L;
    /*保单信息*/

    //主键
    @TableField("F_KEY")
    private String fKey;

    //客户编号
    @TableField("ACCTID")
    private String accId;

    //表具编号
    @TableField("APPGASNO")
    private String appGasNO;

    //保单号
    @TableField("POLICYNO")
    private String policyNo;

    //险种代码
    @TableField("INSURANCECODE")
    private String insuranceCode;

    //险种名称
    @TableField("INSURANCENAME")
    private String insuranceName;

    //保费
    @TableField("PREMIUM")
    private String premium;

    //保险起期
    @TableField("INSURSTARTDATE")
    private String insurStartDate;

    //保险止期
    @TableField("INSURENDDATE")
    private String insurEndDate;

    //员工编码
    @TableField("CCLNTCDE")
    private String CClntCde;

    /*投保人信息*/

    //投保人姓名
    @TableField("APPNAME")
    private String appName;

    //投保人联系人
    @TableField("APPCONTNAME")
    private String appContName;

    //投保人证件类型01身份证97纳税人识别号99其他
    @TableField("APPIDTYPE")
    private String appIDType;

    //投保人证件号码
    @TableField("APPID")
    private String appID;

    //投保人出生日期yyyy-mm-dd
    @TableField("APPBTHD")
    private String appBthd;

    // 投保人性别1男2女
    @TableField("APPGEND")
    private String appGend;

    // 投保人地址(标的地址)
    @TableField("APPADDR")
    private String appAddr;

    // 投保人电话
    @TableField("APPMOBILE")
    private String appMobile;

    // 投保人邮箱
    @TableField("APPEMAIL")
    private String appEmail;

    /*被保人信息*/
    
    // 被保人姓名
    @TableField("INSUREDNAME")
    private String insuredName;

    // 被保人联系人
    @TableField("INSUREDCONTNAME")
    private String insuredContName;

    // 被保人证件类型01身份证97纳税人识别号99其他
    @TableField("INSUREDIDTYPE")
    private String insuredIDType;

    // 被保人证件号码
    @TableField("INSUREDID")
    private String insuredID;

    // 被保人出生日期yyyy-mm-dd
    @TableField("INSUREDBTHD")
    private String insuredBthd;

    // 被保人性别1男2女
    @TableField("INSUREDGEND")
    private String insuredGend;
    
    // 被保人地址(标的地址)
    @TableField("INSUREDADDR")
    private String insuredAddr;

    // 被保人电话
    @TableField("INSUREDMOBILE")
    private String insuredMobile;
    
    // 被保人邮箱
    @TableField("INSUREDEMAIL")
    private String insuredEmail;

    // 被保人与投保人关系00本人01配偶02父母03子女
    @TableField("RELATION")
    private String relation;
}
