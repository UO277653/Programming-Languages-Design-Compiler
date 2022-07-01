package ast.type;

import ast.errorhandler.ErrorHandler;
import ast.expression.CharLiteral;
import ast.semantic.Visitor;

public class CharType extends AbstractType implements Type {

    private int size = 1;

    public CharType(int line, int column) {
        super(line, column);
    }

    @Override
    public String toString() {
        return " char ";
    }

    @Override
    public Type arithmetic(int line, int column, Type t) {

        if(t instanceof CharType)
            return new IntType(line, column);
        if(t instanceof ErrorType)
            return t;

        return new ErrorType(line, column, String.format("Type %s does not support arithmetic operations", t));
    }

    @Override
    public void assignment(int line, int column, Type t) {

        if(t instanceof CharType){
            return;
        }

        new ErrorType(line, column, String.format("The types %s and %s do not support assignment operator", this, t));
    }

    @Override
    public void returnStatement(int line, int column, Type type) {

        if(!(type instanceof CharType)){
            new ErrorType(line, column, String.format("The return type does not match"));
        }

        return;
    }

    @Override
    public String suffix(){

        return "b";
    }

    @Override
    public Type modulus(int line, int column, Type t) {
        if (t instanceof CharType){
            return new IntType(line, column);
        }

        return new ErrorType(line, column, String.format("The types %s and %s do not support modulus", this, t));
    }

    @Override
    public String convertTo(Type type){

        if(type instanceof IntType){
            return "b2i";
        } else if(type instanceof DoubleType){
            return "b2i\n" + "i2f";
        }

        return "";

    }

    @Override
    public Type superType(Type type) {
        if(type instanceof IntType || type instanceof DoubleType){
            return type;
        }  else {
            return this;
        }
    }

    @Override
    public int numberOfBytes() {
        return this.size;
    }

    @Override
    public void write(int line, int column) {

        return;
    }

    @Override
    public Type cast(int line, int column, Type t) {

        if(t instanceof CharType){
            return t;
        }

        return new ErrorType(line, column, String.format("The types %s and %s do not support cast", this, t));
    }

    @Override
    public Type comparison(int line, int column, Type t) {

        if(t instanceof CharType){

            return new BooleanType(line, column);
        }

        return new ErrorType(line, column, String.format("The types %s and %s do not support the comparison operator", this, t));
    }


    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP param) {
        return visitor.visit(this, param);
    }
}
