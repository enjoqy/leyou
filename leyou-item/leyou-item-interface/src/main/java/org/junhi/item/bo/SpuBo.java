package org.junhi.item.bo;

import org.junhi.item.pojo.Sku;
import org.junhi.item.pojo.Spu;
import org.junhi.item.pojo.SpuDetail;

import java.util.List;

/**
 * 这个实体类是对spu实体类的扩展
 * @author junhi
 * @date 2019/7/26 14:23
 */
public class SpuBo extends Spu {

    private String cname;
    private String bname;
    private SpuDetail spuDetail;// 商品详情
    private List<Sku> skus;// sku列表

    public SpuDetail getSpuDetail() {
        return spuDetail;
    }

    public void setSpuDetail(SpuDetail spuDetail) {
        this.spuDetail = spuDetail;
    }

    public List<Sku> getSkus() {
        return skus;
    }

    public void setSkus(List<Sku> skus) {
        this.skus = skus;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }
}
