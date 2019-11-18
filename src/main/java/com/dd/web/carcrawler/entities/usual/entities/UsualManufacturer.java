package com.dd.web.carcrawler.entities.usual.entities;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Entity
@Table(name = "Car_usual_manufacturers")
public class UsualManufacturer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @OneToMany(
            mappedBy = "usualManufacturer",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<UsualModel> usualModels;

    public UsualManufacturer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UsualModel> getUsualModels() {
        return usualModels;
    }

    public void setUsualModels(List<UsualModel> usualModels) {
        this.usualModels = usualModels;
    }

    public void addModel(UsualModel usualModel){
        if (this.usualModels == null) {
            this.usualModels = new ArrayList<>();
        }
        this.usualModels.add(usualModel);
    }

    @Override
    public String toString() {
        return "UsualManufacturer{" +
                "name='" + name + '\'' +
                ", usualModels=" + usualModels +
                '}';
    }
}
