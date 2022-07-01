package ast.type;

import ast.semantic.Visitor;

public class Integer extends AbstractType implements Type {

    private int value;

    public Integer(int line, int column, int value) {
        super(line, column);

        this.value = value;
    }

    @Override
    public String toString() {
        return " " + value;
    }

    @Override
    public String suffix(){

        return "i";
    }

    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP param) {
        return visitor.visit(this, param);
    }
}
