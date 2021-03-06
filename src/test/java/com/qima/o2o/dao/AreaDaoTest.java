package com.qima.o2o.dao;

import java.util.Date;

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
	@Ignore
	public void testQueryArea() {
		//List<Area> areaList = areaDao.queryArea();
		Area area = new Area();
		area.setAreaName("北京13");
		area.setAreaDesc("北京des13c");
		area.setCreateTime(new Date());
		area.setLastEditTime(new Date());
		area.setPriority(2);
		areaDao.insertArea(area);
		//assertEquals(4, areaList.size());
	}

}
