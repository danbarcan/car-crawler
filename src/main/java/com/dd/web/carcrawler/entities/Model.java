package com.dd.web.carcrawler.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Car_models")
public class Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @OneToMany(
            mappedBy = "model",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<EngineSize> engineSizes;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "manufacturer_id")
    private Manufacturer manufacturer;

    public Model() {
    }

    public Model(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<EngineSize> getEngineSizes() {
        return engineSizes;
    }

    public void setEngineSizes(List<EngineSize> engineSizes) {
        this.engineSizes = engineSizes;
    }

    public void addEngineSize(EngineSize engineSize) {
        if (this.engineSizes == null) {
            this.engineSizes = new ArrayList<>();
        }
        this.engineSizes.add(engineSize);
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    @Override
    public String toString() {
        return "\n\tModel{" +
                "name='" + name + '\'' +
                ", engineSizes=" + engineSizes +
                '}';
    }
}
