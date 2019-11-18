package com.dd.web.carcrawler.entities.usual.entities;

import javax.persistence.*;

@Entity
@Table(name = "Car_usual_powers")
public class Power {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "engine_size_id")
    private EngineSize engineSize;

    public Power() {
    }

    public Power(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EngineSize getEngineSize() {
        return engineSize;
    }

    public void setEngineSize(EngineSize engineSize) {
        this.engineSize = engineSize;
    }

    @Override
    public String toString() {
        return "name=" + name;
    }
}
