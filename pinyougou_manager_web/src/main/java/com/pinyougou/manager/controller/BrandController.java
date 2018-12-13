package com.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbBrand;
import com.pinyougou.sellergoods.service.BrandService;
import entity.PageResult;
import entity.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


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

    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("/findPage")
    public PageResult findPage(Integer pageNum, Integer pageSize){

        return brandService.findPage(pageNum,pageSize);
    }
    @RequestMapping("/search")
    public PageResult search(@RequestBody TbBrand brand, Integer pageNum, Integer pageSize){
        return brandService.search(brand,pageNum,pageSize);

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
            return new Result(true,"修改 成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"修改 失败");
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
            return new Result(false,"删除 失败");
        }

    }

    /**
     * 查询模板关联的品牌数据
     */
    @RequestMapping("/selectBrandList")
    public List<Map> selectBrandList(){

        return brandService.selectBrandList();
    }


}












