package com.microfocus.migrationtool;

import com.hp.ucmdb.api.topology.*;
import com.hp.ucmdb.api.types.TopologyCI;

import java.util.Collection;
import java.util.List;

/**
 * Created by caiy on 2017/11/2.
 */
public class GetStorgeLicense extends Thread{
    private int storgeCi;
    private QueryDefinition queryDefinition;
    private TopologyQueryService topologyQueryService;
    private Collection<TopologyCI> hosts;
    private TopologyQueryFactory queryFactory;
    private Topology topology;
    private QueryNode hostNode;
    private List<String> allSupportCiTypeNames;
    private boolean version;
    GetStorgeLicense(TopologyQueryService topologyQueryService, List<String> allSupportCiTypeNames, boolean version){
        this.topologyQueryService = topologyQueryService;
        queryFactory = topologyQueryService.getFactory();
        this.allSupportCiTypeNames = allSupportCiTypeNames;
        this.version = version;
    }


    public int getLessThen40(){
        int allStorage = 0;

        if (allSupportCiTypeNames.contains("storagearray")) {
            queryDefinition = queryFactory.createQueryDefinition("query tql");
            hostNode = queryDefinition.addNode("Storage Array").ofType("storagearray");
            topology = topologyQueryService.executeQuery(queryDefinition);
            hosts = topology.getAllCIs();

            allStorage = allStorage + hosts.size();
        }

        if (allSupportCiTypeNames.contains("netApp_filer")) {
            queryDefinition = queryFactory.createQueryDefinition("query tql");
            hostNode = queryDefinition.addNode("NetApp Filer").ofType("netApp_filer");
            topology = topologyQueryService.executeQuery(queryDefinition);
            hosts = topology.getAllCIs();

            allStorage = allStorage + hosts.size();
        }

        if (allSupportCiTypeNames.contains("netapp_node")) {
            queryDefinition = queryFactory.createQueryDefinition("query tql");
            hostNode = queryDefinition.addNode("NetApp Node").ofType("netapp_node");
            topology = topologyQueryService.executeQuery(queryDefinition);
            hosts = topology.getAllCIs();

            allStorage = allStorage + hosts.size();
        }

        if (allSupportCiTypeNames.contains("tapelibrary")) {
            queryDefinition = queryFactory.createQueryDefinition("query tql");
            hostNode = queryDefinition.addNode("Tape Library").ofType("tapelibrary");
            topology = topologyQueryService.executeQuery(queryDefinition);
            hosts = topology.getAllCIs();

            allStorage = allStorage + hosts.size();
        }
        return allStorage;
    }

    public int getStorgeCi() {
        return storgeCi;
    }

    public void setStorgeCi(int storgeCi) {
        this.storgeCi = storgeCi;
    }

    @Override
    public void run() {
        setStorgeCi(getLessThen40());
    }
}
