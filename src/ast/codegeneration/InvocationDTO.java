package ast.codegeneration;

public class InvocationDTO {

    private int bytesReturn;
    private int bytesLocals;
    private int bytesParams;

    public InvocationDTO(int bytesReturn, int bytesLocals, int bytesParams){

        setBytesReturn(bytesReturn);
        setBytesLocals(bytesLocals);
        setBytesParams(bytesParams);

    }

    public int getBytesReturn() {
        return bytesReturn;
    }

    public void setBytesReturn(int bytesReturn) {
        this.bytesReturn = bytesReturn;
    }

    public int getBytesLocals() {
        return bytesLocals;
    }

    public void setBytesLocals(int bytesLocals) {
        this.bytesLocals = bytesLocals;
    }

    public int getBytesParams() {
        return bytesParams;
    }

    public void setBytesParams(int bytesParams) {
        this.bytesParams = bytesParams;
    }
}
