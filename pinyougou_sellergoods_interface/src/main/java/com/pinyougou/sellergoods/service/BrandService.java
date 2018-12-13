package com.pinyougou.sellergoods.service;

import com.pinyougou.pojo.TbBrand;
import entity.PageResult;

import java.util.List;
import java.util.Map;

public interface BrandService {
    public List<TbBrand> findAll();

    public PageResult findPage(Integer pageNum, Integer pageSize);

    public void add(TbBrand tbBrand);

    void update(TbBrand tbBrand);

    TbBrand findOne(Long id);

    void delete(Long[] ids);

    PageResult search(TbBrand brand, Integer pageNum, Integer pageSize);

    List<Map> selectBrandList();

}
















