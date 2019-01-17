package com.aha.tech.repository.dao.readwrite;

import com.aha.tech.model.entity.UserEntity;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

/**
 * @Author: luweihong
 * @Date: 2018/7/27
 */
public interface UserMapper extends Mapper<UserEntity>, MySqlMapper<UserEntity> {

    int batchUpdate(List<UserEntity> userEntityList);
}
