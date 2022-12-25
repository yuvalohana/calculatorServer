package Server.Calculator.services;

import Server.Calculator.exceptions.CalcException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IndependentCalc {
    private final Calculator calculator;

    public IndependentCalc (){
        this.calculator= new Calculator();
    }

    public int operationExecute(int[] arguments, String operationStr) throws CalcException {
        return calculator.operationExecute(operationStr, Arrays.stream(arguments).boxed().collect(Collectors.toList()));
    }
}
