package fitedit.editors.outline;

import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jface.text.BadPositionCategoryException;
import org.eclipse.jface.text.DefaultPositionUpdater;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IPositionUpdater;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.text.source.projection.ProjectionAnnotation;
import org.eclipse.jface.text.source.projection.ProjectionAnnotationModel;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;

import fitedit.editors.outline.SegmentTree.NodeType;

public class OutlineContentProvider implements ITreeContentProvider {

	private FitContentParser parser = null;
	private IDocumentProvider documentProvider = null;

	private final static String SEGMENTS= "__segments";
	private IPositionUpdater positionUpdater= new DefaultPositionUpdater(SEGMENTS);
	private IAnnotationModel model;
	private SegmentTree tree;
	
	public OutlineContentProvider(IDocumentProvider documentProvider, ITextEditor editor) {
		this.documentProvider = documentProvider;
		parser = new FitContentParser();
		model = (IAnnotationModel) editor.getAdapter(ProjectionAnnotationModel.class);
	}
	
	@Override
	public void dispose() {
		
	}

	private void clearAnnotations(){
		if(model == null) {
			return;
		}
		
		Iterator itor = model.getAnnotationIterator();
		while( itor.hasNext() ){
			Annotation a = (Annotation) itor.next();
			model.removeAnnotation(a);
		}
	}
	
	private void addAnnotations(String src){
		if(model == null) {
			return;
		}
		
		for (SegmentTree seg : tree.children) {
			if(seg.nodeType != NodeType.FOLDING) continue;
			if(seg.matched.length() <= 1) continue;
			
			String target = "[^\\w\\*]" + seg.matched.substring(1).replace("*", "\\*") + "!";
			Matcher matcher = Pattern.compile(target).matcher(src);
			
			boolean isFound = matcher.find(seg.position.offset);
			if(!isFound) {
				continue;
			}
			
			int startIdx = matcher.start();
			if(startIdx < 0) {
				continue;
			}
			
			model.addAnnotation(new ProjectionAnnotation(), new Position(seg.position.offset, (startIdx - seg.position.offset + seg.matched.length())));
		}
	}
	
	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		clearAnnotations();
		
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
				addAnnotations(document.get());
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
