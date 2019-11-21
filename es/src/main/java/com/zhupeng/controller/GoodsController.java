package com.zhupeng.controller;

import com.alibaba.fastjson.JSONObject;
import com.zhupeng.entity.Goods;
import com.zhupeng.service.GoodsRepository;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.metrics.avg.InternalAvg;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

@RestController
@Slf4j
public class GoodsController {

    @Autowired
    GoodsRepository goodsRepository;

    /**
     * 新增一条文档
     * @return
     */
    @RequestMapping("save")
    public String save(){
        Goods goods = new Goods();
        goods.setId(12334L);
        goods.setCategory("书籍");
        goods.setBrand("文献");
        goods.setImages("adadfa");
        goods.setPrice(123.34);
        goods.setTitle("java开发大全");

        goodsRepository.save(goods);

        return JSONObject.toJSONString(goods);
    }

    /**
     * 批量添加文档 （ES中没有修改文档，根据主键，当新增一条主键相同的记录，就是修改，这样是先将原来的记录删除，然后再新增）
     * @return
     */
    @RequestMapping("batchSave")
    public String batchSave(){
        Goods goods = new Goods();
        goods.setId(1L);
        goods.setCategory("书籍");
        goods.setBrand("文献");
        goods.setImages("adadfa");
        goods.setPrice(23.34);
        goods.setTitle("java源码透析");

        Goods goods2 = new Goods();
        goods2.setId(2L);
        goods2.setCategory("书籍");
        goods2.setBrand("文献");
        goods2.setImages("adadfa");
        goods2.setPrice(2.34);
        goods2.setTitle("php开发大全");


        Goods goods3 = new Goods();
        goods3.setId(3L);
        goods3.setCategory("书籍");
        goods3.setBrand("文献");
        goods3.setImages("adadfa");
        goods3.setPrice(3.34);
        goods3.setTitle("c++开发大全");


        Goods goods4 = new Goods();
        goods4.setId(5L);
        goods4.setCategory("书籍");
        goods4.setBrand("文献");
        goods4.setImages("adadfa");
        goods4.setPrice(50.34);
        goods4.setTitle("pythan源码透析");

        Goods goods6 = new Goods();
        goods6.setId(6L);
        goods6.setCategory("书籍");
        goods6.setBrand("文献");
        goods6.setImages("adadfa");
        goods6.setPrice(80.34);
        goods6.setTitle("c开发大全");


        Goods goods7 = new Goods();
        goods7.setId(7L);
        goods7.setCategory("书籍");
        goods7.setBrand("文献");
        goods7.setImages("adadfa");
        goods7.setPrice(90.34);
        goods7.setTitle("汇编语言");



        Goods goods8 = new Goods();
        goods8.setId(8L);
        goods8.setCategory("书籍");
        goods8.setBrand("文献");
        goods8.setImages("adadfa");
        goods8.setPrice(12.34);
        goods8.setTitle("vue");


        Goods goods9 = new Goods();
        goods9.setId(9L);
        goods9.setCategory("书籍");
        goods9.setBrand("文献");
        goods9.setImages("adadfa");
        goods9.setPrice(0.34);
        goods9.setTitle("html透析");

        Goods goods10 = new Goods();
        goods10.setId(10L);
        goods10.setCategory("蛋糕");
        goods10.setBrand("食物");
        goods10.setImages("adadfa");
        goods10.setPrice(220.34);
        goods10.setTitle("草莓蛋糕");


        Goods goods11 = new Goods();
        goods11.setId(7L);
        goods11.setCategory("蛋糕");
        goods11.setBrand("食物");
        goods11.setImages("adadfa");
        goods11.setPrice(100.34);
        goods11.setTitle("奶油蛋糕");


        Goods goods16 = new Goods();
        goods16.setId(16L);
        goods16.setCategory("书籍");
        goods16.setBrand("文献");
        goods16.setImages("adadfa");
        goods16.setPrice(90.34);
        goods16.setTitle("oracle语言");



        Goods goods12 = new Goods();
        goods12.setId(12L);
        goods12.setCategory("书籍");
        goods12.setBrand("文献");
        goods12.setImages("adadfa");
        goods12.setPrice(1.34);
        goods12.setTitle("js");


        Goods goods13 = new Goods();
        goods13.setId(13L);
        goods13.setCategory("书籍");
        goods13.setBrand("文献");
        goods13.setImages("adadfa");
        goods13.setPrice(5.34);
        goods13.setTitle("透javascript析");

        Goods goods14 = new Goods();
        goods14.setId(14L);
        goods14.setCategory("蛋糕");
        goods14.setBrand("食物");
        goods14.setImages("adadfa");
        goods14.setPrice(20.34);
        goods14.setTitle("蛋鸡蛋糕");

        Goods goods15 = new Goods();
        goods15.setId(15L);
        goods15.setCategory("书籍");
        goods15.setBrand("文献");
        goods15.setImages("adadfa");
        goods15.setPrice(70.34);
        goods15.setTitle("透mysql");


        List<Goods> goodsList = new LinkedList<>();
        goodsList.add(goods);
        goodsList.add(goods2);
        goodsList.add(goods3);
        goodsList.add(goods4);
        goodsList.add(goods6);
        goodsList.add(goods7);
        goodsList.add(goods8);
        goodsList.add(goods9);
        goodsList.add(goods10);
        goodsList.add(goods11);
        goodsList.add(goods12);
        goodsList.add(goods13);
        goodsList.add(goods14);
        goodsList.add(goods15);
        goodsList.add(goods16);

        goodsRepository.saveAll(goodsList);

        return goodsList.size() + "";
    }

    /**
     * 删除文档
     * @return
     */
    @RequestMapping("delete")
    public String delete(){

        Iterable<Goods> list = null;

        //根据主键删除文档
        goodsRepository.deleteById(12334L);

        list = goodsRepository.findAll();

        for(Goods goods1 : list){
            log.info("111111=" + goods1.getTitle() + goods1.getId());
        }

        Goods goods = new Goods();
        goods.setId(3444L);
        goods.setCategory("书籍");
        goods.setBrand("文献");
        goods.setImages("adadfa");
        goods.setPrice(23.34);
        goods.setTitle("java源码透析");

        //根据实体类的信息进行删除 TODO   感觉这个方法也是根据主键进行删除对象，跟其他属性值没有任何关系
        goodsRepository.delete(goods);

        list = goodsRepository.findAll();

        for(Goods goods1 : list){
            log.info("222222=" + goods1.getTitle() + goods1.getId());
        }

        Goods goods2 = new Goods();
        goods2.setId(2L);
        goods2.setCategory("书籍");
        goods2.setBrand("文献");
        goods2.setImages("adadfa");
        goods2.setPrice(2.34);
        goods2.setTitle("php开发大全");


        Goods goods3 = new Goods();
        goods3.setId(3L);
        goods3.setCategory("书籍");
        goods3.setBrand("文献");
        goods3.setImages("adadfa");
        goods3.setPrice(3.34);
        goods3.setTitle("c++开发大全");


        List<Goods> goodsList = new LinkedList<>();
        goodsList.add(goods3);
        goodsList.add(goods2);

        goodsRepository.deleteAll(goodsList);

        list = goodsRepository.findAll();

        for(Goods goods1 : list){
            log.info("333333=" + goods1.getTitle() + goods1.getId());
        }

        Goods goods4 = new Goods();
        goods4.setId(4L);
        goods4.setCategory("书籍");
        goods4.setBrand("文献");
        goods4.setImages("adadfa");
        goods4.setPrice(4.34);
        goods4.setTitle("c++开发大全");

        goodsRepository.save(goods4);

        list = goodsRepository.findAll();

        for(Goods goods1 : list){
            log.info("44444=" + goods1.getTitle() + goods1.getId());
        }

        return "删除成功";
    }

    @RequestMapping("find")
    public String find(){
        Iterable<Goods> list = null;

        list = goodsRepository.findAll(Sort.by("price").ascending());

        log.info( "findAll And Sort : " +JSONObject.toJSONString(list));

        list = goodsRepository.findByPriceBetween(2.0 , 5.0);

        log.info( "findByPriceBetween : " +JSONObject.toJSONString(list));

        list = goodsRepository.findByTitleAndPrice("大全" , 4.34);

        log.info( "findByTitleAndPrice : " +JSONObject.toJSONString(list));

        list = goodsRepository.findByTitleAndCategory("大全" , "书籍");

        log.info( "findByTitleAndCategory : " +JSONObject.toJSONString(list));

        list = goodsRepository.findByTitleOrCategory("大全" , "");

        log.info( "findByTitleOrCategory : " + JSONObject.toJSONString(list));

        return JSONObject.toJSONString(list);
    }

    /**
     * matchQuery  termQuery  boolQuery  FuzzyQuery 进行自定义查询
     */
    @RequestMapping("matchQuery")
    public String matchQuery(){

        /**
         * matchQuery 查询（匹配查询，会匹配title中含有“大全”的所有记录，但是不会匹配查询出“大家全”这样的记录(数字不能这样)；matchQuery只会匹配字符串，数字类型的要完全相等的）
         */
        NativeSearchQueryBuilder matchQuery = new NativeSearchQueryBuilder(); //查询条件构建器

        matchQuery.withQuery(QueryBuilders.matchQuery("title" , "大全")); //查询条件

        int page = 0 ;
        int size = 2;
        matchQuery.withPageable(PageRequest.of(page , size)); //分页查询

        matchQuery.withSort(SortBuilders.fieldSort("price").order(SortOrder.ASC)); //排序查询

        Page<Goods> matchQueryGoodsPage = goodsRepository.search(matchQuery.build()); //这里没有进行分页，直接将所有符合条件的数据全部取出

        log.info("matchQuery== 总条数" + matchQueryGoodsPage.getTotalElements() + "size" + matchQueryGoodsPage.getSize() +"总页数：" + matchQueryGoodsPage.getTotalPages() + JSONObject.toJSONString(matchQueryGoodsPage));


        // 总条数
        long total = matchQueryGoodsPage.getTotalElements();
        System.out.println("总条数 = " + total);
        // 总页数
        System.out.println("总页数 = " + matchQueryGoodsPage.getTotalPages());
        // 当前页
        System.out.println("当前页：" + matchQueryGoodsPage.getNumber());
        // 每页大小
        System.out.println("每页大小：" + matchQueryGoodsPage.getSize());



        /**
         * termQuery 查询
         */
        NativeSearchQueryBuilder termQuery = new NativeSearchQueryBuilder(); //查询条件构建器

        termQuery.withQuery(QueryBuilders.termQuery("price" , 3.34)); //termQuery:功能更强大，除了匹配字符串以外，还可以匹配int/long/double/float/....

        Page<Goods> termQueryGoodsPage = goodsRepository.search(termQuery.build()); //这里没有进行分页，直接将所有符合条件的数据全部取出

        log.info("termQuery== 总条数" + termQueryGoodsPage.getTotalElements() + "size" + termQueryGoodsPage.getSize() +"总页数：" + termQueryGoodsPage.getTotalPages() + JSONObject.toJSONString(termQueryGoodsPage));


        /**
         * boolQuery 查询
         */
        NativeSearchQueryBuilder boolQuery = new NativeSearchQueryBuilder(); //查询条件构建器

        boolQuery.withQuery(QueryBuilders.boolQuery()
                        .must(QueryBuilders.matchQuery("title" , "语言"))
                        .must(QueryBuilders.matchQuery("category" , "书籍"))
                        );

        Page<Goods> boolQueryGoodsPage = goodsRepository.search(boolQuery.build()); //这里没有进行分页，直接将所有符合条件的数据全部取出

        log.info("boolQuery== 总条数" + boolQueryGoodsPage.getTotalElements() +"总页数：" + boolQueryGoodsPage.getTotalPages() + JSONObject.toJSONString(boolQueryGoodsPage));



        /**
         * fuzzyQuery 模糊查询
         */
        NativeSearchQueryBuilder fuzzyQuery = new NativeSearchQueryBuilder(); //查询条件构建器

        fuzzyQuery.withQuery(QueryBuilders.fuzzyQuery("title" , "透析"));

        Page<Goods> fuzzyQueryGoodsPage = goodsRepository.search(fuzzyQuery.build()); //这里没有进行分页，直接将所有符合条件的数据全部取出

        log.info("fuzzyQuery== 总条数" + fuzzyQueryGoodsPage.getTotalElements() +"总页数：" + fuzzyQueryGoodsPage.getTotalPages() + JSONObject.toJSONString(fuzzyQueryGoodsPage));

        return "success";
    }


    @RequestMapping("aggregated")
    public String aggregated(){

        NativeSearchQueryBuilder termAggregated = new NativeSearchQueryBuilder();

        //不查询任何结果
        termAggregated.withSourceFilter(new FetchSourceFilter(new String[]{""}, null));

        //1、添加一个新的聚合，聚合类型为terms，聚合名称为brands，聚合字段为brand
        termAggregated.addAggregation(
                AggregationBuilders.terms("brands").field("brand")
        );

        // 2、查询,需要把结果强转为AggregatedPage类型
        AggregatedPage<Goods> atermAggregatedPage = (AggregatedPage<Goods>)goodsRepository.search(termAggregated.build());


        // 3、解析
        // 3.1、从结果中取出名为brands的那个聚合，
        // 因为是利用String类型字段来进行的term聚合，所以结果要强转为StringTerm类型
        StringTerms stringTerms = (StringTerms)atermAggregatedPage.getAggregation("brands");

        // 3.2、获取桶
        List<StringTerms.Bucket> stringTermsBucket = stringTerms.getBuckets();

        //遍历桶
        for(StringTerms.Bucket bucket : stringTermsBucket){

            // 3.4、获取桶中的key，即品牌名称
            String key = bucket.getKeyAsString();

            // 3.5、获取桶中的文档数量
            long docCount = bucket.getDocCount();

            log.info("桶中的key:{} , 桶中的文档数量:{}" , key , docCount);
        }




        NativeSearchQueryBuilder aggregated = new NativeSearchQueryBuilder();

        //不查询任何结果
        aggregated.withSourceFilter(new FetchSourceFilter(new String[]{""}, null));

        //1、添加一个新的聚合，聚合类型为terms，聚合名称为brands，聚合字段为brand
        aggregated.addAggregation(
                AggregationBuilders.terms("brands").field("brand")
                .subAggregation(AggregationBuilders.avg("priceAvg").field("price"))  // 在品牌聚合桶内进行嵌套聚合，求平均值
        );

        // 2、查询,需要把结果强转为AggregatedPage类型
        AggregatedPage<Goods> aggregatedPage = (AggregatedPage<Goods>)goodsRepository.search(aggregated.build());

        // 3、解析
        // 3.1、从结果中取出名为brands的那个聚合，
        // 因为是利用String类型字段来进行的term聚合，所以结果要强转为StringTerm类型
        StringTerms aggregationStringTerms = (StringTerms)aggregatedPage.getAggregation("brands");

        // 3.2、获取桶
        List<StringTerms.Bucket> aggregationStringTermsBucket = aggregationStringTerms.getBuckets();

        //遍历桶
        for(StringTerms.Bucket bucket : aggregationStringTermsBucket){

            // 3.4、获取桶中的key，即品牌名称  3.5、获取桶中的文档数量
            log.info(bucket.getKeyAsString() + "，共" + bucket.getDocCount() + "台");

            InternalAvg internalAvg = (InternalAvg)bucket.getAggregations().asMap().get("priceAvg");

            log.info("平均价格：" + internalAvg.getValue());


        }

        return "success";
    }

}
