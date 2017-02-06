package com.rory.demo.dtos;

import com.rory.demo.enums.InvoiceTypeEnum;

import java.io.Serializable;
import java.math.BigDecimal;

public class InvoiceDTO implements Serializable {

    private Long id;

    private String number;

    private BigDecimal value;

    private InvoiceTypeEnum type;

    private EstablishmentEmissionPointDTO establishmentEmissionPoint;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public InvoiceTypeEnum getType() {
        return type;
    }

    public void setType(InvoiceTypeEnum type) {
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public EstablishmentEmissionPointDTO getEstablishmentEmissionPoint() {
        return establishmentEmissionPoint;
    }

    public void setEstablishmentEmissionPoint(EstablishmentEmissionPointDTO establishmentEmissionPoint) {
        this.establishmentEmissionPoint = establishmentEmissionPoint;
    }
}
