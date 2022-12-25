package Server.Calculator.Controller;


import Server.Calculator.exceptions.CalcException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import Server.Calculator.requestArg.IndependentReq;
import Server.Calculator.response.Response;
import Server.Calculator.services.IndependentCalc;

@RestController
public class IndependentCalculation {

    private final IndependentCalc independentCalc = new IndependentCalc();

    @PostMapping(value = "/independent/calculate", consumes = {MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<Response> calculate(@RequestBody IndependentReq body) {

    try {
        int result = independentCalc.operationExecute(body.getArguments(), body.getOperation());
        Response res = new Response(result, null);
        return new ResponseEntity<>(res,HttpStatus.OK);
    }catch (CalcException exception){

        Response res = new Response(exception.getMessage());
        return new ResponseEntity<>(res,HttpStatus.CONFLICT);

    }
  }


  }
