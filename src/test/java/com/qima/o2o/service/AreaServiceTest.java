package com.qima.o2o.service;

import java.io.IOException;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.qima.o2o.entity.Area;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AreaServiceTest {

	@Autowired
	private AreaService areaService;
//	@Autowired
//	private CacheService cacheService;
	@Test
	@Ignore
	public void testGetAreaList() throws JsonParseException, JsonMappingException, IOException {
		List<Area> areaList = areaService.getAreaList();
		for (Area area : areaList) {
			System.out.println(area.getAreaName());
		}
	}
}
