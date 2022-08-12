package com.shohag.Backend.services;

import org.springframework.stereotype.Service;
import org.springframework.util.ErrorHandler;

@Service
public class JmsErrorHandler implements ErrorHandler {

//    private static final Logger log = LoggerFactory.getLogger(JmsErrorHandler.class);

    @Override
    public void handleError(Throwable t) {
        System.out.println("In default jms error handler...");
        System.out.println("Error Message : " + t.getMessage());
    }

}
