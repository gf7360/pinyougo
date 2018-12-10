package com.pinyougou.sellergoods.service;

import com.pinyougou.pojo.TbSpecification;
import entity.PageResult;

public interface SpecificationService {

    PageResult search(TbSpecification specification, Integer pageNum, Integer pageSize);


}
