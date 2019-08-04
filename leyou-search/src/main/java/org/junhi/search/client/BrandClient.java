package org.junhi.search.client;

import org.junhi.item.api.BrandApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author junhi
 * @date 2019/7/30 16:17
 */
@FeignClient("item-service")
public interface BrandClient extends BrandApi {
}
