package clean.internet.restapi.controller;

import clean.internet.restapi.common.LineGraphType;
import clean.internet.restapi.service.GraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.ParseException;

@RestController
public class GraphController {

    @Autowired
    GraphService graphService;

    @RequestMapping(value = "/data/graph/line")
    public String lineGraph(
            @RequestParam(value = "type") String type
            , @RequestParam(value = "begin") String begin
            , @RequestParam(value = "end") String end) throws IOException, ParseException {

        LineGraphType graphType = LineGraphType.find(type);
        String data = "";

        switch (graphType) {
            case DAY: {
                data = graphService.getLineGraphDataForPeriod(begin, end);
                break;
            }
        }

        return data;
    }
}