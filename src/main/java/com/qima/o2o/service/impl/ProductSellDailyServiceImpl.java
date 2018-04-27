package com.qima.o2o.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qima.o2o.dao.ProductSellDailyDao;
import com.qima.o2o.entity.ProductSellDaily;
import com.qima.o2o.service.ProductSellDailyService;

@Service
public class ProductSellDailyServiceImpl implements ProductSellDailyService {

	private static final Logger log = LoggerFactory.getLogger(ProductSellDailyServiceImpl.class);
	@Autowired
	private ProductSellDailyDao productSellDailyDao;
	
	@Override
	public void dailyCalculate() {
		log.info("Quartz running,执行时间" + new Date());
		productSellDailyDao.insertProductSellDaily();
		productSellDailyDao.insertDefaultProductSellDaily();
	}

	@Override
	public List<ProductSellDaily> listProductSellDaily(ProductSellDaily productSellDailyCondition, Date beginTime,
			Date endTime) {
		// TODO Auto-generated method stub
		return productSellDailyDao.queryProductSellDailyList(productSellDailyCondition,beginTime,endTime);
	}

}
