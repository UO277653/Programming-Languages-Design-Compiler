package ast.type;

import ast.errorhandler.ErrorHandler;
import ast.semantic.Visitor;

public class ErrorType extends AbstractType implements Type {

    private String message;

    public ErrorType(int line, int column, String message) {
        super(line, column);

        this.message = message;

        ErrorHandler.getInstance().addError(this);
    }

    @Override
    public void read(int line, int column) {

        ErrorHandler.getInstance().addError(this);

        return;
    }

    @Override
    public void write(int line, int column) {

        ErrorHandler.getInstance().addError(this);

        return;
    }

    @Override
    public void assignment(int line, int column, Type t) {
        ErrorHandler.getInstance().addError(this);

        return;
    }

    @Override
    public String toString() {
        return "Line: " + getLine() + " Column: " + getColumn() + " Message: " + message;
    }

    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP param) {
        return visitor.visit(this, param);
    }
}
