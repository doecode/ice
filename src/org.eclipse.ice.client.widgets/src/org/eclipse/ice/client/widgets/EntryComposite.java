/*******************************************************************************
 * Copyright (c) 2012, 2014 UT-Battelle, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Initial API and implementation and/or initial documentation - Jay Jay Billings,
 *   Jordan H. Deyton, Dasha Gorin, Alexander J. McCaskey, Taylor Patterson,
 *   Claire Saunders, Matthew Wang, Anna Wojtowicz
 *******************************************************************************/
package org.eclipse.ice.client.widgets;

import java.io.File;
import java.util.ArrayList;

import org.eclipse.ice.client.common.internal.ClientHolder;
import org.eclipse.ice.datastructures.form.AllowedValueType;
import org.eclipse.ice.datastructures.form.Entry;
import org.eclipse.ice.datastructures.updateableComposite.IUpdateable;
import org.eclipse.ice.datastructures.updateableComposite.IUpdateableListener;
import org.eclipse.ice.iclient.IClient;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.IMessageManager;

/**
 * <!-- begin-UML-doc -->
 * <p>
 * This is an subclass of SWT's Composite class made specifically to work with
 * ICE Entries.
 * </p>
 * <p>
 * Changes to this class are broadcasted using SWT's event system. Marking the
 * FormEditor as dirty, for example, should be handled by registering an event
 * listener with instances of this class and catching the signal.
 * </p>
 * <p>
 * The EntryComposite can post messages about its work with an Entry to a
 * IMessageManager if it is set by calling setMessageManager().
 * </p>
 * <!-- end-UML-doc -->
 * 
 * @author gqx
 * @generated 
 *            "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public class EntryComposite extends Composite implements IUpdateableListener {
	/**
	 * <!-- begin-UML-doc -->
	 * <p>
	 * A label that describes the Entry.
	 * </p>
	 * <!-- end-UML-doc -->
	 * 
	 * @generated 
	 *            "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private Label label;
	/**
	 * <!-- begin-UML-doc -->
	 * <p>
	 * A text field that is used if the Entry type is unspecified.
	 * </p>
	 * <!-- end-UML-doc -->
	 * 
	 * @generated 
	 *            "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private Text text;
	/**
	 * <!-- begin-UML-doc -->
	 * <p>
	 * A drop-down menu for the Entry.
	 * </p>
	 * <!-- end-UML-doc -->
	 * 
	 * @generated 
	 *            "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private Combo dropDown;
	/**
	 * <!-- begin-UML-doc -->
	 * <p>
	 * A set of buttons for the Entry.
	 * </p>
	 * <!-- end-UML-doc -->
	 * 
	 * @generated 
	 *            "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	protected ArrayList<Button> buttons = null;
	/**
	 * <!-- begin-UML-doc -->
	 * <p>
	 * The Entry that is displayed by the EntryComposite.
	 * </p>
	 * <!-- end-UML-doc -->
	 * 
	 * @generated 
	 *            "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	protected Entry entry;

	/**
	 * <!-- begin-UML-doc -->
	 * <p>
	 * The message manager to which message about the success or failure of
	 * manipulating the Entry should be posted.
	 * </p>
	 * <!-- end-UML-doc -->
	 * 
	 * @generated 
	 *            "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private IMessageManager messageManager;
	/**
	 * <!-- begin-UML-doc -->
	 * <p>
	 * The name of the message posted to the message manager.
	 * </p>
	 * <!-- end-UML-doc -->
	 * 
	 * @generated 
	 *            "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private String messageName;

	/**
	 * Entry map of binary/boolean-type allowed values
	 */
	private ArrayList<String> allowedBinaryValues = new ArrayList<String>();

	/**
	 * List of allowed values for the entry in lowercase text
	 */
	private ArrayList<String> lowercaseAllowedValues = new ArrayList<String>();

	public EntryComposite(Composite parent, int style, Layout compLayout,
			Object data, Entry refEntry) {
		super(parent, style);

		setLayout(compLayout);
		setLayoutData(data);
		
		// Set the Entry reference
		if (refEntry != null) {
			entry = refEntry;
		} else {
			throw new RuntimeException("Entry passed to EntryComposite "
					+ "constructor cannot be null!");
		}
		// Create the Buttons array
		buttons = new ArrayList<Button>();

		// Create the MessageName String
		messageName = new String();

		// Setup the allowedBinaryValues for check boxes
		allowedBinaryValues = new ArrayList<String>();
		// Setup the list of values that are equivalent to "ready"
		allowedBinaryValues.add("ready");
		allowedBinaryValues.add("yes");
		allowedBinaryValues.add("y");
		allowedBinaryValues.add("true");
		allowedBinaryValues.add("enabled");
		allowedBinaryValues.add("on");
		// Setup the list of values that are equivalent to "not ready"
		allowedBinaryValues.add("not ready");
		allowedBinaryValues.add("no");
		allowedBinaryValues.add("n");
		allowedBinaryValues.add("false");
		allowedBinaryValues.add("disabled");
		allowedBinaryValues.add("off");

		// Register for updates from the Entry
		entry.register(this);

		// Add a listener to the Entry that unregisters this composite as a
		// listener upon disposal.
		addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				entry.unregister(EntryComposite.this);
			}
		});

		// Render the entry
		render();

		return;
	}

	/**
	 * <!-- begin-UML-doc -->
	 * <p>
	 * The Constructor
	 * </p>
	 * <!-- end-UML-doc -->
	 * 
	 * @param parent
	 *            <p>
	 *            The parent Composite.
	 *            </p>
	 * @param style
	 *            <p>
	 *            The style of the EntryComposite.
	 *            </p>
	 * @param refEntry
	 *            <p>
	 *            An Entry that should be used to create the widget, to update
	 *            when changed by the user and to be updated from when changed
	 *            internally by ICE.
	 *            </p>
	 * @generated 
	 *            "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public EntryComposite(Composite parent, int style, Entry refEntry) {
		// begin-user-code

		// Call the super constructor
		super(parent, style);

		// Set the Entry reference
		if (refEntry != null) {
			entry = refEntry;
		} else {
			throw new RuntimeException("Entry passed to EntryComposite "
					+ "constructor cannot be null!");
		}
		// Create the Buttons array
		buttons = new ArrayList<Button>();

		// Create the MessageName String
		messageName = new String();

		// Setup the allowedBinaryValues for check boxes
		allowedBinaryValues = new ArrayList<String>();
		// Setup the list of values that are equivalent to "ready"
		allowedBinaryValues.add("ready");
		allowedBinaryValues.add("yes");
		allowedBinaryValues.add("y");
		allowedBinaryValues.add("true");
		allowedBinaryValues.add("enabled");
		allowedBinaryValues.add("on");
		// Setup the list of values that are equivalent to "not ready"
		allowedBinaryValues.add("not ready");
		allowedBinaryValues.add("no");
		allowedBinaryValues.add("n");
		allowedBinaryValues.add("false");
		allowedBinaryValues.add("disabled");
		allowedBinaryValues.add("off");

		// Register for updates from the Entry
		entry.register(this);

		// Add a listener to the Entry that unregisters this composite as a
		// listener upon disposal.
		addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				entry.unregister(EntryComposite.this);
			}
		});

		// Render the entry
		render();

		return;
		// end-user-code
	}

	/**
	 * Returns the entry stored on this composite
	 * 
	 * @return
	 */
	public Entry getEntry() {
		return entry;
	}

	/**
	 * <!-- begin-UML-doc --> <!-- end-UML-doc -->
	 * 
	 * @generated 
	 *            "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private void createLabel() {
		// begin-user-code

		// Create the Label
		label = new Label(this, 0);
		label.setText(entry.getName() + ":");
		label.setToolTipText(entry.getDescription());
		label.setBackground(Display.getCurrent()
				.getSystemColor(SWT.COLOR_WHITE));

		return;
		// end-user-code
	}

	/**
	 * <!-- begin-UML-doc --> <!-- end-UML-doc -->
	 * 
	 * @generated 
	 *            "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private void createCheckbox() {
		// begin-user-code

		// Local Declarations
		Button tmpButton = null;

		// Create the check box
		tmpButton = new Button(this, SWT.CHECK);
		tmpButton.setText(entry.getName());
		tmpButton.setToolTipText(entry.getDescription());

		// Set the box to be checked if the current entry value is one of the
		// "positive" answers from the allowed values
		if (allowedBinaryValues.subList(0, 5).contains(
				entry.getValue().toLowerCase())) {
			tmpButton.setSelection(true);
		} else {
			// Otherwise unchecked
			tmpButton.setSelection(false);
		}
		// Add the listeners
		tmpButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				// Notify any listeners that the selection has
				// changed
				EntryComposite.this.notifyListeners(SWT.Selection, new Event());
				// Get the index of the value
				int index = lowercaseAllowedValues.indexOf(entry.getValue()
						.toLowerCase());
				// Set the correct value
				String value = null;
				value = (index == 0) ? entry.getAllowedValues().get(1) : entry
						.getAllowedValues().get(0);
				EntryComposite.this.setEntryValue(value);
				System.out.println("EntryComposite Message: Updated Entry "
						+ entry.getName() + " with value=" + entry.getValue());
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// Notify any listeners that the selection has
				// changed
				EntryComposite.this.notifyListeners(SWT.Selection, new Event());
				// Get the index of the value
				int index = lowercaseAllowedValues.indexOf(entry.getValue()
						.toLowerCase());
				// Set the correct value
				String value = null;
				value = (index == 0) ? entry.getAllowedValues().get(1) : entry
						.getAllowedValues().get(0);
				EntryComposite.this.setEntryValue(value);
				System.out.println("EntryComposite Message: Updated Entry "
						+ entry.getName() + " in registry with value="
						+ entry.getValue());
			}
		});

		// Add the button to the list
		tmpButton.setBackground(Display.getCurrent().getSystemColor(
				SWT.COLOR_WHITE));
		buttons.add(tmpButton);

		// end-user-code
	}

	/**
	 * <!-- begin-UML-doc -->
	 * <p>
	 * This operation creates buttons on the Composite.
	 * </p>
	 * <!-- end-UML-doc -->
	 * 
	 * @generated 
	 *            "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private void createButtons() {
		// begin-user-code

		// Local Declarations
		Button tmpButton = null;

		// Create the radio buttons
		for (String i : entry.getAllowedValues()) {
			// Create the button and set its text fields
			tmpButton = new Button(this, SWT.RADIO);
			tmpButton.setText(i);
			tmpButton.setToolTipText(entry.getDescription());
			// Select the default value if it is this one
			if (i.equals(entry.getValue())) {
				tmpButton.setSelection(true);
			}
			// Add the listeners
			tmpButton.addSelectionListener(new SelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					// Notify any listeners that the selection has
					// changed
					EntryComposite.this.notifyListeners(SWT.Selection,
							new Event());
					// Set the value of the Entry
					EntryComposite.this.setEntryValue(((Button) e.getSource())
							.getText());
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
					// Notify any listeners that the selection has
					// changed
					EntryComposite.this.notifyListeners(SWT.Selection,
							new Event());
					// Set the value of the Entry
					EntryComposite.this.setEntryValue(((Button) e.getSource())
							.getText());
				}
			});
			// Fix the color
			tmpButton.setBackground(Display.getCurrent().getSystemColor(
					SWT.COLOR_WHITE));
			// Add the button to the list
			buttons.add(tmpButton);
		}

		return;
		// end-user-code
	}

	/**
	 * <!-- begin-UML-doc -->
	 * <p>
	 * This operation creates a drop-down menu on the Composite.
	 * </p>
	 * <!-- end-UML-doc -->
	 * 
	 * @generated 
	 *            "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private void createDropdown() {
		// begin-user-code

		// Create a drop-down menu
		dropDown = new Combo(this, SWT.DROP_DOWN | SWT.SINGLE | SWT.V_SCROLL
				| SWT.H_SCROLL | SWT.READ_ONLY);
		dropDown.setBackground(Display.getCurrent().getSystemColor(
				SWT.COLOR_WHITE));

		// Add the data to the dropdown menu
		for (String i : entry.getAllowedValues()) {
			dropDown.add(i);
		}
		// Set the default selection
		dropDown.select(dropDown.indexOf(entry.getValue()));
		// Add the listener
		dropDown.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				// Notify any listeners that the selection has changed
				EntryComposite.this.notifyListeners(SWT.Selection, new Event());
				// Set the value of the Entry
				EntryComposite.this.setEntryValue(dropDown.getItem(dropDown
						.getSelectionIndex()));
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// Notify any listeners that the selection has changed
				EntryComposite.this.notifyListeners(SWT.Selection, new Event());
				// Set the value of the Entry
				EntryComposite.this.setEntryValue(dropDown.getItem(dropDown
						.getSelectionIndex()));

			}
		});

		return;
		// end-user-code
	}

	/**
	 * <!-- begin-UML-doc -->
	 * <p>
	 * This operation creates a textfield on the Composite.
	 * </p>
	 * <!-- end-UML-doc -->
	 * 
	 * @generated 
	 *            "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private void createTextfield() {
		// begin-user-code

		// Create a textfield
		if (!entry.isSecret()) {
			text = new Text(this, SWT.LEFT | SWT.BORDER);
		} else {
			text = new Text(this, SWT.LEFT | SWT.BORDER | SWT.PASSWORD);
		}
		text.setToolTipText(entry.getDescription());
		text.setText(entry.getValue());
		text.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));

		// Add the Focus Listeners
		text.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
			};

			@Override
			public void focusLost(FocusEvent e) {
				// Notify any listeners that the selection has changed
				EntryComposite.this.notifyListeners(SWT.Selection, new Event());
				// Set the value of the Entry
				EntryComposite.this.setEntryValue(text.getText());

			};

		});

		// Add a listener for the "DefaultSelection" key (return/enter). It
		// needs to be registered with the EntryComposite and the Text widget,
		// in case it is being used by JFace, which doesn't always post standard
		// SWT events.
		Listener enterListener = new Listener() {
			public void handleEvent(Event e) {
				// Notify any listeners that the selection has changed
				EntryComposite.this.notifyListeners(SWT.Selection, new Event());
				// Set the value of the Entry
				EntryComposite.this.setEntryValue(text.getText());

			}
		};
		this.addListener(SWT.DefaultSelection, enterListener);
		text.addListener(SWT.DefaultSelection, enterListener);

		return;
		// end-user-code
	}

	/**
	 * This method creates a browse button on the EntryComposite. Clicking the
	 * button opens a file browser, and once a file is selected, the file is
	 * imported into the default workspace.
	 */
	private void createBrowseButton() {
		
		// Create a new button, set the text
		Button browseButton = new Button(this, SWT.PUSH);
		browseButton.setText("Browse...");
		
		// Add an event listener that displays a Directory Dialog prompt
		browseButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				// Notify any listeners that the selection has changed
				EntryComposite.this.notifyListeners(SWT.Selection, new Event());
				// Get the Client
				IClient client = ClientHolder.getClient();

				// Create the dialog and get the files
				FileDialog fileDialog = new FileDialog(getShell());
				fileDialog.setText("Select a file to import into ICE");
				String fileName = fileDialog.open();
				if (fileName != null) {
					// Import the files
					File importedFile = new File(fileName);
					client.importFile(importedFile.toURI());
					File file = new File(fileName);
					EntryComposite.this.setEntryValue(file.getName());
				}

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// Notify any listeners that the selection has changed
				EntryComposite.this.notifyListeners(SWT.Selection, new Event());
				// Get the Client
				IClient client = ClientHolder.getClient();

				// Create the dialog and get the files
				FileDialog fileDialog = new FileDialog(getShell());
				fileDialog.setText("Select a file to import into ICE");
				String fileName = fileDialog.open();
				if (fileName != null) {
					// Import the files
					File importedFile = new File(fileName);
					client.importFile(importedFile.toURI());
					EntryComposite.this.setEntryValue(fileName);
				}

			}
		});
		
		// Add the browse button
		buttons.add(browseButton);
		
		return;
	}

	/**
	 * <!-- begin-UML-doc -->
	 * <p>
	 * This operation renders the SWT widgets for the Entry.
	 * </p>
	 * <!-- end-UML-doc -->
	 * 
	 * @generated 
	 *            "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	protected void render() {
		// begin-user-code

		// Local Declarations
		int numAllowedValues = 0, maxValueLength = 12, maxShortValues = 8;
		boolean shortValues = true;
		AllowedValueType valueType = null;

		// Make all of the allowed values lowercase
		for (String value : entry.getAllowedValues()) {
			lowercaseAllowedValues.add(value.toLowerCase());
		}

		// Set the allowed value type
		valueType = entry.getValueType();
		// Get the number of allowed values
		numAllowedValues = entry.getAllowedValues().size();
		// Figure out if the allowed values are small in length. This
		// will let us draw it in a more intuitive way, but it only
		// takes one value that is not short to ruin the view.
		for (String i : entry.getAllowedValues()) {
			if (i.length() > maxValueLength) {
				shortValues = false;
				break;
			}
		}

		if (getLayout() == null) {
			// Set the Composite's layout
			FillLayout layout = new FillLayout(SWT.VERTICAL);
			layout.marginHeight = 5;
			layout.marginWidth = 3;
			layout.spacing = 5;
			setLayout(layout);
		}

		// If the valueType is Discrete and there are some allowed values, 
		// figure out how to draw it
		if (valueType == AllowedValueType.Discrete && numAllowedValues > 0) {
			// We can use Radio buttons if the allowed values are few
			if (numAllowedValues <= maxShortValues && shortValues) {
				// Check to see if this is something that should use a check box
				if (numAllowedValues == 2
						&& allowedBinaryValues
								.containsAll(lowercaseAllowedValues)) {
					createCheckbox();
				} else {
					// Otherwise create the regular button set
					createLabel();
					createButtons();
				}
			} else {
				// Otherwise we can use a drop-down menu
				createLabel();
				createDropdown();
			}
		} else if (valueType == AllowedValueType.Discrete) {
			// If no values were found, throw an error
			throwMissingValuesError();
		} else if (valueType == AllowedValueType.File) {
			// If this is a File entry, draw dropdown (if applicable)
			// and browse button
			createLabel();
			if (numAllowedValues > 0) {
				createDropdown();
			}
			createBrowseButton();		
		} else {
			// Otherwise create a text field
			createLabel();
			createTextfield();
		}
		
		return;
		// end-user-code
	}

	/**
	 * This operation will post a message to the message manager (if one exists)
	 * if the Entry has no value, but requires a value from a discrete set.
	 */
	private void throwMissingValuesError() {
		
		if (messageManager != null) {
			// Get the message
			String errorMessage = entry.getErrorMessage();
			// Post it if it exists
			if (errorMessage != null) {
				// Display the error at the top of the screen
				if (messageManager != null) {
					messageManager.addMessage(messageName, errorMessage, null,
							IMessageProvider.ERROR);
				}
				// Highlight the text if it is in a text box
				if (text != null) {
					Color color = new Color(Display.getCurrent(), 200, 0, 0);
					text.setForeground(color);
					FontData fontData = new FontData();
					fontData.setStyle(SWT.BOLD);
					Font font = new Font(getDisplay(), fontData);
					text.setFont(font);
				}
			}
		}

		return;
	}

	/**
	 * <!-- begin-UML-doc -->
	 * <p>
	 * This operation directs the EntryComposite to refresh its view of the
	 * Entry. This should be called in the event that the Entry has changed on
	 * the file system and the view needs to be updated.
	 * </p>
	 * <!-- end-UML-doc -->
	 * 
	 * @generated 
	 *            "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void refresh() {
		// begin-user-code

		// Print an error if this Entry has been prematurely disposed.
		if (isDisposed()) {
			System.out.println("EntryComposite Message: "
					+ "This composite has been prematurely disposed!");
		}

		// Dispose of the old widgets
		if (dropDown != null) {
			dropDown.dispose();
		}
		if (label != null) {
			label.dispose();
		}
		if (text != null) {
			text.dispose();
		}
		for (Button button : buttons) {
			if (!button.isDisposed()) {
				button.dispose();
			}
		}

		// Re-render the Composite
		render();

		// Re-draw the Composite
		layout();

		return;
		// end-user-code
	}

	/**
	 * <!-- begin-UML-doc -->
	 * <p>
	 * This operation sets the Message Manager that should be used by the
	 * EntryComposite to post messages about the Entry. If the Message Manager
	 * is not set, the EntryComposite will not attempt to post messages.
	 * </p>
	 * <!-- end-UML-doc -->
	 * 
	 * @param manager
	 *            <p>
	 *            The Message Manager that the EntryComposite should use.
	 *            </p>
	 * @generated 
	 *            "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void setMessageManager(IMessageManager manager) {
		// begin-user-code

		// Set the messageManager
		messageManager = manager;

		return;
		// end-user-code
	}

	/**
	 * <!-- begin-UML-doc -->
	 * <p>
	 * This operation sets the value of the Entry and, if possible and
	 * necessary, reports to the message manager.
	 * </p>
	 * <!-- end-UML-doc -->
	 * 
	 * @param value
	 * @generated 
	 *            "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	protected void setEntryValue(String value) {
		// begin-user-code

		// Set the value and post a message if necessary
		if (!entry.setValue(value) && messageManager != null) {
			// Get the message
			String errorMessage = entry.getErrorMessage();
			// Post it if it exists
			if (errorMessage != null) {
				// Display the error at the top of the screen
				if (messageManager != null) {
					messageManager.addMessage(messageName, errorMessage, null,
							IMessageProvider.ERROR);
				}
				// Highlight the text if it is in a text box
				if (text != null) {
					Color color = new Color(Display.getCurrent(), 200, 0, 0);
					text.setForeground(color);
					FontData fontData = new FontData();
					fontData.setStyle(SWT.BOLD);
					Font font = new Font(getDisplay(), fontData);
					text.setFont(font);
				}
			}
		} else {
			// Remove a posted message if necessary
			if (messageManager != null) {
				messageManager.removeMessage(messageName);
			}
			// Remove the text box highlight if it is in a text box
			if (text != null) {
				Color color = new Color(Display.getCurrent(), 0, 0, 0);
				text.setForeground(color);
				FontData fontData = new FontData();
				fontData.setStyle(SWT.NORMAL);
				fontData.setHeight(10);
				Font font = new Font(getDisplay(), fontData);
				text.setFont(font);
			}

		}

		return;
		// end-user-code
	}

	int hitCounter = 0;

	/**
	 * Listen for updates from the Entry and redraw if needed.
	 */
	public void update(IUpdateable component) {
		// When the Entry has updated, refresh on the Eclipse UI thread.
		if (component == entry) {
			PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
				public void run() {
					refresh();
				}
			});
		}
		++hitCounter;
		return;
	}
}