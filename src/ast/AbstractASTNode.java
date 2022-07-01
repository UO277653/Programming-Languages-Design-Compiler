package ast;

import ast.semantic.Visitor;

public abstract class AbstractASTNode implements ASTNode{

    private int line;
    private int column;

    public AbstractASTNode(int line, int column){
        this.line = line;
        this.column = column;
    }

    @Override
    public int getLine() {
        return this.line;
    }

    @Override
    public int getColumn() {
        return this.column;
    }

    @Override
    public abstract <TP, TR> TR accept(Visitor<TP, TR> visitor, TP param);


}
