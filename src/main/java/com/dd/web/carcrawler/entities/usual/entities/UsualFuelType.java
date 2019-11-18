package com.dd.web.carcrawler.entities.usual.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Car_usual_fuel_types")
public class UsualFuelType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @OneToMany(
            mappedBy = "usualFuelType",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<UsualEngineSize> usualEngineSizes;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "model_id")
    private UsualModel usualModel;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UsualFuelType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UsualEngineSize> getUsualEngineSizes() {
        return usualEngineSizes;
    }

    public void setUsualEngineSizes(List<UsualEngineSize> usualEngineSizes) {
        this.usualEngineSizes = usualEngineSizes;
    }

    public void addYear(String year) {
        if (this.usualEngineSizes == null) {
            this.usualEngineSizes = new ArrayList<>();
        }
        this.usualEngineSizes.add(new UsualEngineSize(year));
    }

    public UsualModel getUsualModel() {
        return usualModel;
    }

    public void setUsualModel(UsualModel usualModel) {
        this.usualModel = usualModel;
    }

    @Override
    public String toString() {
        return "\n\t\tUsualFuelType{" +
                "name='" + name + '\'' +
                ", usualEngineSizes=" + usualEngineSizes +
                '}';
    }
}
