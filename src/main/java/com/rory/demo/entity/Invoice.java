package com.rory.demo.entity;

import com.rory.demo.enums.InvoiceTypeEnum;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "invoice")
@SequenceGenerator(name = "COMPANY_ID_SEQ", sequenceName = "invoice_id_seq", allocationSize = 1)
public class Invoice implements BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COMPANY_ID_SEQ")
    private Long id;

    @Column(name = "number")
    private String number;

    @Column(name = "value")
    private BigDecimal value;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "type")
    private InvoiceTypeEnum type;

    @ManyToOne
    @JoinColumn(name = "establishment_emission_point_id")
    private EstablishmentEmissionPoint establishmentEmissionPoint;

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

    public EstablishmentEmissionPoint getEstablishmentEmissionPoint() {
        return establishmentEmissionPoint;
    }

    public void setEstablishmentEmissionPoint(EstablishmentEmissionPoint establishmentEmissionPoint) {
        this.establishmentEmissionPoint = establishmentEmissionPoint;
    }
}
