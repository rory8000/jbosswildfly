package com.rory.demo.entity;

import javax.persistence.*;

@Entity
@Table(name = "establishment_emission_point")
@SequenceGenerator(name = "ESTABLISHMENT_EMISSION_POINT_ID_SEQ", sequenceName = "establishment_emission_point_id_seq", allocationSize = 1)
public class EstablishmentEmissionPoint implements BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ESTABLISHMENT_EMISSION_POINT_ID_SEQ")
    private Long id;

    @Column(name = "establishment")
    private String establishment;

    @Column(name = "emission_point")
    private String emissionPoint;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEstablishment() {
        return establishment;
    }

    public void setEstablishment(String establishment) {
        this.establishment = establishment;
    }

    public String getEmissionPoint() {
        return emissionPoint;
    }

    public void setEmissionPoint(String emissionPoint) {
        this.emissionPoint = emissionPoint;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
