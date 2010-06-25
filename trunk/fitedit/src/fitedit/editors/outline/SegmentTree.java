package fitedit.editors.outline;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.Position;

/**
 * Tree representing the data structure of a document.
 * 
 */
class SegmentTree {

	static enum NodeType {
		TABLE, HEADLINE, FOLDING, DEFAULT
	}

	public SegmentTree parent;
	public List<SegmentTree> children = new ArrayList<SegmentTree>();

	public String matched;
	public String args;
	public Position position;
	public NodeType nodeType;

	public SegmentTree(SegmentTree parent, NodeType nodeType, String matched, String args,
			Position position) {
		this.parent = parent;
		this.nodeType = nodeType;
		this.matched = matched;
		this.args = args;
		this.position = position;
	}

	public String toString() {
		return args;
	}

}