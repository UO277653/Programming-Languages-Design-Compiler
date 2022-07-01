package ast.type;

import ast.semantic.Visitor;

import java.util.List;

public class Array extends AbstractType implements Type {

    private int size;
    private Type type;

    public Array(int line, int column, int size, Type type) {
        super(line, column);

        this.size = size;
        this.type = type;
    }


    public static Array createArray(int line, int column, int size, Type type){

        if(type instanceof Array){

            Array newArray = (Array) type;
            newArray.setType(createArray(line, column, size, newArray.getType()));
            return newArray;
        }

        return new Array(line, column, size, type);
    }

    @Override
    public Type squareBrackets(int line, int column, Type t) {

        if(t instanceof IntType)
            return this.type;
        if(t instanceof ErrorType)
            return t;

        return new ErrorType(line, column, String.format("%s cannot be uses as an index", t));
    }

    @Override
    public Type parenthesis(int line, int column, List<Type> p) {
        return new ErrorType(line, column, String.format("The type %s does not support parenthesis", this));
    }

    public void setType(Type type){
        this.type = type;
    }

    public Type getType(){
        return this.type;
    }

    @Override
    public String toString() {
        return " " + "[" + size + "]" + type.toString();
    }

    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP param) {
        return visitor.visit(this, param);
    }

    @Override
    public int numberOfBytes() {
        return type.numberOfBytes()*size;
    }
}
