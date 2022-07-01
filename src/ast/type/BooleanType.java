package ast.type;

import ast.errorhandler.ErrorHandler;
import ast.semantic.Visitor;

public class BooleanType extends AbstractType implements Type {

    private int size = 2;

    public BooleanType(int line, int column) {
        super(line, column);

    }

    @Override
    public String toString() {
        return " boolean ";
    }

    @Override
    public Type logical(int line, int column, Type t) {
        if(t instanceof BooleanType){
            return new BooleanType(line, column);
        }

        return new ErrorType(line, column, String.format("The types %s and %s do not support logical operations", this, t));
    }

    @Override
    public void assignment(int line, int column, Type t) {

        if(t instanceof BooleanType){
            return;
        }

        if(!(t instanceof ErrorType)) {
            new ErrorType(line, column, String.format("The types %s and %s do not support assignment operator", this, t));
        }
    }

    @Override
    public void write(int line, int column) {

        return;
    }

    @Override
    public boolean asBoolean(int line, int column) {

        return true;
    }

    @Override
    public Type unaryNot(int line, int column) {
        return this;
    }

    @Override
    public void returnStatement(int line, int column, Type type) {

        if(!(type instanceof BooleanType)){
            new ErrorType(line, column, String.format("The return type does not match"));
        }

        return;
    }

    @Override
    public int numberOfBytes() {
        return this.size;
    }

    @Override
    public String suffix(){

        return "i";
    }

    @Override
    public Type superType(Type type) {

        return this;
    }

    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP param) {
        return visitor.visit(this, null);
    }
}
