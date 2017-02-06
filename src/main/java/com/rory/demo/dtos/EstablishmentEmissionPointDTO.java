package com.rory.demo.dtos;

import java.io.Serializable;

public class EstablishmentEmissionPointDTO implements Serializable {

    private Long id;

    private String establishment;

    private String emissionPoint;

    private Long companyId;

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

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
}
