package fitedit.editors;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.ide.ResourceUtil;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

import fitedit.editors.outline.FitOutlinePage;


/**
 * Entry point of this plugin
 * 
 * @author Toshiyuki Fukuzawa
 */
public class FitnesseEditor extends TextEditor {

	private FitOutlinePage outlinePage = null;

	public FitnesseEditor() {
		super();
		setSourceViewerConfiguration(new FitSourceViewerConfiguration());
		setDocumentProvider(new FitDocumentProvider());
	}

	@Override
	public Object getAdapter(Class required) {

		// set outline page
		if (IContentOutlinePage.class.equals(required)) {
			if (outlinePage == null) {
				outlinePage = new FitOutlinePage(getDocumentProvider(), this);
				outlinePage.setInput(getEditorInput());
			}
			return outlinePage;
		}

		return super.getAdapter(required);
	}

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		super.init(site, input);
		setTabLabel(input);
	}
	
	/**
	 * Set the tab label with the parent directory's name
	 */
	private void setTabLabel(IEditorInput input){
		IFile file = ResourceUtil.getFile(input);
		String fileName = file.getName();
		if(fileName == null || !fileName.equals("context.txt")) {
			return;
		}
		
		IContainer parent = file.getParent();
		if(parent == null){
			return;
		}
		
		setPartName(parent.getName());
	}
	
	@Override
	public void doSave(IProgressMonitor progressMonitor) {
		super.doSave(progressMonitor);
		
		if(outlinePage != null) {
			outlinePage.update();
		}
	}
}
