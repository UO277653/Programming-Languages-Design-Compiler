package ast.expression;

import ast.AbstractASTNode;
import ast.semantic.Visitor;
import ast.statement.Statement;
import ast.type.ErrorType;
import ast.type.RecordField;
import ast.type.Type;

import java.util.ArrayList;
import java.util.List;

public class FunctionInvocation extends AbstractExpression implements Statement {

    private Variable v;
    private List<Expression> arguments;
    private Type returnType;

    public FunctionInvocation(int line, int column, Variable v, List<Expression> arguments) {
        super(line, column);
        this.v = v;
        this.arguments = arguments;
    }

    @Override
    public String toString() {
        return v.toString() + "(" + argumentsToString() + ")";
    }

    private String argumentsToString(){

        String res = " ";

        if(!arguments.isEmpty()) {
            res += arguments.get(0).toString();

            for (int i = 1; i < arguments.size(); i++) {
                res += "," + arguments.get(i).toString();
            }
        }

        return res;
    }

    public Variable getV() {
        return v;
    }

    public List<Expression> getArguments() {
        return arguments;
    }

    @Override
    public Type getReturnType() {
        return returnType;
    }

    @Override
    public void setReturnType(Type returnType) {
        this.returnType = returnType;
    }

    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP param) {
        return visitor.visit(this, param);
    }
}
