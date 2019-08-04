package org.junhi.item.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author junhi
 * @date 2019/7/23 10:17
 */
@RequestMapping("/category")
public interface CategoryApi {

    /**
     * 根据id集合，查询商品名字集合
     * @param ids
     * @return
     */
    @GetMapping
     List<String> queryNamesByIds(@RequestParam("ids")List<Long> ids);

}
