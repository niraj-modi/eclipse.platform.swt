package org.eclipse.swt.tests.junit;

/*
 * (c) Copyright IBM Corp. 2000, 2002. All rights reserved.
 * This file is made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 */

import junit.framework.*;
import junit.textui.*;
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.events.*;
/**
 * Automated Test Suite for class org.eclipse.swt.widgets.Monitor
 *
 * @see org.eclipse.swt.widgets.Monitor
 */
public class Test_org_eclipse_swt_widgets_Monitor extends SwtTestCase {

Display display = null;
Monitor[] monitors = null;
Monitor primary = null;
	
public Test_org_eclipse_swt_widgets_Monitor(String name) {
	super(name);
}

public static void main(String[] args) {
	TestRunner.run(suite());
}

protected void setUp() {
	display = Display.getDefault();
	monitors = display.getMonitors();
	primary = display.getPrimaryMonitor();
}

protected void tearDown() {
}

public void test_equalsLjava_lang_Object() {
	int i;
	for (i = 0; i < monitors.length; i++) {
		if (primary.equals(monitors[i])) break;
	}
	if (i == monitors.length) fail();
	for (i = 0; i  < monitors.length; i++) {
		Monitor test = monitors[i];
		for (int j = 0; j < monitors.length; j++) {
			if (test.equals(monitors[j])) {
				if (i != j) fail("Monitors "+i+" and "+j+" should not be equal");
			}
		}
	}
}

public void test_getBounds() {
	Rectangle bounds = primary.getBounds();
	assertNotNull(bounds);
	for (int i = 0; i < monitors.length; i++) {
		bounds = monitors[i].getBounds();
		assertNotNull(bounds);
	}
}

public void test_getClientArea() {
	Rectangle bounds = primary.getClientArea();
	assertNotNull(bounds);
	for (int i = 0; i < monitors.length; i++) {
		bounds = monitors[i].getClientArea();
		assertNotNull(bounds);
	}
}

public void test_hashCode() {
	for (int i = 0; i < monitors.length; i++) {
		if (primary.equals(monitors[i])) {
			assertTrue(primary.hashCode() == monitors[i].hashCode());
			break;
		}
	}
}


public static Test suite() {
	TestSuite suite = new TestSuite();
	java.util.Vector methodNames = methodNames();
	java.util.Enumeration e = methodNames.elements();
	while (e.hasMoreElements()) {
		suite.addTest(new Test_org_eclipse_swt_widgets_Monitor((String)e.nextElement()));
	}
	return suite;
}

public static java.util.Vector methodNames() {
	java.util.Vector methodNames = new java.util.Vector();
	methodNames.addElement("test_equalsLjava_lang_Object");
	methodNames.addElement("test_getBounds");
	methodNames.addElement("test_getClientArea");
	methodNames.addElement("test_hashCode");
	return methodNames;
}

protected void runTest() throws Throwable {
	if (getName().equals("test_equalsLjava_lang_Object")) test_equalsLjava_lang_Object();
	else if (getName().equals("test_getBounds")) test_getBounds();
	else if (getName().equals("test_getClientArea")) test_getClientArea();
	else if (getName().equals("test_hashCode")) test_hashCode();
}
}
