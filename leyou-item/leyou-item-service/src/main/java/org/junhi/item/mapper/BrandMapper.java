package org.junhi.item.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.junhi.item.pojo.Brand;

import java.util.List;

/**
 * @author junhi
 * @date 2019/7/23 14:49
 */
public interface BrandMapper extends tk.mybatis.mapper.common.Mapper<Brand> {

    /**
     * 在品牌，种类中插入一条数据
     * @param cid
     * @param bid
     */
    @Insert("insert into tb_category_brand (category_id, brand_id) values (#{cid}, #{bid})")
    void insertCategoryAndBrand(@Param("cid")Long cid, @Param("bid")Long bid);

    /**
     * 根据cid查询一个品牌
     * @param cid
     * @return
     */
    @Select("select * from tb_brand a inner join tb_category_brand b on a.id=b.brand_id where b.category_id=#{cid}")
    List<Brand> queryBrandsByCid(Long cid);
}
