import ast.codegeneration.CodeGenerator;
import ast.codegeneration.ExecuteCGVisitor;
import ast.codegeneration.OffsetVisitor;
import ast.errorhandler.ErrorHandler;
import ast.expression.Expression;
import ast.program.Program;
import ast.program.VarDefinition;
import ast.semantic.IdentificationVisitor;
import ast.semantic.TypeCheckingVisitor;
import ast.type.Type;
import introspector.model.IntrospectorModel;
import introspector.view.IntrospectorTree;
import parser.*;

import org.antlr.v4.runtime.*;

import java.util.List;

public class Main {

	public static void main(String... args) throws Exception {
		   if (args.length<1) {
		        System.err.println("Please, pass me the input file.");
		        return;
		    }

		 // create a lexer that feeds off of input CharStream
		CharStream input = CharStreams.fromFileName(args[0]);
		CmmLexer lexer = new CmmLexer(input);

		// create a parser that feeds off the tokens buffer
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		CmmParser parser = new CmmParser(tokens);

		/*

			Order

			First design, then implement

			ANTLR, then Java Class, semantic, code generation...

			Lexical > Grammar > AST > Identification > Type Checking > Offset > Code Generation

		 */

		Program ast = parser.program().ast;

		CodeGenerator.getGenerator().writeNoTab("#source \"" + args[0] + "\"");

		IdentificationVisitor identificationVisitor = new IdentificationVisitor();

		identificationVisitor.visit(ast, null);

		TypeCheckingVisitor typeCheckingVisitor = new TypeCheckingVisitor();

		typeCheckingVisitor.visit(ast, null);

		OffsetVisitor offsetVisitor = new OffsetVisitor();

		offsetVisitor.visit(ast, null);

		ExecuteCGVisitor executeCGVisitor = new ExecuteCGVisitor();

		executeCGVisitor.visit(ast, null);

		if (ErrorHandler.getInstance().anyErrors())
			ErrorHandler.getInstance().showErrors(System.err);
		else {
			// * The AST is shown
			IntrospectorModel model=new IntrospectorModel(
					"Program", ast);
			new IntrospectorTree("Introspector", model);
		}
	}
	

}
