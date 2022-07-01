package ast.expression;

import ast.AbstractASTNode;
import ast.semantic.Visitor;
import ast.type.Type;
import introspector.test.ast.ASTNode;

public abstract class AbstractExpression extends AbstractASTNode implements Expression { // LAB07

    private boolean lvalue;

    private Type type;

    public AbstractExpression(int line, int column) {
        super(line, column);
    }

    @Override
    public boolean getLvalue() {
        return this.lvalue;
    }

    @Override
    public void setLvalue(boolean lvalue) {
        this.lvalue = lvalue;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public abstract <TP, TR> TR accept(Visitor<TP, TR> visitor, TP param);

}
