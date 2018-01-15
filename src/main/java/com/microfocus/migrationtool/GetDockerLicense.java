package com.microfocus.migrationtool;

import com.hp.ucmdb.api.topology.QueryDefinition;
import com.hp.ucmdb.api.topology.Topology;
import com.hp.ucmdb.api.topology.TopologyQueryFactory;
import com.hp.ucmdb.api.topology.TopologyQueryService;
import com.hp.ucmdb.api.types.TopologyCI;

import java.util.Collection;
import java.util.List;

/**
 * Created by caiy on 2017/11/2.
 */
public class GetDockerLicense extends Thread{
    private int dockerCi;
    private TopologyQueryService topologyQueryService;
    private List<String> allSupportCiTypeNames;
    private boolean version;
    GetDockerLicense(TopologyQueryService topologyQueryService, List<String> allSupportCiTypeNames,boolean version){
        this.topologyQueryService = topologyQueryService;
        this.allSupportCiTypeNames = allSupportCiTypeNames;
        this.version = version;
    }

    public int getLessThen40(){
        int allDocker = 0;
        TopologyQueryFactory queryFactory = topologyQueryService.getFactory();
        QueryDefinition queryDefinition = queryFactory.createQueryDefinition("query tql");
        if (allSupportCiTypeNames.contains("docker_container")) {
            Topology topology = topologyQueryService.executeQuery(queryDefinition);
            Collection<TopologyCI> hosts = topology.getAllCIs();
            allDocker = allDocker + hosts.size();
        }
        return allDocker;
    }

    public int getDockerCi() {
        return dockerCi;
    }

    public void setDockerCi(int dockerCi) {
        this.dockerCi = dockerCi;
    }

    @Override
    public void run() {
        setDockerCi (getLessThen40());
    }
}
