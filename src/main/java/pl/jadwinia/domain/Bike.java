package pl.jadwinia.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Bike.
 */
@Entity
@Table(name = "bike")
public class Bike implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "model")
    private String model;

    @Column(name = "serial_no")
    private String serialNo;

    @ManyToOne
    @JsonIgnoreProperties("bikes")
    private Biker biker;

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

    public Bike model(String model) {
        this.model = model;
        return this;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public Bike serialNo(String serialNo) {
        this.serialNo = serialNo;
        return this;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public Biker getBiker() {
        return biker;
    }

    public Bike biker(Biker biker) {
        this.biker = biker;
        return this;
    }

    public void setBiker(Biker biker) {
        this.biker = biker;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Bike)) {
            return false;
        }
        return id != null && id.equals(((Bike) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Bike{" +
            "id=" + getId() +
            ", model='" + getModel() + "'" +
            ", serialNo='" + getSerialNo() + "'" +
            "}";
    }
}
