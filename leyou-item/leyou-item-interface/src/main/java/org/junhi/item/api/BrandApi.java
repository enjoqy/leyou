package org.junhi.item.api;

import org.junhi.item.pojo.Brand;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author junhi
 * @date 2019/7/23 14:50
 */
@RequestMapping("brand")
public interface BrandApi {

    /**
     * 根据id查询一个品牌的信息
     * @param id
     * @return
     */
    @GetMapping("{id}")
     Brand queryBrandById(@PathVariable("id")Long id);
}
