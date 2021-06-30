package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.Customer;

import com.example.demo.entity.InsuranceMessage;
import com.example.demo.mapper.CustomerMapper;
import com.example.demo.mapper.GasmeterMapper;
import com.example.demo.mapper.InsuranceMessageMapper;
import com.example.demo.utils.UuidUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


import java.util.*;

@Controller
@RequestMapping("/insruance")
@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class InsuranceInterface {
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private GasmeterMapper gasmeterMapper;
    @Autowired
    private InsuranceMessageMapper insuranceMessageMapper;

    /**
     * 根据客户编号，查询用户信息
     * @param strModel    acctId:客户编号   appGasNO 表具编号
     * @return
     */
    @PostMapping(value = "/getByCuscode",produces = "application/json;charset=UTF-8")
    @Transactional(propagation = Propagation.REQUIRED, noRollbackFor = Exception.class)
    @ResponseBody
    public String getCustomer(@RequestBody String strModel){
        HashMap<String, Object> map = new HashMap<>();
        try {
            String customerCode = JSON.parseObject(strModel).getString("acctId");
            String gasCode = JSON.parseObject(strModel).getString("appGasNO");
            if (customerCode == null && gasCode==null) {
                map.put("responseCode", "10");
                map.put("responseInfo", "参数中没找到客户编号或表具表号");
                return  JSONObject.toJSONString(map, filter);
            }
//            通过客户编号查询客户信息
            if (customerCode != null) {
                Customer customer = getCustomerByCode(customerCode);
                if (customer != null) {
                    System.out.println(customer);
                    System.out.println(customer.getFAddress());
                    System.out.println(customer.getFCardno());
                    map.put("responseCode", "11");
                    map.put("responseInfo", "Success");
                    map.put("appName", customer.getFCustomername());
                    map.put("appMobile", customer.getFPhone());
                    map.put("appID", customer.getFCardno());
                    map.put("appIDType", "001".equals(customer.getFCardtype()) ? "01" : customer.getFCardtype());
                    map.put("appEmail", "");
                    map.put("appAddr", customer.getFAddress());
                } else {
                    map.put("responseCode", "10");
                    map.put("responseInfo", "没有查询到数据");
                }
            } else {
//                通过物联网编号查询客户信息
                List<Map> customerByGascode = gasmeterMapper.getCustomerByGascode(gasCode);
                if (customerByGascode.size() == 0) {
                    map.put("responseCode", "10");
                    map.put("responseInfo", "没有查询到数据");
                } else {
                    System.out.println(customerByGascode.get(0).get("fAddress"));
                    map.put("responseCode", "11");
                    map.put("responseInfo", "Success");
                    map.put("appName", customerByGascode.get(0).get("fCustomername"));
                    map.put("appMobile", customerByGascode.get(0).get("fPhone"));
                    map.put("appID", customerByGascode.get(0).get("fCardno"));
                    map.put("appIDType", "001".equals(customerByGascode.get(0).get("fCardtype")) ? "01" : customerByGascode.get(0).get("fCardtype") == null ? "" : "99");
                    map.put("appEmail", "");
                    map.put("appAddr", customerByGascode.get(0).get("fAddress"));

                }
            }


            System.out.println(map);

        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            map.put("responseCode", "10");
            map.put("responseInfo", "查询失败");
        }
        return  JSONObject.toJSONString(map, filter);
    }
    @RequestMapping(value = "/getByGascode",produces = "application/json;charset=UTF-8")
    @Transactional(propagation = Propagation.REQUIRED, noRollbackFor = Exception.class)
    @ResponseBody
    public String getByGascode(String Gascode){
        HashMap<String, Object> map = new HashMap<>();
        try {
            List<Map> gascode = gasmeterMapper.getCustomerByGascode(Gascode);
            if(gascode.size()==0){
                map.put("type","false");
                map.put("message","未查到用户信息");
            }else{

                /*第一种遍历方法*/
//                System.out.println("123"+gascode.get(0).keySet());
//                Set set = gascode.get(0).entrySet();
//                Iterator iterator = set.iterator();
//                while (iterator.hasNext()){
//                    Map.Entry mapentry = (Map.Entry) iterator.next();
//                    if (mapentry.getValue()==null){
//                        mapentry.setValue("");
//                    }
//                }

                /*第二种遍历方法*/
//                Set keys = gascode.get(0).keySet();
//                if (keys != null){
//                    Iterator it = keys.iterator();
//                    while (it.hasNext()){
//                      Object key =  it.next();
//                      Object value = gascode.get(0).get(key);
//                      if(value==null){
//                         gascode.get(0).put(key,"");
//                      }
//
//                    }
//
//                }
                System.out.println("结果"+gascode.get(0));
                map.put("type","true");
                map.put("message","用户信息");
                map.put("data",gascode.get(0));
            }


        } catch (Exception e) {
            map.put("type","false");
            map.put("message","查询失败");
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return JSONObject.toJSONString(map,filter);
//        return JSONObject.toJSONString(map);
    }

    /*
     * 保存数据接口
     * @param strModel
     * @return
     */
    @PostMapping(value = "/submitData",produces = "application/json;charset=UTF-8")
    @Transactional(propagation = Propagation.REQUIRED, noRollbackFor = Exception.class)
    @ResponseBody
    public String subData(@RequestBody String strModel){
        HashMap<String, Object> map = new HashMap<>();
        try {
            JSONObject parseObject = JSONObject.parseObject(strModel);
            //投保人json信息体
            JSONObject appntObject = parseObject.getJSONObject("appntObject");
            //保单json信息体
            JSONObject plyObject = parseObject.getJSONObject("plyObject");
            //保单号
            String policyNo = plyObject.getString("policyNo");
            //被保人josn信息体
            JSONObject insuredObject = parseObject.getJSONObject("insuredObject");
            //查询数据库中是否已存在保单号
            List<InsuranceMessage> insuranceMessageList = getByPolicyNo(policyNo);
            if (insuranceMessageList.size()>0){
                map.put("responseCode", "3");
                map.put("responseInfo", "保单已同步,保单号已存在");
            }else{
                //保单信息
                InsuranceMessage insuranceMessage = new InsuranceMessage();
                insuranceMessage.setFKey(UuidUtil.getUuId());
                insuranceMessage.setAccId(plyObject.getString("acctId"));      //客户编号
                insuranceMessage.setAppGasNO(plyObject.getString("appGasNO")); //表具编号
                insuranceMessage.setPolicyNo(plyObject.getString("policyNo")); //保单号
                insuranceMessage.setInsuranceCode(plyObject.getString("insuranceCode")); //险种代码
                insuranceMessage.setInsuranceName(plyObject.getString("insuranceName")); //险种名称
                insuranceMessage.setPremium(plyObject.getString("premium")); //保费
                insuranceMessage.setInsurStartDate(plyObject.getString("insurStartDate")); //保险起期
                insuranceMessage.setInsurEndDate(plyObject.getString("insurEndDate")); //保险止期
                insuranceMessage.setCClntCde(plyObject.getString("CClntCde")); //员工编号
                //投保人信息
                insuranceMessage.setAppName(appntObject.getString("appName"));  //投保人姓名
                insuranceMessage.setAppContName(appntObject.getString("appContName")); //投保人联系人
                insuranceMessage.setAppIDType(appntObject.getString("appIDType"));  //投保人证件类型
                insuranceMessage.setAppID(appntObject.getString("appID"));      //投保人证件号
                insuranceMessage.setAppBthd(appntObject.getString("appBthd"));  //投保人出生日期
                insuranceMessage.setAppGend(appntObject.getString("appGend"));  //投保人性别
                insuranceMessage.setAppAddr(appntObject.getString("appAddr"));  //投保人地址
                insuranceMessage.setAppMobile(appntObject.getString("appMobile"));  //投保人电话
                insuranceMessage.setAppEmail(appntObject.getString("appEmail"));  //投保人邮箱
                //被保人信息
                insuranceMessage.setInsuredName(insuredObject.getString("insuredName")); //被保人姓名
                insuranceMessage.setInsuredContName(insuredObject.getString("insuredContName"));  //被保人联系人
                insuranceMessage.setInsuredIDType(insuredObject.getString("insuredIDType")); //被保人证件类型
                insuranceMessage.setInsuredID(insuredObject.getString("insuredID")); //被保人证件号
                insuranceMessage.setInsuredBthd(insuredObject.getString("insuredBthd")); //被保人出生日期
                insuranceMessage.setInsuredGend(insuredObject.getString("insuredGend")); //被保人性别
                insuranceMessage.setInsuredAddr(insuredObject.getString("insuredAddr")); //被保人地址
                insuranceMessage.setInsuredMobile(insuredObject.getString("insuredMobile")); //被保人电话
                insuranceMessage.setInsuredEmail(insuredObject.getString("insuredEmail")); //被保人邮箱
                insuranceMessage.setRelation(insuredObject.getString("relation")); //被保人与投保人关系
                int insert = insuranceMessageMapper.insert(insuranceMessage);
                if(insert>0){
                    map.put("responseCode", "1");
                    map.put("responseInfo", "同步成功");
                }
                else {
                    map.put("responseCode", "2");
                    map.put("responseInfo", "同步失败");
                }
            }



        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            map.put("responseCode", "2");
            map.put("responseInfo", "同步失败");
        }
        return JSON.toJSONString(map);
    }

    private Customer getCustomerByCode(String cusCode){
        LambdaQueryWrapper<Customer> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(true, Customer::getFCustomercode, cusCode);
        wrapper.eq(true, Customer::getFFlag,"0");
        Page<Customer> customerPage = new Page<>(10,2);
        return customerMapper.selectOne(wrapper);
//        return (Customer) customerMapper.selectPage(customerPage,wrapper);
    }
    private List<InsuranceMessage> getByPolicyNo(String policyNo){
        LambdaQueryWrapper<InsuranceMessage> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(true,InsuranceMessage::getPolicyNo,policyNo);
        return insuranceMessageMapper.selectList(wrapper);
    }
    private ValueFilter filter = new ValueFilter() {
        @Override
        public Object process(Object obj, String s, Object v) {
            if(v==null)
                return "";
            return v;
        }
    };



}
