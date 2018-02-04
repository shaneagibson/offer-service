package uk.co.epsilontechnologies.offerservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class PingController extends AbstractController {

    @Autowired
    public PingController() {
        super(LoggerFactory.getLogger(PingController.class));
    }

    @RequestMapping(path = "/ping", method = GET)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ping() { }

}