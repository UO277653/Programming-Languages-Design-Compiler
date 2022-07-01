package ast.type;

import ast.ASTNode;

import java.util.List;

public interface Type extends ASTNode {

    Type arithmetic(int line, int column, Type t); // Add line and column
    Type modulus(int line, int column, Type t);
    Type squareBrackets(int line, int column, Type t);
    Type parenthesis(int line, int column, List<Type> p);
    Type dot(int line, int column, String c);
    boolean asBoolean(int line, int column);

    Type comparison(int line, int column, Type t);
    Type logical(int line, int column, Type t);
    Type cast(int line, int column, Type t);
    Type unaryNot(int line, int column);
    Type unaryMinus(int line, int column);


    void assignment(int line, int column, Type t);
    void write(int line, int column);
    void read(int line, int column);
    void returnStatement(int line, int column, Type t);

    int numberOfBytes();
    String suffix();

    String convertTo(Type type);

    Type superType(Type type);
}
