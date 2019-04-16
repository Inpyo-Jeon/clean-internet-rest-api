package clean.internet.restapi.repository;

import clean.internet.restapi.model.raw.CrwDcData;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CrwDcDataRepository extends CrudRepository<CrwDcData, Long> {

    @Query(value = "SELECT * FROM crw_dc_data INNER JOIN (SELECT NO FROM crw_dc_data ORDER BY NO DESC LIMIT ?1, ?2) AS DATA USING(NO)", nativeQuery = true)
    List<CrwDcData> getBoardDataByPaging(int begin, int count);
}