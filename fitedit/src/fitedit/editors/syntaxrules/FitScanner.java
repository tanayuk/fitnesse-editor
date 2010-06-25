package fitedit.editors.syntaxrules;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.*;
import org.eclipse.jface.text.rules.*;
import org.eclipse.swt.SWT;


public class FitScanner extends RuleBasedScanner {

	public FitScanner(ColorManager manager) {
		IToken defaultToken =
			new Token(
				new TextAttribute(manager.getColor(IFitColorConstants.DEFAULT)));
		
		IToken actionToken =
			new Token(
				new TextAttribute(manager.getColor(IFitColorConstants.ACTION)));

		IToken actionBoldToken =
			new Token(
				new TextAttribute(manager.getColor(IFitColorConstants.ACTION_BOLD), null, SWT.BOLD));
		
		IToken headlineToken =
			new Token(
				new TextAttribute(manager.getColor(IFitColorConstants.HEADLINE)));
		
		IToken headlineBoldToken =
			new Token(
				new TextAttribute(manager.getColor(IFitColorConstants.HEADLINE), null, SWT.BOLD));
				
		IToken tableToken =
			new Token(
				new TextAttribute(manager.getColor(IFitColorConstants.TABLE)));
		
		IToken tableBoldToken =
			new Token(
				new TextAttribute(manager.getColor(IFitColorConstants.TABLE), null, SWT.BOLD));
		
		IToken wordToken =
			new Token(
				new TextAttribute(manager.getColor(IFitColorConstants.WORD), null, SWT.BOLD));
		
		List<IRule> rules = new ArrayList<IRule>();
		
		// Table Rule
		rules.add(new EndOfLineRule("!|", tableBoldToken));
		rules.add(new EndOfLineRule("|", tableToken));
		
		// Action Rule
		rules.add(new EndOfLineRule("!define", actionToken));
		
		rules.add(new EndOfLineRule("!include", actionBoldToken));
		rules.add(new EndOfLineRule("!1", headlineBoldToken));
		rules.add(new EndOfLineRule("!2", headlineBoldToken));
		rules.add(new EndOfLineRule("!3", headlineBoldToken));
		rules.add(new EndOfLineRule("!4", headlineToken));
		rules.add(new EndOfLineRule("!5", headlineToken));
		rules.add(new EndOfLineRule("!6", headlineToken));

		rules.add(new EndOfLineRule("!*", actionBoldToken));
		rules.add(new EndOfLineRule("*!", actionBoldToken));
		rules.add(new EndOfLineRule("**!", actionBoldToken));
		rules.add(new EndOfLineRule("***!", actionBoldToken));
		rules.add(new EndOfLineRule("****!", actionBoldToken));
		rules.add(new EndOfLineRule("*****!", actionBoldToken));
		rules.add(new EndOfLineRule("******!", actionBoldToken));
		
		// Word Rule
		WordRule wordRule = new WordRule(new FitWordDetector(), defaultToken);
		wordRule.addWord("!note", wordToken);
		wordRule.addWord("!-", wordToken);
		wordRule.addWord("!<", wordToken);
		wordRule.addWord("!see", wordToken);
		wordRule.addWord("!anchor", wordToken);
		wordRule.addWord("!lastmodified", wordToken);
		wordRule.addWord("!today", wordToken);
		wordRule.addWord("!contents", wordToken);
		wordRule.addWord("!img", wordToken);
		
		rules.add(wordRule);

//		rules.add(new WhitespaceRule(new XMLWhitespaceDetector()));

		IRule[] rulesArray = (IRule[]) rules.toArray(new IRule[rules.size()]);
		setRules(rulesArray);
	}
}
