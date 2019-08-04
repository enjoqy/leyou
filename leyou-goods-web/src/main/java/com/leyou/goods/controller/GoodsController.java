package com.leyou.goods.controller;

import com.leyou.goods.service.GoodsHtmlService;
import com.leyou.goods.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

/**
 * @author junhi
 * @date 2019/8/1 15:44
 */
@Controller
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private GoodsHtmlService goodsHtmlService;

    /**
     * 对商品详情页做静态化处理
     * @param id
     * @param model
     * @return
     */
    @GetMapping("item/{id}.html")
    public String toItemPage(@PathVariable("id")Long id, Model model){

        // 加载所需的数据
        Map<String, Object> map = this.goodsService.loadData(id);

        // 把数据放入数据模型
        model.addAllAttributes(map);

        // 页面静态化，生成html 的代码不能对用户请求产生影响，所以这里我们使用额外的线程进行异步创建。
        this.goodsHtmlService.asyncExcute(id);

        return "item";
    }

}
