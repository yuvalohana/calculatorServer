package Server.Calculator.Controller;


import Server.Calculator.exceptions.CalcException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import Server.Calculator.requestArg.IndependentReq;
import Server.Calculator.response.Response;
import Server.Calculator.services.IndependentCalc;

import java.util.Locale;


@RestController
public class IndependentCalculation {

    private final IndependentCalc independentCalc = new IndependentCalc();
    private final Logger independentLogger = LogManager.getLogger("independent-logger");
    private final Logger requestLogger = LogManager.getLogger("request-logger");


    @PostMapping(value = "/independent/calculate", consumes = {MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<Response> calculate(@RequestBody IndependentReq body) {

    try {
        int result = independentCalc.operationExecute(body.getArguments(), body.getOperation());
        Response res = new Response(result, null);

        String operationSeparatedByComma= "";
        if(body.getArguments().length==1)
            operationSeparatedByComma= body.getArguments().toString();
        else {
            operationSeparatedByComma = body.getArguments()[0] + "," + body.getArguments()[1];
        }

        independentLogger.info("Performing operation " + body.getOperation().toLowerCase(Locale.ROOT) + ". Result is " + result);
        independentLogger.debug("Performing operation: " + body.getOperation().toLowerCase(Locale.ROOT) + "(" + operationSeparatedByComma + ") = " + result);

        return new ResponseEntity<>(res,HttpStatus.OK);

    }catch (CalcException exception){

        Response res = new Response(exception.getMessage());

        independentLogger.error("Server encountered an error ! message: " + exception.getMessage());
        return new ResponseEntity<>(res,HttpStatus.CONFLICT);

    }
  }


  }
