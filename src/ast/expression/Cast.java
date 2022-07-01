package ast.expression;

import ast.semantic.Visitor;
import ast.type.Type;

public class Cast extends AbstractExpression {

    private Expression exp;
    private Type type;

    public Cast(int line, int column, Expression exp, Type type) {
        super(line, column);

        this.exp = exp;
        this.type = type;
    }

    @Override
    public String toString() {
        return "(" + type.toString() + ") " + exp.toString();
    }

    public Expression getExp() {
        return exp;
    }

    public Type getType() {
        return type;
    }

    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP param) {
        return visitor.visit(this, param);
    }
}
