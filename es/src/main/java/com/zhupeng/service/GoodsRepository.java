package com.zhupeng.service;

import com.zhupeng.entity.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface GoodsRepository extends ElasticsearchRepository<Goods, Long> {

    List<Goods> findByPriceBetween(Double price1 ,Double price2);

    List<Goods> findByTitleAndPrice(String title , Double price);


    List<Goods> findByTitleAndCategory(String title , String category);

    List<Goods> findByTitleOrCategory(String title , String category);


//    List<Person> findByEmailAddressAndLastname(EmailAddress emailAddress, String lastname);
//
//    // Enables the distinct flag for the query
//    List<Person> findDistinctPeopleByLastnameOrFirstname(String lastname, String firstname);
//    List<Person> findPeopleDistinctByLastnameOrFirstname(String lastname, String firstname);
//
//    // Enabling ignoring case for an individual property
//    List<Person> findByLastnameIgnoreCase(String lastname);
//    // Enabling ignoring case for all suitable properties
//    List<Person> findByLastnameAndFirstnameAllIgnoreCase(String lastname, String firstname);
//
//    // Enabling static ORDER BY for a query
//    List<Person> findByLastnameOrderByFirstnameAsc(String lastname);
//    List<Person> findByLastnameOrderByFirstnameDesc(String lastname);
}
