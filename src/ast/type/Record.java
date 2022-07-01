package ast.type;

import ast.errorhandler.ErrorHandler;
import ast.semantic.Visitor;

import java.util.ArrayList;
import java.util.List;

public class Record extends AbstractType implements Type {

    private List<RecordField> fields;

    public Record(int line, int column, List<RecordField> fields) {
        super(line, column);

        if(fields.size() < 1){
            throw new RuntimeException("There has to be at least one type");
        }

        checkDuplicatedFields(fields);

        this.fields = fields;
    }

    private boolean checkDuplicatedFields(List<RecordField> definitions) {

        List<String> checked = new ArrayList<>();

        for (RecordField def: definitions){
            if(checked.contains(def.getName())){

                ErrorHandler.getInstance().addError(new ErrorType(def.getLine(), def.getColumn(), "The name of the variable is already used inside the struct"));
                return true;
            }

            checked.add(def.getName());
        }

        return false;
    }

    @Override
    public String toString() {
        return recordFieldToStringOneLine();
    }

    private String recordFieldToString(){

        String res = "struct {";

        for(RecordField s: fields){
            res += s.toString();
        }

        res+= "}";

        return res;
    }


    private String recordFieldToStringOneLine(){

        String res = "record(";

        for(RecordField s: fields){
            res += "( " + s.getName() + " x " + s.getType() + ")";
        }
        res += ")";

        return res;
    }

    @Override
    public Type dot(int line, int column, String c) {

        for (RecordField field: fields){
            if(field.getName().equals(c)){
                return field.getType();
            }
        }

        return new ErrorType(line, column, String.format("The type %s does not contain the field %s", this, c));
    }

    public List<RecordField> getFields() {
        return new ArrayList<>(fields);
    }

    public RecordField getField(String ID){

        for (RecordField field: fields){
            if(field.getName().equals(ID)){
                return field;
            }
        }

        return null;
    }

    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP param) {
        return visitor.visit(this, param);
    }

    @Override
    public int numberOfBytes() {
        int bytes = 0;

        for(RecordField rf: fields){
            bytes += rf.getType().numberOfBytes();
        }

        return bytes;
    }
}
