package lostark.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A EffRequest.
 */
@Entity
@Table(name = "eff_request")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EffRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "base_percent")
    private Double basePercent;

    @Column(name = "addition_percent_per_fail")
    private Double additionPercentPerFail;

    @Column(name = "max_percent_after_mats")
    private Double maxPercentAfterMats;

    @Column(name = "fusion_mat_1_amount")
    private Integer fusionMat1Amount;

    @Column(name = "fusion_mat_2_amount")
    private Integer fusionMat2Amount;

    @Column(name = "fusion_mat_3_amount")
    private Integer fusionMat3Amount;

    @Column(name = "fail_limit")
    private Integer failLimit;

    @Column(name = "eqid")
    private Long eqid;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getEqid() {
        return eqid;
    }

    public void setEqid(Long eqid) {
        this.eqid = eqid;
    }

    public Long getId() {
        return this.id;
    }

    public EffRequest id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getBasePercent() {
        return this.basePercent;
    }

    public EffRequest basePercent(Double basePercent) {
        this.setBasePercent(basePercent);
        return this;
    }

    public void setBasePercent(Double basePercent) {
        this.basePercent = basePercent;
    }

    public Double getAdditionPercentPerFail() {
        return this.additionPercentPerFail;
    }

    public EffRequest additionPercentPerFail(Double additionPercentPerFail) {
        this.setAdditionPercentPerFail(additionPercentPerFail);
        return this;
    }

    public void setAdditionPercentPerFail(Double additionPercentPerFail) {
        this.additionPercentPerFail = additionPercentPerFail;
    }

    public Double getMaxPercentAfterMats() {
        return this.maxPercentAfterMats;
    }

    public EffRequest maxPercentAfterMats(Double maxPercentAfterMats) {
        this.setMaxPercentAfterMats(maxPercentAfterMats);
        return this;
    }

    public void setMaxPercentAfterMats(Double maxPercentAfterMats) {
        this.maxPercentAfterMats = maxPercentAfterMats;
    }

    public Integer getFusionMat1Amount() {
        return this.fusionMat1Amount;
    }

    public EffRequest fusionMat1Amount(Integer fusionMat1Amount) {
        this.setFusionMat1Amount(fusionMat1Amount);
        return this;
    }

    public void setFusionMat1Amount(Integer fusionMat1Amount) {
        this.fusionMat1Amount = fusionMat1Amount;
    }

    public Integer getFusionMat2Amount() {
        return this.fusionMat2Amount;
    }

    public EffRequest fusionMat2Amount(Integer fusionMat2Amount) {
        this.setFusionMat2Amount(fusionMat2Amount);
        return this;
    }

    public void setFusionMat2Amount(Integer fusionMat2Amount) {
        this.fusionMat2Amount = fusionMat2Amount;
    }

    public Integer getFusionMat3Amount() {
        return this.fusionMat3Amount;
    }

    public EffRequest fusionMat3Amount(Integer fusionMat3Amount) {
        this.setFusionMat3Amount(fusionMat3Amount);
        return this;
    }

    public void setFusionMat3Amount(Integer fusionMat3Amount) {
        this.fusionMat3Amount = fusionMat3Amount;
    }

    public Integer getFailLimit() {
        return this.failLimit;
    }

    public EffRequest failLimit(Integer failLimit) {
        this.setFailLimit(failLimit);
        return this;
    }

    public void setFailLimit(Integer failLimit) {
        this.failLimit = failLimit;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EffRequest)) {
            return false;
        }
        return id != null && id.equals(((EffRequest) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EffRequest{" +
            "id=" + getId() +
            ", basePercent=" + getBasePercent() +
            ", additionPercentPerFail=" + getAdditionPercentPerFail() +
            ", maxPercentAfterMats=" + getMaxPercentAfterMats() +
            ", fusionMat1Amount=" + getFusionMat1Amount() +
            ", fusionMat2Amount=" + getFusionMat2Amount() +
            ", fusionMat3Amount=" + getFusionMat3Amount() +
            ", failLimit=" + getFailLimit() +
            ", eqid=" + getEqid() +
            "}";
    }
}
