package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.EquipType;
import com.mycompany.myapp.domain.enumeration.ShardType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A HoningMat.
 */
@Entity
@Table(name = "honing_mat")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class HoningMat implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "tier", nullable = false)
    private Integer tier;

    @NotNull
    @Column(name = "level", nullable = false)
    private Integer level;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "shard_type", nullable = false)
    private ShardType shardType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "equip_type", nullable = false)
    private EquipType equipType;

    @NotNull
    @Column(name = "fusion_material_name_1", nullable = false)
    private String fusionMaterialName1;

    @NotNull
    @Column(name = "fusion_material_num_1", nullable = false)
    private Integer fusionMaterialNum1;

    @Column(name = "fusion_material_name_2")
    private String fusionMaterialName2;

    @Column(name = "fusion_material_num_2")
    private Integer fusionMaterialNum2;

    @Column(name = "fusion_material_name_3")
    private String fusionMaterialName3;

    @Column(name = "fusion_material_num_3")
    private Integer fusionMaterialNum3;

    @ManyToMany
    @JoinTable(
        name = "rel_honing_mat__market_price",
        joinColumns = @JoinColumn(name = "honing_mat_id"),
        inverseJoinColumns = @JoinColumn(name = "market_price_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "honingMats" }, allowSetters = true)
    private Set<MarketPrice> marketPrices = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public HoningMat id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTier() {
        return this.tier;
    }

    public HoningMat tier(Integer tier) {
        this.setTier(tier);
        return this;
    }

    public void setTier(Integer tier) {
        this.tier = tier;
    }

    public Integer getLevel() {
        return this.level;
    }

    public HoningMat level(Integer level) {
        this.setLevel(level);
        return this;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public ShardType getShardType() {
        return this.shardType;
    }

    public HoningMat shardType(ShardType shardType) {
        this.setShardType(shardType);
        return this;
    }

    public void setShardType(ShardType shardType) {
        this.shardType = shardType;
    }

    public EquipType getEquipType() {
        return this.equipType;
    }

    public HoningMat equipType(EquipType equipType) {
        this.setEquipType(equipType);
        return this;
    }

    public void setEquipType(EquipType equipType) {
        this.equipType = equipType;
    }

    public String getFusionMaterialName1() {
        return this.fusionMaterialName1;
    }

    public HoningMat fusionMaterialName1(String fusionMaterialName1) {
        this.setFusionMaterialName1(fusionMaterialName1);
        return this;
    }

    public void setFusionMaterialName1(String fusionMaterialName1) {
        this.fusionMaterialName1 = fusionMaterialName1;
    }

    public Integer getFusionMaterialNum1() {
        return this.fusionMaterialNum1;
    }

    public HoningMat fusionMaterialNum1(Integer fusionMaterialNum1) {
        this.setFusionMaterialNum1(fusionMaterialNum1);
        return this;
    }

    public void setFusionMaterialNum1(Integer fusionMaterialNum1) {
        this.fusionMaterialNum1 = fusionMaterialNum1;
    }

    public String getFusionMaterialName2() {
        return this.fusionMaterialName2;
    }

    public HoningMat fusionMaterialName2(String fusionMaterialName2) {
        this.setFusionMaterialName2(fusionMaterialName2);
        return this;
    }

    public void setFusionMaterialName2(String fusionMaterialName2) {
        this.fusionMaterialName2 = fusionMaterialName2;
    }

    public Integer getFusionMaterialNum2() {
        return this.fusionMaterialNum2;
    }

    public HoningMat fusionMaterialNum2(Integer fusionMaterialNum2) {
        this.setFusionMaterialNum2(fusionMaterialNum2);
        return this;
    }

    public void setFusionMaterialNum2(Integer fusionMaterialNum2) {
        this.fusionMaterialNum2 = fusionMaterialNum2;
    }

    public String getFusionMaterialName3() {
        return this.fusionMaterialName3;
    }

    public HoningMat fusionMaterialName3(String fusionMaterialName3) {
        this.setFusionMaterialName3(fusionMaterialName3);
        return this;
    }

    public void setFusionMaterialName3(String fusionMaterialName3) {
        this.fusionMaterialName3 = fusionMaterialName3;
    }

    public Integer getFusionMaterialNum3() {
        return this.fusionMaterialNum3;
    }

    public HoningMat fusionMaterialNum3(Integer fusionMaterialNum3) {
        this.setFusionMaterialNum3(fusionMaterialNum3);
        return this;
    }

    public void setFusionMaterialNum3(Integer fusionMaterialNum3) {
        this.fusionMaterialNum3 = fusionMaterialNum3;
    }

    public Set<MarketPrice> getMarketPrices() {
        return this.marketPrices;
    }

    public void setMarketPrices(Set<MarketPrice> marketPrices) {
        this.marketPrices = marketPrices;
    }

    public HoningMat marketPrices(Set<MarketPrice> marketPrices) {
        this.setMarketPrices(marketPrices);
        return this;
    }

    public HoningMat addMarketPrice(MarketPrice marketPrice) {
        this.marketPrices.add(marketPrice);
        marketPrice.getHoningMats().add(this);
        return this;
    }

    public HoningMat removeMarketPrice(MarketPrice marketPrice) {
        this.marketPrices.remove(marketPrice);
        marketPrice.getHoningMats().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HoningMat)) {
            return false;
        }
        return id != null && id.equals(((HoningMat) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HoningMat{" +
            "id=" + getId() +
            ", tier=" + getTier() +
            ", level=" + getLevel() +
            ", shardType='" + getShardType() + "'" +
            ", equipType='" + getEquipType() + "'" +
            ", fusionMaterialName1='" + getFusionMaterialName1() + "'" +
            ", fusionMaterialNum1=" + getFusionMaterialNum1() +
            ", fusionMaterialName2='" + getFusionMaterialName2() + "'" +
            ", fusionMaterialNum2=" + getFusionMaterialNum2() +
            ", fusionMaterialName3='" + getFusionMaterialName3() + "'" +
            ", fusionMaterialNum3=" + getFusionMaterialNum3() +
            "}";
    }
}
