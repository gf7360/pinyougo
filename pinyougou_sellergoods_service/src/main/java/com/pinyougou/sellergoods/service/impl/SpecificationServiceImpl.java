package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.mapper.TbSpecificationMapper;
import com.pinyougou.mapper.TbSpecificationOptionMapper;
import com.pinyougou.pojo.TbSpecification;
import com.pinyougou.pojo.TbSpecificationExample;
import com.pinyougou.pojo.TbSpecificationOption;
import com.pinyougou.pojo.TbSpecificationOptionExample;
import com.pinyougou.sellergoods.service.SpecificationService;
import entity.PageResult;
import group.Specification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SpecificationServiceImpl implements SpecificationService {

    @Autowired
    private TbSpecificationMapper specificationMapper;

    /**
     * 根据名称分页查询；
     * @param specification
     * @param pageNum
     * @param pageSize
     * @return
     */
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

    @Autowired
    private TbSpecificationOptionMapper specificationOptionMapper;
    /**
     * 新增
     * @param specification
     */
    @Override
    public void add(Specification specification) {
        //增加规格
        TbSpecification tbSpecification = specification.getSpecification();
        specificationMapper.insert(tbSpecification);
        //增加规格选项；
        List<TbSpecificationOption> specificationOptions = specification.getSpecificationOptions();
        for (TbSpecificationOption specificationOption : specificationOptions) {
            specificationOption.setSpecId(tbSpecification.getId());
            specificationOptionMapper.insert(specificationOption);
        }

    }

    @Override
    public void update(Specification specification) {
        //修改规格
        TbSpecification tbSpecification = specification.getSpecification();
        specificationMapper.updateByPrimaryKey(tbSpecification);
        //修改规格选项   先删除原有规格选项列表；在新增页面重新提交的规格选项数据；
        TbSpecificationOptionExample example = new TbSpecificationOptionExample();
        TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
        criteria.andSpecIdEqualTo(tbSpecification.getId());
        specificationOptionMapper.deleteByExample(example);

        //在将页面数据重新保存；
        List<TbSpecificationOption> specificationOptions = specification.getSpecificationOptions();
        for (TbSpecificationOption specificationOption : specificationOptions) {
            specificationOption.setSpecId(tbSpecification.getId());
            specificationOptionMapper.insert(specificationOption);
        }

    }



    @Override
    public Specification findOne(Long id) {
        //规格记录；
        Specification specification = new Specification();
        TbSpecification tbSpecification = specificationMapper.selectByPrimaryKey(id);
        specification.setSpecification(tbSpecification);
        //根据规格id查询对应的规格选项；
        //封装查询条件数据；
        TbSpecificationOptionExample example = new TbSpecificationOptionExample();
        TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
        criteria.andSpecIdEqualTo(id);
        //查询规格选项；
        List<TbSpecificationOption> tbSpecificationOptions = specificationOptionMapper.selectByExample(example);
        specification.setSpecificationOptions(tbSpecificationOptions);
        return specification;
    }


    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            specificationMapper.deleteByPrimaryKey(id);//删除规格；
            //删除规格选项
            TbSpecificationOptionExample example = new TbSpecificationOptionExample();
            TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
            criteria.andSpecIdEqualTo(id);
            specificationOptionMapper.deleteByExample(example);
        }
    }
}



















