package clean.internet.restapi.repository;

import clean.internet.restapi.model.raw.CrwLineGraph;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.BigIntegerType;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

@Repository
public class CrwDcDataCustomRepository {

    @PersistenceContext
    private EntityManager em;

    public List<CrwLineGraph> getLineGraphDataForPeriod(Date begin, Date end) {
        Query query = em.createNativeQuery("SELECT DATE_FORMAT(DATE, '%Y%m%d') AS dateTime, COUNT(*) AS totalCount FROM crw_dc_data WHERE DATE(DATE) BETWEEN :begin AND :end GROUP BY DATE_FORMAT(DATE, '%Y%m%d') ORDER BY DATE DESC");

        //Scalar로 할 경우, Object와 getter/setter 이름 맞춰줘야한다.
        //COUNT, DATE같은 예약어는 안된다.
        //TODO : Deprecated된 클래스와 메서드를 대체해야 한다.
        query.unwrap(SQLQuery.class)
                .addScalar("dateTime", StringType.INSTANCE)
                .addScalar("totalCount", BigIntegerType.INSTANCE)
                .setParameter("begin", begin)
                .setParameter("end", end)
                .setResultTransformer(Transformers.aliasToBean(CrwLineGraph.class));

        List<CrwLineGraph> items = (List<CrwLineGraph>) query.getResultList();
        return items;
    }



}