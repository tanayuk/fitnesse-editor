package fitedit.editors.syntaxrules;

import org.eclipse.jface.text.rules.IWordDetector;

public class FitWordDetector implements IWordDetector {

	@Override
	public boolean isWordStart(char c) {
		 if(c == '!') return true;
		 return Character.isJavaIdentifierStart(c);
	}

	@Override
	public boolean isWordPart(char c) {
		if (c == '*' || c == '<' || c == '>') return true;
		return Character.isJavaIdentifierPart(c);
	}

}
