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
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import static java.time.temporal.TemporalAdjusters.firstDayOfYear;
import static java.time.temporal.TemporalAdjusters.lastDayOfYear;
import static java.time.temporal.TemporalAdjusters.firstDayOfMonth;

@Service
public class GraphService {

    @Autowired
    CrwDcDataCustomRepository crwDcDataCustomRepository;

    @Autowired
    ObjectMapper mapper;

    public String getLineGraphDataForDay(String b, String e) throws IOException, ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date begin = sdf.parse(b);
        Date end = sdf.parse(e);

        List<CrwLineGraph> crwLineGraphList = crwDcDataCustomRepository.getLineGraphDataForDay(begin, end);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        mapper.writeValue(out, crwLineGraphList);

        return new String(out.toByteArray());
    }

    public String getLineGraphDataForMonth(String b, String e) throws IOException {
        Date begin = Date.from(getFirstDayOfMonth(b)
                .atStartOfDay(ZoneId.systemDefault()).toInstant());

        Date end = Date.from(getLastDayOfMonth(e)
                .atStartOfDay(ZoneId.systemDefault()).toInstant());

        List<CrwLineGraph> crwLineGraphList = crwDcDataCustomRepository.getLineGraphDataForMonth(begin, end);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        mapper.writeValue(out, crwLineGraphList);

        return new String(out.toByteArray());
    }

    public String getLineGraphDataForYear(String b, String e) throws IOException {
        Date begin = Date.from(getFirstDayOfYear(b)
                .atStartOfDay(ZoneId.systemDefault()).toInstant());

        Date end = Date.from(getLastDayOfYear(e)
                .atStartOfDay(ZoneId.systemDefault()).toInstant());

        List<CrwLineGraph> crwLineGraphList = crwDcDataCustomRepository.getLineGraphDataForYear(begin, end);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        mapper.writeValue(out, crwLineGraphList);

        return new String(out.toByteArray());
    }

    private LocalDate getFirstDayOfMonth(String date) {
        LocalDate convertedDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return convertedDate.with(firstDayOfMonth());
    }

    private LocalDate getLastDayOfMonth(String date) {
        LocalDate convertedDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return convertedDate.withDayOfMonth(convertedDate.getMonth().length(convertedDate.isLeapYear()));
    }

    private LocalDate getFirstDayOfYear(String date) {
        LocalDate convertedDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return convertedDate.with(firstDayOfYear());
    }

    private LocalDate getLastDayOfYear(String date) {
        LocalDate convertedDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return convertedDate.with(lastDayOfYear());
    }
}