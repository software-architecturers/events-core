package com.kpi.miss;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class GirlImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    private String link;

}
