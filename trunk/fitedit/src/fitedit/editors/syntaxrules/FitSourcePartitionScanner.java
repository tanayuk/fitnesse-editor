package fitedit.editors.syntaxrules;

import org.eclipse.jface.text.rules.*;


public class FitSourcePartitionScanner extends RuleBasedPartitionScanner {
	public final static String FIT_COMMENT = "__fit_comment";

	public FitSourcePartitionScanner() {
		IToken comment = new Token(FIT_COMMENT);
		IPredicateRule[] rules = new IPredicateRule[1];
		rules[0] = new EndOfLineRule("#", comment);

		setPredicateRules(rules);
	}
}
