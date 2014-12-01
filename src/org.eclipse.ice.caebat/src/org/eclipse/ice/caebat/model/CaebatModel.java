/*******************************************************************************
 * Copyright (c) 2013, 2014 UT-Battelle, LLC.
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
package org.eclipse.ice.caebat.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ice.datastructures.form.AllowedValueType;
import org.eclipse.ice.datastructures.form.DataComponent;
import org.eclipse.ice.datastructures.form.Entry;
import org.eclipse.ice.datastructures.form.Form;
import org.eclipse.ice.datastructures.form.FormStatus;
import org.eclipse.ice.datastructures.form.MasterDetailsComponent;
import org.eclipse.ice.datastructures.form.TableComponent;
import org.eclipse.ice.datastructures.form.TimeDataComponent;
import org.eclipse.ice.datastructures.updateableComposite.Component;
import org.eclipse.ice.io.ips.IPSReader;
import org.eclipse.ice.io.ips.IPSWriter;
import org.eclipse.ice.item.Item;

/**
 * <!-- begin-UML-doc -->
 * <p>
 * This class is the model representation of the CAEBAT model. It inherits from
 * the Item Class. It will obtain it's specified entries for each DataComponent
 * from XML files stored in the plugin's directory under a folder titled
 * caebatModel. The files are hard coded in the loadDataComponent's operation,
 * and should be adjusted as DataComponent files are added or deleted. Once all
 * the data is correctly specified, an xml file will be generated to be used by
 * the launcher or a dat file (to be deprecated) for Caebat's runtime. All of
 * the required DataComponents need to exist inside the project's workspace
 * under Caebat's folder with the correct extension, otherwise it will refuse to
 * process the item.
 * 
 * This class will also grab complete forms that store entire "use cases" under
 * a folder called "caebatCases".
 * 
 * All files must be specified within their operations in order to be picked up,
 * otherwise they will be completely ignored.
 * </p>
 * <!-- end-UML-doc -->
 * 
 * @author s4h, bzq
 */
@XmlRootElement(name = "CaebatModel")
public class CaebatModel extends Item {

	/**
	 * <!-- begin-UML-doc -->
	 * <p>
	 * A custom tag for ini files operation. Set in the constructor.
	 * </p>
	 * <!-- end-UML-doc -->
	 */
	private String customTaggedExportString = "Export to Caebat INI format";

	// The name of the example chosen
	protected String exampleName; // Default for now

	/**
	 * A nullary constructor that delegates to the project constructor.
	 */
	public CaebatModel() {
		this(null);
		return;
	}

	/**
	 * <!-- begin-UML-doc -->
	 * <p>
	 * The constructor for the CaebatModel. Calls the constructor for Item by
	 * passing the IProject. It should call setupForm() in the super
	 * constructor.
	 * </p>
	 * <!-- end-UML-doc -->
	 * 
	 * @param project
	 *            <p>
	 *            The passed IProject for the workspace.
	 *            </p>
	 */
	public CaebatModel(IProject project) {

		// begin-user-code

		// Setup the form and everything
		super(project);
		return;
		// end-user-code

	}

	/**
	 * <!-- begin-UML-doc -->
	 * <p>
	 * This operation overrides the Item.setupForm() operation.
	 * </p>
	 * <!-- end-UML-doc -->
	 */
	public void setupForm() {
		// begin-user-code

		// This method will create a new Form and add all the dataComponents to
		// the form. These dataComponents will be accessed later in
		// loadDataComponents.
		form = new Form();
		ArrayList<String> problemFiles = null;
		String separator = System.getProperty("file.separator");

		// Setup Item information
		setName("Caebat Model");
		setDescription("This model creates input for CAEBAT.");

		// Get the Caebat IPS files from the project
		if (project != null && project.isAccessible()) {

			IFolder caebatFolder = getPreferencesDirectory();

			if (caebatFolder != null && caebatFolder.exists()) {
				try {

					// Grab the list of problem files in the Caebat directory
					problemFiles = getProjectFiles(caebatFolder);

					// Create the DataComponent that selects which problem
					// to load
					form.addComponent(createSelectorComponent(problemFiles));

					// If the list of problem files is valid
					if (problemFiles != null && !(problemFiles.isEmpty())) {

						// Push the work onto the loader
						loadExample(caebatFolder.getLocation().toOSString()
								+ separator + problemFiles.get(0));
					}

				} catch (FileNotFoundException e) {
					// Complain
					if (debuggingEnabled) {
						System.err.println("CaebatModel Message: "
								+ "Unable to find INI file.");
					}
					e.printStackTrace();
				} catch (IOException e) {
					// Complain
					if (debuggingEnabled) {
						System.err.println("CaebatModel Message: "
								+ "Unable to load INI file.");
					}
					e.printStackTrace();
				}
			}
		}

		// Add an action to the list to allow for the INI exports
		customTaggedExportString = "Export to Caebat INI format";
		allowedActions.add(0, customTaggedExportString);

		// ----- Finish setting up the Form so that it can be immediately
		// launched

		// start with a thing that scans for files in the workspace
		// then a user will select one of the files
		// the file will be read in via the ipsReader
		// each of the data components will be parsed into their spots

		return;
		// end-user-code

	}

	/**
	 * <!-- begin-UML-doc -->
	 * <p>
	 * Overrides the reviewEntries operation. This will still call
	 * super.reviewEntries, but will handle the dependencies after all other dep
	 * handing is finished.
	 * </p>
	 * <!-- end-UML-doc -->
	 * 
	 * @return the status of the form
	 */
	protected FormStatus reviewEntries(Form preparedForm) {

		// begin-user-code
		FormStatus retStatus = FormStatus.ReadyToProcess;
		DataComponent dataComp = null;
		Entry problemEntry = null;
		String problemName = null;
		String separator = System.getProperty("file.separator");

		// Grab the data component from the Form and only proceed if it exists
		dataComp = (DataComponent) preparedForm.getComponent(1);
		if (dataComp != null
				&& "Caebat Input Problems".equals(dataComp.getName())) {

			problemEntry = dataComp.retrieveAllEntries().get(0);
			problemName = problemEntry.getValue();

			if (!problemName.equals(exampleName)) {

				IFolder caebatFolder = getPreferencesDirectory();
				String problemPathName = caebatFolder.getLocation()
						.toOSString() + separator + problemName;
				try {
					loadExample(problemPathName);
					System.out.println("CaebatModel Message: Loading File: "
							+ problemName);
					exampleName = problemName;
				} catch (FileNotFoundException e) {
					if (debuggingEnabled) {
						System.out
								.println("CaebatModel Message: Could not find file "
										+ problemName);
					}
					e.printStackTrace();
				} catch (IOException e) {
					if (debuggingEnabled) {
						System.out
								.println("CaebatModel Message: Could not read file "
										+ problemName);
					}
					e.printStackTrace();
				}
			}
		} else {
			// somehow verify that the form is filled in correctly
			// Call super submitForm
			retStatus = FormStatus.InfoError;
		}

		return retStatus;
	}

	/**
	 * <!-- begin-UML-doc -->
	 * <p>
	 * Overrides item's process by adding a customTaggedExportString (ini).
	 * Still utilizes Item's process functionality for all other calls.
	 * </p>
	 * <!-- end-UML-doc -->
	 */
	public FormStatus process(String actionName) {
		// begin-user-code
		FormStatus retStatus;

		// If it is the custom operation, call this here.
		if (this.customTaggedExportString.equals(actionName)) {

			// Get the file from the project space to create the output
			String filename = getName().replaceAll("\\s+", "_") + "_" + getId()
					+ ".conf";
			IFile outputFile = project.getFile(filename);
			// Get the file path
			String outputFilePath = outputFile.getLocation().toString();
			File iniFile = new File(outputFilePath);

			// Get the data from the form
			ArrayList<Component> components = form.getComponents();
			if (components.size() > 4) {

				// create a new IPSWriter with the output file
				IPSWriter writer = new IPSWriter();
				try {
					iniFile.createNewFile();
					writer.writeINIFile(components, iniFile);
					// Refresh the project space
					project.refreshLocal(IResource.DEPTH_ONE, null);
				} catch (IOException e1) {
					// Complain
					System.err.println("CaebatModel Message: "
							+ "Failed to write the file.");
					e1.printStackTrace();
				} catch (CoreException e) {
					// Complain
					System.err.println("CaebatModel Message: "
							+ "Failed to refresh the project space.");
					e.printStackTrace();
				}
				// ensure that the new file is all good
				// return a success
				retStatus = FormStatus.Processed;
			} else {
				System.err.println("Not enough components to write new file!");
				retStatus = FormStatus.InfoError;
			}

		}

		// Otherwise let item deal with the process
		else {
			retStatus = super.process(actionName);
		}

		return retStatus;
		// end-user-code

	}

	/**
	 * <!-- begin-UML-doc -->
	 * <p>
	 * Return a list of IPS files in the Caebat_Model folder of the project
	 * space or null if the folder doesn't exist
	 * </p>
	 * <!-- end-UML-doc -->
	 * 
	 * @return the list of input files
	 * 
	 */
	private ArrayList<String> getProjectFiles(IFolder caebatFolder) {

		// Local Declarations
		ArrayList<String> files = new ArrayList<String>();

		// Look for files in the project space and add them to the form
		// if (project != null) {
		try {
			// Get the Caebat folder
			// IFolder caebatFolder = getPreferencesDirectory();
			// if it exists, get any existing .conf files out
			// if (caebatFolder.exists()) {
			// Get all of the resources
			//files = new ArrayList<String>();
			//IResource[] resources = caebatFolder.members();
			for (IResource resource : caebatFolder.members()) {
				if (debuggingEnabled) {
					System.out.println("CaebatModel Message: " + "Found file "
							+ resource.getName() + ".");
				}
				// Only add the .conf files
				if (resource.getType() == IResource.FILE
						&& resource.getProjectRelativePath().lastSegment()
								.contains(".conf")) {
					files.add(resource.getName());
				}
			}
			// }
		} catch (CoreException e) {
			e.printStackTrace();
		}
		// }

		return files;
	}

	/**
	 * <!-- begin-UML-doc -->
	 * <p>
	 * 
	 * </p>
	 * <!-- end-UML-doc -->
	 * 
	 * @param files
	 * @return
	 */
	private DataComponent createSelectorComponent(final ArrayList<String> files) {

		// Local Declaration
		DataComponent filesComp = new DataComponent();
		Entry filesEntry = null;
		final String noFilesValue = "No input files available.";
		String entryName = "Available input files";
		String entryDesc = "The input problem file that will be loaded.";

		// Setup the data component
		filesComp.setName("Caebat Input Problems");
		filesComp.setDescription("The following is a list of Caebat input "
				+ "problems available to modify in ICE.");
		// Get the last component id in the form
		// Get the id based on the last component in the Form
		filesComp.setId(1);

		// Configure the values of the file Entry
		if (files != null && !(files.isEmpty())) {
			// Setup the Entry with the list of files
			filesEntry = new Entry() {
				protected void setup() {
					allowedValues.addAll(files);
					allowedValueType = AllowedValueType.Discrete;
				}
			};
		} else {
			// Setup the Entry with only value to describe that there are no
			// examples.
			filesEntry = new Entry() {
				protected void setup() {
					allowedValues.add(noFilesValue);
					allowedValueType = AllowedValueType.Discrete;
				}
			};
		}

		// Setup the file Entry's descriptive information
		filesEntry.setName(entryName);
		filesEntry.setDescription(entryDesc);
		filesEntry.setId(1);

		// Add the Entry to the Component
		filesComp.addEntry(filesEntry);

		return filesComp;
	}

	/**
	 * <!-- begin-UML-doc -->
	 * <p>
	 * This operation loads the given example into the Form.
	 * </p>
	 * <!-- end-UML-doc -->
	 * 
	 * @param name
	 *            The path name of the example file name to load.
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	private void loadExample(String name) throws FileNotFoundException,
			IOException {

		// Load the components from the file
		File file = new File(name);
		IPSReader reader = new IPSReader();
		ArrayList<Component> components = reader.loadINIFile(file);
		ArrayList<Component> existingComponents = form.getComponents();

		// Update the components by copying the new ones
		if (components != null) {
			// Add a dummy to account for the form's first entry
			// components.add(0, new DataComponent());

			// Remove the old components
			for (int i = 0; i < existingComponents.size(); i++) {
				form.removeComponent(i);
			}

			// Add the new components
			// form.addComponent(buildPortsTable((DataComponent)
			// components.get(1)));
			for (int i = 0; i < components.size(); i++) {
				form.addComponent(components.get(i));
			}
		}
	}

	private TableComponent buildPortsTable(DataComponent component) {
		TableComponent portsTable = new TableComponent();
		ArrayList<Entry> portsEntries = new ArrayList<Entry>();
		portsTable.setRowTemplate(portsEntries);
		return portsTable;
	}

}