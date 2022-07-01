grammar Cmm;	

@header{
    import ast.*;
    import ast.expression.*;
    import ast.type.*;
    import ast.program.*;
    import ast.statement.*;
}

// Syntax analysis
program returns[Program ast]:
       {List<Definition> definitions = new ArrayList<Definition>(); }
       ( d = definition { definitions.addAll($d.ast); })* vf = mainfunction { definitions.add($vf.ast); } EOF
       { $ast = new Program($vf.ast.getLine(), $vf.ast.getColumn(), definitions); }
       ;

definition returns[List<Definition> ast = new ArrayList<Definition>()]:
               vd = var_definition { $ast.addAll($vd.ast); }
               | fd = function_def { $ast.add($fd.ast); }
               ;

mainfunction returns[FuncDefinition ast]:
                'void' n = 'main' '('  ')' fb = function_body
                { $ast = new FuncDefinition($n.getLine(), $n.getCharPositionInLine() + 1, new FunctionType($n.getLine(), $n.getCharPositionInLine() + 1, new ArrayList<VarDefinition>(), new VoidType($n.getLine(), $n.getCharPositionInLine() + 1)), $n.text, $fb.ast);}
                ;

function_def returns[FuncDefinition ast]:
            t = return_type id = ID '(' p = parameters ')' fb = function_body
            { $ast = new FuncDefinition($t.ast.getLine(), $t.ast.getColumn(),
            new FunctionType($t.ast.getLine(), $t.ast.getColumn(), $p.ast, $t.ast), $id.text, $fb.ast);}
            ;

parameters returns[List<VarDefinition> ast = new ArrayList<VarDefinition>()]:
        bt1 = built_in_type id1 = ID { $ast.add(new VarDefinition($bt1.ast.getLine(), $bt1.ast.getColumn(), $bt1.ast, $id1.text)); } (',' bt2 = built_in_type id2 = ID { $ast.add(new VarDefinition($bt2.ast.getLine(), $bt2.ast.getColumn(), $bt2.ast, $id2.text)); } )*
        |
        ;

function_body returns[List<Statement> ast = new ArrayList<Statement>()]:
             '{' (vd1 = var_definition { $ast.addAll($vd1.ast); })* (st1 = statement { $ast.addAll($st1.ast); })* '}'
             ;

built_in_type returns[Type ast]:
              t1 = 'int' { $ast = new IntType($t1.getLine(), $t1.getCharPositionInLine() + 1); }
              | t1 = 'double' { $ast = new DoubleType($t1.getLine(), $t1.getCharPositionInLine() + 1); }
              | t1 = 'char' { $ast = new CharType($t1.getLine(), $t1.getCharPositionInLine() + 1); }
              | t1 = 'boolean' { $ast = new BooleanType($t1.getLine(), $t1.getCharPositionInLine() + 1); }
              ;

return_type returns[Type ast]:
           t1 = built_in_type { $ast = $t1.ast; }
           | v1 = 'void' {$ast = new VoidType($v1.getLine(), $v1.getCharPositionInLine() + 1);}
           ;

type returns[Type ast]:
    bt1 = built_in_type { $ast = $bt1.ast; }
    | t1 = type '[' s1 = INT_CONSTANT ']'
    { $ast = Array.createArray($t1.ast.getLine(), $t1.ast.getColumn(), LexerHelper.lexemeToInt($s1.text), $t1.ast);} // DOUBT Inverted problem
    | st1 = struct_type { $ast = $st1.ast; }
    ;

struct_type returns[ast.type.Record ast]:
           s1 = 'struct' '{' b1 = struct_body '}'
           {
                $ast = new ast.type.Record($s1.getLine(), $s1.getCharPositionInLine() + 1, $b1.ast);
           }
           ;

struct_body returns[List<RecordField> ast = new ArrayList<RecordField>()]:

                   (v1 = var_definition
                   {
                        $v1.ast.forEach( varD -> $ast.add(new RecordField(varD.getLine(), varD.getColumn(), varD.getName(), varD.getType())));

                   })*
                   ;

var_definition returns[List<VarDefinition> ast = new ArrayList<VarDefinition>()]:
                   t1 = type v = variables ';' { $v.ast.forEach( var -> $ast.add(new VarDefinition(var.getLine(), var.getColumn(), $t1.ast, var.getName())));}
                   ;

variables returns[List<Variable> ast = new ArrayList<Variable>()]:
                   id1 = ID {$ast.add(new Variable($id1.getLine(), $id1.getCharPositionInLine() + 1, $id1.text)); } (',' id2 = ID { $ast.add(new Variable($id2.getLine(), $id2.getCharPositionInLine() + 1, $id2.text)); })*
                   ;

function_invocation returns[FunctionInvocation ast]:
                   id = ID '(' arg = functionArguments ')' { $ast = new FunctionInvocation($id.getLine(), $id.getCharPositionInLine() + 1, new Variable($id.getLine(), $id.getCharPositionInLine() + 1, $id.text), $arg.ast); }
                   ;

functionArguments returns[List<Expression> ast = new ArrayList<Expression>()]:
                   e1 = expression { $ast.add($e1.ast); } (',' e2 = expression { $ast.add($e2.ast); })*
                   |
                   ;

block returns[List<Statement> ast = new ArrayList<Statement>()]: s1 = statement { $ast.addAll($s1.ast); }
                                                               | '{' (s2 = statement { $ast.addAll($s2.ast); } )* '}'
                                                               ;

expressions returns[List<Expression> ast = new ArrayList<Expression>()]:
                (e1 = expression',' { $ast.add($e1.ast); })* e2 = expression ';' { $ast.add($e2.ast);}
                ;

statement returns[List<Statement> ast = new ArrayList<Statement>()]:
         e1 = expression '=' e2 = expression ';'  { $ast.add(new Assignment($e1.ast.getLine(), $e1.ast.getColumn(), $e1.ast, $e2.ast)); }
         | 'while' '(' e1 = expression ')' b1 = block { $ast.add(new While($e1.ast.getLine(), $e1.ast.getColumn(), $e1.ast, $b1.ast)); }
         | 'if' '(' e1 = expression ')' b1 = block { $ast.add(new If($e1.ast.getLine(), $e1.ast.getColumn(), $b1.ast, $e1.ast));} ('else' b2 = block {( (If) $ast.get($ast.size()-1)).setElseBlock($b2.ast);})?
         | 'return' e1 = expression ';' { $ast.add(new Return( $e1.ast.getLine(), $e1.ast.getColumn(), $e1.ast));}
         | 'write'  exp1 = expressions { $exp1.ast.forEach(exp -> $ast.add(new Write(exp.getLine(), exp.getColumn(), exp))); }
         | 'read'   exp2 = expressions {$exp2.ast.forEach(exp -> $ast.add(new Read(exp.getLine(), exp.getColumn(), exp)));}
         | f1 = function_invocation ';' { $ast.add( $f1.ast ); }
         ;

expression returns[Expression ast]: '(' e1=expression ')' { $ast = $e1.ast;}
          | e1 = expression'[' e2 = expression ']' { $ast = new Indexing($e1.ast.getLine(), $e1.ast.getColumn(), $e1.ast, $e2.ast); }
          | e1 = expression '.' ID  { $ast = new FieldAccess($e1.ast.getLine(), $e1.ast.getColumn(), $e1.ast, $ID.text); }
          | '(' t1 = built_in_type ')' e1 = expression { $ast = new Cast($e1.ast.getLine(), $e1.ast.getColumn(), $e1.ast, $t1.ast);}
          | '-' e1 = expression { $ast = new UnaryMinus($e1.ast.getLine(), $e1.ast.getColumn(), $e1.ast); }
          | '!' e1 = expression { $ast = new UnaryNot($e1.ast.getLine(), $e1.ast.getColumn(), $e1.ast); }
          | e1 = expression op = ('*'|'/'|'%') e2 = expression { $ast = ExpressionFactory.createExpression($e1.ast.getLine(), $e1.ast.getColumn(), $e1.ast, $e2.ast, $op.text); }
          | e1 = expression op = ('+'|'-') e2 = expression { $ast = new Arithmetic($e1.ast.getLine(), $e1.ast.getColumn(), $e1.ast, $e2.ast, $op.text); }
          | e1 = expression op = ('>'|'>='|'<'|'<='|'!='|'==') e2 = expression { $ast = new Comparison($e1.ast.getLine(), $e1.ast.getColumn(), $op.text, $e1.ast, $e2.ast); } // ??? Is it comparison?
          | e1 = expression op = ('&&'|'||') e2 = expression { $ast = new Logical($e1.ast.getLine(), $e1.ast.getColumn(), $op.text, $e1.ast, $e2.ast); }
          | f = function_invocation  { $ast = $f.ast; }
          | b1 = BOOLEAN { $ast = new BooleanExpression($b1.getLine(), $b1.getCharPositionInLine() + 1, $b1.text); }
          | i1 = INT_CONSTANT { $ast = new IntLiteral($i1.getLine(), $i1.getCharPositionInLine() + 1, LexerHelper.lexemeToInt($i1.text)); }
          | r1 = REAL_CONSTANT { $ast = new RealLiteral($r1.getLine(), $r1.getCharPositionInLine() + 1, LexerHelper.lexemeToReal($r1.text)); }
          | c1 = CHAR_CONSTANT { $ast = new CharLiteral($c1.getLine(), $c1.getCharPositionInLine() + 1, LexerHelper.lexemeToChar($c1.text)); }
          //| c1 = SPECIAL_CHAR { $ast = new CharLiteral($c1.getLine(), $c1.getCharPositionInLine() + 1, LexerHelper.lexemeToChar($c1.text)); }
          | ID { $ast = new Variable($ID.getLine(), $ID.getCharPositionInLine() + 1, $ID.text); }
          ;

// Lexical Analysis
fragment
DIGIT: [0-9]
     ;

fragment
LETTER: [a-zA-Z]
       ;

BOOLEAN: 'true'
       | 'false'
       ;

INT_CONSTANT: '0'
            | [1-9] DIGIT*
            ;

ONE_LINE_COMMENT: '//' .*? ('\n'| EOF ) -> skip
                ;

MULTI_LINE_COMMENT: '/*' .*? '*/' -> skip
                ;

ID: ('_'|LETTER) ('_'|DIGIT|LETTER)*
  ;

REAL_CONSTANT: DECIMAL
             | (INT_CONSTANT | DECIMAL) [Ee] [+-]? DIGIT*
             ;

fragment DECIMAL: DIGIT+ '.' DIGIT*
                | DIGIT* '.' DIGIT+
                ;

fragment EXPONENT: ('E'|'e') ('+'|'-'|) (DIGIT)*
        ;

fragment SPECIAL_CHAR: ['] '\\' ('n'|'t') [']
            ;

CHAR_CONSTANT: ['] . [']
             | SPECIAL_CHAR
             | CHAR_CONSTANT_NUMBER
             ;

fragment CHAR_CONSTANT_NUMBER: ['][\\] DIGIT* [']
                    ;

BLANK: [ \n\t\r]+ -> skip
     ;

