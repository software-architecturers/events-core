package com.kpi.miss;

import javax.persistence.*;

@Entity
public class GirlImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private MissKpiEntity girl;

    public MissKpiEntity getGirl() {
        return girl;
    }

    public void setGirl(MissKpiEntity girl) {
        this.girl = girl;
    }

    private String link;

    public GirlImage() {
    }

    public GirlImage(String link) {
        this.link = link;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
