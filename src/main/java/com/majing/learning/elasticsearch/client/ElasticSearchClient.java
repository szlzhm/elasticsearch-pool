package com.majing.learning.elasticsearch.client;

import org.apache.http.Header;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.MainResponse;

import com.majing.learning.elasticsearch.exception.ElasticSearchException;
import com.majing.learning.elasticsearch.pool.ElasticSearchConnectionManager;
import com.majing.learning.elasticsearch.pool.ElasticSearchPool;


/**
 * @author : jingma2
 * @date :2018/7/21
 * @description
 */
public class ElasticSearchClient {

    private ElasticSearchPool pool;

    private ElasticSearchClient(String clusterName){
        this.pool = ElasticSearchConnectionManager.sharePool(clusterName);
    }

    public boolean ping(){
        RestHighLevelClient client = null;
        try{
            client = getResource();
            boolean result = client.ping(RequestOptions.DEFAULT);
            returnResource(client);
            return result;
        }catch(Exception e){
            returnBrokenResource(client);
            return false;
        }
    }

    public MainResponse info(){
        RestHighLevelClient client = null;
        try{
            client = getResource();
            org.elasticsearch.client.core.MainResponse result = client.info(RequestOptions.DEFAULT);
            returnResource(client);
            return result;
        }catch(Exception e){
            returnBrokenResource(client);
            throw new ElasticSearchException(e);
        }
    }

    public boolean exists(GetRequest getRequest, RequestOptions options){
        RestHighLevelClient client = null;
        try{
            client = getResource();
            boolean result = client.exists(getRequest,options);
            returnResource(client);
            return result;
        }catch(Exception e){
            returnBrokenResource(client);
            throw new ElasticSearchException(e);
        }
    }

    public GetResponse get(GetRequest getRequest, RequestOptions options){
        RestHighLevelClient client = null;
        try{
            client = getResource();
            GetResponse result = client.get(getRequest,options);
            returnResource(client);
            return result;
        }catch(Exception e){
            returnBrokenResource(client);
            throw new ElasticSearchException(e);
        }
    }

    public RestHighLevelClient getResource(){
        RestHighLevelClient client = null;
        try{
            client = pool.getResource();
            return client;
        }catch(RuntimeException e){
            if(client!=null) {
                returnBrokenResource(client);
            }
            throw new ElasticSearchException(e);
        }
    }

    public void returnResource(RestHighLevelClient client){
        try{
            if(client!=null){
                this.pool.returnResource(client);
            }
        }catch(Exception e){
            this.pool.returnBrokenResource(client);
        }
    }

    public void returnBrokenResource(RestHighLevelClient client){
        pool.returnBrokenResource(client);
    }
}
