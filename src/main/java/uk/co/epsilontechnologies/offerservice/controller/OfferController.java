package uk.co.epsilontechnologies.offerservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import uk.co.epsilontechnologies.offerservice.exception.OfferNotFoundException;
import uk.co.epsilontechnologies.offerservice.model.Error;
import uk.co.epsilontechnologies.offerservice.model.Offer;
import uk.co.epsilontechnologies.offerservice.service.OfferService;

import javax.validation.Valid;

import java.util.UUID;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(path = "/offers")
public class OfferController extends AbstractController {

    private final OfferService offerService;

    @Autowired
    public OfferController(final OfferService offerService) {
        super(LoggerFactory.getLogger(OfferController.class));
        this.offerService = offerService;
    }

    @RequestMapping(method = POST, consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Offer create(@Valid @RequestBody final Offer offer) {
        return offerService.create(offer);
    }

    @RequestMapping(path = "/{id}", method = POST, consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Offer update(@PathVariable("id") final UUID id, @Valid @RequestBody final Offer offer) {
        return offerService.update(id, offer);
    }

    @RequestMapping(path = "/{id}", method = GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Offer getById(@PathVariable("id") final UUID id) {
        return offerService.getById(id);
    }

    @ExceptionHandler(OfferNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public Error handleOfferNotFoundException(final OfferNotFoundException e) {
        log.warn(e.getMessage(), e);
        return new Error(e.getMessage());
    }

}