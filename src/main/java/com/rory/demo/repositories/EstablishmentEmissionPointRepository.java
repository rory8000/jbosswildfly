package com.rory.demo.repositories;

import com.rory.demo.entity.EstablishmentEmissionPoint;

import javax.persistence.TypedQuery;

public class EstablishmentEmissionPointRepository extends BaseRepository<EstablishmentEmissionPoint, Long> {

    public EstablishmentEmissionPointRepository() {
        super(EstablishmentEmissionPoint.class);
    }

    public EstablishmentEmissionPoint find(Long companyId, String establishment, String emissionPoint) {
        StringBuilder sql = new StringBuilder();
        sql.append("select i from EstablishmentEmissionPoint i ");
        sql.append(" where i.company.id = :companyId");
        sql.append(" and i.establishment = :establishment");
        sql.append(" and i.emissionPoint = :emissionPoint");

        TypedQuery<EstablishmentEmissionPoint> query = entityManager.createQuery(sql.toString(), EstablishmentEmissionPoint.class);
        query.setParameter("companyId", companyId);
        query.setParameter("establishment", establishment);
        query.setParameter("emissionPoint", emissionPoint);

        return query.getSingleResult();
    }
}
