package ast.semantic;

import ast.errorhandler.ErrorHandler;
import ast.expression.*;
import ast.program.FuncDefinition;
import ast.statement.*;
import ast.type.*;

import java.util.List;

public class TypeCheckingVisitor extends AbstractVisitor<Void, Void> { // LAB07

    // Annotate expressions, everything else in default (assignment and read throw errors)

    @Override
    public Void visit(Comparison comparison, Void param) {

        comparison.getExp1().accept(this, null);
        comparison.getExp2().accept(this, null);

        comparison.setLvalue(false);

        comparison.setType(comparison.getExp1().getType()
                .comparison(comparison.getLine(), comparison.getColumn(), comparison.getExp2().getType()));

        return null;
    }

    @Override
    public Void visit(Logical logical, Void param) {

        logical.getExp1().accept(this, null);
        logical.getExp2().accept(this, null);

        logical.setLvalue(false);

        if(!(logical.getExp1().getType() instanceof BooleanType && logical.getExp2().getType() instanceof BooleanType)){

            new ErrorType(logical.getLine(), logical.getColumn(), "The types of the expressions in a logical operation have to be boolean");
        }

        if (!(logical.getExp1().getType()
                .logical(logical.getLine(), logical.getColumn(), logical.getExp2().getType()) instanceof BooleanType)) {
            new ErrorType(logical.getLine(), logical.getColumn(), "The type returned by a logical operation has to be boolean");
        }

        logical.setType(logical.getExp1().getType()
                .logical(logical.getLine(), logical.getColumn(), logical.getExp2().getType()));


        return null;
    }

    @Override
    public Void visit(Indexing indexing, Void param) {

        indexing.getExp1().accept(this, null);
        indexing.getExp2().accept(this, null);

        indexing.setLvalue(true);

        indexing.setType(indexing.getExp1().getType()
                .squareBrackets(indexing.getLine(), indexing.getColumn(), indexing.getExp2().getType()));

        return null;
    }

    @Override
    public Void visit(FieldAccess fieldAccess, Void param) {

        fieldAccess.getExp().accept(this, null);

        fieldAccess.setLvalue(true);

        fieldAccess.setType(fieldAccess.getExp().getType()
                .dot(fieldAccess.getLine(), fieldAccess.getColumn(), fieldAccess.getName()));

        return null;
    }

    @Override
    public Void visit(Modulus modulus, Void param) {

        modulus.getExp1().accept(this, null);
        modulus.getExp2().accept(this, null);

        modulus.setLvalue(false);

        modulus.setType(modulus.getExp1().getType().modulus(modulus.getLine(),
                modulus.getColumn(), modulus.getExp2().getType()));

        return null;
    }

    @Override
    public Void visit(Cast cast, Void param) {

        cast.getExp().accept(this, null);
        cast.getType().accept(this, null);

        cast.setLvalue(false);

        cast.setType(cast.getExp().getType()
                .cast(cast.getLine(), cast.getColumn(), cast.getExp().getType()));

        return null;
    }

    @Override
    public Void visit(UnaryNot unaryNot, Void param) {

        unaryNot.getExp().accept(this, null);
        unaryNot.setLvalue(false);

        if(!(unaryNot.getExp().getType().asBoolean(unaryNot.getLine(), unaryNot.getColumn()))){
            ErrorType error = new ErrorType(unaryNot.getExp().getLine(), unaryNot.getExp().getColumn()
                    , "The expression " + unaryNot.getExp().toString() + " is not valid for " +
                    "boolean operations");

            unaryNot.setType(error);

        } else {
            unaryNot.setType(unaryNot.getExp().getType()
                    .unaryNot(unaryNot.getLine(), unaryNot.getColumn()));
        }

        return null;
    }

    @Override
    public Void visit(UnaryMinus unaryMinus, Void param) {

        unaryMinus.getExp().accept(this, null);
        unaryMinus.setLvalue(false);

        unaryMinus.setType(unaryMinus.getExp().getType()
                .unaryMinus(unaryMinus.getLine(), unaryMinus.getColumn()));

        return null;
    }

    @Override
    public Void visit(Arithmetic arithmetic, Void param) {

        arithmetic.getExp1().accept(this, null);
        arithmetic.getExp2().accept(this, null);

        arithmetic.setLvalue(false);

        arithmetic.setType(arithmetic.getExp1().getType()
                .arithmetic(arithmetic.getLine(),arithmetic.getColumn(), arithmetic.getExp2().getType()));

        return null;
    }

    @Override
    public Void visit(Variable variable, Void param){

        variable.setLvalue(true);

        if(variable.getDefinition().getType() == null){
            variable.setType
                    (new ErrorType(variable.getLine(), variable.getColumn(), "The variable " + variable.getName() + " does not have a type"));
        } else {
            variable.setType(variable.getDefinition().getType());
        }

        return null;
    }

    @Override
    public Void visit(IntLiteral intLiteral, Void param) {

        intLiteral.setLvalue(false);

        intLiteral.setType(new IntType(intLiteral.getLine(), intLiteral.getColumn()));

        return null;
    }

    // EXAM
    @Override
    public Void visit(BooleanExpression booleanExpression, Void param) {

        booleanExpression.setLvalue(false);

        booleanExpression.setType(new BooleanType(booleanExpression.getLine(), booleanExpression.getColumn()));

        return null;
    }

    @Override
    public Void visit(CharLiteral charLiteral, Void param) {

        charLiteral.setLvalue(false);

        charLiteral.setType(new CharType(charLiteral.getLine(), charLiteral.getColumn()));

        return null;
    }

    @Override
    public Void visit(RealLiteral realLiteral, Void param) {

        realLiteral.setLvalue(false);

        realLiteral.setType(new DoubleType(realLiteral.getLine(), realLiteral.getColumn()));

        return null;
    }

    @Override
    public Void visit(FunctionInvocation functionInvocation, Void param) {

        functionInvocation.getV().accept(this, null);
        functionInvocation.getArguments().forEach(arg -> arg.accept(this, null));

        functionInvocation.setLvalue(false);

        List<Type> argTypes = functionInvocation.getArguments().stream().map( exp -> exp.getType()).toList();

        functionInvocation.setType(functionInvocation.getV().getType()
                .parenthesis(functionInvocation.getLine(), functionInvocation.getColumn(), argTypes));

        return null;
    }

    /*
        super.visit(assignment, null); Alternative, but has drawbacks
        Assignment is not an expression
     */
    @Override
    public Void visit(Assignment assignment, Void param){
        assignment.getLHS().accept(this, null);
        assignment.getRHS().accept(this, null);

        if(!assignment.getLHS().getLvalue()){
            ErrorHandler.getInstance().addError
                    (new ErrorType(assignment.getLHS().getLine(), assignment.getLHS().getColumn(), "L-value required."));
        }

        assignment.getLHS().getType()
                .assignment(assignment.getLine(), assignment.getColumn(), assignment.getRHS().getType());

        return null;
    }

    @Override
    public Void visit(Read read, Void param) {

        read.getExpression().accept(this, null);

        if(!read.getExpression().getLvalue()){
            ErrorHandler.getInstance().addError
                    (new ErrorType(read.getExpression().getLine(), read.getExpression().getColumn(), "L-value required."));
        } else {
            read.getExpression().getType()
                    .read(read.getLine(), read.getColumn());
        }

        return null;
    }

    @Override
    public Void visit(Write write, Void param) {

        write.getExpression().accept(this, null);

        write.getExpression().getType()
                .write(write.getLine(), write.getColumn());

        return null;
    }

    @Override
    public Void visit(While whileStatement, Void param) {

        whileStatement.getExpression().accept(this, null);
        whileStatement.getBlock().forEach(statement -> statement.accept(this, null));

        //if(!(whileStatement.getExpression().getType().asBoolean(whileStatement.getLine(), whileStatement.getColumn()))){
        if(!(whileStatement.getExpression().getType() instanceof BooleanType)){
            new ErrorType(whileStatement.getExpression().getLine(), whileStatement.getExpression().getColumn()
                            , "The expression " + whileStatement.getExpression().toString() + " is not valid for " +
                            "boolean operations");
        } else {

            //whileStatement.getExpression().getType()
            //        .asBoolean(whileStatement.getLine(), whileStatement.getColumn());

            whileStatement.getBlock().stream()
                    .forEach(statement -> statement.setReturnType(whileStatement.getReturnType()));
        }

        return null;
    }

    @Override
    public Void visit(FuncDefinition funcDefinition, Void param) {

        funcDefinition.getType().accept(this, null);
        Type returnType = ((FunctionType) funcDefinition.getType()).getReturnType();

        funcDefinition.setReturnType(returnType);

        funcDefinition.getBody().stream().forEach( statement -> statement.setReturnType(returnType));

        funcDefinition.getBody().forEach(statement -> statement.accept(this, null));
        return null;
    }

    @Override
    public Void visit(Return returnStatement, Void param) {

        returnStatement.getExpression().accept(this, null);

        returnStatement.getExpression().getType()
                .returnStatement(returnStatement.getLine(), returnStatement.getColumn(), returnStatement.getReturnType());

        return null;
    }

    @Override
    public Void visit(If ifStatement, Void param) {

        ifStatement.getExpression().accept(this, null);
        ifStatement.getBlock().forEach(statement -> statement.accept(this, null));

        if(ifStatement.getElseBlock() != null) {
            ifStatement.getElseBlock().forEach(statement -> statement.accept(this, null));
        }

        //if(!(ifStatement.getExpression().getType().asBoolean(ifStatement.getLine(), ifStatement.getColumn()))){
        if(!(ifStatement.getExpression().getType() instanceof BooleanType)){
            new ErrorType(ifStatement.getExpression().getLine(), ifStatement.getExpression().getColumn()
                            , "The expression " + ifStatement.getExpression().toString() + " is not valid for " +
                            "boolean operations");
        } else {

            //ifStatement.getExpression().getType()
            //        .asBoolean(ifStatement.getLine(), ifStatement.getColumn());

            ifStatement.getBlock().stream().forEach(statement -> statement.setReturnType(ifStatement.getReturnType()));

            if (ifStatement.getElseBlock() != null) {
                ifStatement.getElseBlock().stream().forEach(statement -> statement.setReturnType(ifStatement.getReturnType()));
            }
        }

        return null;
    }

    /*

        P: Arithmetic: expression1 -> expression2 (+|-|*|/) expression3
        R: expression1.type = expression2.type.arithmetic(expression3.type)

        P: Indexing: expression1 -> expression2 expression3
        R: expression1.type = expression2.type.squareBrackets(expression3.type)

        P: FuncInvocation: expression1 -> expression2 expression*
        R: List<Type> argTypes = expression3*.stream().map( exp -> exp.type).toList();
           // .collect(Collections.toList()); Older versions
           expression1.type = expression2.type.parenthesis(argTypes)

        P: WhileStmt: statement1 -> expression statement2*
        R: expression.type.asBoolean();
           statement2.stream().forEach( statement -> statement.returnType = statement1.returnType)

        // Finish for all expressions and all statements

        P: Comparison: expression1 -> expression2 ('>'|'>='|'<'|'<='|'!='|'==') expression3
        R: expression1.type = expression2.type.comparison(expression3.type);

        P: Logical: expression1 -> expression2 ('&&'|'||') expression3
        R: expression1.type = expression2.type.logical(expression3.type);

        P: FieldAccess: expression1 -> expression2 ID
        R: expression1.type = expression2.type.dot(ID);

        P: Cast: expression1 -> type1 expression2
        R: expression1.type = expression2.type.cast(type1);

        P: UnaryNot: expression1 -> '!' expression2
        R: expression1.type = expression2.type.unaryNot();

        P: UnaryMinus: expression1 -> '-' expression2
        R: expression1.type = expression2.type.unaryMinus();

        P: Variable: expression1 -> ID
        R: if(expression1.definition.type == null){
                expression1.type = new ErrorType(ID.getLine(),
                    ID.getColumn(), "The variable " + expression1.getName() + " does not have a type");
            } else {
                expression1.type = expression1.definition.type;
            }

        P: IntLiteral: expression1 -> INT_CONSTANT
        R: expression1.type = new IntLiteral(INT_CONSTANT.getLine(), INT_CONSTANT.getColumn(), INT_CONSTANT);

        P: CharLiteral: expression1 -> CHAR_CONSTANT
        R: expression1.type = new CharLiteral(CHAR_CONSTANT.getLine(), CHAR_CONSTANT.getColumn(), CHAR_CONSTANT);

        P: RealLiteral: expression1 -> REAL_CONSTANT
        R: expression1.type = new RealLiteral(REAL_CONSTANT.getLine(), REAL_CONSTANT.getColumn(), REAL_CONSTANT);

        // STATEMENTS

        P: Assignment: statement1 -> expression2 expression3
        R: expression2.type.assignment(expression3.type);

        P: Write: statement1 -> expression1
        R: expression1.type.write()

        P: Read: statement1 -> expression1
        R: expression1.type.read()

        P: If: statement1 -> expression statement2*
        R: expression.type.asBoolean();
           statement2.stream().forEach( statement -> statement.returnType = statement1.returnType)

        P: FunctionDefinition: funcDef -> type statement2*
        R: funcDef.returnType = type.returnType;
           statement2.stream().forEach( statement -> statement.returnType = type.returnType)

        P: Return: statement1 -> expression1
        R: expression1.type.returnStatement(statement1.returnType)

     */
}
