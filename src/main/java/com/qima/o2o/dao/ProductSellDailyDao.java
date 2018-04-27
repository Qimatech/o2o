package com.qima.o2o.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.qima.o2o.entity.ProductSellDaily;

public interface ProductSellDailyDao {

	

	List<ProductSellDaily> queryProductSellDailyList(
			@Param("productSellDailyCondition")ProductSellDaily productSellDailyCondition, 
			@Param("beginTime")Date beginTime,
			@Param("endTime")Date endTime);
	
	int insertProductSellDaily();
	int insertDefaultProductSellDaily();

}
