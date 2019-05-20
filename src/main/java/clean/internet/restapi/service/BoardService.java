package clean.internet.restapi.service;

import clean.internet.restapi.model.json.BoardData;
import clean.internet.restapi.model.raw.CrwData;
import clean.internet.restapi.repository.CrwDataRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class BoardService {

    CrwDataRepository crwDataRepository;
    ObjectMapper mapper;

    private BoardService(CrwDataRepository crwDataRepository, ObjectMapper mapper) {
        this.crwDataRepository = crwDataRepository;
        this.mapper = mapper;
    }

    public String getBoardData(int begin, int count) throws IOException {
        List<CrwData> rawData = crwDataRepository.getBoardDataByPaging(begin, count);
        List<BoardData> boardDataList = new ArrayList<>();

        rawData.forEach(
                item -> {
                    BoardData boardData = new BoardData();
                    BeanUtils.copyProperties(item, boardData);
                    boardData.setConcatContent(boardData.getTitle() + " " + boardData.getContent());
                    boardDataList.add(boardData);
                }
        );

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        mapper.writeValue(out, boardDataList);

        return new String(out.toByteArray());
    }
}