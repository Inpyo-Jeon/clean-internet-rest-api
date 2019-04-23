package clean.internet.restapi.repository;

import clean.internet.restapi.model.raw.CrwLineGraph;
import clean.internet.restapi.model.raw.CrwPieGraph;
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

    public List<CrwLineGraph> getLineGraphDataForDay(Date begin, Date end) {
        Query query = em.createNativeQuery("SELECT DATE_FORMAT(DATE, '%Y-%m-%d') AS dateTime, COUNT(*) AS totalCount FROM crw_data WHERE DATE(DATE) BETWEEN :begin AND :end GROUP BY DATE_FORMAT(DATE, '%Y%m%d') ORDER BY DATE DESC");
        query = setAliasLineGraphForPeriod(query, begin, end);

        List<CrwLineGraph> items = (List<CrwLineGraph>) query.getResultList();
        return items;
    }

    public List<CrwLineGraph> getLineGraphDataForMonth(Date begin, Date end) {
        Query query = em.createNativeQuery("SELECT DATE_FORMAT(DATE, '%Y-%m') AS dateTime, count(*) AS totalCount FROM crw_data WHERE DATE(DATE) BETWEEN :begin AND :end GROUP BY DATE_FORMAT(DATE, '%Y%m') ORDER BY date DESC");
        query = setAliasLineGraphForPeriod(query, begin, end);

        List<CrwLineGraph> items = (List<CrwLineGraph>) query.getResultList();
        return items;
    }

    public List<CrwLineGraph> getLineGraphDataForYear(Date begin, Date end) {
        Query query = em.createNativeQuery("SELECT DATE_FORMAT(DATE, '%Y') AS dateTime, count(*) AS totalCount FROM crw_data WHERE DATE(DATE) BETWEEN :begin AND :end GROUP BY DATE_FORMAT(DATE, '%Y') ORDER BY date DESC");
        query = setAliasLineGraphForPeriod(query, begin, end);

        List<CrwLineGraph> items = (List<CrwLineGraph>) query.getResultList();
        return items;
    }

    public List<CrwPieGraph> getPieGraphDataForMonth(Date begin, Date end){
        Query query = em.createNativeQuery("SELECT DATE_FORMAT(DATE, '%Y-%m') AS dateTime, COUNT(IF(category='1', category, null)) AS 'yes', COUNT(IF(category='0', category, null)) AS 'no' FROM crw_data WHERE DATE(DATE) BETWEEN :begin AND :end GROUP BY dateTime ORDER BY dateTime DESC");
        query = setAliasPieGraphForPeriod(query, begin, end);

        List<CrwPieGraph> items = (List<CrwPieGraph>) query.getResultList();
        return items;
    }

    public Query setAliasLineGraphForPeriod(Query query, Date begin, Date end) {
        //TODO : Deprecated된 클래스와 메서드를 대체해야 한다.
        //Scalar로 할 경우, Object와 getter/setter 이름 맞춰줘야한다.
        //COUNT, DATE같은 예약어는 안된다.
        return query.unwrap(SQLQuery.class)
                .addScalar("dateTime", StringType.INSTANCE)
                .addScalar("totalCount", BigIntegerType.INSTANCE)
                .setParameter("begin", begin)
                .setParameter("end", end)
                .setResultTransformer(Transformers.aliasToBean(CrwLineGraph.class));
    }

    public Query setAliasPieGraphForPeriod(Query query, Date begin, Date end) {
        return query.unwrap(SQLQuery.class)
                .addScalar("dateTime", StringType.INSTANCE)
                .addScalar("yes", BigIntegerType.INSTANCE)
                .addScalar("no", BigIntegerType.INSTANCE)
                .setParameter("begin", begin)
                .setParameter("end", end)
                .setResultTransformer(Transformers.aliasToBean(CrwPieGraph.class));
    }
}