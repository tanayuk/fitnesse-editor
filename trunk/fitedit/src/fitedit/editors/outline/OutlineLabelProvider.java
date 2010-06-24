package fitedit.editors.outline;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import fitedit.Activator;
import fitedit.editors.outline.SegmentTree.NodeType;

public class OutlineLabelProvider extends LabelProvider {
	
	private static final String ICONS_DIR = "icons";
	
	private static Map<NodeType, ImageDescriptor> imageMap = null;
	
	/* (non Javadoc)
	 * @see org.eclipse.jface.viewers.ILabelProvider#getImage(java.lang.Object)
	 */
	public Image getImage(Object element) {
		if(! (element instanceof SegmentTree)) {
			return super.getImage(element);
		}
		
		if(imageMap == null) {
			imageMap = new HashMap<SegmentTree.NodeType, ImageDescriptor>();
			imageMap.put(NodeType.FOLDING, Activator.getImageDescriptor(ICONS_DIR + "/icon_C.gif"));
			imageMap.put(NodeType.TABLE, Activator.getImageDescriptor(ICONS_DIR + "/icon_small_blue.gif"));
			imageMap.put(NodeType.HEADLINE, Activator.getImageDescriptor(ICONS_DIR + "/icon_small_orange.gif"));
			imageMap.put(NodeType.DEFAULT, Activator.getImageDescriptor(ICONS_DIR + "/icon_small_small_blue.gif"));
		}
		
		SegmentTree tree = (SegmentTree) element;
		ImageDescriptor descriptor = imageMap.get(tree.nodeType);
		if(descriptor != null) {
			return descriptor.createImage();
		}
		
		return super.getImage(element);
	}
}
