package ast.semantic;

import ast.errorhandler.ErrorHandler;
import ast.expression.Variable;
import ast.program.FuncDefinition;
import ast.program.VarDefinition;
import ast.symboltable.SymbolTable;
import ast.type.ErrorType;

public class IdentificationVisitor extends AbstractVisitor<Void, Void> {

    private SymbolTable symbolTable = new SymbolTable();

    @Override
    public Void visit(Variable variable, Void param) {

        super.visit(variable, param);

        // Error if variable has not been found
        if(symbolTable.find(variable.getName()) == null){
            ErrorHandler.getInstance().addError(new ErrorType(variable.getLine(),
                    variable.getColumn(), "The variable " + variable.getName() + " has not been defined"));
        }

        variable.setDefinition(symbolTable.find(variable.getName()));

        return null;
    }

    @Override
    public Void visit(VarDefinition varDefinition, Void param) {

        super.visit(varDefinition, param);

        if(!symbolTable.insert(varDefinition)){
            ErrorHandler.getInstance().addError(new ErrorType(varDefinition.getLine(),
                    varDefinition.getColumn(), "The variable " + varDefinition.getName() + " has already been defined in the current scope"));

        }

        return null;
    }

    @Override
    public Void visit(FuncDefinition funcDefinition, Void param) {

        if(!symbolTable.insert(funcDefinition)){
            ErrorHandler.getInstance().addError(new ErrorType(funcDefinition.getLine(),
                    funcDefinition.getColumn(), "The function " + funcDefinition.getName() + " has already been defined in the current scope"));
        }

        symbolTable.set();

        super.visit(funcDefinition, param);

        symbolTable.reset();

        return null;
    }

}
