package clean.internet.restapi.repository;

import clean.internet.restapi.model.raw.TokenizedData;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TokenizedDataRepository extends CrudRepository<TokenizedData, Long> {

    @Query(value = "SELECT * FROM tokenized_data INNER JOIN (SELECT NO FROM tokenized_data WHERE CATEGORY IS NOT NULL ORDER BY NO DESC LIMIT ?1, ?2) AS DATA USING(NO)", nativeQuery = true)
    List<TokenizedData> getTokenizedDataByPaging(int begin, int count);
}
