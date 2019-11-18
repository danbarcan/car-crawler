package com.dd.web.carcrawler.entities.usual.entities;

import javax.persistence.*;

@Entity
@Table(name = "Car_usual_powers")
public class UsualPower {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "engine_size_id")
    private UsualEngineSize usualEngineSize;

    public UsualPower() {
    }

    public UsualPower(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UsualEngineSize getUsualEngineSize() {
        return usualEngineSize;
    }

    public void setUsualEngineSize(UsualEngineSize usualEngineSize) {
        this.usualEngineSize = usualEngineSize;
    }

    @Override
    public String toString() {
        return "name=" + name;
    }
}
