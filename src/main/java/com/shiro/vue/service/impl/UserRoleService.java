package com.shiro.vue.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shiro.vue.mapper.UserRoleMapper;
import com.shiro.vue.pojo.entity.UserRole;
import com.shiro.vue.service.IUserRoleService;
import org.springframework.stereotype.Service;

/**
 * @author: QuincySu
 * Date: 2018-10-11
 * Time: 16:45
 */
@Service
public class UserRoleService extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {
}
