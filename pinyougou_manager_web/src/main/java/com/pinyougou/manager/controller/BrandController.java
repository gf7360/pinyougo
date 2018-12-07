package com.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbBrand;
import com.pinyougou.sellergoods.service.BrandService;
import entity.PageResult;
import entity.Result;
import org.junit.Test;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RequestMapping("/brand")
@RestController
public class BrandController {
    @Reference
    private BrandService brandService;

    /**
     * 查询商品列表；
     * @return
     */
    @RequestMapping("/findAll")
    public List<TbBrand> findAll(){
        return brandService.findAll();

    }
@RequestMapping("/findPage")
    public PageResult findPage(Integer pageNum,Integer pageSize){
        return brandService.findPage(pageNum,pageSize);
    }

    /**
     *品牌新增 添加数据
     * @param tbBrand
     * @return
     */
    @RequestMapping("/add")
    public Result add(@RequestBody TbBrand tbBrand){

        try {
            brandService.add(tbBrand);
            return new Result(true,"添加成功");
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
    public TbBrand findOne(Long id){
       return brandService.findOne(id);


    }
    @RequestMapping("/update")
    public Result update(@RequestBody TbBrand tbBrand){

        try {
            brandService.update(tbBrand);
            return new Result(true,"修改成功");
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
            brandService.delete(ids);
            return new Result(true,"删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"删除失败");
        }

    }



}












