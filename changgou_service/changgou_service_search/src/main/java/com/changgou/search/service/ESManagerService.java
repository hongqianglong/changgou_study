package com.changgou.search.service;

/**
 * @author hql
 * @date 2020-01-09 20:08
 */

public interface ESManagerService {
        //创建索引库
    void createIndex();
        //导入全部的数据
    void  importAll();
    //根据spuid查询skuList,再导入索引库
    void importDataBySpuId(String spuId);

    //根据souid删除es索引库中相关的sku数据
    void delDataBySpuId(String spuId);


}
