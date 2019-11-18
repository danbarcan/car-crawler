package com.dd.web.carcrawler.entities.usual.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Car_usual_models")
public class UsualModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @OneToMany(
            mappedBy = "usualModel",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<UsualFuelType> usualFuelTypes;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "manufacturer_id")
    private UsualManufacturer usualManufacturer;

    public UsualModel() {
    }

    public UsualModel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UsualFuelType> getUsualFuelTypes() {
        return usualFuelTypes;
    }

    public void setUsualFuelTypes(List<UsualFuelType> usualFuelTypes) {
        this.usualFuelTypes = usualFuelTypes;
    }

    public void addEngineSize(UsualFuelType usualFuelType) {
        if (this.usualFuelTypes == null) {
            this.usualFuelTypes = new ArrayList<>();
        }
        this.usualFuelTypes.add(usualFuelType);
    }

    public UsualManufacturer getUsualManufacturer() {
        return usualManufacturer;
    }

    public void setUsualManufacturer(UsualManufacturer usualManufacturer) {
        this.usualManufacturer = usualManufacturer;
    }

    @Override
    public String toString() {
        return "\n\tUsualModel{" +
                "name='" + name + '\'' +
                ", usualFuelTypes=" + usualFuelTypes +
                '}';
    }
}
