package com.leyou.goods.service;

import com.leyou.goods.utils.ThreadUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * 产生页面静态化的类
 * @author junhi
 * @date 2019/8/2 10:24
 */
@Service
public class GoodsHtmlService {

    @Autowired
    private TemplateEngine engine;

    @Autowired
    private GoodsService goodsService;

    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsHtmlService.class);

    /**
     * 产生一个静态页面
     * @param spuId
     */
    public void createHtml(Long spuId){

        //初始化运行上下文
        Context context = new Context();
        //设置数据模型
        context.setVariables(this.goodsService.loadData(spuId));

        PrintWriter printWriter = null;
        try {
            //把静态文件生成到服务器本地
            // 创建输出流
            File file = new File("H:\\tools\\nginx-1.14.0\\html\\item\\" + spuId + ".html");
            printWriter = new PrintWriter(file);


            this.engine.process("item",context,printWriter);
        } catch (FileNotFoundException e) {
            LOGGER.error("页面静态化出错：{}，"+ e, spuId);
            e.printStackTrace();
        } finally {
            if(printWriter != null){
                printWriter.close();
            }
        }
    }

    /**
     * 新建线程处理页面静态化
     * 生成html 的代码不能对用户请求产生影响，所以这里我们使用额外的线程进行异步创建。
     * @param spuId
     */
    public void asyncExcute(Long spuId) {
        ThreadUtils.execute(()->createHtml(spuId));
        /*ThreadUtils.execute(new Runnable() {
            @Override
            public void run() {
                createHtml(spuId);
            }
        });*/
    }

    /**
     * 删除一个静态的页面
     * @param id
     */
    public void deleteHtml(Long id) {
        try {
            File file = new File("H:\\tools\\nginx-1.14.0\\html\\item\\" + id + ".html");
            //文件如果存在就删除
            file.deleteOnExit();
        } catch (Exception e) {
            LOGGER.error("页面静态化删除出错：{}，"+ e, id);
            e.printStackTrace();
        }
    }
}
