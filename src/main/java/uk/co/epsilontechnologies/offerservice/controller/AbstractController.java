package uk.co.epsilontechnologies.offerservice.controller;

import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import uk.co.epsilontechnologies.offerservice.model.Error;

public abstract class AbstractController {

    final Logger log;

    AbstractController(final Logger log) {
        this.log = log;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Error handleIllegalArgumentException(final IllegalArgumentException e) {
        log.warn(e.getMessage(), e);
        return new Error(e.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Error handleIllegalStateException(final IllegalStateException e) {
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
