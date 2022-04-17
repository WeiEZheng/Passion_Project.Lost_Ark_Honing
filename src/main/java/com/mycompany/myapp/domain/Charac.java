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
    @Column(name = "job", nullable = false)
    private String job;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "server", nullable = false)
    private Server server;

    @OneToMany(mappedBy = "charac")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "charac" }, allowSetters = true)
    private Set<Equipment> characterEqs = new HashSet<>();

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

    public String getJob() {
        return this.job;
    }

    public Charac job(String job) {
        this.setJob(job);
        return this;
    }

    public void setJob(String job) {
        this.job = job;
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

    public Set<Equipment> getCharacterEqs() {
        return this.characterEqs;
    }

    public void setCharacterEqs(Set<Equipment> equipment) {
        if (this.characterEqs != null) {
            this.characterEqs.forEach(i -> i.setCharac(null));
        }
        if (equipment != null) {
            equipment.forEach(i -> i.setCharac(this));
        }
        this.characterEqs = equipment;
    }

    public Charac characterEqs(Set<Equipment> equipment) {
        this.setCharacterEqs(equipment);
        return this;
    }

    public Charac addCharacterEq(Equipment equipment) {
        this.characterEqs.add(equipment);
        equipment.setCharac(this);
        return this;
    }

    public Charac removeCharacterEq(Equipment equipment) {
        this.characterEqs.remove(equipment);
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
            ", job='" + getJob() + "'" +
            ", server='" + getServer() + "'" +
            "}";
    }
}
