package clean.internet.restapi.controller;

import ch.qos.logback.classic.Logger;
import clean.internet.restapi.common.PeriodType;
import clean.internet.restapi.service.GraphService;
import clean.internet.restapi.util.NotifierSlack;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


@RestController
public class GraphController {

    private static final Logger logger = (Logger) LoggerFactory.getLogger(GraphController.class);

    @Autowired
    GraphService graphService;

    @Autowired
    NotifierSlack notifierSlack;

    @RequestMapping(value = "/data/graph/line", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", required = true, example = "day", value = "조회할 타입", paramType = "query", dataType = "string", allowableValues = "day, month, year")
            , @ApiImplicitParam(name = "begin", required = true, example = "2019-01-01", value = "조회 시작 날 (YYYY-MM-DD)", paramType = "query", dataType = "string")
            , @ApiImplicitParam(name = "end", required = true, example = "2019-02-01", value = "조회 마지막 날 (YYYY-MM-DD)", paramType = "query", dataType = "string")
    })
    public ResponseEntity lineGraphData(
            @RequestParam(value = "type") String type
            , @RequestParam(value = "begin") String begin
            , @RequestParam(value = "end") String end) throws JsonProcessingException {

        ResponseEntity responseEntity;

        try {

            PeriodType periodType = PeriodType.find(type);
            String data = "";

            switch (periodType) {
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


    @RequestMapping(value = "/data/graph/pie", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", required = true, example = "day", value = "조회할 타입", paramType = "query", dataType = "string", allowableValues = "day, month, year")
            , @ApiImplicitParam(name = "begin", required = true, example = "2019-01-01", value = "조회 시작 날 (YYYY-MM-DD)", paramType = "query", dataType = "string")
            , @ApiImplicitParam(name = "end", required = true, example = "2019-02-01", value = "조회 마지막 날 (YYYY-MM-DD)", paramType = "query", dataType = "string")
    })
    public ResponseEntity pieGraphData(
            @RequestParam(value = "type") String type
            , @RequestParam(value = "begin") String begin
            , @RequestParam(value = "end") String end) throws IOException {

        ResponseEntity responseEntity;

        try {

            PeriodType periodType = PeriodType.find(type);
            String data = "";

            switch (periodType) {
                case MONTH:
                    data = graphService.getPieGraphDataForMonth(begin, end);
                    break;
            }

            responseEntity = ResponseEntity
                    .status(HttpStatus.OK)
                    .body(data);
        } catch (Exception exception) {

            responseEntity = ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Sorry, Graph pie Error");

            logger.error("error : ", exception);
            notifierSlack.notifySend(ExceptionUtils.getRootCauseMessage(exception), ExceptionUtils.getStackTrace(exception));
        }

        return responseEntity;
    }
}