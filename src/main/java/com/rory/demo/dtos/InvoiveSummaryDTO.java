package com.rory.demo.dtos;

import com.rory.demo.enums.InvoiceTypeEnum;

import java.math.BigDecimal;

public class InvoiveSummaryDTO {

    private InvoiceTypeEnum type;

    private BigDecimal total;

    public InvoiveSummaryDTO() {
    }

    public InvoiveSummaryDTO(InvoiceTypeEnum type, BigDecimal total) {
        this.type = type;
        this.total = total;
    }

    public InvoiceTypeEnum getType() {
        return type;
    }

    public void setType(InvoiceTypeEnum type) {
        this.type = type;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
