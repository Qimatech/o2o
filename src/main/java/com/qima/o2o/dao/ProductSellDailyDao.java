package com.qima.o2o.dao;

import java.util.Date;
import java.util.List;

import com.qima.o2o.entity.ProductSellDaily;

public interface ProductSellDailyDao {

	void insertProductSellDaily();

	List<ProductSellDaily> queryProductSellDailyList(ProductSellDaily productSellDailyCondition, Date beginTime,
			Date endTime);

}
