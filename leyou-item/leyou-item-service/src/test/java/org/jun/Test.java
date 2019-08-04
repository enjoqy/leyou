package org.jun;

import org.junhi.item.pojo.Sku;

/**
 * @author junhi
 * @date 2019/7/29 9:49
 */
public class Test {

    public static void main(String[] args) {
        Sku build = Sku.builder().build();
        Sku build2 = Sku.builder().build();
        System.out.println(build == build2);
        System.out.println(build.equals(build2));
    }

}
