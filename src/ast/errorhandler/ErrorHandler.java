package ast.errorhandler;

import ast.type.ErrorType;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class ErrorHandler {

    private List<ErrorType> errors;

    private static ErrorHandler handler;

    public ErrorHandler(){

        this.errors = new ArrayList<>();
    }

    public static ErrorHandler getInstance(){

        if(handler==null){
            handler = new ErrorHandler();
        }
        return handler;
    }

    public boolean anyErrors(){

        return !errors.isEmpty();
    }

    public void showErrors(PrintStream out){

        for(ErrorType error: errors){

            out.println(error.toString());
        }
    }

    public void addError(ErrorType error){

        errors.add(error);
    }

    public List<ErrorType> getErrors() {
        return new ArrayList<>(errors);
    }
}
