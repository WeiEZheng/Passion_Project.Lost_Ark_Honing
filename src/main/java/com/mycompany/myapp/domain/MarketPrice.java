package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A MarketPrice.
 */
@Entity
@Table(name = "market_price")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MarketPrice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "item_name", nullable = false)
    private String itemName;

    @NotNull
    @Column(name = "item_price_per_stack", nullable = false)
    private Integer itemPricePerStack;

    @NotNull
    @Column(name = "number_per_stack", nullable = false)
    private Integer numberPerStack;

    @NotNull
    @Column(name = "time_updated", nullable = false)
    private ZonedDateTime timeUpdated;

    @ManyToMany(mappedBy = "marketPrices")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "marketPrices" }, allowSetters = true)
    private Set<HoningMat> honingMats = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public MarketPrice id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemName() {
        return this.itemName;
    }

    public MarketPrice itemName(String itemName) {
        this.setItemName(itemName);
        return this;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getItemPricePerStack() {
        return this.itemPricePerStack;
    }

    public MarketPrice itemPricePerStack(Integer itemPricePerStack) {
        this.setItemPricePerStack(itemPricePerStack);
        return this;
    }

    public void setItemPricePerStack(Integer itemPricePerStack) {
        this.itemPricePerStack = itemPricePerStack;
    }

    public Integer getNumberPerStack() {
        return this.numberPerStack;
    }

    public MarketPrice numberPerStack(Integer numberPerStack) {
        this.setNumberPerStack(numberPerStack);
        return this;
    }

    public void setNumberPerStack(Integer numberPerStack) {
        this.numberPerStack = numberPerStack;
    }

    public ZonedDateTime getTimeUpdated() {
        return this.timeUpdated;
    }

    public MarketPrice timeUpdated(ZonedDateTime timeUpdated) {
        this.setTimeUpdated(timeUpdated);
        return this;
    }

    public void setTimeUpdated(ZonedDateTime timeUpdated) {
        this.timeUpdated = timeUpdated;
    }

    public Set<HoningMat> getHoningMats() {
        return this.honingMats;
    }

    public void setHoningMats(Set<HoningMat> honingMats) {
        if (this.honingMats != null) {
            this.honingMats.forEach(i -> i.removeMarketPrice(this));
        }
        if (honingMats != null) {
            honingMats.forEach(i -> i.addMarketPrice(this));
        }
        this.honingMats = honingMats;
    }

    public MarketPrice honingMats(Set<HoningMat> honingMats) {
        this.setHoningMats(honingMats);
        return this;
    }

    public MarketPrice addHoningMat(HoningMat honingMat) {
        this.honingMats.add(honingMat);
        honingMat.getMarketPrices().add(this);
        return this;
    }

    public MarketPrice removeHoningMat(HoningMat honingMat) {
        this.honingMats.remove(honingMat);
        honingMat.getMarketPrices().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MarketPrice)) {
            return false;
        }
        return id != null && id.equals(((MarketPrice) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MarketPrice{" +
            "id=" + getId() +
            ", itemName='" + getItemName() + "'" +
            ", itemPricePerStack=" + getItemPricePerStack() +
            ", numberPerStack=" + getNumberPerStack() +
            ", timeUpdated='" + getTimeUpdated() + "'" +
            "}";
    }
}
