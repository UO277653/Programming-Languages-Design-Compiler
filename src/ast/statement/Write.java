package ast.statement;

import ast.AbstractASTNode;
import ast.expression.Expression;
import ast.semantic.Visitor;

import java.util.List;

public class Write extends AbstractStatement implements Statement {

    private Expression expression;

    public Write(int line, int column, Expression expression) {
        super(line, column);

        this.expression = expression;
    }

    @Override
    public String toString() {
        return "write " + expression.toString();
    }

    public Expression getExpression() {
        return expression;
    }

    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP param) {
        return visitor.visit(this, param);
    }
}
