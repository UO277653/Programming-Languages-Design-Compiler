package ast;

import ast.semantic.Visitor;

public interface ASTNode {

    int getLine();
    int getColumn();

    <TP,TR> TR accept(Visitor<TP,TR> visitor, TP param);
}
