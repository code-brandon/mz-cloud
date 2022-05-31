package com.mz.auth.service;

import com.mz.common.security.service.MzUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * What --
 * <br>
 * Describe --
 * <br>
 *
 * @Package: com.mz.auth.service
 * @ClassName: MzUserService
 * @Author: 小政同学    QQ:xiaozheng666888@qq.com
 * @CreateTime: 2022/5/29 17:03
 */
@Service
public class MzUserDetailsServiceImpl implements MzUserDetailsService {


    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 按用户名加载用户
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        String password = passwordEncoder.encode("123456");
        User user = new User("admin",password, Arrays.asList(new SimpleGrantedAuthority("admin")));
        return user;
    }
}
