package ast.expression;

import ast.AbstractASTNode;
import ast.semantic.Visitor;

public class Indexing extends AbstractExpression{

    private Expression exp1;
    private Expression exp2;

    public Indexing(int line, int column, Expression exp1, Expression exp2) {
        super(line, column);
        this.exp1 = exp1;
        this.exp2 = exp2;
    }

    @Override
    public String toString() {
        return exp1.toString() + "[" + exp2.toString() + "]";
    }

    public Expression getExp1() {
        return exp1;
    }

    public Expression getExp2() {
        return exp2;
    }

    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP param) {
        return visitor.visit(this, param);
    }
}
