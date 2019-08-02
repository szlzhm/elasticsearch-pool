package com.majing.learning.elasticsearch.highapi.aggregation.metrics;

import java.io.IOException;
import java.util.Map;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.metrics.MinAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.ParsedMin;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import com.majing.learning.elasticsearch.highapi.HighLevelClient;

/**
 * @author:admin
 * @date:2018/7/12
 * @description
 */
public class MaxMinAggregationMain {
    public static void main(String[] args) throws IOException {
        RestHighLevelClient client = HighLevelClient.getInstance();
        try{
//            MaxAggregationBuilder aggregationBuilder = AggregationBuilders.max("utm").field("utm");
            MinAggregationBuilder aggregationBuilder = AggregationBuilders.min("utm").field("utm");

            SearchRequest searchRequest = new SearchRequest("serverlog_20180715");//限定index
            searchRequest.types("log");//限定type

            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.aggregation(aggregationBuilder);
            searchSourceBuilder.size(0);//设置不需要文档数据，只需要返回聚合结果
            searchRequest.source(searchSourceBuilder);

            SearchResponse searchResponse = client.search(searchRequest,RequestOptions.DEFAULT);
            System.out.println(searchResponse);

            //统计结果
            Aggregations aggregations = searchResponse.getAggregations();
            Map<String, Aggregation> aggregationMap = aggregations.asMap();
            for(Map.Entry<String,Aggregation> each: aggregationMap.entrySet()){
//                System.out.println(((ParsedMax)(each.getValue())).getValue());
                System.out.println(((ParsedMin)(each.getValue())).getValue());
            }

        }finally{
            HighLevelClient.close();
        }
    }
}
