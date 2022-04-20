package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.Server;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Charac.
 */
@Entity
@Table(name = "charac")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Charac implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "adv_class", nullable = false)
    private String advClass;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "server", nullable = false)
    private Server server;

    @OneToMany(mappedBy = "charac")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "charac" }, allowSetters = true)
    private Set<Equipment> equipment = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Charac id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Charac name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdvClass() {
        return this.advClass;
    }

    public Charac advClass(String advClass) {
        this.setAdvClass(advClass);
        return this;
    }

    public void setAdvClass(String advClass) {
        this.advClass = advClass;
    }

    public Server getServer() {
        return this.server;
    }

    public Charac server(Server server) {
        this.setServer(server);
        return this;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public Set<Equipment> getEquipment() {
        return this.equipment;
    }

    public void setEquipment(Set<Equipment> equipment) {
        if (this.equipment != null) {
            this.equipment.forEach(i -> i.setCharac(null));
        }
        if (equipment != null) {
            equipment.forEach(i -> i.setCharac(this));
        }
        this.equipment = equipment;
    }

    public Charac equipment(Set<Equipment> equipment) {
        this.setEquipment(equipment);
        return this;
    }

    public Charac addEquipment(Equipment equipment) {
        this.equipment.add(equipment);
        equipment.setCharac(this);
        return this;
    }

    public Charac removeEquipment(Equipment equipment) {
        this.equipment.remove(equipment);
        equipment.setCharac(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Charac)) {
            return false;
        }
        return id != null && id.equals(((Charac) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Charac{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", advClass='" + getAdvClass() + "'" +
            ", server='" + getServer() + "'" +
            "}";
    }
}
