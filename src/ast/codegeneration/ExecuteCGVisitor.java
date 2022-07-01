package ast.codegeneration;

import ast.expression.FunctionInvocation;
import ast.program.*;
import ast.statement.*;
import ast.type.FunctionType;
import ast.type.VoidType;

import java.util.List;

public class ExecuteCGVisitor extends AbstractCGVisitor<InvocationDTO, Void> {

    private CodeGenerator cg = CodeGenerator.getGenerator();

    /*

        FuncInvocation
        execute[[FuncInvocation: statement -> expression1 expression2*]]: expression2.forEach( expression -> value[[expression]])
                                                                          <call > expression1.getName()
                                                                          if(!(expression1.type.returnType instanceof VoidType))
                                                                                <pop > expression1.type.returnType.suffix()

        Return
        execute[[Return: statement -> expression]](int bytesReturn, int bytesLocals, int bytesParams): value[[expression]]
                                                                                                       <ret > bytesReturn <,> bytesLocals <,> bytesParams






        While
        execute[[While: statement1 -> expression statement2* ]] = String conditionLabel = cg.nextLabel();
                                                                 String exitLabel = cg.nextLabel();
                                                                 conditionLabel <:>
                                                                 value[[expression]]
                                                                 <jz > exitLabel
                                                                 statement2.forEach(stmnt -> execute[[stmnt]](bytesReturn, bytesLocals, bytesParams))
                                                                 <jmp > conditionLabel
                                                                 exitLabel <:>


        If
        execute[[If: statement1 -> exp statement2+ statement3*]] = String elseLabel = cg.nextLabel()
                                                                   String exitLabel = cg.nextLabel()
                                                                   value[[exp]]
                                                                   <jz > elseLabel
                                                                   statement2.forEach(stmnt -> execute[[stmnt]](bytesReturn, bytesLocals, bytesParams))
                                                                   <jmp > exitLabel
                                                                   elseLabel <:>
                                                                   statement3.forEach(stmnt -> execute[[stmnt]](bytesReturn, bytesLocals, bytesParams))
                                                                   exitLabel <:>

     */

    @Override
    public Void visit(Return returnStatement, InvocationDTO param) {

        returnStatement.getExpression().accept(new ValueCGVisitor(), null);
        cg.ret(param);

        return null;
    }

    @Override
    public Void visit(FunctionInvocation functionInvocation, InvocationDTO param) {

        functionInvocation.getArguments().forEach( expression -> expression.accept(new ValueCGVisitor(), null));
        cg.call(functionInvocation.getV().getName());

        if(!(((FunctionType)(functionInvocation.getV().getType())).getReturnType() instanceof VoidType)){
            cg.pop((((FunctionType)(functionInvocation.getV().getType())).getReturnType()));
        }

        return null;
    }

    @Override
    public Void visit(While whileStmnt, InvocationDTO param) {

        cg.whileStmnt(whileStmnt, param);

        return null;
    }

    @Override
    public Void visit(If ifStmnt, InvocationDTO param) {

        cg.ifStmnt(ifStmnt, param);

        return null;
    }

    @Override
    public Void visit(Assignment assignment, InvocationDTO param) {

        assignment.getLHS().accept(new AddressCGVisitor(), null);
        assignment.getRHS().accept(new ValueCGVisitor(), null);

        cg.store(assignment.getRHS().getType());

        return null;
    }

    @Override
    public Void visit(Write write, InvocationDTO param) {

        write.getExpression().accept(new ValueCGVisitor(), null);

        cg.out(write.getExpression().getType());

        return null;
    }

    @Override
    public Void visit(Read read, InvocationDTO param) {

        read.getExpression().accept(new AddressCGVisitor(), null);

        cg.in(read.getExpression().getType());
        cg.store(read.getExpression().getType());

        return null;
    }

    @Override
    public Void visit(VarDefinition varDefinition, InvocationDTO param) {

        cg.variableDef(varDefinition);

        return null;
    }

    @Override
    public Void visit(FuncDefinition funcDefinition, InvocationDTO param) {

        cg.writeNoTab("\n " + funcDefinition.getName() + ":");
        cg.write("' * Parameters");
        ((FunctionType) funcDefinition.getType()).getParameters().stream().forEach( parameter -> parameter.accept(new ExecuteCGVisitor(), null));
        cg.write("' * Local variables");

        List<VarDefinition> definitionList = funcDefinition.getBody().stream().filter(statement -> statement instanceof VarDefinition).map(statement -> (VarDefinition) statement).toList();

        definitionList.forEach( statement -> statement.accept(new ExecuteCGVisitor(), null));

        int bytesLocals = definitionList.isEmpty() ? 0 : -definitionList.get(definitionList.size()-1).getOffset();

        cg.write("enter " + bytesLocals);

        int bytesParams =  ((FunctionType) funcDefinition.getType()).getParameters().stream().mapToInt( parameter -> parameter.getType().numberOfBytes()).sum();
        int bytesReturn = ((FunctionType) funcDefinition.getType()).getReturnType().numberOfBytes();

        InvocationDTO dto = new InvocationDTO(bytesReturn, bytesLocals, bytesParams);

        List<Statement> statementList = funcDefinition.getBody().stream().filter(statement -> !(statement instanceof VarDefinition)).toList();
        statementList.stream().forEach(s->s.accept(new ExecuteCGVisitor(), dto)); // HERE I PASS THE PARAMETERS

        if(((FunctionType) funcDefinition.getType()).getReturnType() instanceof VoidType){
            cg.write("ret " + bytesReturn + "," + bytesLocals + "," + bytesParams);
        }


        return null;
    }

    @Override
    public Void visit(Program program, InvocationDTO param) {

        cg.writeNoTab("' Invocation to the main function");
        cg.writeNoTab("call main");
        cg.writeNoTab("halt \n");
        program.getDefinitions().stream().forEach( def -> def.accept(new ExecuteCGVisitor(), null));

        cg.close();

        return null;
    }

    /*

        Assignment
        execute[[Assignment: expression1 = expression2 '=' expression3]] = address[[expression2]]
                                                                           value[[expression3]]
                                                                           <store> expression3.type.suffix()

        Write
        execute[[Write: statement -> expression]] = value[[expression]]
                                                    <out> expression.type.suffix()

        Read
        execute[[Read: statement -> expression]] = address[[expression]]
                                                   <in> expression.type.suffix()
                                                   <store> expression.type.suffix()

        (Global or local) Variable definition
        execute[[VarDefinition: definition -> type ID]] = <' * > type.toString() definition.getName() <(offset > definition.offset <)>

        Function definition
        execute[[FuncDefinition: definition1 -> type ID definition2* statement*]] = ID <:>
                                                                                    <' * Parameters>
                                                                                    type.parameters.stream().forEach( param -> execute[[param]])
                                                                                    <' * Local variables>
                                                                                    definition2*.stream().forEach( def -> execute[[def]])
                                                                                    int bytesLocals = definition2*.isEmpty() ? 0 :
                                                                                        -definition2*.get(definition2*.size()-1).offset;
                                                                                    <enter > bytesLocals
                                                                                    int bytesParams = type.parameters.stream().mapToInt( param -> param.type.numberOfBytes() ).sum();
                                                                                    int bytesReturn=type.returnType.numberOfBytes();
                                                                                    statement.stream().forEach( s -> execute[[s]](bytesReturn, bytesLocals, bytesParams))
                                                                                    if (type.returnType instanceof VoidType)
                                                                                        <ret> bytesReturn , bytesLocals , bytesParams


        Program
        execute[[Program: program -> definition*]] = definition.stream().forEach( def -> execute[[def]] )


     */
}
