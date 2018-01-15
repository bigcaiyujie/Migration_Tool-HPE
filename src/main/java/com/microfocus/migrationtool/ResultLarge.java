package com.microfocus.migrationtool;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Created by caiy on 2017/11/2.
 */
public class ResultLarge {
    protected int migrationUnits;
    protected int usdUnits;
    protected int totalUnits;
    protected int workstationOfOpbs;
    protected int workstationFull;
    protected int workstationBasic;
    protected int serverOfFull;
    protected int serverBasic;
    protected int serverOpsb;
    protected int networkCis;
    protected int storageCis;
    protected int dockerCis;
    protected int mdr;
    protected int unitCapacity;
    protected String version;

    public void setWorkstationBasic(int workstationBasic) {
        this.workstationBasic = workstationBasic;
    }

    public int getWorkstationBasic() {
        return workstationBasic;
    }

    public int getServerOpsb() {
        return serverOpsb;
    }

    public void setServerOpsb(int serverOpsb) {
        this.serverOpsb = serverOpsb;
    }

    public int getMdr() {
        return mdr;
    }

    public void setMdr(int mdr) {
        this.mdr = mdr;
    }

    public int getUnitCapacity() {
        return unitCapacity;
    }

    public void setUnitCapacity(int unitCapacity) {
        this.unitCapacity = unitCapacity;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
    public int getMigrationUnits() {
        return migrationUnits;
    }

    public void setMigrationUnits(int migrationUnits) {
        this.migrationUnits = migrationUnits;
    }

    public double getUsdUnits() {
        DecimalFormat df = new DecimalFormat( "0.0");
        return  Double.valueOf(df.format(getPoint(getWorkstationFull(),0.1)+getPoint(getWorkstationBasic(),0.1)+getPoint(getNetworkCis(),0.1)+getPoint(getStorageCis(),0.1)+getPoint(getDockerCis(),0.1)+getPoint(getWorkstationOfOpbs(),0.2)+getPoint(getServerBasic(),0.2)+getPoint(getServerOpsb(),0.2)+getPoint(getServerOfFull(),1)));
    }

    public void setUsdUnits(int usdUnits) {
        this.usdUnits = usdUnits;
    }

    public int getTotalUnits() {
        return  totalUnits;
    }

    public void setTotalUnits(int totalUnits) {
        this.totalUnits = totalUnits;
    }

    public int getNetworkCis() {
        return networkCis;
    }

    public void setNetworkCis(int networkCis) {
        this.networkCis = networkCis;
    }

    public int getStorageCis() {
        return storageCis;
    }

    public void setStorageCis(int storageCis) {
        this.storageCis = storageCis;
    }

    public int getDockerCis() {
        return dockerCis;
    }

    public void setDockerCis(int dockerCis) {
        this.dockerCis = dockerCis;
    }


    public int getWorkstationOfOpbs() {
        return workstationOfOpbs;
    }

    public void setWorkstationOfOpbs(int workstationOfOpbs) {
        this.workstationOfOpbs = workstationOfOpbs;
    }

    public int getWorkstationFull() {
        return workstationFull;
    }

    public void setWorkstationFull(int workstationFull) {
        this.workstationFull = workstationFull;
    }

    public int getServerOfFull() {
        return serverOfFull;
    }

    public void setServerOfFull(int serverOfFull) {
        this.serverOfFull = serverOfFull;
    }

    public int getServerBasic() {
        return serverBasic;
    }

    public void setServerBasic(int serverBasic) {
        this.serverBasic = serverBasic;
    }

    public double getPoint(int number, double rate) {
        BigDecimal tempDecimal = new BigDecimal(number);
        double basicServerPoint = tempDecimal.multiply(new BigDecimal(rate)).setScale(1, RoundingMode.HALF_UP).doubleValue();
        return basicServerPoint;
    }
}
