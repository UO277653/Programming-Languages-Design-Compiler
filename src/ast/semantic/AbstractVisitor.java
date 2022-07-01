package ast.semantic;

import ast.errorhandler.ErrorHandler;
import ast.expression.*;
import ast.program.FuncDefinition;
import ast.program.Program;
import ast.program.VarDefinition;
import ast.statement.*;
import ast.type.Integer;
import ast.type.Record;
import ast.type.*;

public class AbstractVisitor<TP, TR> implements Visitor<TP, TR> { // LAB07

    @Override
    public TR visit(Program program, TP param){

        program.getDefinitions().forEach(def -> def.accept(this, null));

        return null;
    }

    @Override
    public TR visit(Comparison comparison, TP param) {

        comparison.getExp1().accept(this, null);
        comparison.getExp2().accept(this, null);

        return null;
    }

    @Override
    public TR visit(Logical logical, TP param) {

        logical.getExp1().accept(this, null);
        logical.getExp2().accept(this, null);

        return null;
    }

    @Override
    public TR visit(Indexing indexing, TP param) {

        indexing.getExp1().accept(this, null);
        indexing.getExp2().accept(this, null);

        return null;
    }

    @Override
    public TR visit(FieldAccess fieldAccess, TP param) {

        fieldAccess.getExp().accept(this, null);

        return null;
    }

    @Override
    public TR visit(Cast cast, TP param) {

        cast.getExp().accept(this, null);
        cast.getType().accept(this, null);

        return null;
    }

    @Override
    public TR visit(UnaryNot unaryNot, TP param) {
        unaryNot.getExp().accept(this, null);
        return null;
    }

    @Override
    public TR visit(UnaryMinus unaryMinus, TP param) {
        unaryMinus.getExp().accept(this, null);
        return null;
    }

    @Override
    public TR visit(Arithmetic arithmetic, TP param) {

        arithmetic.getExp1().accept(this, null);
        arithmetic.getExp2().accept(this, null);

        return null;
    }

    @Override
    public TR visit(Modulus modulus, TP param) {

        modulus.getExp1().accept(this, null);
        modulus.getExp2().accept(this, null);

        return null;
    }

    @Override
    public TR visit(Variable variable, TP param) {
        return null;
    }

    @Override
    public TR visit(IntLiteral intLiteral, TP param) {
        return null;
    }

    @Override
    public TR visit(CharLiteral charLiteral, TP param) {
        return null;
    }

    @Override
    public TR visit(RealLiteral realLiteral, TP param) {
        return null;
    }

    @Override
    public TR visit(FunctionInvocation functionInvocation, TP param) {

        functionInvocation.getV().accept(this, null);
        functionInvocation.getArguments().forEach(arg -> arg.accept(this, null));

        return null;
    }

    @Override
    public TR visit(VarDefinition varDefinition, TP param) {

        varDefinition.getType().accept(this, null);

        return null;
    }

    @Override
    public TR visit(FuncDefinition funcDefinition, TP param) {

        funcDefinition.getType().accept(this, null);

        funcDefinition.getBody().forEach(statement -> statement.accept(this, null));

        return null;
    }

    @Override
    public TR visit(If ifStatement, TP param) {

        ifStatement.getExpression().accept(this, null);
        ifStatement.getBlock().forEach(statement -> statement.accept(this, null));

        if(ifStatement.getElseBlock() != null) {
            ifStatement.getElseBlock().forEach(statement -> statement.accept(this, null));
        }

        return null;
    }

    @Override
    public TR visit(While whileStatement, TP param) {

        whileStatement.getExpression().accept(this, null);
        whileStatement.getBlock().forEach(statement -> statement.accept(this, null));

        return null;
    }

    @Override
    public TR visit(Return returnStatement, TP param) {

        returnStatement.getExpression().accept(this, null);

        return null;
    }

    @Override
    public TR visit(Assignment assignment, TP param) {

        assignment.getLHS().accept(this, null);
        assignment.getRHS().accept(this, null);

        return null;
    }

    @Override
    public TR visit(Write write, TP param) {

        write.getExpression().accept(this, null);

        return null;
    }

    @Override
    public TR visit(Read read, TP param) {

        read.getExpression().accept(this, null);

        return null;
    }

    @Override
    public TR visit(FunctionType functionType, TP param) {

        functionType.getReturnType().accept(this, null);
        functionType.getParameters().forEach(parameter -> parameter.accept(this, null));

        return null;
    }

    @Override
    public TR visit(Array array, TP param) {

        array.getType().accept(this, null);

        return null;
    }

    @Override
    public TR visit(Record record, TP param) {

        record.getFields().forEach(field -> field.accept(this, null));

        return null;
    }

    @Override
    public TR visit(RecordField recordField, TP param) {

        recordField.getType().accept(this, null);
        return null;
    }

    @Override
    public TR visit(Integer integer, TP param) {
        return null;
    }

    @Override
    public TR visit(IntType intType, TP param) {
        return null;
    }

    @Override
    public TR visit(CharType charType, TP param) {
        return null;
    }

    @Override
    public TR visit(DoubleType doubleType, TP param) {
        return null;
    }

    @Override
    public TR visit(VoidType voidType, TP param) {
        return null;
    }

    @Override
    public TR visit(ErrorType errorType, TP param) {

        return null;
    }

    @Override
    public TR visit(ErrorHandler errorHandler, TP param) {

        errorHandler.getErrors().forEach(error -> error.accept(this, null));
        return null;
    }

    @Override
    public TR visit(BooleanType booleanType, TP param) {

        return null;
    }

    @Override
    public TR visit(BooleanExpression booleanExpression, TP param) {

        return null;
    }

    // One visit per concrete class, just traverse the children


}
