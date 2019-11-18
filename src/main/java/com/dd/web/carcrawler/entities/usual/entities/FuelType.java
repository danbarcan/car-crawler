package com.dd.web.carcrawler.entities.usual.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Car_usual_fuel_types")
public class FuelType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @OneToMany(
            mappedBy = "engineSize",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<EngineSize> engineSizes;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "model_id")
    private Model model;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public FuelType(String name) {
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

    public void addYear(String year) {
        if (this.engineSizes == null) {
            this.engineSizes = new ArrayList<>();
        }
        this.engineSizes.add(new EngineSize(year));
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    @Override
    public String toString() {
        return "\n\t\tFuelType{" +
                "name='" + name + '\'' +
                ", engineSizes=" + engineSizes +
                '}';
    }
}
