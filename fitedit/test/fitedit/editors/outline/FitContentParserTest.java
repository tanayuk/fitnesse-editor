package fitedit.editors.outline;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class FitContentParserTest {

	FitContentParser parser;
	
	@Before
	public void setUp(){
		parser = new FitContentParser();
	}
	
	@Test
	public void testSegmentation(){
		SegmentTree tree = parser.parse("!define \n" +
				"!1 test head1 \n" +
				" test \n" +
				"!* test fold1 \n" +
				" aaa \n" +
				"*!\n" +
				"!|table1|test \n");
		
		assertEquals(3, tree.children.size());
		assertEquals("!1", tree.children.get(0).matched);
		assertEquals("test head1", tree.children.get(0).args);
		
		assertEquals("!*", tree.children.get(1).matched);
		assertEquals("test fold1", tree.children.get(1).args);
		
		assertEquals("!|", tree.children.get(2).matched);
		assertEquals("table1|test", tree.children.get(2).args);
	}
	
}
