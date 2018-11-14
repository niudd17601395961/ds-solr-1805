package com.mr.controller;

import com.mr.model.TMallSku;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by niudd on 2018/11/14.
 */
@Controller
public class SkuController {

    @Autowired
    private HttpSolrServer httpSolrServer;

    /**
     * 提供接口给sale系统
     * @return
     */
    @ResponseBody
    @RequestMapping("querySkuBySolr")
    public List<TMallSku> querySkuBySolr(String key) throws SolrServerException {
        //创建solrQuery
        SolrQuery solrQuery = new SolrQuery();

        solrQuery.setQuery("skuMch:"+key);

        //查询
        QueryResponse queryResponse = httpSolrServer.query(solrQuery);
        List<TMallSku> beans = queryResponse.getBeans(TMallSku.class);
        System.out.println(beans);
        return beans;
    }
}
