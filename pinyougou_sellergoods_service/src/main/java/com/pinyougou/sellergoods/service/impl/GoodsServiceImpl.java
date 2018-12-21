package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.mapper.*;
import com.pinyougou.pojo.*;
import com.pinyougou.pojo.TbGoodsExample.Criteria;
import com.pinyougou.sellergoods.service.GoodsService;
import entity.PageResult;
import group.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 服务实现层
 * @author Administrator
 *
 */
@Service
@Transactional
public class GoodsServiceImpl implements GoodsService {

	@Autowired
	private TbGoodsMapper goodsMapper;
	
	/**
	 * 查询全部
	 */
	@Override
	public List<TbGoods> findAll() {
		return goodsMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);		
		Page<TbGoods> page=   (Page<TbGoods>) goodsMapper.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}
@Autowired
private TbGoodsDescMapper tbGoodsDescMapper;
	@Autowired
	private TbItemCatMapper tbItemCatMapper;
	@Autowired
	private TbBrandMapper tbBrandMapper;
	@Autowired
	private TbSellerMapper tbSellerMapper;
	@Autowired
	private TbItemMapper tbItemMapper;
	/**
	 * 增加
	 */
	@Override
	public void add(Goods goods) {
		//获取tb_goods数据
		TbGoods tbGoods = goods.getGoods();
		//设置状态 新录入的商品状态都是“0”表示未审核；
		tbGoods.setAuditStatus("0");
		goodsMapper.insert(tbGoods);
		//获取tb_goods_desc的数据
		TbGoodsDesc goodsDesc = goods.getGoodsDesc();
		//获取tb_goods 保存时返回的id
		goodsDesc.setGoodsId(tbGoods.getId());
		tbGoodsDescMapper.insert(goodsDesc);
//判断是否采用规格
		if("1".equals(tbGoods.getIsEnableSpec())){
			//保存tb_item表数据
			List<TbItem> itemList = goods.getItemList();
			for (TbItem tbItem : itemList) {
				//获取商品标题   先获取商品名称 SPU名称；
				String title = tbGoods.getGoodsName();
				//这是前台输入的规格选项集合 {"机身内存":"16G","网络":"联通3G"}
				String spec = tbItem.getSpec();
				Map<String,String> specMap = JSON.parseObject(spec, Map.class);
				for (String key : specMap.keySet()) { //specMap.keySet()得到map集合中的所有的key；
					title+=" "+specMap.get(key);//拼装商品名称和规格选项；得到商品标题
				}
				tbItem.setTitle(title);//将商品标题设置到TbItem对象；
				//调用方法封装数据
				setItemValue(tbGoods, goodsDesc, tbItem);
				tbItemMapper.insert(tbItem);
			}
		}else{
			//没有启用规格   生成一条默认的item数据；
			TbItem tbItem = new TbItem();
			tbItem.setTitle(tbGoods.getGoodsName());
			setItemValue(tbGoods, goodsDesc, tbItem);
			tbItem.setSpec("{}");
			tbItem.setPrice(tbGoods.getPrice());
			tbItem.setNum(9999);//库存
			tbItem.setStatus("1");
			tbItem.setIsDefault("1");
			tbItemMapper.insert(tbItem);
		}


	//后台组装
	//`title` varchar(100) NOT NULL COMMENT '商品标题',   // 商品名称（SPU名称）+ 商品规格选项名称 中间以空格隔开
	//`image` varchar(2000) DEFAULT NULL COMMENT '商品图片',  // 从 tb_goods_desc item_images中获取第一张  搜索时展现的具体的商品的图片；

	//`categoryId` bigint(10) NOT NULL COMMENT '所属类目，叶子类目',  //三级分类id
	//`create_time` datetime NOT NULL COMMENT '创建时间',
	//`update_time` datetime NOT NULL COMMENT '更新时间',
	//`goods_id` bigint(20) DEFAULT NULL,
	//`seller_id` varchar(30) DEFAULT NULL,
	//以下字段作用： 为搜索做数据准备：
	//`category` varchar(200) DEFAULT NULL, //三级分类名称
	//`brand` varchar(100) DEFAULT NULL,//品牌名称
	//`seller` varchar(200) DEFAULT NULL,//商家店铺名称


	}

	/**
	 * 抽取封装规格数据的方法；
	 * @param tbGoods
	 * @param goodsDesc
	 * @param tbItem
	 */
	private void setItemValue(TbGoods tbGoods, TbGoodsDesc goodsDesc, TbItem tbItem) {
		//image数据的组装；
		String itemImages = goodsDesc.getItemImages();
		List<Map> imageList = JSON.parseArray(itemImages,Map.class);
		if(imageList!=null && imageList.size()>0){
            String image = (String)imageList.get(0).get("url");
            tbItem.setImage(image);
        }
		tbItem.setCategoryid(tbGoods.getCategory3Id());
		tbItem.setCreateTime(new Date());
		tbItem.setUpdateTime(new Date());
		tbItem.setGoodsId(tbGoods.getId());
		tbItem.setSellerId(tbGoods.getSellerId());
		TbItemCat tbItemCat = tbItemCatMapper.selectByPrimaryKey(tbGoods.getCategory3Id());
		tbItem.setCategory(tbItemCat.getName());
		TbBrand tbBrand = tbBrandMapper.selectByPrimaryKey(tbGoods.getBrandId());
		tbItem.setBrand(tbBrand.getName());
		TbSeller tbSeller = tbSellerMapper.selectByPrimaryKey(tbGoods.getSellerId());
		tbItem.setSeller(tbSeller.getSellerId());

	}


	/**
	 * 修改
	 */
	@Override
	public void update(TbGoods goods){
		goodsMapper.updateByPrimaryKey(goods);
	}	
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public TbGoods findOne(Long id){
		return goodsMapper.selectByPrimaryKey(id);
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(Long[] ids) {
		for(Long id:ids){
			goodsMapper.deleteByPrimaryKey(id);
		}		
	}
	
	
		@Override
	public PageResult findPage(TbGoods goods, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		TbGoodsExample example=new TbGoodsExample();
		Criteria criteria = example.createCriteria();
		
		if(goods!=null){			
						if(goods.getSellerId()!=null && goods.getSellerId().length()>0){
				criteria.andSellerIdLike("%"+goods.getSellerId()+"%");
			}
			if(goods.getGoodsName()!=null && goods.getGoodsName().length()>0){
				criteria.andGoodsNameLike("%"+goods.getGoodsName()+"%");
			}
			if(goods.getAuditStatus()!=null && goods.getAuditStatus().length()>0){
				criteria.andAuditStatusLike("%"+goods.getAuditStatus()+"%");
			}
			if(goods.getIsMarketable()!=null && goods.getIsMarketable().length()>0){
				criteria.andIsMarketableLike("%"+goods.getIsMarketable()+"%");
			}
			if(goods.getCaption()!=null && goods.getCaption().length()>0){
				criteria.andCaptionLike("%"+goods.getCaption()+"%");
			}
			if(goods.getSmallPic()!=null && goods.getSmallPic().length()>0){
				criteria.andSmallPicLike("%"+goods.getSmallPic()+"%");
			}
			if(goods.getIsEnableSpec()!=null && goods.getIsEnableSpec().length()>0){
				criteria.andIsEnableSpecLike("%"+goods.getIsEnableSpec()+"%");
			}
			if(goods.getIsDelete()!=null && goods.getIsDelete().length()>0){
				criteria.andIsDeleteLike("%"+goods.getIsDelete()+"%");
			}
	
		}
		
		Page<TbGoods> page= (Page<TbGoods>)goodsMapper.selectByExample(example);		
		return new PageResult(page.getTotal(), page.getResult());
	}
	
}
