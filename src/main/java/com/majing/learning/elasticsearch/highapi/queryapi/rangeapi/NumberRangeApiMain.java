package com.majing.learning.elasticsearch.highapi.queryapi.rangeapi;

import com.majing.learning.elasticsearch.highapi.HighLevelClient;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;

public class NumberRangeApiMain {

    public static void main(String[] args) throws IOException {
        RestHighLevelClient client = HighLevelClient.getInstance();
        try{
            RangeQueryBuilder matchQueryBuilder = QueryBuilders.rangeQuery("utm").from(9,false).to(11,false);

            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(matchQueryBuilder);
            searchSourceBuilder.from(0);
            searchSourceBuilder.size(5);

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
