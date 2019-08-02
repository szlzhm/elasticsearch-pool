package com.majing.learning.elasticsearch.highapi.miscellaneous.infoapi;

import java.io.IOException;

import org.elasticsearch.Build;
import org.elasticsearch.Version;
import org.elasticsearch.action.main.MainResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.cluster.ClusterName;

import com.majing.learning.elasticsearch.highapi.HighLevelClient;

public class InfoApiMain {

    public static void main(String[] args) throws IOException {
        info();
    }

    public static void info() throws IOException {
        try{
            RestHighLevelClient client = HighLevelClient.getInstance();

            org.elasticsearch.client.core.MainResponse response = client.info(RequestOptions.DEFAULT);

            String clusterName = response.getClusterName();
            String clusterUuid = response.getClusterUuid();
            String nodeName = response.getNodeName();
           // String version = response.getVersion();
            //Build build = response.getBuild();

            System.out.println("clusterName: "+clusterName);
            System.out.println("clusterUuid: "+clusterUuid);
            System.out.println("nodeName: "+nodeName);
            //System.out.println("version: "+version);
            //System.out.println("build: "+build);

        }finally{
            HighLevelClient.close();
        }
    }
}
