import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;

public class TableCursor extends Canvas {
	Table table;
	int row, column;
	Listener tableListener, resizeListener;

public TableCursor (Table parent, int style) {
	super (parent, style);
	table = parent;
	Listener listener = new Listener () {
		public void handleEvent (Event event) {
			switch (event.type) {
				case SWT.Dispose:	dispose (event); break;
				case SWT.FocusOut:	focusOut (event); break;
				case SWT.KeyDown:	keyDown (event); break;
				case SWT.Paint:	    paint (event); break;
				case SWT.Traverse:	traverse (event); break;
			}
		}
	};
	addListener (SWT.Dispose, listener);
	addListener (SWT.FocusOut, listener);
	addListener (SWT.KeyDown, listener);
	addListener (SWT.Paint, listener);
	addListener (SWT.Traverse, listener);
	
	tableListener = new Listener () {
		public void handleEvent (Event event) {
			switch (event.type) {
				case SWT.MouseDown: tableMouseDown (event); break;
				case SWT.FocusIn:	tableFocusIn (event); break;
			}
		}
	};
	table.addListener (SWT.FocusIn, tableListener);
	table.addListener (SWT.MouseDown, tableListener);
	
	resizeListener = new Listener () {
		public void handleEvent (Event event) {
			resize ();
		}
	};
	int columns = table.getColumnCount ();
	for (int i=0; i<columns; i++) {
		TableColumn column = table.getColumn (i);
		column.addListener (SWT.Resize, resizeListener);
	}
	ScrollBar hBar = table.getHorizontalBar ();
	if (hBar != null) {
		hBar.addListener (SWT.Selection, resizeListener);
	}
	ScrollBar vBar = table.getVerticalBar ();
	if (vBar != null) {
		vBar.addListener (SWT.Selection, resizeListener);
	}
}

void dispose (Event event) {
	Display display = getDisplay ();
	display.asyncExec (new Runnable () {
		public void run () {
			if (table.isDisposed ()) return;
			table.removeListener (SWT.FocusIn, tableListener);
			table.removeListener (SWT.MouseDown, tableListener);
			int columns = table.getColumnCount ();
			for (int i=0; i<columns; i++) {
				TableColumn column = table.getColumn (i);
				column.removeListener (SWT.Resize, resizeListener);
			}
			ScrollBar hBar = table.getHorizontalBar ();
			if (hBar != null) {
				hBar.removeListener (SWT.Selection, resizeListener);
			}
			ScrollBar vBar = table.getVerticalBar ();
			if (vBar != null) {
				vBar.removeListener (SWT.Selection, resizeListener);
			}
		}
	});
}

void keyDown (Event event) {
	switch (event.character) {
		case SWT.CR:
			notifyListeners (SWT.DefaultSelection, new Event ());
			return;
	}
	switch (event.keyCode) {
		case SWT.ARROW_UP:
			setRowColumn (row - 1 , column);
			break;
		case SWT.ARROW_DOWN:
			setRowColumn (row + 1, column);
			break;
		case SWT.ARROW_LEFT:
			setRowColumn (row, column - 1);
			break;
		case SWT.ARROW_RIGHT:
			setRowColumn (row, column + 1);
			break;
		case SWT.HOME:
			setRowColumn (0, column);
			break;
		case SWT.END: {
			int row = table.getItemCount () - 1;
			setRowColumn (row, column);
			break;
		}
		case SWT.PAGE_UP: {
			int index = table.getTopIndex ();
			if (index == row) {
				Rectangle rect = table.getClientArea ();
				TableItem item = table.getItem (index);
				Rectangle itemRect = item.getBounds (0);
				rect.height -= itemRect.y;
				int height = table.getItemHeight ();
				int page = Math.max (1, rect.height / height);
				index = Math.max (0, index - page + 1);
			}
			setRowColumn (index, column);
			break;
		}
		case SWT.PAGE_DOWN: {
			int index = table.getTopIndex ();
			Rectangle rect = table.getClientArea ();
			TableItem item = table.getItem (index);
			Rectangle itemRect = item.getBounds (0);
			rect.height -= itemRect.y;
			int height = table.getItemHeight ();
			int page = Math.max (1, rect.height / height);
			int end = table.getItemCount () - 1;
			index = Math.min (end, index + page - 1);
			if (index == row) {
				index = Math.min (end, index + page - 1);
			}
			setRowColumn (index, column);
			break;
		}
	}
}

void focusOut (Event event) {
	setVisible (false);
}

void paint (Event event) {
	GC gc = event.gc;
	Display display = getDisplay ();
	gc.setForeground (display.getSystemColor (SWT.COLOR_LIST_SELECTION_TEXT));
	gc.setBackground (display.getSystemColor (SWT.COLOR_LIST_SELECTION));
	gc.fillRectangle (event.x, event.y, event.width, event.height);
	TableItem item = table.getItems () [row];
	int x = 1, y = 1;
	Image image = item.getImage (column);
	if (image != null) {
		gc.drawImage (image, x, y);
		x += image.getBounds ().width + 2;
	}
	gc.drawString (item.getText (column), x, y);
	if (isFocusControl ()) {
		gc.setBackground(display.getSystemColor(SWT.COLOR_BLACK));
		gc.setForeground(display.getSystemColor(SWT.COLOR_WHITE));
		Rectangle rect = getClientArea ();
		gc.drawFocus (rect.x, rect.y, rect.width, rect.height);
	}
	
}

void tableFocusIn (Event event) {
	setVisible (true);
	setFocus ();
}

void tableMouseDown (Event event) {
	Point pt = new Point (event.x, event.y);
	Rectangle clientRect = table.getClientArea ();
	int columns = table.getColumnCount ();
	int start = table.getTopIndex ();
	int end = table.getItemCount ();
	for (int row=start; row<end; row++) {
		TableItem item = table.getItem (row);
		for (int column=0; column<columns; column++) {
			Rectangle rect = item.getBounds (column);
			if (rect.y > clientRect.y + clientRect.height) return;
			if (rect.contains (pt)) {
				setRowColumn (row, column);
				setFocus ();
				return;
			}
		}
	}								
}

void traverse (Event event) {
	switch (event.detail) {
		case SWT.TRAVERSE_ARROW_NEXT:
		case SWT.TRAVERSE_ARROW_PREVIOUS:
		case SWT.TRAVERSE_RETURN:
			event.doit = false;
			return;
	}
	event.doit = true;
}

void setRowColumn (int row, int column) {
	if (0 <= row && row < table.getItemCount ()) {
		if (0 <= column && column < table.getColumnCount ()) {
			this.row = row;
			this.column = column;
			TableItem item = table.getItems () [row];
			table.showItem (item);
			setBounds (item.getBounds (column));
			redraw ();
			notifyListeners (SWT.Selection, new Event ());
		}
	}
}

public void setVisible (boolean visible) {
	if (visible) resize ();
	super.setVisible (visible);
}

void resize () {
	TableItem item = table.getItems () [row];
	setBounds (item.getBounds (column));
}

}

