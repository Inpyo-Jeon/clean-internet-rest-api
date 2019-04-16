package clean.internet.restapi.controller;


import clean.internet.restapi.model.row.CrwDcData;
import clean.internet.restapi.model.json.BoardData;
import clean.internet.restapi.repository.CrwDcDataRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class BoardController {

    @Autowired
    CrwDcDataRepository crwDCDataRepository;

    @Autowired
    ObjectMapper mapper;

    @RequestMapping(value = "/data/board", method = RequestMethod.GET)
    public String getBoardData(
            @RequestParam(value = "begin") int begin
            , @RequestParam(value = "count") int count
    ) throws IOException {

        List<CrwDcData> rawData = crwDCDataRepository.getBoardDataByPaging(begin, count);
        List<BoardData> boardDataList = new ArrayList<>();

        rawData.forEach(
                item -> {
                    BoardData boardData = new BoardData();
                    BeanUtils.copyProperties(item, boardData);
                    boardData.setConcatContent(boardData.getTitle() + " // " + boardData.getContent());
                    boardDataList.add(boardData);
                }
        );

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        mapper.writeValue(out, boardDataList);

        return new String(out.toByteArray());
    }
}