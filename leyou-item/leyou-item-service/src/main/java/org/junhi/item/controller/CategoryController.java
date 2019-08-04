package org.junhi.item.controller;

import org.junhi.item.pojo.Category;
import org.junhi.item.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author junhi
 * @date 2019/7/23 10:17
 */
@Controller
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 根据父节点的id查询子节点
     *
     * @param pid
     * @return
     */
    @GetMapping("/list")
    public ResponseEntity<List<Category>> queryCategoriesById(@RequestParam(value = "pid", defaultValue = "0") Long pid) {
        if (pid == null || pid < 0) {
            //相应400，参数不合法
            //return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            //return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            return ResponseEntity.badRequest().build();
        }
        List<Category> categories = this.categoryService.queryCategoriesById(pid);
        //判断结果集是否为空
        if (CollectionUtils.isEmpty(categories)) {
            //相应404，资源没有找到
            return ResponseEntity.notFound().build();
        }
        //相应200，ok
        return ResponseEntity.ok(categories);
    }

    /**
     * 根据id集合，查询商品名字集合
     * @param ids
     * @return
     */
    @GetMapping
    public ResponseEntity<List<String>> queryNamesByIds(@RequestParam("ids")List<Long> ids){
        List<String> names = this.categoryService.queryNamesByIds(ids);
        //判断结果集是否为空
        if (CollectionUtils.isEmpty(names)) {
            //相应404，资源没有找到
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(names);
    }

}
