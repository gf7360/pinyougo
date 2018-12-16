package group;

import com.pinyougou.pojo.TbGoods;
import com.pinyougou.pojo.TbGoodsDesc;
import com.pinyougou.pojo.TbItem;

import java.io.Serializable;
import java.util.List;

public class Goods implements Serializable {

    private TbGoods goods;
    private TbGoodsDesc goodsDesc;
    private List<TbItem> itemList ;
    //因为一个品牌spu对应着多个sku

    public TbGoods getGoods() {
        return goods;
    }

    public void setGoods(TbGoods goods) {
        this.goods = goods;
    }

    public TbGoodsDesc getGoodsDesc() {
        return goodsDesc;
    }

    public void setGoodsDesc(TbGoodsDesc goodsDesc) {
        this.goodsDesc = goodsDesc;
    }

    public List<TbItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<TbItem> itemList) {
        this.itemList = itemList;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "goods=" + goods +
                ", goodsDesc=" + goodsDesc +
                ", itemList=" + itemList +
                '}';
    }
}
