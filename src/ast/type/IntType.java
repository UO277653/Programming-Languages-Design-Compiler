package ast.type;

import ast.errorhandler.ErrorHandler;
import ast.expression.IntLiteral;
import ast.semantic.Visitor;

public class IntType extends AbstractType implements Type {

    private int size = 2;

    public IntType(int line, int column) {
        super(line, column);
    }

    @Override
    public String toString() {
        return " int ";
    }

    @Override
    public Type arithmetic(int line, int column, Type t) {

        if(t instanceof IntType)
            return this;
        if(t instanceof ErrorType)
            return t;

        return new ErrorType(line, column, String.format("Type %s does not support arithmetic operations", t));
    }

    @Override
    public void assignment(int line, int column, Type t) {

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

        if(!(type instanceof IntType)){
            new ErrorType(line, column, String.format("The return type does not match"));
        }

        return;
    }

    @Override
    public int numberOfBytes() {
        return this.size;
    }

    @Override
    public Type cast(int line, int column, Type t) {

        return t;
    }

    @Override
    public String suffix(){

        return "i";
    }

    @Override
    public String convertTo(Type type){

        if(type instanceof DoubleType){
            return "i2f";
        } if(type instanceof CharType){
            return "i2b";
        }

        return "";

    }

    @Override
    public Type superType(Type type) {
        if(type instanceof DoubleType){
            return type;
        }  else {
            return this;
        }
    }

//    @Override
//    public Type logical(int line, int column, Type t) {
//        if(t instanceof IntType){
//            return new BooleanType(line, column);
//        }
//
//        return new ErrorType(line, column, String.format("The types %s and %s do not support logical operations", this, t));
//    }

    @Override
    public boolean asBoolean(int line, int column) {

        return true;
    }

    @Override
    public Type unaryNot(int line, int column) {
        return this;
    }

    @Override
    public Type modulus(int line, int column, Type t) {
        if (t instanceof IntType){
            return t;
        }

        return new ErrorType(line, column, String.format("The types %s and %s do not support modulus", this, t));
    }

    @Override
    public Type comparison(int line, int column, Type t) {

        if(t instanceof IntType){

            return new BooleanType(line, column);
        }

        return new ErrorType(line, column, String.format("The types %s and %s do not support the comparison operator", this, t));
    }

    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP param) {
        return visitor.visit(this, param);
    }
}
