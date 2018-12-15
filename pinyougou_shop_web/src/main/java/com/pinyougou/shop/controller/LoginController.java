package com.pinyougou.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbSeller;
import com.pinyougou.sellergoods.service.SellerService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Reference
    private SellerService sellerService;
    @RequestMapping("/getName")
    public Map<String,String> getName(){
        String loginName = SecurityContextHolder.getContext().getAuthentication().getName();
        Map<String, String> map = new HashMap<>();
        map.put("loginName",loginName);
        return map;
    }
    @RequestMapping("/findByName")
    public TbSeller findByName(){
        String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
        return sellerService.findOne(sellerId);

    }
}
