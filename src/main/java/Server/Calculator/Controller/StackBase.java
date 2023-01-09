package Server.Calculator.Controller;

import Server.Calculator.services.StackCalc;
import Server.Calculator.exceptions.CalcException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    private final Logger stackLogger = LogManager.getLogger("stack-logger");
    private final Logger requestLogger = LogManager.getLogger("request-logger");

    @GetMapping("/stack/size")
    public ResponseEntity<Response> stackSize(){

            int result = stackCalculation.getStack().size();
            Response res = new Response(result, null);

            stackLogger.info("Stack size is "+result);
            stackLogger.debug("Stack content (first == top): ["+ argumentsCommaSeparated() + "]");

            return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PutMapping("/stack/arguments")
    public ResponseEntity<Response> addArgumentsToStack(@RequestBody StackAddArgReq body) {
        int stackSizeBefore = stackCalculation.getStack().size();
        int result = stackCalculation.addArguments(body.getArguments());
        Response res = new Response(result, null);

        int totalargs = stackCalculation.getStack().size() - stackSizeBefore;

        stackLogger.info("Adding total of " + totalargs + " argument(s) to the stack | Stack size: " +stackCalculation.getStack().size());
        stackLogger.debug("Adding arguments: " + argumentsCommaSeparated() + " | Stack size before " + stackSizeBefore + " | stack size after " + stackCalculation.getStack().size());

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/stack/operate")
    public ResponseEntity<Response> operationExecute(@RequestParam String operation)  {
        try {

            int result = stackCalculation.operationExecute(operation);
            Response res = new Response(result, null);

            String argsWithComma ="";

            if(stackCalculation.getArgToOp().size()==1) {
                argsWithComma = stackCalculation.getArgToOp().toString();
            }
            else{
                argsWithComma = argsWithComma + stackCalculation.getArgToOp().get(0) + "," + stackCalculation.getArgToOp().get(1);
            }


            stackLogger.info("Performing operation " + operation.toLowerCase() + ". Result is " + result + " | stack size: " + stackCalculation.getStack().size());
            stackLogger.debug("Performing operation: " + operation.toLowerCase() + "(" + argsWithComma + ") = "+result);

            return new ResponseEntity<>(res, HttpStatus.OK);

        }catch (CalcException exception){

            Response res = new Response(exception.getMessage());
            stackLogger.error("Server encountered an error ! message: " + exception.getMessage());

            return new ResponseEntity<>(res,HttpStatus.CONFLICT);

        }
    }


    @DeleteMapping("/stack/arguments")
    public ResponseEntity<Response> StackRemoveArguments(@RequestParam String count) {
        try{

            int result = stackCalculation.removeArguments(Integer.valueOf(count));
            Response res = new Response(result, null);

            stackLogger.info("Removing total " + result + " argument(s) from the stack | Stack size: " + stackCalculation.getStack().size());
            return new ResponseEntity<>(res, HttpStatus.OK);

        }catch (CalcException exception){

            Response res = new Response(exception.getMessage());
            stackLogger.error("Server encountered an error ! message: " + exception.getMessage());

            return new ResponseEntity<>(res,HttpStatus.CONFLICT);

        }
    }

    //return string of the arguments comma separated
    private String argumentsCommaSeparated(){
            String stackArg = "";
            for (Integer element : stackCalculation.getStack()) {
                stackArg = element + ", " + stackArg;
            }
            if (stackCalculation.getStack().size() > 0)
                stackArg = stackArg.substring(0, stackArg.length() - 2);
            return stackArg;

    }

}
