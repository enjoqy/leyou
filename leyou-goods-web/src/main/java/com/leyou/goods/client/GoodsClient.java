package com.leyou.goods.client;

import org.junhi.item.api.GoodsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author junhi
 * @date 2019/7/30 15:05
 */
@FeignClient("item-service")
public interface GoodsClient extends GoodsApi {
}
