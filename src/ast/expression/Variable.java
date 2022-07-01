package ast.expression;

import ast.AbstractASTNode;
import ast.program.Definition;
import ast.semantic.Visitor;

public class Variable extends AbstractExpression {

    private String name;

    private Definition definition;

    public Variable(int line, int column, String name) {
        super(line, column);

        this.name = name;
    }

    public Definition getDefinition() {
        return definition;
    }

    public String getName(){
        return this.name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP param) {
        return visitor.visit(this, param);
    }

    public void setDefinition(Definition definition){

        this.definition = definition;
    }
}
