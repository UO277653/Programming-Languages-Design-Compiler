package ast.semantic;


import ast.errorhandler.ErrorHandler;
import ast.program.FuncDefinition;
import ast.program.Program;
import ast.expression.*;
import ast.program.VarDefinition;
import ast.statement.*;
import ast.type.*;
import ast.type.Integer;
import ast.type.Record;

public interface Visitor<TP,TR>{

    TR visit(Program program, TP param);
    // as many visit as concrete classes

    TR visit(Comparison comparison, TP param);
    TR visit(Logical logical, TP param);
    TR visit(Indexing indexing, TP param);
    TR visit(FieldAccess fieldAccess, TP param);
    TR visit(Cast cast, TP param);
    TR visit(UnaryNot unaryNot, TP param);
    TR visit(UnaryMinus unaryMinus, TP param);
    TR visit(Arithmetic arithmetic, TP param);
    TR visit(Modulus modulus, TP param);
    TR visit(Variable variable, TP param);
    TR visit(IntLiteral intLiteral, TP param);
    TR visit(CharLiteral charLiteral, TP param);
    TR visit(RealLiteral realLiteral, TP param);
    TR visit(FunctionInvocation functionInvocation, TP param);

    TR visit(VarDefinition varDefinition, TP param);
    TR visit(FuncDefinition funcDefinition, TP param);

    TR visit(If ifStatement, TP param);
    TR visit(While whileStatement, TP param);
    TR visit(Return returnStatement, TP param);
    TR visit(Assignment assignment, TP param);
    TR visit(Write write, TP param);
    TR visit(Read read, TP param);

    TR visit(FunctionType functionType, TP param);
    TR visit(Array array, TP param);
    TR visit(Record record, TP param);
    TR visit(RecordField recordField, TP param);
    TR visit(Integer integer, TP param);
    TR visit(IntType intType, TP param);
    TR visit(CharType charType, TP param);
    TR visit(DoubleType doubleType, TP param);
    TR visit(VoidType voidType, TP param);
    TR visit(ErrorType errorType, TP param);
    TR visit(ErrorHandler errorHandler, TP param);

    TR visit(BooleanType booleanType, TP param);

    TR visit(BooleanExpression booleanExpression, TP param);
}
