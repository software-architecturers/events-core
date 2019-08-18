package com.kpi.events.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class RefreshToken {
	
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column
    private long id;
    
    private String token;
	
    @ManyToOne
    @JoinColumn
    private User user;

}
