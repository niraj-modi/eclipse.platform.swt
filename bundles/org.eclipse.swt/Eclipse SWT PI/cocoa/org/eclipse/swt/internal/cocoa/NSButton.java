/*******************************************************************************
 * Copyright (c) 2000, 2017 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.internal.cocoa;

public class NSButton extends NSControl {

public NSButton() {
	super();
}

public NSButton(long /*int*/ id) {
	super(id);
}

public NSButton(id id) {
	super(id);
}

public NSAttributedString attributedTitle() {
	long /*int*/ result = OS.objc_msgSend(this.id, OS.sel_attributedTitle);
	return result != 0 ? new NSAttributedString(result) : null;
}

public long /*int*/ bezelStyle() {
	return OS.objc_msgSend(this.id, OS.sel_bezelStyle);
}

public void setAllowsMixedState(boolean allowsMixedState) {
	OS.objc_msgSend(this.id, OS.sel_setAllowsMixedState_, allowsMixedState);
}

public void setAttributedTitle(NSAttributedString attributedTitle) {
	OS.objc_msgSend(this.id, OS.sel_setAttributedTitle_, attributedTitle != null ? attributedTitle.id : 0);
}

public void setBezelStyle(long /*int*/ bezelStyle) {
	OS.objc_msgSend(this.id, OS.sel_setBezelStyle_, bezelStyle);
}

public void setBordered(boolean bordered) {
	OS.objc_msgSend(this.id, OS.sel_setBordered_, bordered);
}

public void setButtonType(long /*int*/ aType) {
	OS.objc_msgSend(this.id, OS.sel_setButtonType_, aType);
}

public void setImage(NSImage image) {
	OS.objc_msgSend(this.id, OS.sel_setImage_, image != null ? image.id : 0);
}

public void setImagePosition(long /*int*/ imagePosition) {
	OS.objc_msgSend(this.id, OS.sel_setImagePosition_, imagePosition);
}

public void setKeyEquivalent(NSString keyEquivalent) {
	OS.objc_msgSend(this.id, OS.sel_setKeyEquivalent_, keyEquivalent != null ? keyEquivalent.id : 0);
}

public void setState(long /*int*/ state) {
	OS.objc_msgSend(this.id, OS.sel_setState_, state);
}

public void setTitle(NSString title) {
	OS.objc_msgSend(this.id, OS.sel_setTitle_, title != null ? title.id : 0);
}

public long /*int*/ state() {
	return OS.objc_msgSend(this.id, OS.sel_state);
}

public static long /*int*/ cellClass() {
	return OS.objc_msgSend(OS.class_NSButton, OS.sel_cellClass);
}

public static void setCellClass(long /*int*/ factoryId) {
	OS.objc_msgSend(OS.class_NSButton, OS.sel_setCellClass_, factoryId);
}

}
