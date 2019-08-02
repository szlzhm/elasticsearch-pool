package com.majing.learning.elasticsearch.highapi.miscellaneous.pingapi;

import com.majing.learning.elasticsearch.highapi.HighLevelClient;

import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

public class PingApiMain {

    public static void main(String[] args) throws IOException {
        ping();
    }

    public static void ping() throws IOException {
        try{
            RestHighLevelClient client = HighLevelClient.getInstance();

            boolean response = client.ping(RequestOptions.DEFAULT);

            System.out.println("ping response: " + response);

        }finally{
            HighLevelClient.close();
        }
    }
}
