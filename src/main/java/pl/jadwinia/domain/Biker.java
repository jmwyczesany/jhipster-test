package pl.jadwinia.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A Biker.
 */
@Entity
@Table(name = "biker")
public class Biker implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "mileage")
    private Integer mileage;

    @OneToMany(mappedBy = "biker")
    private Set<Bike> bikes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Biker name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public Biker lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getMileage() {
        return mileage;
    }

    public Biker mileage(Integer mileage) {
        this.mileage = mileage;
        return this;
    }

    public void setMileage(Integer mileage) {
        this.mileage = mileage;
    }

    public Set<Bike> getBikes() {
        return bikes;
    }

    public Biker bikes(Set<Bike> bikes) {
        this.bikes = bikes;
        return this;
    }

    public Biker addBike(Bike bike) {
        this.bikes.add(bike);
        bike.setBiker(this);
        return this;
    }

    public Biker removeBike(Bike bike) {
        this.bikes.remove(bike);
        bike.setBiker(null);
        return this;
    }

    public void setBikes(Set<Bike> bikes) {
        this.bikes = bikes;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Biker)) {
            return false;
        }
        return id != null && id.equals(((Biker) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Biker{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", mileage=" + getMileage() +
            "}";
    }
}
