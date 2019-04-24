package clean.internet.restapi.repository;

import clean.internet.restapi.model.raw.ApiTokenData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiTokenRepository extends CrudRepository<ApiTokenData, Long> {

    ApiTokenData getApiTokenDataByApiToken(String token);

}