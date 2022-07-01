package ast.expression;

import ast.semantic.Visitor;

public class BooleanExpression extends AbstractExpression {

    private String value;

    public BooleanExpression(int line, int column, String value) {
        super(line, column);

        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "" + value;
    }

    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP param) {
        return visitor.visit(this, null);
    }
}
