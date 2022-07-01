package ast.type;

import ast.semantic.Visitor;

public class RecordField extends AbstractType implements Type {

    private String name;
    private Type type;

    private int offset;

    public RecordField(int line, int column, String name, Type type) {
        super(line, column);

        this.name = name;
        this.type = type;
    }

    @Override
    public String toString() {
        return " " + type.toString() + " " + name + ";" + '\n';
    }

    public String getName(){ // TO UML
        return this.name;
    }

    public Type getType() {
        return type;
    }

    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP param) {
        return visitor.visit(this, param);
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
