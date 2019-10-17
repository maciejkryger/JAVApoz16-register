package pl.sda.register.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.register.exception.UserNotFoundException;

@ControllerAdvice
public class UserControllerAdvice {

    @ExceptionHandler(value = RuntimeException.class)
    public ModelAndView handleException(RuntimeException exception) {
        ModelAndView exceptionModelAndView = new ModelAndView("exception");
        exceptionModelAndView.addObject("message", exception.getMessage());
        return exceptionModelAndView;
    }
}
