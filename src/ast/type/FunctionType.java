package ast.type;

import ast.errorhandler.ErrorHandler;
import ast.program.VarDefinition;
import ast.semantic.Visitor;

import java.util.ArrayList;
import java.util.List;

public class FunctionType extends AbstractType implements Type {

    private List<VarDefinition> parameters;
    private Type returnType;

    public FunctionType(int line, int column, List<VarDefinition> varD, Type returnType) {
        super(line, column);

        this.parameters = varD;
        this.returnType = returnType;
    }

    @Override
    public String toString() {
        return returnType.toString() + definitionsToString();
    }

    private String definitionsToString() {

        String res = "(";

        if(!parameters.isEmpty()) {
            res += parameters.get(0).toString();

            for (int i = 1; i < parameters.size(); i++) {
                res += "," + parameters.get(i).toString();
            }
        }

        res+=")";

        return res;
    }

    @Override
    public Type parenthesis(int line, int column, List<Type> p) {

        if((p.isEmpty() && !parameters.isEmpty()) || (!p.isEmpty() && parameters.isEmpty())){
            ErrorHandler.getInstance().addError(new ErrorType(line, column, String.format("The types of the arguments for the function do not match")));
        }

        for(int i = 0; i < p.size(); i++){

            if(p.get(i).getClass() != parameters.get(i).getType().getClass()){
                new ErrorType(line, column, String.format("The types of the arguments for the function do not match"));
            }

        }
        return this;
    }

    @Override
    public void write(int line, int column) {

        return;
    }

    public List<VarDefinition> getParameters() {
        return new ArrayList<>(parameters);
    }

    public Type getReturnType() {
        return returnType;
    }

    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP param) {
        return visitor.visit(this, param);
    }
}
