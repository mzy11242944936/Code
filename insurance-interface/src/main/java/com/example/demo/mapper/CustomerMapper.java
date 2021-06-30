package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import com.example.demo.entity.Customer;
/**
 * 客户表(户籍) Mapper 接口
 * @author
 * @since
 */
@Component
@Mapper
public interface CustomerMapper extends BaseMapper<Customer> {
    
}
