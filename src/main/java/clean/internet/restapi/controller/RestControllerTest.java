package clean.internet.restapi.controller;


import clean.internet.restapi.model.CrwDcData;
import clean.internet.restapi.repository.CrwDcDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;

@RestController
public class RestControllerTest {

    @Autowired
    CrwDcDataRepository crwDCDataRepository;

    @RequestMapping(value = "/getsample/crwdcdata", method = RequestMethod.GET)
    public String getTest() {
        Iterable<CrwDcData> test = crwDCDataRepository.findAll();

        String result = "";

        Iterator loop = test.iterator();
        while (loop.hasNext()) {
            result += loop.next().toString();
            result += "\n";
        }

        return result;
    }

    @RequestMapping(value = "/postsample", method = RequestMethod.POST)
    public String postTest() {
        return "postTest";
    }
}