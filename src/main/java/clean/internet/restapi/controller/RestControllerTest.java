package clean.internet.restapi.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestControllerTest {

    @RequestMapping(value = "/getsample", method = RequestMethod.GET)
    public String getTest() {
        return "getTest";
    }

    @RequestMapping(value = "/postsample", method = RequestMethod.POST)
    public String postTest() {
        return "postTest";
    }
}