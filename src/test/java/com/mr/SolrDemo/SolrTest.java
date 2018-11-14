package com.mr.SolrDemo;


import com.mr.mapper.SkuMapper;
import com.mr.model.TMallSku;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.List;

/**
 * Created by yaodd on 2018/11/13.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring-base.xml","classpath:spring-common.xml","classpath:spring-solr-config.xml"})
public class SolrTest {

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private HttpSolrServer httpSolrServer;

    /**
     * 查询数据库中sku的内容
     */
    @Test
    public void test1() throws IOException, SolrServerException {
        List<TMallSku> skuList = skuMapper.listSku();
        System.out.println(skuList);

        HttpSolrServer httpSolrServer = new HttpSolrServer("http://127.0.0.1:8983/solr/test");

        //设置保存的xml格式
        httpSolrServer.setParser(new XMLResponseParser());

        httpSolrServer.addBeans(skuList);
        httpSolrServer.commit();

    }
    @Test
    public void test2() throws SolrServerException {
        //创建solrQuery
        SolrQuery solrQuery = new SolrQuery();

        solrQuery.setQuery("skuMch:300");

        //查询
        QueryResponse queryResponse = httpSolrServer.query(solrQuery);
        List<TMallSku> beans = queryResponse.getBeans(TMallSku.class);
        System.out.println(beans);

    }


}
