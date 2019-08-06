package org.junhi.cart.client;

import org.junhi.item.api.GoodsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 远程调用的接口
 * @author junhi
 * @date 2019/8/5 22:26
 */
@FeignClient("item-service")
public interface GoodsClient extends GoodsApi {
}
