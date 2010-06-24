package fitedit.editors.outline;

import org.eclipse.jface.text.BadPositionCategoryException;
import org.eclipse.jface.text.DefaultPositionUpdater;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IPositionUpdater;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.texteditor.IDocumentProvider;

public class OutlineContentProvider implements ITreeContentProvider {

	private FitContentParser parser = null;
	private IDocumentProvider documentProvider = null;

	private final static String SEGMENTS= "__segments";
	private IPositionUpdater positionUpdater= new DefaultPositionUpdater(SEGMENTS);
	private SegmentTree tree;
	
	public OutlineContentProvider(IDocumentProvider documentProvider) {
		this.documentProvider = documentProvider;
		parser = new FitContentParser();
	}
	
	@Override
	public void dispose() {
		
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		if (oldInput != null) {
			IDocument document= documentProvider.getDocument(oldInput);
			if (document != null) {
				try {
					document.removePositionCategory(SEGMENTS);
				} catch (BadPositionCategoryException e) {
					// Do Nothing
				}
				document.removePositionUpdater(positionUpdater);
			}
		}
		
		if (newInput != null) {
			IDocument document= documentProvider.getDocument(newInput);
			if (document != null) {
				document.addPositionCategory(SEGMENTS);
				document.addPositionUpdater(positionUpdater);

				tree = parser.parse(document.get());
			}
		}
	}

	@Override
	public Object[] getElements(Object inputElement) {
		if(tree == null) {
			return new Object[0];
		}
		
		return tree.children.toArray();
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		SegmentTree tree = (SegmentTree)parentElement;
		return tree.children.toArray();
	}

	@Override
	public Object getParent(Object element) {
		SegmentTree tree = (SegmentTree)element;
		return tree.parent;
	}

	@Override
	public boolean hasChildren(Object element) {
		SegmentTree tree = (SegmentTree)element;
		return tree.children.size() != 0;
	}

}
