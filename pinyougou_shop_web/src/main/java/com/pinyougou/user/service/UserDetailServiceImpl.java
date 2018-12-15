package com.pinyougou.user.service;

import com.pinyougou.pojo.TbSeller;
import com.pinyougou.sellergoods.service.SellerService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * 安全框架认证服务类，完成认证和授权操作；
 */
public class UserDetailServiceImpl implements UserDetailsService {

    private SellerService sellerService;

    public void setSellerService(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //基于商家用户名查询商家数据；
        TbSeller tbseller = sellerService.findOne(username);
        if(tbseller!=null){
            //证明用户存在 获取密码和权限
         //只允许审核通过的商家 登录；
            if("1".equals(tbseller.getStatus())) {
                //构建商家权限集合数据；
                List<GrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority("ROLE_SELLER")); //只有这一种角色；
                //参数一用户名；参数二 密码；参数三；权限集合；
                return new User(username, tbseller.getPassword(), authorities);
            }else {
                return null ;
            }
        }else{
            return null;
        }

    }
}










