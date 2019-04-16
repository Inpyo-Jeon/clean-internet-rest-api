package clean.internet.restapi.controller;


import clean.internet.restapi.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class BoardController {

    @Autowired
    BoardService boardService;

    @RequestMapping(value = "/data/board", method = RequestMethod.GET)
    public String getBoardData(
            @RequestParam(value = "begin") int begin
            , @RequestParam(value = "count") int count
    ) throws IOException {

        String result = boardService.getBoardData(begin, count);
        return result;
    }
}