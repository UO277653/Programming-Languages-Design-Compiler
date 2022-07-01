package ast.statement;

import ast.AbstractASTNode;
import ast.expression.Expression;
import ast.semantic.Visitor;

import java.util.List;

public class Read extends AbstractStatement implements Statement {

    private Expression expression;

    public Read(int line, int column, Expression expression) {
        super(line, column);

        this.expression = expression;
    }

    @Override
    public String toString() {
        return "read " + expression.toString() + '\n';
    }

    public Expression getExpression() {
        return expression;
    }

    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP param) {
        return visitor.visit(this, param);
    }
}
