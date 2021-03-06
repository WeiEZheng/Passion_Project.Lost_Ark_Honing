package lostark.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import lostark.domain.enumeration.MaterialName;
import org.checkerframework.common.aliasing.qual.Unique;
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

    @Unique
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "item_name", nullable = false, unique = true)
    private MaterialName itemName;

    @NotNull
    @Column(name = "item_price_per_stack", nullable = false)
    private Integer itemPricePerStack;

    @NotNull
    @Column(name = "number_per_stack", nullable = false)
    private Integer numberPerStack;

    //    @NotNull auto-filled
    @Column(name = "time_updated", nullable = false)
    private Instant timeUpdated;

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

    public MaterialName getItemName() {
        return this.itemName;
    }

    public MarketPrice itemName(MaterialName itemName) {
        this.setItemName(itemName);
        return this;
    }

    public void setItemName(MaterialName itemName) {
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

    public Instant getTimeUpdated() {
        return this.timeUpdated;
    }

    public MarketPrice timeUpdated(Instant timeUpdated) {
        this.setTimeUpdated(timeUpdated);
        return this;
    }

    public void setTimeUpdated(Instant timeUpdated) {
        this.timeUpdated = timeUpdated;
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
