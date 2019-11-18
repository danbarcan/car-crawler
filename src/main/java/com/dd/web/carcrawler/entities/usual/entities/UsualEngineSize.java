package com.dd.web.carcrawler.entities.usual.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Car_usual_engine_sizes")
public class UsualEngineSize {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @OneToMany(
            mappedBy = "usualEngineSize",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<UsualPower> usualPowers;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fuel_type_id")
    private UsualFuelType usualFuelType;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UsualEngineSize(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UsualPower> getUsualPowers() {
        return usualPowers;
    }

    public void setUsualPowers(List<UsualPower> usualPowers) {
        this.usualPowers = usualPowers;
    }

    public void addYear(String power) {
        if (this.usualPowers == null) {
            this.usualPowers = new ArrayList<>();
        }
        this.usualPowers.add(new UsualPower(power));
    }

    public UsualFuelType getUsualFuelType() {
        return usualFuelType;
    }

    public void setUsualFuelType(UsualFuelType usualFuelType) {
        this.usualFuelType = usualFuelType;
    }

    @Override
    public String toString() {
        return "\n\t\tUsualEngineSize{" +
                "name='" + name + '\'' +
                ", usualPowers=" + usualPowers +
                '}';
    }
}
