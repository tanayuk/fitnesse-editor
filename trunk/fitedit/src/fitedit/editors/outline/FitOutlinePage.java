package fitedit.editors.outline;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.views.contentoutline.ContentOutlinePage;

import fitedit.editors.FitnesseEditor;

public class FitOutlinePage extends ContentOutlinePage {

	private FitnesseEditor fitnesseEditor;
	private IEditorInput editorInput = null;
	private IDocumentProvider documentProvider = null;

	public FitOutlinePage(IDocumentProvider documentProvider,
			FitnesseEditor fitnesseEditor) {
		this.documentProvider = documentProvider;
		this.fitnesseEditor = fitnesseEditor;
	}

	public void setInput(IEditorInput editorInput) {
		this.editorInput = editorInput;
	}

	@Override
	public void createControl(Composite parent) {
		super.createControl(parent);

		TreeViewer viewer = getTreeViewer();
		viewer.setContentProvider(new OutlineContentProvider(documentProvider));
		viewer.setLabelProvider(new OutlineLabelProvider());
		viewer.addSelectionChangedListener(this);
		viewer.setInput(editorInput);
	}

	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		super.selectionChanged(event);

		ISelection selection = event.getSelection();
		if (selection.isEmpty()) {
			return;
		}

		SegmentTree tree = (SegmentTree) ((IStructuredSelection) selection)
				.getFirstElement();
		if (tree == null || tree.position == null) {
			return;
		}

		int start = tree.position.getOffset();
		int length = tree.position.getLength();
		try {
			fitnesseEditor.setHighlightRange(start, length, true);
		} catch (IllegalArgumentException x) {
			// Do Nothing
		}
	}

	public void update() {
		TreeViewer viewer = getTreeViewer();
		if (viewer == null) {
			return;
		}
		
		Control control = viewer.getControl();
		if (control != null && !control.isDisposed()) {
			control.setRedraw(false);
			viewer.setInput(editorInput);
			viewer.expandAll();
			control.setRedraw(true);
		}
	}
}
