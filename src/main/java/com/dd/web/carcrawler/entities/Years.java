package com.dd.web.carcrawler.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Car_years")
public class Years {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int year;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "engine_size_id")
    private EngineSize engineSize;

    public Years() {
    }

    public Years(int year) {
        this.year = year;
    }

    public int getName() {
        return year;
    }

    public void setName(int name) {
        this.year = name;
    }

    public EngineSize getEngineSize() {
        return engineSize;
    }

    public void setEngineSize(EngineSize engineSize) {
        this.engineSize = engineSize;
    }

    @Override
    public String toString() {
        return "year=" + year;
    }
}
