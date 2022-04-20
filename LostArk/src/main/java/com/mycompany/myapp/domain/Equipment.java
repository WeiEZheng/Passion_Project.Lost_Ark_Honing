package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.EquipType;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
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
    @Column(name = "character_id", nullable = false)
    private Integer characterID;

    @NotNull
    @Column(name = "tier", nullable = false)
    private Integer tier;

    @NotNull
    @Column(name = "honing_level", nullable = false)
    private Integer honingLevel;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "equipment_type", nullable = false)
    private EquipType equipmentType;

    @ManyToOne
    @JsonIgnoreProperties(value = { "equipment" }, allowSetters = true)
    private Charac charac;

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

    public Integer getCharacterID() {
        return this.characterID;
    }

    public Equipment characterID(Integer characterID) {
        this.setCharacterID(characterID);
        return this;
    }

    public void setCharacterID(Integer characterID) {
        this.characterID = characterID;
    }

    public Integer getTier() {
        return this.tier;
    }

    public Equipment tier(Integer tier) {
        this.setTier(tier);
        return this;
    }

    public void setTier(Integer tier) {
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

    public Charac getCharac() {
        return this.charac;
    }

    public void setCharac(Charac charac) {
        this.charac = charac;
    }

    public Equipment charac(Charac charac) {
        this.setCharac(charac);
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
            ", characterID=" + getCharacterID() +
            ", tier=" + getTier() +
            ", honingLevel=" + getHoningLevel() +
            ", equipmentType='" + getEquipmentType() + "'" +
            "}";
    }
}
