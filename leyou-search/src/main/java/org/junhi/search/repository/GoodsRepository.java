package org.junhi.search.repository;

import org.junhi.search.pojo.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * 实现ElasticsearchRepository接口，就可以用来crud
 * @author junhi
 * @date 2019/7/30 19:44
 */
public interface GoodsRepository extends ElasticsearchRepository<Goods, Long> {
}
