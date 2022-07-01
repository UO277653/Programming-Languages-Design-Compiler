package ast.expression;

import ast.AbstractASTNode;
import ast.semantic.Visitor;

public class Logical extends AbstractExpression {

    private String operator;

    private Expression exp1;
    private Expression exp2;

    public Logical(int line, int column, String operator, Expression exp1, Expression exp2) {
        super(line, column);

        this.operator = operator;
        this.exp1 = exp1;
        this.exp2 = exp2;
    }

    @Override
    public String toString() {
        return exp1 + " " + operator + " " + exp2;
    }

    public Expression getExp1() {
        return exp1;
    }

    public Expression getExp2() {
        return exp2;
    }

    public String getOperator() {
        return operator;
    }

    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP param) {
        return visitor.visit(this, param);
    }
}
