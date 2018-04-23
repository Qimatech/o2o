package com.qima.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.qima.o2o.entity.Award;
import com.qima.o2o.entity.PersonInfo;
import com.qima.o2o.entity.Shop;
import com.qima.o2o.entity.UserAwardMap;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserAwardMapDaoTest{
	@Autowired
	private UserAwardMapDao userAwardMapDao;

	@Test
	public void testAInsertUserAwardMap() throws Exception {
		UserAwardMap userAwardMap = new UserAwardMap();
		
		PersonInfo customer = new PersonInfo();
		customer.setUserId(8L);
		userAwardMap.setUserId(8L);
		userAwardMap.setUser(customer);
		userAwardMap.setOperator(customer);
		
		Award award = new Award();
		userAwardMap.setAwardId(1L);
		userAwardMap.setAward(award);
		
		Shop shop = new Shop();
		userAwardMap.setShopId(20L);
		userAwardMap.setShop(shop);
		userAwardMap.setCreateTime(new Date());
		userAwardMap.setUsedStatus(1);
		userAwardMap.setPoint(1);
		int effectedNum = userAwardMapDao.insertUserAwardMap(userAwardMap);
		assertEquals(1, effectedNum);
		//第二个
		UserAwardMap userAwardMap2 = new UserAwardMap();
		PersonInfo customer2 = new PersonInfo();
		customer2.setUserId(8L);
		userAwardMap2.setUserId(8L);
		userAwardMap2.setUser(customer2);
		userAwardMap2.setOperator(customer2);
		
		Award award2 = new Award();
		award2.setAwardId(1L);
		userAwardMap2.setAward(award2);;
		userAwardMap2.setShop(shop);
		userAwardMap2.setCreateTime(new Date());
		userAwardMap2.setUsedStatus(0);
		userAwardMap2.setPoint(1);
		effectedNum = userAwardMapDao.insertUserAwardMap(userAwardMap);
		assertEquals(1, effectedNum);
	}

	@Test
	public void testBQueryUserAwardMapList() throws Exception {
//		UserAwardMap userAwardMap = new UserAwardMap();
//
//		List<UserAwardMap> userAwardMapList = userAwardMapDao
//				.queryUserAwardMapList(userAwardMap, 0, 3);
//		assertEquals(2, userAwardMapList.size());
//		int count = userAwardMapDao.queryUserAwardMapCount(userAwardMap);
//		assertEquals(2, count);
//		userAwardMap.setUserName("test");
//		userAwardMapList = userAwardMapDao.queryUserAwardMapList(userAwardMap,
//				0, 3);
//		assertEquals(2, userAwardMapList.size());
//		count = userAwardMapDao.queryUserAwardMapCount(userAwardMap);
//		assertEquals(2, count);
//		userAwardMap.setUserId(1L);
//		userAwardMapList = userAwardMapDao.queryUserAwardMapList(userAwardMap,
//				0, 3);
//		assertEquals(1, userAwardMapList.size());
//		count = userAwardMapDao.queryUserAwardMapCount(userAwardMap);
//		assertEquals(1, count);
	}
}
