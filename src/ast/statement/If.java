package ast.statement;

import ast.AbstractASTNode;
import ast.expression.AbstractExpression;
import ast.expression.Expression;
import ast.semantic.Visitor;

import java.util.ArrayList;
import java.util.List;

public class If extends AbstractStatement implements Statement {

    private Expression expression;
    private List<Statement> block;
    private List<Statement> elseBlock;

    public If(int line, int column, List<Statement> block, Expression expression) {
        super(line, column);

        this.block = block;
        this.expression = expression;
    }

    public void setElseBlock(List<Statement> block){

        this.elseBlock = block;
    }

    @Override
    public String toString() {

        String res = "if (" + expression.toString() + ")" + '\n';

        res += statementsToString(block);

        if(!elseBlock.isEmpty()){
            res += "else " + statementsToString(elseBlock);
        }

        return res;
    }

    private String statementsToString(List<Statement> block){

        String res = " {" + '\n';

        if(!block.isEmpty()){

            res+= "   " +block.get(0).toString() + ";";
            for(int i = 1; i < block.size(); i++){
                res += '\n' + "   " + block.get(i).toString() + ";";
            }
        }

        res += '\n' + "}" + '\n';

        return res;
    }

    public Expression getExpression() {
        return expression;
    }

    public List<Statement> getBlock() {
        return new ArrayList<>(block);
    }

    public List<Statement> getElseBlock() {
        if(elseBlock == null){
            return null;
        }
        return new ArrayList<>(elseBlock);
    }

    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP param) {
        return visitor.visit(this, param);
    }
}
