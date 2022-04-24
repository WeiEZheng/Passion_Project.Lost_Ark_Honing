package lostark.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import lostark.domain.enumeration.EquipType;
import lostark.domain.enumeration.TierEnum;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Equipment.
 */
@Entity
@Table(name = "equipment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Equipment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tier", nullable = false)
    private TierEnum tier;

    @NotNull
    @Min(value = 0)
    @Max(value = 20)
    @Column(name = "honing_level", nullable = false)
    private Integer honingLevel;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "equipment_type", nullable = false)
    private EquipType equipmentType;

    @ManyToOne
    private User user;

    @NotNull
    @ManyToOne
    @JsonIgnoreProperties(value = { "user", "equipment" }, allowSetters = true)
    private Characters characters;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Equipment id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TierEnum getTier() {
        return this.tier;
    }

    public Equipment tier(TierEnum tier) {
        this.setTier(tier);
        return this;
    }

    public void setTier(TierEnum tier) {
        this.tier = tier;
    }

    public Integer getHoningLevel() {
        return this.honingLevel;
    }

    public Equipment honingLevel(Integer honingLevel) {
        this.setHoningLevel(honingLevel);
        return this;
    }

    public void setHoningLevel(Integer honingLevel) {
        this.honingLevel = honingLevel;
    }

    public EquipType getEquipmentType() {
        return this.equipmentType;
    }

    public Equipment equipmentType(EquipType equipmentType) {
        this.setEquipmentType(equipmentType);
        return this;
    }

    public void setEquipmentType(EquipType equipmentType) {
        this.equipmentType = equipmentType;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Equipment user(User user) {
        this.setUser(user);
        return this;
    }

    public Characters getCharacters() {
        return this.characters;
    }

    public void setCharacters(Characters characters) {
        this.characters = characters;
    }

    public Equipment characters(Characters characters) {
        this.setCharacters(characters);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Equipment)) {
            return false;
        }
        return id != null && id.equals(((Equipment) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Equipment{" +
            "id=" + getId() +
            ", tier='" + getTier() + "'" +
            ", honingLevel=" + getHoningLevel() +
            ", equipmentType='" + getEquipmentType() + "'" +
            "}";
    }
}
