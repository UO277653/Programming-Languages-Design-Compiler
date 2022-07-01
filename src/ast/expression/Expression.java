package ast.expression;

import ast.ASTNode;
import ast.type.Type;

public interface Expression extends ASTNode {

    // LAB07
    boolean getLvalue(); // Abstract expression?
    void setLvalue(boolean lvalue);

    public Type getType();

    public void setType(Type type);
}
