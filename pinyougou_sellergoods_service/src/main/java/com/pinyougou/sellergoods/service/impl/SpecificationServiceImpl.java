package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.mapper.TbSpecificationMapper;
import com.pinyougou.pojo.TbSpecification;
import com.pinyougou.pojo.TbSpecificationExample;
import com.pinyougou.sellergoods.service.SpecificationService;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SpecificationServiceImpl implements SpecificationService {

    @Autowired
    private TbSpecificationMapper specificationMapper;


    @Override
    public PageResult search(TbSpecification specification, Integer pageNum, Integer pageSize) {
        //分页查询条件
        PageHelper.startPage(pageNum,pageSize);
        //根据条件分页查询；
        TbSpecificationExample example = new TbSpecificationExample();
        TbSpecificationExample.Criteria criteria = example.createCriteria();

        if(specification!=null){
            String specName = specification.getSpecName();
            if(specName!=null && !"".equals(specName)){
                criteria.andSpecNameLike("%"+specName+"%");
            }
        }


        Page<TbSpecification> page = (Page<TbSpecification>)specificationMapper.selectByExample(example);


        return new PageResult(page.getTotal(),page.getResult());
    }



}



















