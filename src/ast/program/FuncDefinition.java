package ast.program;

import ast.AbstractASTNode;
import ast.semantic.Visitor;
import ast.statement.Statement;
import ast.type.Type;

import java.util.List;

public class FuncDefinition extends AbstractASTNode implements Definition {

    private List<Statement> body;
    private Type type;
    private String name;
    private int scope;

    private Type returnType;

    public FuncDefinition(int line, int column, Type type, String name, List<Statement> body) {
        super(line, column);

        this.type = type;
        this.name = name;
        this.body = body;
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public String getName() {
        return name;
    }

    public List<Statement> getBody(){
        return this.body;
    }

    public Type getReturnType() {
        return returnType;
    }

    public void setReturnType(Type returnType) {
        this.returnType = returnType;
    }

    @Override
    public String toString() {
        return name + "; " + type.toString() + statementsToString();
    }

    private String statementsToString() {

        String res = " {";

        for (int i = 0; i < body.size(); i++) {
            res += '\n' + "   " + body.get(i).toString() + ";";
        }

        if (!res.isEmpty()) {
            res += '\n' + "}";
        } else{
            res += "}";
        }

        return res;
    }

    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP param) {
        return visitor.visit(this, param);
    }

    @Override
    public int getScope() {
        return scope;
    }

    @Override
    public void setScope(int scope) {
        this.scope = scope;
    }

}
