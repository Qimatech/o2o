package com.qima.o2o.service;

import java.util.Date;
import java.util.List;

import com.qima.o2o.entity.ProductSellDaily;

public interface ProductSellDailyService {

	/**
	 * 定时统计所有店铺的商品销量
	 */
	void dailyCalculate();
	
	List<ProductSellDaily> listProductSellDaily(ProductSellDaily productSellDailyCondition,Date beginTime,Date endTime);
}
