package ast.statement;

import ast.AbstractASTNode;
import ast.expression.Expression;
import ast.semantic.Visitor;

import java.util.ArrayList;
import java.util.List;

public class While extends AbstractStatement implements Statement {

    private Expression expression;
    private List<Statement> block;

    public While(int line, int column, Expression expression, List<Statement> block) {
        super(line, column);

        this.expression = expression;
        this.block = block;
    }

    @Override
    public String toString() {

        String res = "while (" + expression.toString() + ")" + statementsToString() ;

        return res;
    }

    private String statementsToString(){

        String res = " {" + '\n';

        if(!block.isEmpty()){
            res += "   " + block.get(0).toString();

            for(int i = 1; i < block.size(); i++){
                res += '\n' + "   " + block.get(i).toString() + ";";
            }
        }

        res += '\n' + "}";

        return res;
    }

    public Expression getExpression() {
        return expression;
    }

    public List<Statement> getBlock() {
        return new ArrayList<>(block);
    }



    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP param) {
        return visitor.visit(this, param);
    }
}
