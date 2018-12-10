package com.pinyougou.sellergoods.service;

import com.pinyougou.pojo.TbSpecification;
import entity.PageResult;
import group.Specification;

public interface SpecificationService {

    PageResult search(TbSpecification specification, Integer pageNum, Integer pageSize);

    void add(Specification specification);
    void update(Specification specification);

    Specification findOne(Long id);

    void delete(Long[] ids);


}
