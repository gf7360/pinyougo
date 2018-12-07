package com.pinyougou.mapper;

import com.pinyougou.pojo.TbBrand;

import java.util.List;

public interface TbBrandMapper {

   public List<TbBrand> findAll();
//增加品牌
    void add(TbBrand tbBrand);
//修改品牌
    TbBrand selectByPrimaryKey(Long id);
    void updateByPrimaryKey(TbBrand tbBrand);


    void deleteByPrimaryKey(Long id);
}













