package org.junhi.cart.service;

import org.apache.commons.lang3.StringUtils;
import org.junhi.cart.client.GoodsClient;
import org.junhi.cart.interceptor.LoginInterceptor;
import org.junhi.cart.pojo.Cart;
import org.junhi.common.pojo.UserInfo;
import org.junhi.common.utils.JsonUtils;
import org.junhi.item.pojo.Sku;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author junhi
 * @date 2019/8/5 21:53
 */
@Service
public class CartService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private GoodsClient goodsClient;

    private static final String KEY_PREFIX = "user:cart:";

    /**
     * 将商品加入购物车
     *
     * @param cart
     */
    public void addCart(Cart cart) {

        //获取用户的信息
        UserInfo userInfo = LoginInterceptor.getUserInfo();

        //查询购物车记录
        BoundHashOperations<String, Object, Object> hashOperations = this.redisTemplate.boundHashOps(KEY_PREFIX + userInfo.getId());

        String key = cart.getSkuId().toString();
        Integer num = cart.getNum();

        //判断当前商品是否在购物车记录当中
        if (hashOperations.hasKey(key)) {
            //在，更新数量
            String cartJson = hashOperations.getKey().toString();
            cart = JsonUtils.parse(cartJson, Cart.class);
            cart.setNum(cart.getNum() + num);
            hashOperations.put(key, JsonUtils.serialize(cart));
        } else {
            //不在，新增购物车
            Sku sku = this.goodsClient.querySkuBySkuId(cart.getSkuId());
            cart.setUserId(userInfo.getId());
            cart.setTitle(sku.getTitle());
            cart.setOwnSpec(sku.getOwnSpec());
            cart.setImage(StringUtils.isBlank(sku.getImages()) ? "" : StringUtils.split(sku.getImages(), ",")[0]);
            cart.setPrice(sku.getPrice());
        }
        hashOperations.put(key, JsonUtils.serialize(cart));
    }

    /**
     * 前端查询购物车中的商品
     * @return
     */
    public List<Cart> queryCarts() {
        UserInfo userInfo = LoginInterceptor.getUserInfo();
        //判断用户是否有购物车记录
        if(!this.redisTemplate.hasKey(KEY_PREFIX + userInfo.getId())){
            return null;
        }
        //获取用户的购物车记录
        BoundHashOperations<String, Object, Object> hashOperations = this.redisTemplate.boundHashOps(KEY_PREFIX + userInfo.getId());

        //获取购物车map中的所有Cart值集合
        List<Object> cartsJson = hashOperations.values();
        //如果购物车集合为空，直接返回null
        if(CollectionUtils.isEmpty(cartsJson)){
            return null;
        }

        //把List<Object>集合转化为List<Cart>集合
        return cartsJson.stream().map(cartJson ->
                JsonUtils.parse(cartJson.toString(), Cart.class)).collect(Collectors.toList()
        );
    }

    /**
     * 购物车修改数量
     * @param cart
     */
    public void updateNum(Cart cart) {
        UserInfo userInfo = LoginInterceptor.getUserInfo();
        //判断用户是否有购物车记录
        if(!this.redisTemplate.hasKey(KEY_PREFIX + userInfo.getId())){
            return;
        }

        Integer num = cart.getNum();

        //获取用户的购物车记录
        BoundHashOperations<String, Object, Object> hashOperations = this.redisTemplate.boundHashOps(KEY_PREFIX + userInfo.getId());

        String cartJson = hashOperations.get(cart.getSkuId().toString()).toString();

        cart = JsonUtils.parse(cartJson, Cart.class);
        cart.setNum(num);

        hashOperations.put(cart.getSkuId().toString(), JsonUtils.serialize(cart));
    }

    /**
     * 删除购物车中的商品
     * @param skuId
     * @return
     */
    public void deleteCart(String skuId) {
        // 获取登录用户
        UserInfo user = LoginInterceptor.getUserInfo();
        String key = KEY_PREFIX + user.getId();
        BoundHashOperations<String, Object, Object> hashOps = this.redisTemplate.boundHashOps(key);
        hashOps.delete(skuId);
    }
}
