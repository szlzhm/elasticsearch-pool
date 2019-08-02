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
import org.elasticsearch.search.aggregations.metrics.ParsedSum;
import org.elasticsearch.search.aggregations.metrics.SumAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import com.majing.learning.elasticsearch.highapi.HighLevelClient;

/**
 * @author:admin
 * @date:2018/7/12
 * @description
 */
public class SumAggregationMain {

    public static void main(String[] args) throws IOException {
        RestHighLevelClient client = HighLevelClient.getInstance();
        try{
//            QueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("cmd", "weather_hourforcast");
            SumAggregationBuilder aggregationBuilder = AggregationBuilders.sum("utm").field("utm");

            SearchRequest searchRequest = new SearchRequest("serverlog_20180715");//限定index
            searchRequest.types("log");//限定type

            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//            searchSourceBuilder.query(matchQueryBuilder);
            searchSourceBuilder.aggregation(aggregationBuilder);
            searchSourceBuilder.size(0);
            searchRequest.source(searchSourceBuilder);

            SearchResponse searchResponse = client.search(searchRequest,RequestOptions.DEFAULT);
            System.out.println(searchResponse);

            //统计结果
            Aggregations aggregations = searchResponse.getAggregations();
            Map<String, Aggregation> aggregationMap = aggregations.asMap();
            for(Map.Entry<String,Aggregation> each: aggregationMap.entrySet()){
                System.out.println(((ParsedSum)(each.getValue())).getValue());
            }

        }finally{
            HighLevelClient.close();
        }
    }

}
