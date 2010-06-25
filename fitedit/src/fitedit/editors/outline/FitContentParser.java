package fitedit.editors.outline;

import java.util.Collections;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jface.text.Position;

import fitedit.editors.outline.SegmentTree.NodeType;

public class FitContentParser {

	private static final Pattern patternFolding = Pattern.compile("^(!\\*+)(.*)$", Pattern.MULTILINE);
	private static final Pattern patternHeadline = Pattern.compile("^(!\\d)(.*)$", Pattern.MULTILINE);
	private static final Pattern patternTable = Pattern.compile("^!\\|(.*)$", Pattern.MULTILINE);
	
	public SegmentTree parse(String src) {
		SegmentTree root = new SegmentTree(null, NodeType.DEFAULT, null, "ROOT", null);
		if(src == null) {
			return root;
		}
		
		Matcher matcher;
		
		// search !* 
		matcher = patternFolding.matcher(src);
		while(matcher.find()){
			root.children.add(new SegmentTree(root, NodeType.FOLDING, matcher.group(1).trim(), matcher.group(2).trim(), new Position(matcher.start())));
		}

		matcher = patternHeadline.matcher(src);
		while(matcher.find()){
			root.children.add(new SegmentTree(root, NodeType.HEADLINE, matcher.group(1).trim(), matcher.group(2).trim(), new Position(matcher.start())));
		}

		matcher = patternTable.matcher(src);
		while(matcher.find()){
			root.children.add(new SegmentTree(root, NodeType.TABLE, "!|", matcher.group(1).trim(), new Position(matcher.start())));
		}
		
		Collections.sort(root.children, new Comparator<SegmentTree>() {
			@Override
			public int compare(SegmentTree t1, SegmentTree t2) {
				return t1.position.offset - t2.position.offset;
			}
		});
		
		return root;
	}

}
