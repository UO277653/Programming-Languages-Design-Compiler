package ast.expression;

import ast.AbstractASTNode;
import ast.semantic.Visitor;

public class RealLiteral extends AbstractExpression {



    private double value;

    public RealLiteral(int line, int column, double value){

        super(line, column);

        this.value = value;
    }

    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return " " + value;
    }

    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP param) {
        return visitor.visit(this, param);
    }

}
