package org.junhi.search.client;

import org.junhi.item.api.SpecificationApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author junhi
 * @date 2019/7/30 16:18
 */
@FeignClient("item-service")
public interface SpecificationClient extends SpecificationApi {
}
