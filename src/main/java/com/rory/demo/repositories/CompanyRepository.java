package com.rory.demo.repositories;

import com.rory.demo.entity.Company;

import javax.persistence.TypedQuery;
import java.util.List;

@SuppressWarnings("JpaQlInspection")
public class CompanyRepository extends BaseRepository<Company, Long> {

    public CompanyRepository() {
        super(Company.class);
    }

    @Override
    public List<Company> findAll(String text) {
        String sql = "select distinct c from Company c left join fetch c.establishmentEmissionPointList";
        sql += " where upper(c.name) like upper(:name) or c.ruc like :ruc";
        final TypedQuery<Company> query = entityManager.createQuery(sql, Company.class);
        query.setParameter("name", "%" + text + "%");
        query.setParameter("ruc", text + "%");
        return query.getResultList();
    }

    public List<Company> findCompanies(int startPosition, Integer maxResults, String sortFields, String sortDirections) {
        TypedQuery<Company> query =
                entityManager.createQuery("SELECT c FROM Company c left join fetch c.establishmentEmissionPointList ORDER BY c." + sortFields + " " + sortDirections, Company.class);
        query.setFirstResult(startPosition);
        query.setMaxResults(maxResults);
        return query.getResultList();
    }

    public Integer countCompanies() {
        TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT(p.id) FROM Company p" + "", Long.class);
        return query.getSingleResult().intValue();
    }
}
