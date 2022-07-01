package ast.expression;

public class ExpressionFactory {

    public static Expression createExpression(int line, int column, Expression exp1, Expression exp2, String operator){

        if(operator.equals("%")){
            return new Modulus(line, column, exp1, exp2);
        } else {
            return new Arithmetic(line, column, exp1, exp2, operator);
        }

    }
}
