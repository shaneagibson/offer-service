package uk.co.epsilontechnologies.offerservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import uk.co.epsilontechnologies.offerservice.model.Error;
import uk.co.epsilontechnologies.offerservice.model.Offer;
import uk.co.epsilontechnologies.offerservice.service.OfferService;

import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RequestMapping(path = "/offers")
@RestController
public class OfferController {

    private static final Logger log = LoggerFactory.getLogger(OfferController.class);

    private final OfferService offerService;

    @Autowired
    public OfferController(final OfferService offerService) {
        this.offerService = offerService;
    }

    @RequestMapping(method = POST, consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Offer create(@Valid @RequestBody final Offer offer) {
        return offerService.create(offer);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Error handleIllegalArgumentException(final IllegalArgumentException e) {
        log.warn(e.getMessage(), e);
        return new Error(e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Error handleRuntimeException(final RuntimeException e) {
        log.error(e.getMessage(), e);
        return new Error(e.getMessage());
    }

}