package ast.statement;

import ast.AbstractASTNode;
import ast.expression.Expression;
import ast.semantic.Visitor;

public class Return extends AbstractStatement implements Statement {

    private Expression expression;

    public Return(int line, int column, Expression expression) {
        super(line, column);

        this.expression = expression;
    }

    @Override
    public String toString() {
        return "return " + expression.toString();
    }

    public Expression getExpression() {
        return expression;
    }

    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP param) {
        return visitor.visit(this, param);
    }
}
