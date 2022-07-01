package ast.statement;

import ast.ASTNode;
import ast.type.Type;

public interface Statement extends ASTNode {

    Type getReturnType();
    void setReturnType(Type returnType);
}
