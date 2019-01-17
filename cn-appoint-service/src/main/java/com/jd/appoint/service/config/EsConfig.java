package com.jd.appoint.service.config;

/**
 * Created by shaohongsen on 2018/5/15.
 */
public class EsConfig {
    /**
     * 集群名称
     */
    private String clusterName;
    /**
     * index名称
     */
    private String indexName;
    /**
     * type 名称
     */
    private String typeName;

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
