package clean.internet.restapi.controller;

import ch.qos.logback.classic.Logger;
import clean.internet.restapi.common.LineGraphType;
import clean.internet.restapi.service.GraphService;
import clean.internet.restapi.util.NotifierSlack;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class GraphController {

    private static final Logger logger = (Logger) LoggerFactory.getLogger(GraphController.class);

    @Autowired
    GraphService graphService;

    @Autowired
    NotifierSlack notifierSlack;

    @RequestMapping(value = "/data/graph/line")
    public ResponseEntity lineGraph(
            @RequestParam(value = "type") String type
            , @RequestParam(value = "begin") String begin
            , @RequestParam(value = "end") String end) throws JsonProcessingException {

        ResponseEntity responseEntity;

        try {

            LineGraphType graphType = LineGraphType.find(type);
            String data = "";

            switch (graphType) {
                case DAY:
                    data = graphService.getLineGraphDataForDay(begin, end);
                    break;
                case MONTH:
                    data = graphService.getLineGraphDataForMonth(begin, end);
                    break;
                case YEAR:
                    data = graphService.getLineGraphDataForYear(begin, end);
                    break;
            }

            responseEntity = ResponseEntity
                    .status(HttpStatus.OK)
                    .body(data);
        } catch (Exception exception) {

            responseEntity = ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Sorry, Graph Line Error");

            logger.error("error : ", exception);
            notifierSlack.notifySend(ExceptionUtils.getRootCauseMessage(exception), ExceptionUtils.getStackTrace(exception));
        }

        return responseEntity;
    }
}