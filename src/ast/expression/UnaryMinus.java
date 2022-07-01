package ast.expression;

import ast.AbstractASTNode;
import ast.semantic.Visitor;

public class UnaryMinus extends AbstractExpression {

    private Expression exp;

    public UnaryMinus(int line, int column, Expression exp) {
        super(line, column);

        this.exp = exp;
    }

    public Expression getExp() {
        return exp;
    }

    @Override
    public String toString() {
        return "-" + exp.toString();
    }

    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP param) {
        return visitor.visit(this, param);
    }
}
