package lostark.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import lostark.domain.enumeration.AdvClasses;
import lostark.domain.enumeration.Server;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Characters.
 */
@Entity
@Table(name = "characters")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Characters implements Serializable {

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
    @Enumerated(EnumType.STRING)
    @Column(name = "adv_class", nullable = false)
    private AdvClasses advClass;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "server", nullable = false)
    private Server server;

    @ManyToOne
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Characters id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Characters name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AdvClasses getAdvClass() {
        return this.advClass;
    }

    public Characters advClass(AdvClasses advClass) {
        this.setAdvClass(advClass);
        return this;
    }

    public void setAdvClass(AdvClasses advClass) {
        this.advClass = advClass;
    }

    public Server getServer() {
        return this.server;
    }

    public Characters server(Server server) {
        this.setServer(server);
        return this;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Characters user(User user) {
        this.setUser(user);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Characters)) {
            return false;
        }
        return id != null && id.equals(((Characters) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Characters{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", advClass='" + getAdvClass() + "'" +
            ", server='" + getServer() + "'" +
            "}";
    }
}
