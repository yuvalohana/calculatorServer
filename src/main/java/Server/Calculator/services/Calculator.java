package Server.Calculator.services;



import Server.Calculator.exceptions.CalcException;

import java.util.List;

import static Server.Calculator.exceptions.ErrorMessege.*;


public class Calculator {

    //declare enums to operations
    public enum Operation {
        plus, minus, times, divide, pow, abs, fact
    }

    public int operationExecute(String operationStr, List<Integer> arguments) throws CalcException {
        int res =0;
        //validate operation
        Operation operation = validOpertion(operationStr, arguments);

        // if operation valid
        switch (operation)
        {
            case plus:
                res = plus(arguments.get(0), arguments.get(1));
                break;
            case minus:
                res = minus(arguments.get(0), arguments.get(1));
                break;
            case times:
                res = times(arguments.get(0), arguments.get(1));
                break;
            case divide:
                res = divide(arguments.get(0), arguments.get(1));
                break;
            case pow:
                res = pow(arguments.get(0), arguments.get(1));
                break;
            case abs:
                res = abs(arguments.get(0));
                break;
            case fact:
                res = fact(arguments.get(0));
                break;
        }


        return res;
    }

    private Operation validOpertion(String operationStr, List<Integer> arguments) throws CalcException{

        Operation operation;
        //check the operation is valid
        try {
            operation = Operation.valueOf(operationStr.toLowerCase());
        }  catch (IllegalArgumentException e) {
            throw new CalcException(ILLEGAL_OPERATION + operationStr);
        }
        //check the number of arguments fit to operation
        switch (operation){
            //expecting for two arguments
            case plus, minus,times, divide, pow:{
                //System.out.println(arguments.size());
                if(arguments.size()>2) {throw new CalcException(TOO_MANY_ARGUMENTS + operationStr);}
                if(arguments.size()<2){ throw new CalcException(NOT_ENOUGH_ARGUMENTS + operationStr);}
                break;
            }
            //expecting for one argument
            case abs,fact:{
                if(arguments.size()>1){ throw new CalcException(TOO_MANY_ARGUMENTS + operationStr);}
                if(arguments.size()<1){ throw new CalcException(NOT_ENOUGH_ARGUMENTS + operationStr);}
                break;
            }

        }

        return operation;
    }

    private int plus(Integer int1, Integer int2) {return int1 + int2; }

    private int minus(Integer int1, Integer int2) {return int1 - int2; }

    private int times(Integer int1, Integer int2){return int1 * int2;}

    private int divide(Integer int1, Integer int2) throws CalcException {
        if (int2 == 0 ) {
            throw new CalcException(ILLEGAL_DIVIDE);
        }

        return int1 / int2;
    }

    private int pow(Integer int1, Integer int2){ return (int) Math.pow(int1, int2);}

    private int abs(Integer int1){ return Math.abs(int1);}

    private int fact(Integer int1) throws CalcException {

        if (int1 < 0 ) {
            throw new CalcException(NEGATIVE_NUMBER);
        }

        int fact = 1;
        for (int i = 2; i <= int1; i++) {
            fact = fact * i;
        }
        return fact;
        }
}
