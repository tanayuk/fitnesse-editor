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

	public String name;
	public Position position;
	public NodeType nodeType;

	public SegmentTree(SegmentTree parent, NodeType nodeType, String name,
			Position position) {
		this.parent = parent;
		this.nodeType = nodeType;
		this.name = name;
		this.position = position;
	}

	public String toString() {
		return name;
	}

}