package com.dd.web.carcrawler.entities;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Car_engine_sizes")
public class EngineSize {
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
    private List<Years> years;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "model_id")
    private Model model;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public EngineSize(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Years> getYears() {
        return years;
    }

    public void setYears(List<Years> years) {
        this.years = years;
    }

    public void addYear(String year) {
        if (this.years == null) {
            this.years = new ArrayList<>();
        }
        this.years.add(new Years(Integer.valueOf(year)));
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    @Override
    public String toString() {
        return "\n\t\tUsualEngineSize{" +
                "name='" + name + '\'' +
                ", years=" + years +
                '}';
    }
}
