package ast.codegeneration;

import ast.expression.Arithmetic;
import ast.program.VarDefinition;
import ast.statement.If;
import ast.statement.While;
import ast.type.IntType;
import ast.type.Type;

import java.io.FileWriter;
import java.io.IOException;

public class CodeGenerator {

    private FileWriter outputFile;

    private static CodeGenerator generator;

    private int labels = 1;

    private int lastLine = 0;

    public CodeGenerator(){

        try {
            outputFile = new FileWriter("big-output.txt"); //
        } catch (IOException exception){
            System.err.print("A problem has ocurred while creating the writer");
        }
    }

    public static CodeGenerator getGenerator(){

        if(generator == null){
            generator = new CodeGenerator();
        }

        return generator;
    }

    public void updateLastLine(int line){

        if(line != lastLine){

            writeNoTab("\n#line " + line);
            lastLine = line;
        }
    }

    public String nextLabel() {
        return "label" + this.labels++;
    }

    public void logical(String operator){

        switch(operator){
            case "&&": write("and");
            break;
            case "||": write("or");
            break;
        }
    }

    public void write(String toWrite){

        if(!toWrite.isEmpty()) {
            try {
                outputFile.write("\t" + toWrite + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void writeNoTab(String toWrite){

        if(!toWrite.isEmpty()) {
            try {
                outputFile.write(toWrite + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void pushInt(int value){

        write("pushi " + value);
    }

    public void pushBytes(int value){

        write("pushb " + value);
    }

    public void pushFloat(double value){

        write("pushf " + value);
    }


    public void load(Type type){

        write("load" + type.suffix());
    }


    public void loadGlobalVariable(int offset) {

        write("pusha " + offset);

    }

    public void loadLocalVariable(int offset) {

        write("push bp " +
                    "\n\tpushi " + offset +
                    "\n\taddi");
    }

    public void convert(Type type1, Type type2) {

        write(type1.convertTo(type2));

    }

    public void arithmetic(Arithmetic arithmetic) {

        switch(arithmetic.getOperator()){
            case "+": write("add" + arithmetic.getType().suffix());
                break;
            case "-": write("sub" + arithmetic.getType().suffix());
                break;
            case "*": write("mul" + arithmetic.getType().suffix());
                break;
            case "/": write("div" + arithmetic.getType().suffix());
                break;
        }
    }

    public void not() {

        write("not");
    }

    public void pushZero(Type type) {

        write("push" + type.suffix() + " 0");
    }

    public void sub(Type type) {

        write("sub" + type.suffix());

    }

    public void promoteToInt(Type type) {

        write(type.convertTo(new IntType(type.getLine(), type.getColumn())));

    }

    public void mod(String suffix) {

        write("mod" + suffix);
    }

    public void comparison(String operator, Type superType) {

        switch (operator) {
            case ">": write("gt" + superType.suffix());
                break;
            case ">=": write("ge" + superType.suffix());
                break;
            case "<": write("lt" + superType.suffix());
                break;
            case "<=": write("le" + superType.suffix());
                break;
            case "!=": write("ne" + superType.suffix());
                break;
            case "==": write("eq" + superType.suffix());
                break;
        }
    }

    public void store(Type type) {

        write("store" + type.suffix());
    }


    public void out(Type type) {

        write("out" + type.suffix());
    }

    public void in(Type type) {

        write("in" + type.suffix());
    }

    public void variableDef(VarDefinition varDefinition) {

        write("' * " + varDefinition.getType().toString() + " " +  varDefinition.getName() + " (offset " + varDefinition.getOffset() + ")");
    }

    public void close() {

        try {
            outputFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void whileStmnt(While whileStmnt, InvocationDTO param) {

        String conditionLabel = nextLabel();
        String exitLabel = nextLabel();

        writeNoTab(" " + conditionLabel + ":");
        whileStmnt.getExpression().accept(new ValueCGVisitor(), null);

        write("jz " + exitLabel);
        whileStmnt.getBlock().forEach(stmnt -> stmnt.accept(new ExecuteCGVisitor(), param));

        write("jmp " + conditionLabel);
        writeNoTab(" " + exitLabel + ":");

    }

    public void ifStmnt(If ifStmnt, InvocationDTO param) {

        String elseLabel = nextLabel();
        String exitLabel = nextLabel();

        ifStmnt.getExpression().accept(new ValueCGVisitor(), null);

        write("jz " + elseLabel);
        ifStmnt.getBlock().forEach(stmnt -> stmnt.accept(new ExecuteCGVisitor(), param));
        write("jmp " + exitLabel);
        write(elseLabel + ":");

        if(ifStmnt.getElseBlock() != null) {
            ifStmnt.getElseBlock().forEach(stmnt -> stmnt.accept(new ExecuteCGVisitor(), param));
        }
        write(exitLabel + ":");

    }

    public void call(String name) {

        write("call " + name);
    }

    public void pop(Type returnType) {

        write("pop" + returnType.suffix());
    }

    public void ret(InvocationDTO param) {

        write("ret " + param.getBytesReturn() + "," + param.getBytesLocals() + "," + param.getBytesParams());
    }

    public void pushBoolean(String value) {

        if(value.equals("true")) {
            pushInt(1);
        } else {
            pushInt(0);
        }
    }
}
