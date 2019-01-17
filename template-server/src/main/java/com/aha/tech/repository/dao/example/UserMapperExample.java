package com.aha.tech.repository.dao.example;

import com.aha.tech.model.entity.UserEntity;
import com.aha.tech.repository.dao.readwrite.UserMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: luweihong
 * @Date: 2018/8/2
 */
@Component
public class UserMapperExample {

    @Resource
    private UserMapper userMapper;

    public UserEntity findByUserId(Long userId) {
        Example example = Example.builder(UserEntity.class).build();
        // criteria 就是 sql的 where条件
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", userId);
        List<UserEntity> userEntityList = userMapper.selectByExample(example);

        return CollectionUtils.isEmpty(userEntityList) ? null : userEntityList.get(0);
    }

    public Integer updateUserById(Long userId, UserEntity afterUpdated) {
        Example example = Example.builder(UserEntity.class).build();
        Example.Criteria criteria = example.createCriteria();

        criteria.andEqualTo("userId", userId);

        // 前面一个是set, 后面还是where
        return userMapper.updateByExample(afterUpdated, example);
    }
}
