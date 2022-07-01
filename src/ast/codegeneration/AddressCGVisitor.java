package ast.codegeneration;

import ast.expression.FieldAccess;
import ast.expression.Indexing;
import ast.expression.Variable;
import ast.program.VarDefinition;
import ast.type.Record;

public class AddressCGVisitor extends AbstractCGVisitor<Void, Void> {

    private CodeGenerator cg = CodeGenerator.getGenerator();

    /*
        address[[Indexing: exp1 -> exp2 exp3]] = address[[exp2]]
                                                 value[[exp3]]
                                                 <pushi > exp1.type.numberOfBytes()
                                                 <muli>
                                                 <addi>

         address[[FieldAccess: exp1 -> exp2 ID]] = address[[exp2]]
                                                   <pushi> exp2.type.getField(ID).offset
                                                   <addi>

     */

    @Override
    public Void visit(Indexing indexing, Void param) {

        indexing.getExp1().accept(new AddressCGVisitor(), null);
        indexing.getExp2().accept(new ValueCGVisitor(), null);

        cg.updateLastLine(indexing.getLine());

        cg.write("pushi " + indexing.getType().numberOfBytes());
        cg.write("muli");
        cg.write("addi");

        return null;
    }

    @Override
    public Void visit(FieldAccess fieldAccess, Void param) {

        fieldAccess.getExp().accept(new AddressCGVisitor(), null);

        cg.updateLastLine(fieldAccess.getLine());
        cg.write("pushi " + ((Record) fieldAccess.getExp().getType()).getField(fieldAccess.getName()).getOffset());
        cg.write("addi");

        return null;
    }

    @Override
    public Void visit(Variable variable, Void param) {

        int offset = ((VarDefinition)variable.getDefinition()).getOffset();
        if(variable.getDefinition().getScope() == 0){
            cg.updateLastLine(variable.getLine());
            cg.loadGlobalVariable(offset);
        } else{
            cg.updateLastLine(variable.getLine());
            cg.loadLocalVariable(offset);
        }

        return null;
    }

    /*

        address[[Variable: expression -> ID]] = if (expression.definition.scope==0)
                                                     <pusha> expression.definition.offset
                                                else {
                                                     <push bp>
                                                     <pushi> expression.definition.offset
                                                     <addi>
                                                }


     */
}
