package ast.program;

import ast.AbstractASTNode;
import ast.semantic.Visitor;
import ast.statement.Statement;
import ast.type.Type;

public class VarDefinition extends AbstractASTNode implements Definition, Statement {

    private Type type;
    private String name;
    private int scope;
    private Type returnType;

    private int offset;

    public VarDefinition(int line, int column, Type type, String name) {
        super(line, column);

        this.type = type;
        this.name = name;
    }

    @Override
    public Type getType() {
        return this.type;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return " " + type + " " + name;
    }

    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP param) {
        return visitor.visit(this, param);
    }

    public void setScope(int scope){
        this.scope = scope;
    }

    public int getScope() {
        return this.scope;
    }

    @Override
    public Type getReturnType() {
        return returnType;
    }

    @Override
    public void setReturnType(Type returnType) {
        this.returnType = returnType;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
