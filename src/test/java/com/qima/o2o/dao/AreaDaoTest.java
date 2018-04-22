package com.qima.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.qima.o2o.entity.Area;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AreaDaoTest  {
	@Autowired
	private AreaDao areaDao;

	@Test
	//@Ignore
	public void testQueryArea() {
		List<Area> areaList = areaDao.queryArea();
		assertEquals(4, areaList.size());
	}

}
