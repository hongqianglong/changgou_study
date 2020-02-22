package com.changgou.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.changgou.goods.feign.SkuFeign;
import com.changgou.goods.pojo.Sku;
import com.changgou.search.dao.ESManagerMapper;
import com.changgou.search.service.ESManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;
import pojo.SkuInfo;

import java.util.List;
import java.util.Map;

/**
 * @author hql
 * @date 2020-01-09 20:09
 */

@Service
public class ESManagerServiceImpl implements ESManagerService {


    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
    @Autowired
    private SkuFeign skuFeign;
    @Autowired
    private ESManagerMapper esManagerMapper;
    @Override
    public void createIndex() {
        //创建索引库
        elasticsearchTemplate.createIndex(SkuInfo.class);
        //添加映射
        elasticsearchTemplate.putMapping(SkuInfo.class);
    }

    /**
     * 导入所有的数据进去elasticsearch
     */
    @Override
    public void importAll() {

        List<Sku> skuList = skuFeign.findSkuListBySpuId("all");
        if (skuList!=null&&skuList.size()>0){
          throw new RuntimeException("当前没有数据被查询到，无法导入数据库");
        }
        //把Sku转换为Json
        String jsonSkuList = JSON.toJSONString(skuList);
        //将Json转换为skuInfo对象的集合
        List<SkuInfo> skuInfos = JSON.parseArray(jsonSkuList, SkuInfo.class);
        //sku的里面有规格的信息
        for (SkuInfo skuInfo : skuInfos) {
            String spec = skuInfo.getSpec();
            Map map = JSON.parseObject(spec, Map.class);
            skuInfo.setSpecMap(map);
        }
        esManagerMapper.saveAll(skuInfos);
    }
    //根据spuid查询skuList,添加到索引库
    @Override
    public void importDataBySpuId(String spuId) {

        List<Sku> skuListBySpuId = skuFeign.findSkuListBySpuId(spuId);
        //将根据id拿到的skuList转换为jsonSku
        String jsonSku = JSON.toJSONString(skuListBySpuId);
        //将sku的集合转换为SkuInfo对象的集合
        List<SkuInfo> skuInfos = JSON.parseArray(jsonSku, SkuInfo.class);
        for (SkuInfo skuInfo : skuInfos) {
            String spec = skuInfo.getSpec();
            Map map = JSON.parseObject(spec, Map.class);
            skuInfo.setSpecMap(map);
        }
        esManagerMapper.saveAll(skuInfos);

    }
    @Override
    public void delDataBySpuId(String spuId) {

    }
}
