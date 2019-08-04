package org.junhi.search.controller;

import org.junhi.common.pojo.PageResult;
import org.junhi.search.pojo.Goods;
import org.junhi.search.pojo.SearchRequest;
import org.junhi.search.pojo.SearchResult;
import org.junhi.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author junhi
 * @date 2019/7/31 9:09
 */
@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;

    /**
     * 根据搜索的条件，相应搜索结果
     * @param request
     * @return
     */
    @PostMapping("page")
    public ResponseEntity<SearchResult> search(@RequestBody SearchRequest request){
        SearchResult result = this.searchService.search(request);
        if(result == null || CollectionUtils.isEmpty(result.getItems())){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }
}
