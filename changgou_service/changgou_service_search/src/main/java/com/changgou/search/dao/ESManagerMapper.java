package com.changgou.search.dao;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import pojo.SkuInfo;

/**
 * @author hql
 * @date 2020-01-09 20:08
 */

public interface ESManagerMapper  extends ElasticsearchRepository<SkuInfo,Long> {

}
