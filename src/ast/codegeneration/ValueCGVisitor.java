package ast.codegeneration;

import ast.expression.*;
import ast.type.Type;

public class ValueCGVisitor extends AbstractCGVisitor<Void,Void> {

    private CodeGenerator cg = CodeGenerator.getGenerator();

    /*

        FuncInvocation
        value[[FuncInvocation: expression1 -> expression2 expression3*]]: expression3.forEach( expression -> value[[expression]])
                                                                          <call > expression2.getName()

        value[[Indexing: exp1 -> exp2 exp3]] = address[[exp1]]
                                               <load> exp1.type.suffix()

        value[[FieldAccess: exp1 -> exp2 ID]] = address[[exp1]]
                                               <load> exp1.type.suffix()

     */

    @Override
    public Void visit(FunctionInvocation functionInvocation, Void param) {

        cg.updateLastLine(functionInvocation.getLine());

        functionInvocation.getArguments().forEach( expression -> expression.accept(new ValueCGVisitor(), null));

        cg.call(functionInvocation.getV().getName());

        return null;
    }

    @Override
    public Void visit(Indexing indexing, Void param) {

        indexing.accept(new AddressCGVisitor(), null);

        cg.updateLastLine(indexing.getLine());
        cg.load(indexing.getType());

        return null;
    }

    @Override
    public Void visit(FieldAccess fieldAccess, Void param) {

        fieldAccess.accept(new AddressCGVisitor(), null);

        cg.updateLastLine(fieldAccess.getLine());
        cg.load(fieldAccess.getType());

        return null;
    }

    @Override
    public Void visit(IntLiteral intLiteral, Void param) {

        cg.updateLastLine(intLiteral.getLine());
        cg.pushInt(intLiteral.getValue());

        return null;
    }

    @Override
    public Void visit(CharLiteral charLiteral, Void param) {

        cg.updateLastLine(charLiteral.getLine());
        cg.pushBytes(charLiteral.getValue());

        return null;
    }

    @Override
    public Void visit(RealLiteral realLiteral, Void param) {

        cg.updateLastLine(realLiteral.getLine());
        cg.pushFloat(realLiteral.getValue());

        return null;
    }

    @Override
    public Void visit(BooleanExpression booleanExpression, Void param) {

        cg.updateLastLine(booleanExpression.getLine());

        cg.pushBoolean(booleanExpression.getValue());

        return null;
    }

    @Override
    public Void visit(Variable variable, Void param) {

        variable.accept(new AddressCGVisitor(), null);

        cg.updateLastLine(variable.getLine());
        cg.load(variable.getType());

        return null;
    }

    @Override
    public Void visit(Arithmetic arithmetic, Void param) {

        arithmetic.getExp1().accept(new ValueCGVisitor(), null);

        cg.updateLastLine(arithmetic.getLine());

        cg.write(arithmetic.getExp1().getType().convertTo(arithmetic.getType()));

        arithmetic.getExp2().accept(new ValueCGVisitor(), null);

        cg.write(arithmetic.getExp2().getType().convertTo(arithmetic.getType()));

        cg.arithmetic(arithmetic);

        return null;
    }

    @Override
    public Void visit(Logical logical, Void param) {

        logical.getExp1().accept(new ValueCGVisitor(), null);
        logical.getExp2().accept(new ValueCGVisitor(), null);

        cg.updateLastLine(logical.getLine());

        cg.logical(logical.getOperator());

        return null;
    }

    @Override // duda convertTo
    public Void visit(Cast cast, Void param) {

        cast.getExp().accept(new ValueCGVisitor(), null);

        cg.updateLastLine(cast.getLine());

        cg.convert(cast.getExp().getType(), cast.getType());

        return null;
    }

    @Override
    public Void visit(UnaryNot unaryNot, Void param) {

        unaryNot.getExp().accept(new ValueCGVisitor(), null);

        cg.updateLastLine(unaryNot.getLine());

        cg.not();

        return null;
    }

    @Override
    public Void visit(UnaryMinus unaryMinus, Void param) {

        cg.updateLastLine(unaryMinus.getLine());

        cg.pushZero(unaryMinus.getExp().getType());

        unaryMinus.getExp().accept(new ValueCGVisitor(), null);

        cg.sub(unaryMinus.getExp().getType());

        return null;
    }

    @Override // duda promoteToInt
    public Void visit(Modulus modulus, Void param) {

        cg.updateLastLine(modulus.getLine());

        modulus.getExp1().accept(new ValueCGVisitor(), null);
        cg.promoteToInt(modulus.getExp1().getType()); // promoteToInt?
        modulus.getExp2().accept(new ValueCGVisitor(), null);
        cg.promoteToInt(modulus.getExp2().getType());
        cg.mod("i");
        return null;
    }

    @Override // duda superType?
    public Void visit(Comparison comparison, Void param) {

        cg.updateLastLine(comparison.getLine());

        Type superType = comparison.getExp1().getType().superType(comparison.getExp2().getType());

        comparison.getExp1().accept(new ValueCGVisitor(), null);
        cg.convert(comparison.getExp1().getType(), superType);
        comparison.getExp2().accept(new ValueCGVisitor(), null);
        cg.convert(comparison.getExp2().getType(), superType);
        cg.comparison(comparison.getOperator(), superType);

        return null;
    }





    /*
        value[[IntLiteral: expression -> INT_CONSTANT]] = <pushi> INT_CONSTANT

        value[[CharLiteral: expression -> CHAR_CONSTANT]] = <pushb> (int) CHAR_CONSTANT

        value[[RealLiteral: expression -> REAL_CONSTANT]] = <pushf> REAL_CONSTANT

        value[[Variable: expression -> ID]] =  address[[expression]]
                                               <load> expression.type.suffix()

        value[[Arithmetic: expression1 -> expression2 ('+'|'-'|'*'|'/') expression3]] = value[[expression2.value]]
                                                                                   expression2.type.convertTo(expression1.type)
                                                                                   value[[expression3.value]]
                                                                                   expression3.type.convertTo(expression1.type)
                                                                                   switch(expression1.operator){
                                                                                        case '+': <add> expression1.type.suffix()
                                                                                                  break;
                                                                                        case '-': <sub> expression1.type.suffix()
                                                                                                  break;
                                                                                        case '*': <mul> expression1.type.suffix()
                                                                                                  break;
                                                                                        case '/': <div> expression1.type.suffix()
                                                                                                  break;
                                                                                  }

        value[[Logical: expression1 -> expression2 ('&&'|'||') expression3]] = value[[expression2]]
                                                                          value[[expression3]]
                                                                          switch(expression1.operator){
                                                                            case '&&': <and>
                                                                                       break;
                                                                            case '||': <or>
                                                                                       break;
                                                                           }

        value[[Cast: expression1 -> type expression2]] = value[[expression2]]
                                                         expression2.type.convertTo(type)

        value[[UnaryNot: expression1 -> '!' expression2]] = value[[expression2]]
                                                            <not>

        value[[UnaryMinus: expression1 -> '-' expression2]] = <push> expression2.type.suffix() <0>
                                                              value[[expression2]]
                                                              <sub> expression2.type.suffix()


        value[[Modulus: expression1 -> expression2 '%' expression3]] = value[[expression2.value]]
                                                                       expression2.type.promoteToInt()
                                                                       value[[expression3.value]]
                                                                       expression3.type.promoteToInt()
                                                                       <modi>


        value[[Comparison: expression1 -> expression2 ('>'|'>='|'<'|'<='|'!='|'==') expression3]] = Type superType = expression2.type.superType(expression1.type);
                                                                                                    value[[expression2.value]]
                                                                                                    expression2.type.convertTo(superType)
                                                                                                    value[[expression3.value]]
                                                                                                    expression3.type.convertTo(superType)

                                                                                                    switch(expression1.operator){
                                                                                                        case '>': <gt> superType.suffix()
                                                                                                                   break;
                                                                                                        case '>=': <ge> superType.suffix()
                                                                                                                   break;
                                                                                                        case '<': <lt> superType.suffix()
                                                                                                                   break;
                                                                                                        case '<=': <le> superType.suffix()
                                                                                                                   break;
                                                                                                        case '!=': <ne> superType.suffix()
                                                                                                                   break;
                                                                                                        case '==': <eq> superType.suffix()
                                                                                                                   break;
                                                                                                    }



     */
}
