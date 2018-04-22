package com.qima.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.qima.o2o.entity.UserAwardMap;

public interface UserAwardMapDao {
	/**
	 * 
	 * @param userAwardCondition
	 * @param rowIndex
	 * @param pageSize
	 * @return
	 */
	List<UserAwardMap> queryUserAwardMapList(
			@Param("userAwardCondition") UserAwardMap userAwardCondition,
			@Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);

	/**
	 * 
	 * @param userAwardCondition
	 * @return
	 */
	int queryUserAwardMapCount(
			@Param("userAwardCondition") UserAwardMap userAwardCondition);

	/**
	 * 
	 * @param userAwardId
	 * @return
	 */
	UserAwardMap queryUserAwardMapById(long userAwardId);

	/**
	 * 
	 * @param userAwardMap
	 * @return
	 */
	int insertUserAwardMap(UserAwardMap userAwardMap);

	/**
	 * 
	 * @param userAwardMap
	 * @return
	 */
	int updateUserAwardMap(UserAwardMap userAwardMap);
}
