package Server.Calculator.services;

import Server.Calculator.exceptions.ErrorMessege;
import Server.Calculator.exceptions.CalcException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

@Service
@Getter
@Setter
public class StackCalc {

    private Stack<Integer> stack= new Stack<>();
    private final Calculator calculator= new Calculator();


    public int addArguments(int[] arg) {

        for (int argument : arg) {
            stack.push(argument);
        }
        return stack.size();
    }

    public int operationExecute(String operation) throws CalcException {
        //check the operation is valid, and return the number of arguments
        int numOfArgToOp = vaildOperation(operation);
        //check if there are enough argument in the stack
        if (stack.size()<numOfArgToOp) {
            throw new CalcException(ErrorMessege.NOT_ENOUGH_ARGUMENTS_IN_STACK +operation+ ErrorMessege.CONTINUE1
                    + numOfArgToOp + ErrorMessege.CONTINUE2 + stack.size() + ErrorMessege.CONTINUE3);
        }

        List<Integer> arg = new ArrayList<>();
        for (int i=0; i<numOfArgToOp; i++ )
            arg.add(stack.pop());

        int result = calculator.operationExecute(operation,arg);
        return result;
    }

    private int vaildOperation(String operation) throws CalcException {
        Calculator.Operation validOperation;
        try {
            validOperation = Calculator.Operation.valueOf(operation.toLowerCase());
        }  catch (IllegalArgumentException e) {
            throw new CalcException(ErrorMessege.ILLEGAL_OPERATION + operation);
        }
        //return the number of arguments fit to the operation
        return switch (validOperation) {
            //expecting for two arguments
            case plus, minus, times, divide, pow -> 2;
            //expecting for one argument
            case abs, fact -> 1;
        };
    }

    public int removeArguments(Integer count) throws CalcException {
        //if count bigger than the number of argument in the stack
        if (count > stack.size())throw new CalcException(ErrorMessege.CANT_REMOVE + count +
                ErrorMessege.CANT_REMOVE_CONTINUE1 + stack.size()+ ErrorMessege.CANT_REMOVE_CONTINUE2);
        else {
            for (int i=0 ; i<count;i++)
                stack.pop();
        }
        return stack.size();
    }
}
