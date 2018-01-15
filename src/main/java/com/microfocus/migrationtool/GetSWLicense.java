package com.microfocus.migrationtool;

import com.hp.ucmdb.api.topology.*;
import com.hp.ucmdb.api.types.TopologyCI;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by caiy on 2017/11/2.
 */
public class GetSWLicense extends Thread {
    private int workStationCi;
    private int serverUdiCi;
    private int serverUdfCi;
    private int vm;
    private int workstationOfOpbs;
    private int workstationFullAndBasic;
    private int serverOfFull;
    private int serverBasicAndOpbs;
    private QueryDefinition queryDefinition;
    private TopologyQueryService topologyQueryService;
    private Boolean version;
    private static final String[] EXCLUDE_TYPES = new String[] {"hp_complex", "hp_nonstop", "ibm_pseries_frame", "mainframe", "terminalserver"};
    GetSWLicense(QueryDefinition queryDefinition, TopologyQueryService topologyQueryService, Boolean version) {
        this.queryDefinition = queryDefinition;
        this.topologyQueryService = topologyQueryService;
        this.version = version;
    }

    public void getLessThan40() {
        //calculate servers and workstations
        int fullServer = 0;
        int basicServer = 0;
        int allWorkstation = 0;
        int vm = 0;
        PropertyConditionBuilder b = null;
        TopologyQueryFactory queryFactory = topologyQueryService.getFactory();
        QueryDefinition nodeQueryDefinition = queryFactory.createQueryDefinition("query tql");

        QueryNode hostNode = nodeQueryDefinition.addNode("Computer").ofType("host_node").queryProperties("node_role", "lic_type_udf", "lic_type_udi");
        b = hostNode.propertiesConditionBuilder();
        hostNode.withPropertiesConditions(b.use(b.property("lic_type_udi").isEqualTo(true)).or(b.use(b.property("lic_type_udf").isEqualTo(true)))).setAsPerspectiveContact();
        Topology topology = topologyQueryService.executeQuery(nodeQueryDefinition);
        Collection<TopologyCI> hosts = topology.getAllCIs();
        for (TopologyCI host : hosts) {
            Object nodeRoleValue = host.getPropertyValue("node_role");
            boolean isUDF = (Boolean) host.getPropertyValue("lic_type_udf");
            boolean isUDI = (Boolean) host.getPropertyValue("lic_type_udi");
            String ciType = host.getType();
            if (isInExculdeList(ciType)){
                continue;
            }

            boolean isServer = true;
            if (nodeRoleValue != null && nodeRoleValue instanceof List) {
                List<String> nodeRoleValueList = (List<String>) nodeRoleValue;
                List<String> tempNodeRoleValueList = new ArrayList<String>();
                for (String value : nodeRoleValueList) {
                    tempNodeRoleValueList.add(value.toLowerCase());
                }
                if (tempNodeRoleValueList.contains("desktop")) {
                    isServer = false;
                }
            }


            if (isServer) {
                if (isUDF) {
                    fullServer++;
                } else {
                    basicServer++;
                }
            } else {
                allWorkstation++;
            }

        }

        //adjust the vm link to hypervisor for servers
        nodeQueryDefinition = queryFactory.createQueryDefinition("query tql");
        hostNode = nodeQueryDefinition.addNode("Computer").ofType("host_node").queryProperties("node_role", "lic_type_udf", "lic_type_udi");
        QueryNode hypervisorNode = nodeQueryDefinition.addNode("Hypervisor").ofType("hypervisor");
        hostNode.linkedTo(hypervisorNode).withLinkOfType("composition").atLeast(1);
        b = hostNode.propertiesConditionBuilder();
        hostNode.withPropertiesConditions(b.use(b.property("lic_type_udi").isEqualTo(true)).and(b.use(b.property("lic_type_udf").isEqualTo(false)))).setAsPerspectiveContact();
        topology = topologyQueryService.executeQuery(nodeQueryDefinition);
        hosts = topology.getCIsByName("Computer");
        for (TopologyCI host : hosts) {
            Object nodeRoleValue = host.getPropertyValue("node_role");
            String ciType = host.getType();
            if (isInExculdeList(ciType)){
                continue;
            }
            if (nodeRoleValue != null && nodeRoleValue instanceof List) {
                List<String> nodeRoleValueList = (List<String>) nodeRoleValue;
                List<String> tempNodeRoleValueList = new ArrayList<String>();
                for (String value : nodeRoleValueList) {
                    tempNodeRoleValueList.add(value.toLowerCase());
                }
                if (tempNodeRoleValueList.contains("desktop")) {
                    continue;
                }
            }

            vm++;
            basicServer--;

        }


        nodeQueryDefinition = queryFactory.createQueryDefinition("query tql");
        hostNode = nodeQueryDefinition.addNode("Computer").ofType("host_node").queryProperties("node_role", "lic_type_udf", "lic_type_udi");
        hypervisorNode = nodeQueryDefinition.addNode("Hypervisor").ofType("hypervisor");
        hypervisorNode.linkedTo(hostNode).withLinkOfType("execution_environment").atLeast(1);
        b = hostNode.propertiesConditionBuilder();
        hostNode.withPropertiesConditions(b.use(b.property("lic_type_udi").isEqualTo(true)).and(b.use(b.property("lic_type_udf").isEqualTo(false)))).setAsPerspectiveContact();
        topology = topologyQueryService.executeQuery(nodeQueryDefinition);
        hosts = topology.getCIsByName("Computer");
        for (TopologyCI host : hosts) {
            Object nodeRoleValue = host.getPropertyValue("node_role");
            String ciType = host.getType();
            if (isInExculdeList(ciType)){
                continue;
            }
            if (nodeRoleValue != null && nodeRoleValue instanceof List) {
                List<String> nodeRoleValueList = (List<String>) nodeRoleValue;
                List<String> tempNodeRoleValueList = new ArrayList<String>();
                for (String value : nodeRoleValueList) {
                    tempNodeRoleValueList.add(value.toLowerCase());
                }
                if (tempNodeRoleValueList.contains("desktop")) {
                    continue;
                }
            }

            vm++;
            basicServer--;

        }
        setWorkStationCi(allWorkstation);
        setServerUdfCi(fullServer);
        setServerUdiCi(basicServer);
        setVm(vm);
    }

    private boolean isInExculdeList(String ciType) {
        for (String excludeType : EXCLUDE_TYPES) {
            if (excludeType.equalsIgnoreCase(ciType)) {
                return true;
            }
        }
        return false;
    }

    public int getWorkStationCi() {
        return workStationCi;
    }

    public void setWorkStationCi(int workStationCi) {
        this.workStationCi = workStationCi;
    }

    public int getServerUdiCi() {
        return serverUdiCi;
    }

    public void setServerUdiCi(int serverUdiCi) {
        this.serverUdiCi = serverUdiCi;
    }

    public int getServerUdfCi() {
        return serverUdfCi;
    }

    public void setServerUdfCi(int serverUdfCi) {
        this.serverUdfCi = serverUdfCi;
    }


    public int getWorkstationOfOpbs() {
        return workstationOfOpbs;
    }

    public void setWorkstationOfOpbs(int workstationOfOpbs) {
        this.workstationOfOpbs = workstationOfOpbs;
    }

    public int getWorkstationFullAndBasic() {
        return workstationFullAndBasic;
    }

    public void setWorkstationFullAndBasic(int workstationFullAndBasic) {
        this.workstationFullAndBasic = workstationFullAndBasic;
    }

    public int getServerOfFull() {
        return serverOfFull;
    }

    public void setServerOfFull(int serverOfFull) {
        this.serverOfFull = serverOfFull;
    }

    public int getServerBasicAndOpbs() {
        return serverBasicAndOpbs;
    }

    public void setServerBasicAndOpbs(int serverBasicAndOpbs) {
        this.serverBasicAndOpbs = serverBasicAndOpbs;
    }
    @Override
    public void run() {
        getLessThan40();
    }
    public int getVm() {
        return vm;
    }

    public void setVm(int vm) {
        this.vm = vm;
    }

}
