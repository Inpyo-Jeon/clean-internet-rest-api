package clean.internet.restapi.service;

import clean.internet.restapi.model.raw.CrwLineGraph;
import clean.internet.restapi.repository.CrwDcDataCustomRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class GraphService {

    @Autowired
    CrwDcDataCustomRepository crwDcDataCustomRepository;

    @Autowired
    ObjectMapper mapper;

    public String getLineGraphDataForPeriod(String b, String e) throws IOException, ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date begin = sdf.parse(b);
        Date end = sdf.parse(e);

        List<CrwLineGraph> crwLineGraphList = crwDcDataCustomRepository.getLineGraphDataForPeriod(begin, end);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        mapper.writeValue(out, crwLineGraphList);

        return new String(out.toByteArray());
    }
}