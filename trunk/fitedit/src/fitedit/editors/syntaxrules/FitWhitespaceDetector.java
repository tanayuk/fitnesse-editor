package fitedit.editors.syntaxrules;

import org.eclipse.jface.text.rules.IWhitespaceDetector;

public class FitWhitespaceDetector implements IWhitespaceDetector {

	public boolean isWhitespace(char c) {
		return (c == ' ' || c == '\t' || c == '\n' || c == '\r');
	}
}
