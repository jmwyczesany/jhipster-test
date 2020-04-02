package pl.jadwinia.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Glasses.
 */
@Entity
@Table(name = "glasses")
public class Glasses implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "model")
    private String model;

    @Column(name = "front")
    private String front;

    @Column(name = "temples")
    private String temples;

    @Column(name = "lenses")
    private String lenses;

    @Column(name = "size")
    private String size;

    @OneToOne(mappedBy = "glasses")
    @JsonIgnore
    private Person person;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public Glasses model(String model) {
        this.model = model;
        return this;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getFront() {
        return front;
    }

    public Glasses front(String front) {
        this.front = front;
        return this;
    }

    public void setFront(String front) {
        this.front = front;
    }

    public String getTemples() {
        return temples;
    }

    public Glasses temples(String temples) {
        this.temples = temples;
        return this;
    }

    public void setTemples(String temples) {
        this.temples = temples;
    }

    public String getLenses() {
        return lenses;
    }

    public Glasses lenses(String lenses) {
        this.lenses = lenses;
        return this;
    }

    public void setLenses(String lenses) {
        this.lenses = lenses;
    }

    public String getSize() {
        return size;
    }

    public Glasses size(String size) {
        this.size = size;
        return this;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Person getPerson() {
        return person;
    }

    public Glasses person(Person person) {
        this.person = person;
        return this;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Glasses)) {
            return false;
        }
        return id != null && id.equals(((Glasses) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Glasses{" +
            "id=" + getId() +
            ", model='" + getModel() + "'" +
            ", front='" + getFront() + "'" +
            ", temples='" + getTemples() + "'" +
            ", lenses='" + getLenses() + "'" +
            ", size='" + getSize() + "'" +
            "}";
    }
}
