package com.rory.demo.entity;

import com.rory.demo.util.StringUtils;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "company")
@SequenceGenerator(name = "COMPANY_ID_SEQ", sequenceName = "company_id_seq", allocationSize = 1)
public class Company implements BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COMPANY_ID_SEQ")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "ruc")
    private String ruc;

    @OneToMany(mappedBy = "company")
    private List<EstablishmentEmissionPoint> establishmentEmissionPointList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = StringUtils.toUpperCase(name);
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public List<EstablishmentEmissionPoint> getEstablishmentEmissionPointList() {
        return establishmentEmissionPointList;
    }

    public void setEstablishmentEmissionPointList(List<EstablishmentEmissionPoint> establishmentEmissionPointList) {
        this.establishmentEmissionPointList = establishmentEmissionPointList;
    }
}
