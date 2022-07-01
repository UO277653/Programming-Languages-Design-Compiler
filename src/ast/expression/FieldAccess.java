package ast.expression;

import ast.AbstractASTNode;
import ast.semantic.Visitor;

public class FieldAccess extends AbstractExpression {

    private String name;
    private Expression exp;

    public FieldAccess(int line, int column, Expression exp, String name) {
        super(line, column);

        this.name = name;
        this.exp = exp;
    }

    @Override
    public String toString() {
        return exp.toString() + "." + name;
    }

    public Expression getExp() {
        return exp;
    }

    public String getName() {
        return name;
    }

    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP param) {
        return visitor.visit(this, param);
    }
}
