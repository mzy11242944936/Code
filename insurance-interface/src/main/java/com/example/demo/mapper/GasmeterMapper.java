package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Gasmeter;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Mapper
public interface GasmeterMapper extends BaseMapper {

        List<Map> getCustomerByGascode(String meterdialcode);

//        Gasmeter getUser();


}
