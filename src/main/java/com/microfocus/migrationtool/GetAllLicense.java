package com.microfocus.migrationtool;

import com.hp.ucmdb.api.*;
import com.hp.ucmdb.api.authorization.model.AuthorizationModelService;
import com.hp.ucmdb.api.authorization.types.RoleId;
import com.hp.ucmdb.api.authorization.types.UserId;
import com.hp.ucmdb.api.classmodel.ClassDefinition;
import com.hp.ucmdb.api.classmodel.ClassModelService;
import com.hp.ucmdb.api.license.LicensingService;
import com.hp.ucmdb.api.topology.QueryDefinition;
import com.hp.ucmdb.api.topology.TopologyQueryFactory;
import com.hp.ucmdb.api.topology.TopologyQueryService;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by caiy on 2017/11/2.
 */
public class GetAllLicense {
    private String ip;
    private String protocol;
    private String user;
    private String password;
    private Integer port;
    private UcmdbService ucmdbService;
    private LicensingService licensingService;
    private TopologyQueryService topologyQueryService;
    private QueryDefinition nodeQueryDefinition;
    List<String> allSupportCiTypeNames;
    private boolean version;
    private String ucmdbversion;

    public GetAllLicense(String ip, String protocol, String user, String password, Integer port) {
        this.ip = ip;
        this.protocol = protocol;
        this.user = user;
        this.password = password;
        this.port = port;
        boolean isHTTPS = "HTTPS".equalsIgnoreCase(protocol);
        if (isHTTPS) {
            System.setProperty(UcmdbServiceFactory.DefaultTruststoreManger.DISABLE_CERT_VALIDATION, "true");
        }

    }

    public void judgepermission()
    {
        AuthorizationModelService authorizationModelService = ucmdbService.getAuthorizationModelService();
        UserId userId = authorizationModelService.getAuthorizationDataFactory().createUserId(user);
        Collection<RoleId> roleIds = authorizationModelService.getRoleIdsForUser(userId);
    }
    public void init() throws MalformedURLException{
        UcmdbServiceProvider provider = UcmdbServiceFactory.getServiceProvider(protocol, ip, port);
        Credentials credentials = provider.createCredentials(user, password);
        ClientContext clientContext = provider.createClientContext("cc-test");
        ucmdbService = provider.connect(credentials, clientContext);


        licensingService = ucmdbService.getLicensingService();
        topologyQueryService = ucmdbService.getTopologyQueryService();
        TopologyQueryFactory queryFactory = topologyQueryService.getFactory();
        nodeQueryDefinition = queryFactory.createQueryDefinition("query tql");
        allSupportCiTypeNames = new ArrayList<String>();
        ClassModelService classModelService = ucmdbService.getClassModelService();

        for (ClassDefinition def : classModelService.getAllClasses()) {
            allSupportCiTypeNames.add(def.getName());
        }
        ucmdbversion = ucmdbService.getUcmdbVersion().getFullServerVersion();
        String versionCompare = "";
        if(ucmdbversion.indexOf("C")!=-1){
            versionCompare = ucmdbversion.substring(0,ucmdbversion.indexOf("C")-1);
        }else{
            versionCompare = ucmdbversion;
        }
        if (Double.valueOf(versionCompare).compareTo(Double.valueOf("10.40")) >= 0) {
            this.version = true;
        } else {
            this.version = false;
        }
    }
    public <T> T getLicense() {
        if(version){
            return (T)getMoreThan();
        }else{
            return (T)getLessThan();
        }
    }

    public ResultLess getLessThan() {
        int udf = 0;
        int udi = 0;
        int usedUdf = 0;
        int usedUdi = 0;
        int mdr = 0;
        int usedThirdPartyMDR=0;
        try {
            Method methods[] = licensingService.getClass().getMethods();
            for (Method method : methods) {
                String methodName = method.getName();
                if (methodName != null && methodName.equals("getLicenseCapacityInformation")) {
                    Object licenseCapacityInformation = method.invoke(licensingService, null);
                    Field[] fields = licenseCapacityInformation.getClass().getDeclaredFields();
                    for (Field field : fields) {
                        String fieldName = field.getName();
                        if (fieldName.equals("licensedInventoryDiscovery")) {
                            udi = field.getInt(licenseCapacityInformation);
                        } else if (fieldName.equals("licensedUniversalDiscovery")) {
                            udf = field.getInt(licenseCapacityInformation);
                        } else if (fieldName.equals("usedInventoryDiscovery")) {
                            usedUdi = field.getInt(licenseCapacityInformation);
                        } else if (fieldName.equals("usedUniversalDiscovery")) {
                            usedUdf = field.getInt(licenseCapacityInformation);
                        } else if (fieldName.equals("licensedThirdPartyMDR")) {
                            mdr = field.getInt(licenseCapacityInformation);
                        }else if(fieldName.equals("usedThirdPartyMDR")){
                            usedThirdPartyMDR = field.getInt(licenseCapacityInformation);
                        }
                    }
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        ExecutorService exec = Executors.newCachedThreadPool();
        GetStorgeLicense getStorgeLicense = new GetStorgeLicense(this.topologyQueryService, this.allSupportCiTypeNames, this.version);
        GetNetworkLicense getNetworkLicense = new GetNetworkLicense(this.topologyQueryService, this.version);
        GetDockerLicense getDockerLicense = new GetDockerLicense(this.topologyQueryService, this.allSupportCiTypeNames, this.version);
        GetSWLicense getSWLicense = new GetSWLicense(this.nodeQueryDefinition, this.topologyQueryService, this.version);
        exec.submit(getStorgeLicense);
        exec.submit(getNetworkLicense);
        exec.submit(getStorgeLicense);
        exec.submit(getSWLicense);
        exec.shutdown();
        try {
            exec.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ResultLess resultLess = new ResultLess();
        resultLess.setUdf(udf);
        resultLess.setUdi(udi);
        resultLess.setUsedUdf(usedUdf);
        resultLess.setUsedUdi(usedUdi);
        resultLess.setMdr(mdr);
        resultLess.setServerFull(getSWLicense.getServerUdfCi());
        resultLess.setServerBasic(getSWLicense.getServerUdiCi());
        resultLess.setAllWorkstation(getSWLicense.getWorkStationCi());
        resultLess.setDockerCis(getDockerLicense.getDockerCi());
        resultLess.setStorageCis(getStorgeLicense.getStorgeCi());
        resultLess.setNetworkCis(getNetworkLicense.getNetworkCi());
        resultLess.setVm(getSWLicense.getVm());
        resultLess.setVersion(this.ucmdbversion);
        resultLess.setUsedMdr(usedThirdPartyMDR);
        return  resultLess;
    }

    public ResultLarge getMoreThan() {
        ResultLarge resultLarge = new ResultLarge();

        int usedFullServerDiscovery=0;
        int usedFullWorkstationDiscovery=0;
        int usedFullNetworkDiscovery=0;
        int usedFullStorageDiscovery=0;
        int usedFullDockerDiscovery=0;

        int usedBasicServerDiscovery=0;
        int usedBasicWorkstationDiscovery=0;
        int usedBasicNetworkDiscovery=0;
        int usedBasicStorageDiscovery=0;
        int usedBasicDockerDiscovery=0;

        int usedOperationalServerDiscovery=0;
        int usedOperationalWorkstationDiscovery=0;
        int licensedThirdPartyMDR=0;
        int totalLicenseUnit=0;

        try {
            Method methods[] = licensingService.getClass().getMethods();
            for (Method method : methods) {
                String methodName = method.getName();
                if (methodName != null && methodName.equals("getLicenseCapacityInformation")) {
                    Object licenseCapacityInformation = method.invoke(licensingService, null);
                    Field[] fields = licenseCapacityInformation.getClass().getDeclaredFields();
                    for (Field field : fields) {
                        String fieldName = field.getName();
                        if (fieldName.equals("usedFullServerDiscovery")) {
                            usedFullServerDiscovery = field.getInt(licenseCapacityInformation);
                        } else if (fieldName.equals("usedFullWorkstationDiscovery")) {
                            usedFullWorkstationDiscovery = field.getInt(licenseCapacityInformation);
                        } else if (fieldName.equals("usedFullNetworkDiscovery")) {
                            usedFullNetworkDiscovery = field.getInt(licenseCapacityInformation);
                        } else if (fieldName.equals("usedFullStorageDiscovery")) {
                            usedFullStorageDiscovery = field.getInt(licenseCapacityInformation);
                        } else if (fieldName.equals("usedFullDockerDiscovery")) {
                            usedFullDockerDiscovery = field.getInt(licenseCapacityInformation);
                        } else if (fieldName.equals("usedBasicServerDiscovery")) {
                            usedBasicServerDiscovery = field.getInt(licenseCapacityInformation);
                        } else if (fieldName.equals("usedBasicWorkstationDiscovery")) {
                            usedBasicWorkstationDiscovery = field.getInt(licenseCapacityInformation);
                        } else if (fieldName.equals("usedBasicNetworkDiscovery")) {
                            usedBasicNetworkDiscovery = field.getInt(licenseCapacityInformation);
                        } else if (fieldName.equals("usedBasicStorageDiscovery")) {
                            usedBasicStorageDiscovery = field.getInt(licenseCapacityInformation);
                        } else if (fieldName.equals("usedBasicDockerDiscovery")) {
                            usedBasicDockerDiscovery = field.getInt(licenseCapacityInformation);
                        } else if (fieldName.equals("usedOperationalServerDiscovery")) {
                            usedOperationalServerDiscovery = field.getInt(licenseCapacityInformation);
                        } else if (fieldName.equals("usedOperationalWorkstationDiscovery")) {
                            usedOperationalWorkstationDiscovery = field.getInt(licenseCapacityInformation);
                        }else if (fieldName.equals("licensedThirdPartyMDR")) {
                            licensedThirdPartyMDR = field.getInt(licenseCapacityInformation);
                        }else if (fieldName.equals("totalLicenseUnit")) {
                            totalLicenseUnit = field.getInt(licenseCapacityInformation);
                        }
                    }
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        int fullServer =usedFullServerDiscovery;
        int BasicServer = usedBasicServerDiscovery;
        int opsbServer = usedOperationalServerDiscovery;
        int opsbWorkstation = usedOperationalWorkstationDiscovery;
        int fullcWorkstation = usedFullWorkstationDiscovery;
        int basicWorkstation = usedBasicWorkstationDiscovery;;
        int network = usedFullNetworkDiscovery+usedBasicNetworkDiscovery;
        int storage = usedFullStorageDiscovery+usedBasicStorageDiscovery;
        int docker = usedFullDockerDiscovery+usedBasicDockerDiscovery;
        resultLarge.setServerOfFull(fullServer);
        resultLarge.setServerBasic(BasicServer);
        resultLarge.setServerOpsb(opsbServer);
        resultLarge.setWorkstationOfOpbs(opsbWorkstation);
        resultLarge.setWorkstationFull(fullcWorkstation);
        resultLarge.setWorkstationBasic(basicWorkstation);
        resultLarge.setNetworkCis(network);
        resultLarge.setStorageCis(storage);
        resultLarge.setDockerCis(docker);
        resultLarge.setVersion(this.ucmdbversion);
        resultLarge.setMdr(licensedThirdPartyMDR);
        resultLarge.setUnitCapacity(totalLicenseUnit);
        return resultLarge;
    }

    public boolean getVersion() {
        return version;
    }

    public void setVersion(boolean version) {
        this.version = version;
    }

}
