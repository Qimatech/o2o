package com.qima.o2o.dao;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.qima.o2o.entity.Area;
import com.qima.o2o.entity.PersonInfo;
import com.qima.o2o.entity.Shop;
import com.qima.o2o.entity.ShopCategory;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShopDaoTest {
	
	@Autowired
	private ShopDao shopDao;

	@Test
	public void testInsertShopDao() {
		Shop shop = new Shop();
		PersonInfo owner = new PersonInfo();
		Area area = new Area();
		ShopCategory shopCategory = new ShopCategory();
		ShopCategory parentShopCategory = new ShopCategory();
		
		owner.setUserId(8L);
		area.setAreaId(3L);
		shopCategory.setShopCategoryId(14L);
		shop.setOwner(owner);
		shop.setArea(area);
		shop.setShopCategory(shopCategory);
		shop.setParentCategory(null);
		shop.setShopName("美装秀秀");
		shop.setShopDesc("夕阳红");
		shop.setShopAddr("涡阳");
		shop.setPhone("18721632613");
		shop.setShopImg("test");
		shop.setLongitude(null);
		shop.setLatitude(null);
		shop.setPriority(1);
		shop.setCreateTime(new Date());
		shop.setLastEditTime(new Date());
		shop.setEnableStatus(1);
		shop.setAdvice("审核中");
		
		int effectedNum =  shopDao.insertShop(shop);
		System.out.println("effected-->" + effectedNum);
		
	}

}
