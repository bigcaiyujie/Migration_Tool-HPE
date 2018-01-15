package com.microfocus.migrationtool;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by caiy on 2017/11/2.
 */
public class ResultLess {
    protected int udf;
    protected int udi;
    protected int usedUdf;
    protected int usedUdi;
    protected int migrationUnits;
    protected int compliancyUnits;
    protected int totalUntis;


    protected int serverFull;
    protected int serverBasic;
    protected int allWorkstation;
    protected int networkCis;
    protected int storageCis;
    protected int dockerCis;
    protected int mdr;
    protected int usedMdr;
    protected int vm;
    protected String version;


    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getCompliancyUnits() {
        if(this.udf==0&this.udi==0){
            this.compliancyUnits = 0;
        }else{
            this.compliancyUnits = getPoint(getServerBasic(), 0.1) + getPoint(getNetworkCis(), 0.1) + getPoint(getStorageCis(), 0.1) + getPoint(getDockerCis(), 0.1) + getPoint(getVm(), 0.9);
        }
        return this.compliancyUnits;
    }

    public void setCompliancyUnits(int compliancyUnits) {
        this.compliancyUnits = compliancyUnits;
    }

    public int getTotalUntis() {
        return getMigrationUnits() + getCompliancyUnits();
    }

    public void setTotalUntis(int totalUntis) {
        this.totalUntis = totalUntis;
    }

    public int getUdf() {
        return udf;
    }

    public void setUdf(int udf) {
        this.udf = udf;
    }

    public int getUdi() {
        return udi;
    }

    public void setUdi(int udi) {
        this.udi = udi;
    }


    public int getMigrationUnits() {
        return getUdf() + (int) Math.ceil(0.1 * getUdi());
    }

    public void setMigrationUnits(int migrationUnits) {
        this.migrationUnits = migrationUnits;
    }

    public int getServerBasic() {
        return this.serverBasic;
    }

    public void setServerBasic(int serverBasic) {
        if(serverBasic>this.udi){
            this.serverFull +=(serverBasic-this.udi);
            this.serverBasic = this.udi;
        }else{
            this.serverBasic = serverBasic;
        }
    }

    public int getServerFull() {
        return serverFull;
    }

    public void setServerFull(int serverFull) {
        this.serverFull = serverFull;
    }

    public int getAllWorkstation() {
        return allWorkstation;
    }

    public void setAllWorkstation(int allWorkstation) {
        this.allWorkstation = allWorkstation;
    }

    public int getNetworkCis() {
        return this.networkCis;
    }

    public void setNetworkCis(int networkCis) {
        this.networkCis = networkCis;
    }

    public int getStorageCis() {
        return this.storageCis;
    }

    public void setStorageCis(int storageCis) {
        this.storageCis = storageCis;
    }

    public int getDockerCis() {
        return this.dockerCis;
    }

    public void setDockerCis(int dockerCis) {
        this.dockerCis = dockerCis;
    }


    public int getMdr() {
        return mdr;
    }

    public void setMdr(int mdr) {
        this.mdr = mdr;
    }

    public int getPoint(int number, double rate) {
        BigDecimal tempDecimal = new BigDecimal(number);
        double basicServerPoint = tempDecimal.multiply(new BigDecimal(rate)).setScale(1, RoundingMode.HALF_UP).doubleValue();
        return (int) Math.ceil(basicServerPoint);
    }

    public int getVm() {
        return vm;
    }

    public void setVm(int vm) {
        this.vm = vm;
    }

    public int getUsedUdf() {
        return usedUdf;
    }

    public void setUsedUdf(int usedUdf) {
        this.usedUdf = usedUdf;
    }

    public int getUsedUdi() {
        return usedUdi;
    }

    public void setUsedUdi(int usedUdi) {
        this.usedUdi = usedUdi;
    }

    public int getUsedMdr() {
        return usedMdr;
    }

    public void setUsedMdr(int usedMdr) {
        this.usedMdr = usedMdr;
    }
}
