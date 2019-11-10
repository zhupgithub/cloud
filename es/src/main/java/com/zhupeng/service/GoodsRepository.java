package com.zhupeng.service;

import com.zhupeng.entity.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface GoodsRepository extends ElasticsearchRepository<Goods, Long> {

    List<Goods> findByPriceBetween(Double price1 ,Double price2);

    List<Goods> findByTitleAndPrice(String title , Double price);


    List<Goods> findByTitleAndCategory(String title , String category);

    List<Goods> findByTitleOrCategory(String title , String category);
}
