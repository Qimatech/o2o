package com.qima.o2o.service;

import com.qima.o2o.dto.UserShopMapExecution;
import com.qima.o2o.entity.UserShopMap;

public interface UserShopMapService {

	UserShopMapExecution listUserShopMap(UserShopMap userShopMapCondition,
			int pageIndex, int pageSize);

}
