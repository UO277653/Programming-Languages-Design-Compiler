package ast.codegeneration;

import ast.errorhandler.ErrorHandler;
import ast.expression.*;
import ast.program.FuncDefinition;
import ast.program.Program;
import ast.program.VarDefinition;
import ast.semantic.Visitor;
import ast.statement.*;
import ast.type.*;
import ast.type.Integer;
import ast.type.Record;

public class AbstractCGVisitor<TP, TR> implements Visitor<TP, TR> {

    @Override
    public TR visit(Program program, TP param){

        assert false: "Undefined template visitX for the code function " + this.getClass().getName();

        return null;
    }

    @Override
    public TR visit(Comparison comparison, TP param) {

        assert false: "Undefined template visitX for the code function " + this.getClass().getName();

        return null;
    }

    @Override
    public TR visit(Logical logical, TP param) {

        assert false: "Undefined template visitX for the code function " + this.getClass().getName();

        return null;
    }

    @Override
    public TR visit(Indexing indexing, TP param) {

        assert false: "Undefined template visitX for the code function " + this.getClass().getName();

        return null;
    }

    @Override
    public TR visit(FieldAccess fieldAccess, TP param) {

        assert false: "Undefined template visitX for the code function " + this.getClass().getName();

        return null;
    }

    @Override
    public TR visit(Cast cast, TP param) {



        assert false: "Undefined template visitX for the code function " + this.getClass().getName();

        return null;
    }

    @Override
    public TR visit(UnaryNot unaryNot, TP param) {

        assert false: "Undefined template visitX for the code function " + this.getClass().getName();

        return null;
    }

    @Override
    public TR visit(UnaryMinus unaryMinus, TP param) {

        assert false: "Undefined template visitX for the code function " + this.getClass().getName();

        return null;
    }

    @Override
    public TR visit(Arithmetic arithmetic, TP param) {

        assert false: "Undefined template visitX for the code function " + this.getClass().getName();

        return null;
    }

    @Override
    public TR visit(Modulus modulus, TP param) {
        assert false: "Undefined template visitX for the code function " + this.getClass().getName();

        return null;
    }

    @Override
    public TR visit(Variable variable, TP param) {
        assert false: "Undefined template visitX for the code function " + this.getClass().getName();

        return null;
    }

    @Override
    public TR visit(IntLiteral intLiteral, TP param) {
        assert false: "Undefined template visitX for the code function " + this.getClass().getName();

        return null;
    }

    @Override
    public TR visit(CharLiteral charLiteral, TP param) {
        assert false: "Undefined template visitX for the code function " + this.getClass().getName();

        return null;
    }

    @Override
    public TR visit(RealLiteral realLiteral, TP param) {

        assert false: "Undefined template visitX for the code function " + this.getClass().getName();

        return null;
    }

    @Override
    public TR visit(FunctionInvocation functionInvocation, TP param) {

        assert false: "Undefined template visitX for the code function " + this.getClass().getName();

        return null;
    }

    @Override
    public TR visit(VarDefinition varDefinition, TP param) {

        assert false: "Undefined template visitX for the code function " + this.getClass().getName();

        return null;
    }

    @Override
    public TR visit(FuncDefinition funcDefinition, TP param) {

        assert false: "Undefined template visitX for the code function " + this.getClass().getName();

        return null;
    }

    @Override
    public TR visit(If ifStatement, TP param) {

        assert false: "Undefined template visitX for the code function " + this.getClass().getName();

        return null;
    }

    @Override
    public TR visit(While whileStatement, TP param) {

        assert false: "Undefined template visitX for the code function " + this.getClass().getName();

        return null;
    }

    @Override
    public TR visit(Return returnStatement, TP param) {

        assert false: "Undefined template visitX for the code function " + this.getClass().getName();

        return null;
    }

    @Override
    public TR visit(Assignment assignment, TP param) {

        assert false: "Undefined template visitX for the code function " + this.getClass().getName();

        return null;
    }

    @Override
    public TR visit(Write write, TP param) {

        assert false: "Undefined template visitX for the code function " + this.getClass().getName();

        return null;
    }

    @Override
    public TR visit(Read read, TP param) {

        assert false: "Undefined template visitX for the code function " + this.getClass().getName();

        return null;
    }

    @Override
    public TR visit(FunctionType functionType, TP param) {

        assert false: "Undefined template visitX for the code function " + this.getClass().getName();

        return null;
    }

    @Override
    public TR visit(Array array, TP param) {

        assert false: "Undefined template visitX for the code function " + this.getClass().getName();

        return null;
    }

    @Override
    public TR visit(Record record, TP param) {

        assert false: "Undefined template visitX for the code function " + this.getClass().getName();

        return null;
    }

    @Override
    public TR visit(RecordField recordField, TP param) {

        assert false: "Undefined template visitX for the code function " + this.getClass().getName();

        return null;
    }

    @Override
    public TR visit(Integer integer, TP param) {
        assert false: "Undefined template visitX for the code function " + this.getClass().getName();

        return null;
    }

    @Override
    public TR visit(IntType intType, TP param) {

        assert false: "Undefined template visitX for the code function " + this.getClass().getName();

        return null;
    }

    @Override
    public TR visit(CharType charType, TP param) {

        assert false: "Undefined template visitX for the code function " + this.getClass().getName();

        return null;
    }

    @Override
    public TR visit(DoubleType doubleType, TP param) {

        assert false: "Undefined template visitX for the code function " + this.getClass().getName();

        return null;
    }

    @Override
    public TR visit(VoidType voidType, TP param) {

        assert false: "Undefined template visitX for the code function " + this.getClass().getName();

        return null;
    }

    @Override
    public TR visit(ErrorType errorType, TP param) {

        assert false: "Undefined template visitX for the code function " + this.getClass().getName();

        return null;
    }

    @Override
    public TR visit(ErrorHandler errorHandler, TP param) {

        assert false: "Undefined template visitX for the code function " + this.getClass().getName();

        return null;
    }

    @Override
    public TR visit(BooleanType booleanType, TP param) {

        assert false: "Undefined template visitX for the code function " + this.getClass().getName();

        return null;
    }

    @Override
    public TR visit(BooleanExpression booleanExpression, TP param) {

        assert false: "Undefined template visitX for the code function " + this.getClass().getName();

        return null;
    }
}
