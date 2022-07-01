package ast.type;

import ast.errorhandler.ErrorHandler;
import ast.semantic.Visitor;

public class DoubleType extends AbstractType implements Type {

    private int size = 4;

    public DoubleType(int line, int column) {
        super(line, column);
    }

    @Override
    public String toString() {
        return " real ";
    }

    @Override
    public Type arithmetic(int line, int column, Type t) {

        if(t instanceof DoubleType)
            return this;
        if(t instanceof IntType)
            return this;
        if(t instanceof ErrorType)
            return t;

        return new ErrorType(line, column, String.format("Type %s does not support arithmetic operations", t));
    }

    @Override
    public void assignment(int line, int column, Type t) {

        if(t instanceof DoubleType){
            return;
        }

        if(t instanceof IntType){
            return;
        }

        new ErrorType(line, column, String.format("The types %s and %s do not support assignment operator", this, t));
    }

    @Override
    public void read(int line, int column) {

        return;
    }

    @Override
    public void write(int line, int column) {

        return;
    }

    @Override
    public void returnStatement(int line, int column, Type type) {

        if(!(type instanceof DoubleType)){
            new ErrorType(line, column, String.format("The return type does not match"));
        }

        return;
    }

    @Override
    public Type comparison(int line, int column, Type t) {

        if(t instanceof DoubleType){

            return new BooleanType(line, column);
        }

        return new ErrorType(line, column, String.format("The types %s and %s do not support the comparison operator", this, t));
    }

    @Override
    public Type superType(Type type) {
        return this;
    }

    @Override
    public int numberOfBytes() {
        return this.size;
    }

    @Override
    public String suffix(){

        return "f";
    }

    @Override
    public Type cast(int line, int column, Type t) {

        if(t instanceof DoubleType){
            return t;
        }

        return new ErrorType(line, column, String.format("The types %s and %s do not support cast", this, t));
    }

    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP param) {
        return visitor.visit(this, param);
    }
}
