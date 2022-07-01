package ast.program;

import ast.AbstractASTNode;
import ast.semantic.Visitor;
import ast.statement.Statement;

import java.util.ArrayList;
import java.util.List;

public class Program extends AbstractASTNode {

    private List<Definition> defs;

    public Program(int line, int column, List<Definition> defs){

        super(line, column);

        this.defs = new ArrayList<Definition>(defs);
    }

    @Override
    public String toString() {
        return "This is a program with " + defs.size() + " definitions" + '\n' + " Program {" + '\n' + defsToString() + '}';
    }

    private String defsToString(){

        String res = "";

        for(Definition s: defs){
            res += s.toString() + ";" + '\n';
        }

        return res;
    }

    // LAB07
    public List<Definition> getDefinitions(){
        return this.defs;
    }

    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP param) {
        return visitor.visit(this, param);
    }
}
