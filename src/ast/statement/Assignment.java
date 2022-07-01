package ast.statement;

import ast.AbstractASTNode;
import ast.expression.Expression;
import ast.semantic.Visitor;

public class Assignment extends AbstractStatement implements Statement {

    private Expression exp1;
    private Expression exp2;

    public Assignment(int line, int column, Expression exp1, Expression exp2) {
        super(line, column);

        this.exp1 = exp1;
        this.exp2 = exp2;
    }

    @Override
    public String toString() {
        return " " + exp1.toString() + " = " + exp2.toString();
    }


    // LAB07
    public Expression getLHS(){
        return this.exp1;
    }

    public Expression getRHS(){
        return this.exp2;
    }

    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP param) {
        return visitor.visit(this, param);
    }
}
