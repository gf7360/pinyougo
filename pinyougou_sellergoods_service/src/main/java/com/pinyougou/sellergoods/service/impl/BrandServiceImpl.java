package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.mapper.TbBrandMapper;
import com.pinyougou.pojo.TbBrand;
import com.pinyougou.pojo.TbBrandExample;
import com.pinyougou.sellergoods.service.BrandService;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class BrandServiceImpl implements BrandService {
    @Autowired
    private TbBrandMapper tbBrandMapper;

    /**
     * 查询所有
     * @return
     */
    @Override
    public List<TbBrand> findAll() {
        return tbBrandMapper.selectByExample(null);
    }

    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageResult findPage(Integer pageNum, Integer pageSize) {
        //设置分页查询条件
        PageHelper.startPage(pageNum,pageSize);
        Page<TbBrand> page = (Page<TbBrand>)tbBrandMapper.selectByExample(null);

        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public PageResult search(TbBrand brand, Integer pageNum, Integer pageSize) {
        //定义分页查询条件；
        PageHelper.startPage(pageNum,pageSize);
        //设置分页条件
        TbBrandExample brandExample = new TbBrandExample();
        TbBrandExample.Criteria criteria = brandExample.createCriteria();
        if(brand !=null){
            String name = brand.getName();
            String firstChar = brand.getFirstChar();
            if(name!=null && !"".equals(name)){
                criteria.andNameLike("%"+name+"%");
            }
            if(firstChar!=null && !"".equals(firstChar)){
                criteria.andFirstCharEqualTo(firstChar);
            }
        }

        Page<TbBrand> brands =(Page<TbBrand>) tbBrandMapper.selectByExample(brandExample);
        return new PageResult(brands.getTotal(),brands.getResult());
    }

    /**
     * 添加数据的方法；
     * @param tbBrand
     */
    @Override
    public void add(TbBrand tbBrand) {

        tbBrandMapper.insert(tbBrand);
    }

    @Override
    public TbBrand findOne(Long id) {
        return tbBrandMapper.selectByPrimaryKey(id);
    }

    /**
     * 修改品牌数据
     * @param tbBrand
     */
    @Override
    public void update(TbBrand tbBrand) {
        tbBrandMapper.updateByPrimaryKey(tbBrand);
    }

    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            tbBrandMapper.deleteByPrimaryKey(id);
        }

    }

    /**
     * 查询模板关联的品牌数据，展示下拉选
     * @return
     */
    @Override
    public List<Map> selectBrandList() {
        return tbBrandMapper.selectBrandList();
    }
}


















