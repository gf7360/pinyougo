package com.pinyougou.manager.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbSpecification;
import com.pinyougou.sellergoods.service.SpecificationService;
import entity.PageResult;
import entity.Result;
import group.Specification;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/specification")
public class SpecificationController {

    @Reference
    private SpecificationService specificationService;

    /**
     * 根据条件查询；
     * @param specification 查询的条件 规格名称
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("/search")
    public PageResult search(@RequestBody TbSpecification specification,Integer pageNum,Integer pageSize){
       return specificationService.search(specification,pageNum,pageSize);
    }

    /**
     *品牌新增 添加数据
     * @param specification
     * @return
     */
    @RequestMapping("/add")
    public Result add(@RequestBody Specification specification){

        try {
            specificationService.add(specification);
            return new Result(true,"添加 成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"添加失败");
        }

    }

    /**
     * 修改品牌数据；
     * @param
     * @return
     *
     * 根据品牌id查询当前记录，回显在小窗口；
     */
    @RequestMapping("/findOne")
    public Specification findOne(Long id){
        return specificationService.findOne(id);


    }
    @RequestMapping("/update")
    public Result update(@RequestBody Specification specification){

        try {
            specificationService.update(specification);
            return new Result(true,"修改 成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"修改失败");
        }

    }

    /**
     * 删除品牌
     * @param ids
     * @return
     */
    @RequestMapping("/delete")
    public Result delete(Long[] ids){

        try {
            specificationService.delete(ids);
            return new Result(true,"删除 成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"删除失败");
        }

    }

    /**
     * 查询模板关联的品牌数据
     */
    @RequestMapping("/selectSpecList")
    public List<Map> selectSpecList(){

        return specificationService.selectSpecList();
    }












}
