package com.microfocus.migrationtool;

import com.hp.ucmdb.api.topology.*;
import com.hp.ucmdb.api.types.TopologyCI;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.Collection;

/**
 * Created by caiy on 2017/11/2.
 */
public class GetNetworkLicense extends Thread {
    private int networkCi;
    private TopologyQueryService topologyQueryService;
    private boolean version;

    GetNetworkLicense(TopologyQueryService topologyQueryService, boolean version) {
        this.topologyQueryService = topologyQueryService;
        this.version = version;
    }

    public int getLessThen40() {
        int allNetwork = 0;
        TopologyQueryFactory queryFactory = topologyQueryService.getFactory();
        QueryDefinition queryDefinition = queryFactory.createQueryDefinition("query tql");
        QueryNode hostNode = queryDefinition.addNode("Net Device").ofType("netdevice").queryProperties("class-name","data_source");
        Topology topology = topologyQueryService.executeQuery(queryDefinition);
        Collection<TopologyCI> hosts = topology.getAllCIs();
        try{
            for (TopologyCI host : hosts) {
                String classType = (String) host.getPropertyValue("class-name");
                String a = (String) host.getPropertyValue("data_source");
                if (!"storagearray".equals(classType) && !"netApp_filer".equals(classType)
                        && !"netapp_node".equals(classType) && !"tapelibrary".equals(classType))
                    allNetwork++;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return allNetwork;
    }

    public int getNetworkCi() {
        return networkCi;
    }

    public void setNetworkCi(int networkCi) {
        this.networkCi = networkCi;
    }

    @Override
    public void run() {
        setNetworkCi(getLessThen40());
    }
}
