package com.majing.learning.elasticsearch.highapi.queryapi.termlevel;

import com.majing.learning.elasticsearch.highapi.HighLevelClient;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.ExistsQueryBuilder;
import org.elasticsearch.index.query.PrefixQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;

/**
 * @author:admin
 * @date:2018/7/12
 * @description
 */
public class PrefixApiMain {

    public static void main(String[] args) throws IOException {
        RestHighLevelClient client = HighLevelClient.getInstance();
        try{
            PrefixQueryBuilder matchQueryBuilder = QueryBuilders.prefixQuery("cmd","get_fee");//查询某个字段存在的记录

            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(matchQueryBuilder);
            searchSourceBuilder.from(0);
            searchSourceBuilder.size(1);

            SearchRequest searchRequest = new SearchRequest("serverlog_20180701");//限定index
            searchRequest.types("log");//限定type
            searchRequest.source(searchSourceBuilder);

            SearchResponse searchResponse = client.search(searchRequest,RequestOptions.DEFAULT);
            System.out.println(searchResponse);


        }finally{
            HighLevelClient.close();
        }
    }
}
