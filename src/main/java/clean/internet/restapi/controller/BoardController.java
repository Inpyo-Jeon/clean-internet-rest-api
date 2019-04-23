package clean.internet.restapi.controller;


import ch.qos.logback.classic.Logger;
import clean.internet.restapi.service.BoardService;
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

@RestController
public class BoardController {

    private static final Logger logger = (Logger) LoggerFactory.getLogger(BoardController.class);

    @Autowired
    BoardService boardService;

    @Autowired
    NotifierSlack notifierSlack;

    @RequestMapping(value = "/data/board", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "begin", required = true, example = "1", value = "조회를 시작할 수", paramType = "query", dataType = "string")
            , @ApiImplicitParam(name = "count", required = true, example = "10", value = "조회할 갯수", paramType = "query", dataType = "string")
    })
    public ResponseEntity boardData(
            @RequestParam(value = "begin") int begin
            , @RequestParam(value = "count") int count
    ) throws JsonProcessingException {

        ResponseEntity responseEntity;

        try {

            responseEntity = ResponseEntity
                    .status(HttpStatus.OK)
                    .body(boardService.getBoardData(begin, count));

        } catch (Exception exception) {

            responseEntity = ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Sorry, Board Error");

            logger.error("error : ", exception);
            notifierSlack.notifySend(ExceptionUtils.getRootCauseMessage(exception), ExceptionUtils.getStackTrace(exception));
        }

        return responseEntity;
    }
}