package com.example.demo;

import com.example.demo.entity.Gasmeter;
import com.example.demo.mapper.GasmeterMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	GasmeterMapper gasmeterMapper ;
	@Test
	void contextLoads() {
	}
//	@Test
////	void get() {
////		System.out.println(gasmeterMapper.getUser());
////	}


}
