package clean.internet.restapi.controller;


import ch.qos.logback.classic.Logger;
import clean.internet.restapi.service.BoardService;
import clean.internet.restapi.util.NotifierSlack;
import com.fasterxml.jackson.core.JsonProcessingException;
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
    public ResponseEntity getBoardData(
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