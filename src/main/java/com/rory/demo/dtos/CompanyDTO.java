package com.rory.demo.dtos;

import java.io.Serializable;
import java.util.List;

public class CompanyDTO implements Serializable {

    private Long id;

    private String name;

    private String ruc;

    private List<EstablishmentEmissionPointDTO> establishmentEmissionPointList;

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
        this.name = name;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public List<EstablishmentEmissionPointDTO> getEstablishmentEmissionPointList() {
        return establishmentEmissionPointList;
    }

    public void setEstablishmentEmissionPointList(List<EstablishmentEmissionPointDTO> establishmentEmissionPointList) {
        this.establishmentEmissionPointList = establishmentEmissionPointList;
    }
}
