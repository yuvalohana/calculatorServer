package Server.Calculator.Controller;

import Server.Calculator.services.StackCalc;
import Server.Calculator.exceptions.CalcException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import Server.Calculator.requestArg.IndependentReq;
import Server.Calculator.requestArg.StackAddArgReq;
import Server.Calculator.requestArg.StackOperateReq;
import Server.Calculator.response.Response;

@RestController
public class StackBase {
    private final StackCalc stackCalculation = new StackCalc();

    @GetMapping("/stack/size")
    public ResponseEntity<Response> stackSize(){
            int result = stackCalculation.getStack().size();
            Response res = new Response(result, null);
            return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PutMapping("/stack/arguments")
    public ResponseEntity<Response> addArgumentsToStack(@RequestBody StackAddArgReq body) {
        int result = stackCalculation.addArguments(body.getArguments());
        Response res = new Response(result, null);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/stack/operate")
    public ResponseEntity<Response> operationExecute(@RequestParam String operation)  {
        try {

            int result = stackCalculation.operationExecute(operation);
            Response res = new Response(result, null);
            return new ResponseEntity<>(res, HttpStatus.OK);

        }catch (CalcException exception){

            Response res = new Response(exception.getMessage());
            return new ResponseEntity<>(res,HttpStatus.CONFLICT);

        }
    }


    @DeleteMapping("/stack/arguments")
    public ResponseEntity<Response> StackRemoveArguments(@RequestParam String count) {
        try{

            int result = stackCalculation.removeArguments(Integer.valueOf(count));
            Response res = new Response(result, null);
            return new ResponseEntity<>(res, HttpStatus.OK);

        }catch (CalcException exception){

            Response res = new Response(exception.getMessage());
            return new ResponseEntity<>(res,HttpStatus.CONFLICT);

        }
    }


}
