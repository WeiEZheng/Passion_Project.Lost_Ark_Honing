package lostark.calc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class EffRequest {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "basePercent")
    Double basePercent;

    @Column(name = "additionPercentPerFail")
    Double additionPercentPerFail;

    @Column(name = "maxPercentAfterMats")
    Double maxPercentAfterMats;

    @Column(name = "fusionMat1Amount")
    Integer fusionMat1Amount;

    @Column(name = "fusionMat2Amount")
    Integer fusionMat2Amount;

    @Column(name = "fusionMat3Amount")
    Integer fusionMat3Amount;

    @Column(name = "failLimit")
    Integer failLimit;

    @Column(name = "eqid")
    Long eqid;

    public Long getEqid() {
        return eqid;
    }

    public void setEqid(Long eqid) {
        this.eqid = eqid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getBasePercent() {
        return basePercent;
    }

    public void setBasePercent(Double basePercent) {
        this.basePercent = basePercent;
    }

    public Double getAdditionPercentPerFail() {
        return additionPercentPerFail;
    }

    public void setAdditionPercentPerFail(Double additionPercentPerFail) {
        this.additionPercentPerFail = additionPercentPerFail;
    }

    public Double getMaxPercentAfterMats() {
        return maxPercentAfterMats;
    }

    public void setMaxPercentAfterMats(Double maxPercentAfterMats) {
        this.maxPercentAfterMats = maxPercentAfterMats;
    }

    public Integer getFusionMat1Amount() {
        return fusionMat1Amount;
    }

    public void setFusionMat1Amount(Integer fusionMat1Amount) {
        this.fusionMat1Amount = fusionMat1Amount;
    }

    public Integer getFusionMat2Amount() {
        return fusionMat2Amount;
    }

    public void setFusionMat2Amount(Integer fusionMat2Amount) {
        this.fusionMat2Amount = fusionMat2Amount;
    }

    public Integer getFusionMat3Amount() {
        return fusionMat3Amount;
    }

    public void setFusionMat3Amount(Integer fusionMat3Amount) {
        this.fusionMat3Amount = fusionMat3Amount;
    }

    public Integer getFailLimit() {
        return failLimit;
    }

    public void setFailLimit(Integer failLimit) {
        this.failLimit = failLimit;
    }
}
