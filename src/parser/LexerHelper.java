package parser;

import ast.errorhandler.ErrorHandler;
import ast.program.VarDefinition;
import ast.type.ErrorType;
import ast.type.RecordField;

import java.util.ArrayList;
import java.util.List;

public class LexerHelper {

	public static int lexemeToInt(String str) {
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException e) {
			System.out.println(e);
		}
		return -1;
	}

	// TODO: Implement the lexemeToChar and lexemeToReal methods
	public static char lexemeToChar(String str) {
		try {
			if (str.equals("'\\n'")) {
				return '\n';
			}

			if (str.equals("'\\t'")) {
				return '\t';
			}

			return Character.valueOf(str.charAt(1));
		} catch (Exception e) {
			System.out.println(e);
		}
		return '\0';
	}

	public static double lexemeToReal(String str) {
		try {
			return Double.parseDouble(str);
		} catch (NumberFormatException e) {
			System.out.println(e);
		}
		return -1;
	}



}
