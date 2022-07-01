package ast.codegeneration;

import ast.program.FuncDefinition;
import ast.program.VarDefinition;
import ast.semantic.AbstractVisitor;
import ast.statement.Statement;
import ast.type.FunctionType;
import ast.type.Record;
import ast.type.RecordField;

import java.util.List;

public class OffsetVisitor extends AbstractVisitor<Void, Void> {


    /*

        // Global variables
        P: VarDefinition: varDef -> type ID
        R: varDef.offset = globalBytesSum;
           globalBytesSum += type.numberOfBytes();

        // Local variables
        P: FunctionDefinition: definition -> type statement*
        R: for(Statement st: statement){
            if(st instanceof VarDefinition){
                VarDefinition vd = (VarDefinition) st;
                localBytesSum += vd.type.numberOfBytes();
                vd.offset = -localBytesSum;
            }
        }

        // Parameters
        P: FunctionType: type -> type2 varDefinition*
        R: for(VarDefinition varD: varDefinition){
            varD.offset = 4 + paramsBytesSum;
            paramsBytesSum += varD.type.numberOfBytes();
        }

        // Field
        P: RecordField: rf -> type name
        R: rf.offset = fieldBytesSum;
           fieldBytesSum += type.numberOfBytes();
           // When finishing with a record initialize to 0

        // bugs: parameters al reves
     */

    private int globalBytesSum = 0;
    private int localBytesSum = 0;
    private int paramsBytesSum = 0;
    private int fieldsBytesSum = 0;

    @Override
    public Void visit(VarDefinition varDefinition, Void param) {

        varDefinition.getType().accept(this, null);

        varDefinition.setOffset(globalBytesSum);
        globalBytesSum += varDefinition.getType().numberOfBytes();

        return null;
    }

    @Override
    public Void visit(FuncDefinition funcDefinition, Void param) {

        funcDefinition.getType().accept(this, null);
        funcDefinition.getBody().forEach(parameter -> parameter.accept(this, null));

        List<Statement> statements = funcDefinition.getBody();

        for(Statement st: statements){
            if(st instanceof VarDefinition){
                VarDefinition vd = (VarDefinition) st;
                localBytesSum += vd.getType().numberOfBytes();
                vd.setOffset(-localBytesSum);
            }
        }

        localBytesSum = 0;

        return null;
    }

    @Override
    public Void visit(FunctionType functionType, Void param) {

        functionType.getReturnType().accept(this, null);
        functionType.getParameters().forEach(parameter -> parameter.accept(this, null));

        List<VarDefinition> varDefinitions = functionType.getParameters();

        for(int i = varDefinitions.size()-1; i >= 0; i--){
            varDefinitions.get(i).setOffset(4 + paramsBytesSum);
            paramsBytesSum += varDefinitions.get(i).getType().numberOfBytes();
        }

        paramsBytesSum = 0;

        return null;
    }

    @Override
    public Void visit(Record record, Void param) {

        record.getFields().forEach(field -> field.accept(this, null));

        this.fieldsBytesSum = 0;

        return null;
    }

    @Override
    public Void visit(RecordField recordField, Void param) {

        recordField.getType().accept(this, null);

        recordField.setOffset(fieldsBytesSum);

        fieldsBytesSum += recordField.getType().numberOfBytes();

        return null;
    }
}
