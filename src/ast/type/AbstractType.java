package ast.type;

import ast.AbstractASTNode;
import ast.errorhandler.ErrorHandler;
import ast.type.ErrorType;
import ast.type.Type;

import java.util.List;

public abstract class AbstractType extends AbstractASTNode implements Type {

    public AbstractType(int line, int column) {
        super(line, column);
    }

    // Default behaviour

    @Override
    public Type arithmetic(int line, int column, Type t) {
        return new ErrorType(line, column, String.format("The types %s and %s do not support arithmetic operations", this, t));
    }

    @Override
    public Type modulus(int line, int column, Type t) {
        return new ErrorType(line, column, String.format("The types %s and %s do not support modulus operation", this, t));
    }

    @Override
    public Type squareBrackets(int line, int column, Type t) {
        return new ErrorType(line, column, String.format("The types %s and %s do not support square brackets", this, t));
    }

    @Override
    public Type parenthesis(int line, int column, List<Type> p) {
        return new ErrorType(line, column, String.format("The type %s does not support parenthesis", this));
    }

    @Override
    public Type dot(int line, int column, String c) {
        return new ErrorType(line, column, String.format("The type %s does not support dot operator", this));
    }

    @Override
    public boolean asBoolean(int line, int column) {

        new ErrorType(line, column, String.format("The type is not valid for boolean operations"));

        return false;
    }

    @Override
    public Type comparison(int line, int column, Type t) {
        return new ErrorType(line, column, String.format("The types %s and %s do not support the comparison operator", this, t));
    }

    @Override
    public Type logical(int line, int column, Type t) {
        return new ErrorType(line, column, String.format("The types %s and %s do not support logical operations", this, t));
    }

    @Override
    public Type cast(int line, int column, Type t) {
        return new ErrorType(line, column, String.format("The types %s and %s do not support cast", this, t));
    }

    @Override
    public Type unaryNot(int line, int column) {
        return new ErrorType(line, column, String.format("The type %s does not support unary not operator", this));
    }

    @Override
    public Type unaryMinus(int line, int column) {
        return new ErrorType(line, column, String.format("The type %s does not support unary minus operator", this));
    }

    @Override
    public void assignment(int line, int column, Type t) {
        new ErrorType(line, column, String.format("The types %s and %s do not support assignment operator", this, t));
    }

    @Override
    public void write(int line, int column) {
        new ErrorType(line, column, String.format("The type %s does not support write operator", this));
    }

    @Override
    public void read(int line, int column) {
        new ErrorType(line, column, String.format("The type %s does not support read operator", this));
    }

    @Override
    public void returnStatement(int line, int column, Type type) {
        new ErrorType(line, column, String.format("The type %s does not support return operator", this));
    }

    @Override
    public int numberOfBytes() {
        // Error?
        return 0;
    }

    @Override
    public String suffix(){

        return "";
    }

    @Override
    public String convertTo(Type type){

        return "";
    }

    @Override
    public Type superType(Type type) {
        return null;
    }

}
