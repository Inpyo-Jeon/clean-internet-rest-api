package clean.internet.restapi.service;

import clean.internet.restapi.model.raw.TokenizedData;
import clean.internet.restapi.repository.TokenizedDataRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class TokenizedService {

    @Autowired
    TokenizedDataRepository tokenizedDataRepository;

    @Autowired
    ObjectMapper mapper;

    public String getTokenizedData(int begin, int count) throws IOException {
        List<TokenizedData> rawData = tokenizedDataRepository.getTokenizedDataByPaging(begin, count);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        mapper.writeValue(out, rawData);

        return new String(out.toByteArray());
    }
}