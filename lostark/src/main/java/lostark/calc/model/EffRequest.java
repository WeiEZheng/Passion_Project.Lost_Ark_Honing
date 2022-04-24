package lostark.calc.model;

public class EffRequest {
    Double basePercent, additionPercentPerFail, maxPercentAfterMats;
    Integer fusionMat1Amount, fusionMat2Amount,fusionMat3Amount, failLimit;

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
