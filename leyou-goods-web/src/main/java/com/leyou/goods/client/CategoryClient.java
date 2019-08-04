package com.leyou.goods.client;

import org.junhi.item.api.CategoryApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author junhi
 * @date 2019/7/30 16:17
 */
@FeignClient("item-service")
public interface CategoryClient extends CategoryApi {
}
