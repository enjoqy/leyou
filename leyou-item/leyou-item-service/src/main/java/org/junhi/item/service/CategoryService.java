package org.junhi.item.service;

import org.junhi.item.mapper.CategoryMapper;
import org.junhi.item.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author junhi
 * @date 2019/7/23 10:16
 */
@Service
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 根据父节点的id查询子节点
     * @param pid
     * @return
     */
    public List<Category> queryCategoriesById(Long pid) {
        Category record = Category.builder().parentId(pid).build();
        return this.categoryMapper.select(record);
    }

    /**
     * 根据id集合查询一个数据集合，单独获取name组成一个新的集合
     * @param ids
     * @return
     */
    public List<String> queryNamesByIds(List<Long> ids){
        List<Category> categories = this.categoryMapper.selectByIdList(ids);
        //流式操作
        return categories.stream().map(category -> category.getName()).collect(Collectors.toList());
    }
}
