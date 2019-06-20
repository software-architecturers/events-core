package com.kpi.miss;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class MissKpiEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String girlName;

    private String description;

    @OneToMany
    private Collection<GirlImage> images;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGirlName() {
        return girlName;
    }

    public void setGirlName(String girlName) {
        this.girlName = girlName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Collection<GirlImage> getImages() {
        return images;
    }

    public void setImages(Collection<GirlImage> images) {
        this.images = images;
    }
}
