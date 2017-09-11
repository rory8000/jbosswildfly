package com.rory.demo.repositories;

import com.rory.demo.dtos.InvoiveSummaryDTO;
import com.rory.demo.entity.Invoice;

import javax.persistence.TypedQuery;
import java.util.List;

public class InvoiceRepository extends BaseRepository<Invoice, Long> {

    public InvoiceRepository() {
        super(Invoice.class);
    }

    public Invoice find(Long establishmentEmissionPointId, String number) {
        StringBuilder sql = new StringBuilder();
        sql.append("select i from Invoice i ");
        sql.append(" where i.establishmentEmissionPoint.id = :establishmentEmissionPointId");
        sql.append(" and i.number = :number");

        TypedQuery<Invoice> query = entityManager.createQuery(sql.toString(), Invoice.class);
        query.setParameter("establishmentEmissionPointId", establishmentEmissionPointId);
        query.setParameter("number", number);

        return query.getSingleResult();
    }

    public List<InvoiveSummaryDTO> getSummary() {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT new com.rory.demo.dtos.InvoiveSummaryDTO(c.type, SUM(c.value))\n" +
                "        FROM Invoice c\n" +
                " WHERE c.year is null" +
                "        GROUP BY c.type");
        TypedQuery<InvoiveSummaryDTO> query = entityManager.createQuery(sql.toString(), InvoiveSummaryDTO.class);
        return query.getResultList();
    }
}
