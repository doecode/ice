/*******************************************************************************
 * Copyright (c) 2014 UT-Battelle, LLC.
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
package org.eclipse.ice.datastructures.test;

import org.eclipse.ice.datastructures.form.ResourceComponent;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;
import org.eclipse.ice.datastructures.resource.ICEResource;

/**
 * <!-- begin-UML-doc -->
 * <p>
 * The ResourceComponentTester class is responsible for testing the
 * functionality of the ResourceComponent class.
 * </p>
 * <!-- end-UML-doc -->
 * 
 * @author Jay Jay Billings
 * @generated 
 *            "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public class ResourceComponentTester {
	/**
	 * <!-- begin-UML-doc --> <!-- end-UML-doc -->
	 * 
	 * @generated 
	 *            "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private ResourceComponent resourceComponent;
	/**
	 * <!-- begin-UML-doc --> <!-- end-UML-doc -->
	 * 
	 * @generated 
	 *            "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private TestComponentListener testComponentListener;
	/**
	 * <!-- begin-UML-doc --> <!-- end-UML-doc -->
	 * 
	 * @generated 
	 *            "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private TestVisitor testVisitor;

	/**
	 * <!-- begin-UML-doc -->
	 * <p>
	 * This operation checks the construction of ResourceComponents to insure
	 * that the contract with ICEObject is not broken, amongst other things.
	 * </p>
	 * <!-- end-UML-doc -->
	 * 
	 * @generated 
	 *            "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	@Test
	public void checkCreation() {
		// begin-user-code

		// Local declarations
		int id = 2266;
		String name = "3F 127 on Deck 9, section 2";
		String description = "Bones' Quarters";

		// Create the ResourceComponent
		resourceComponent = new ResourceComponent();

		// Set the id, name and description
		resourceComponent.setId(id);
		resourceComponent.setDescription(description);
		resourceComponent.setName(name);

		// Check the id, name and description
		assertEquals(resourceComponent.getDescription(), description);
		assertEquals(resourceComponent.getId(), id);
		assertEquals(resourceComponent.getName(), name);
		// end-user-code
	}

	/**
	 * <!-- begin-UML-doc -->
	 * <p>
	 * This operation checks the ResourceComponents ability to add Resources to
	 * its list.
	 * </p>
	 * <!-- end-UML-doc -->
	 * 
	 * @generated 
	 *            "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	@Test
	public void checkResources() {
		// begin-user-code

		// Local Declarations
		ArrayList<ICEResource> resources = new ArrayList<ICEResource>();
		ArrayList<ICEResource> retResources = null;

		// Create the ResourceComponent
		resourceComponent = new ResourceComponent();

		// Create five test resources - this one themed after places where Bones
		// lived
		try {
			resources.add(new ICEResource(new File("Mississippi.testFile")));
			resources.add(new ICEResource(new File("Enterprise.testFile")));
			resources.add(new ICEResource(new File("EnterpriseA.testFile")));
			resources.add(new ICEResource(new File("DramiaII.testFile")));
			resources.add(new ICEResource(new File("CapellaIV.testFile")));
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		}

		// Add the Resources to the ResourceComponent
		for (ICEResource i : resources) {
			resourceComponent.addResource(i);
		}

		// Get the list of Resources and check it
		retResources = resourceComponent.getResources();
		assertNotNull(retResources);
		assertEquals(resources, retResources);

		return;

		// end-user-code
	}

	/**
	 * <!-- begin-UML-doc -->
	 * <p>
	 * This operation checks the visitation capabilities of the
	 * ResourceComponent.
	 * </p>
	 * <!-- end-UML-doc -->
	 * 
	 * @generated 
	 *            "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	@Test
	public void checkVisitation() {
		// begin-user-code

		// Setup the visitor
		testVisitor = new TestVisitor();

		// Setup the ResourceComponent
		resourceComponent = new ResourceComponent();

		// Send the visitor
		resourceComponent.accept(testVisitor);

		// Check the visitor
		assertTrue(testVisitor.wasVisited());

		return;
		// end-user-code
	}

	/**
	 * <!-- begin-UML-doc -->
	 * <p>
	 * This operation checks the ability of the ResourceComponent to notify
	 * listeners when it has been updated. That is, when the list of Resources
	 * has changed in some way or the identifying information of the component
	 * has changed.
	 * </p>
	 * <!-- end-UML-doc -->
	 * 
	 * @generated 
	 *            "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	@Test
	public void checkNotifications() {
		// begin-user-code

		// Setup the listener
		testComponentListener = new TestComponentListener();

		// Setup the ResourceComponent
		resourceComponent = new ResourceComponent();

		// Register the listener
		resourceComponent.register(testComponentListener);

		// Create a new Resource in the ResourceComponent
		try {
			resourceComponent.addResource(new ICEResource(new File(
					"SickBay.testFile")));
		} catch (IOException e1) {
			e1.printStackTrace();
			fail();
		}

		// Check the Listener
		assertTrue(testComponentListener.wasNotified());

		// Reset the listener
		testComponentListener.reset();

		// Add a second Resource, just to make sure.
		try {
			resourceComponent.addResource(new ICEResource(new File(
					"ShoreLeavePlanet.testFile")));
		} catch (IOException e1) {
			e1.printStackTrace();
			fail();
		}

		// Check the listener to make sure it was updated
		assertTrue(testComponentListener.wasNotified());

		return;

		// end-user-code
	}

	/**
	 * <!-- begin-UML-doc -->
	 * <p>
	 * This operation checks the ResourceComponent to insure that its equals()
	 * operation works.
	 * </p>
	 * <!-- end-UML-doc -->
	 * 
	 * @generated 
	 *            "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	@Test
	public void checkEquality() {
		// begin-user-code

		// Create ResourceComponents to test
		ResourceComponent outComp1 = new ResourceComponent();
		ResourceComponent equalComp = new ResourceComponent();
		ResourceComponent unEqual = new ResourceComponent();
		ResourceComponent transitiveComp = new ResourceComponent();
		try {
			outComp1.addResource(new ICEResource(new File("TestFile.test")));
			outComp1.addResource(new ICEResource(
					new File("SecondTestFile.test")));

			// Add equal files to equalComp
			equalComp.addResource(new ICEResource(new File("TestFile.test")));
			equalComp.addResource(new ICEResource(new File(
					"SecondTestFile.test")));

			// Create unequal ResourceComponent
			unEqual.addResource(new ICEResource(new File("OtherFile.test")));
			unEqual.addResource(new ICEResource(new File("HelloFile.file")));

			// Create equal File for transitive test
			transitiveComp.addResource(new ICEResource(
					new File("TestFile.test")));
			transitiveComp.addResource(new ICEResource(new File(
					"SecondTestFile.test")));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Assert two equal ResourceComponents return true
		assertTrue(outComp1.equals(equalComp));

		// Assert two unequal ResourceComponents return false
		assertFalse(outComp1.equals(unEqual));

		// Assert equals() is reflexive
		assertTrue(outComp1.equals(outComp1));

		// Check that equals() is Symmetric
		assertTrue(outComp1.equals(equalComp) && equalComp.equals(outComp1));

		// Check equals() is transitive
		if (outComp1.equals(equalComp) && equalComp.equals(transitiveComp)) {
			assertTrue(outComp1.equals(transitiveComp));
		} else {
			fail();
		}

		// Check consistent nature of equals()
		assertTrue(outComp1.equals(equalComp) && outComp1.equals(equalComp)
				&& outComp1.equals(equalComp));
		assertTrue(!outComp1.equals(unEqual) && !outComp1.equals(unEqual)
				&& !outComp1.equals(unEqual));

		// Assert checking equality with null value is false
		assertFalse(outComp1==null);

		// Assert that two equal objects return the same hashcode
		assertTrue(outComp1.equals(equalComp)
				&& (outComp1.hashCode() == equalComp.hashCode()));

		// Assert that hashcode is consistent
		assertTrue(outComp1.hashCode() == outComp1.hashCode());

		// Assert hashcodes for unequal objects are different
		assertFalse(outComp1.hashCode() == unEqual.hashCode());

		// end-user-code
	}

	/**
	 * <!-- begin-UML-doc -->
	 * <p>
	 * This operation checks the ResourceComponent to ensure that its copy() and
	 * clone() operations work as specified.
	 * </p>
	 * <!-- end-UML-doc -->
	 * 
	 * @generated 
	 *            "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	@Test
	public void checkCopying() {
		// begin-user-code
		// TODO Auto-generated method stub
		/*
		 * The following sets of operations will be used to test the
		 * "clone and copy" portion of ResourceComponent.
		 */

		// Test to show valid usage of clone

		// Local Declarations
		ArrayList<ICEResource> resources = new ArrayList<ICEResource>();
		ArrayList<ICEResource> retResources = null;
		TestComponentListener listener = new TestComponentListener();

		// Local declarations
		int id = 2266;
		String name = "3F 127 on Deck 9, section 2";
		String description = "Bones' Quarters";

		// Create the ResourceComponent
		resourceComponent = new ResourceComponent();

		// Create five test resources - this one themed after places where Bones
		// lived
		try {
			resources.add(new ICEResource(new File("Mississippi.testFile")));
			resources.add(new ICEResource(new File("Enterprise.testFile")));
			resources.add(new ICEResource(new File("EnterpriseA.testFile")));
			resources.add(new ICEResource(new File("DramiaII.testFile")));
			resources.add(new ICEResource(new File("CapellaIV.testFile")));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

		// Add the Resources to the ResourceComponent
		for (ICEResource i : resources) {
			resourceComponent.addResource(i);
		}

		// Set the id, name and description
		resourceComponent.setId(id);
		resourceComponent.setDescription(description);
		resourceComponent.setName(name);

		// add listener
		resourceComponent.register(listener);

		// Run clone operation
		ResourceComponent cloneOutput = (ResourceComponent) resourceComponent
				.clone();

		// Check contents
		assertEquals(resourceComponent.getDescription(),
				cloneOutput.getDescription());
		assertEquals(resourceComponent.getId(), cloneOutput.getId());
		assertEquals(resourceComponent.getName(), cloneOutput.getName());
		assertEquals(resourceComponent.getResources(),
				cloneOutput.getResources());

		// Test to show valid usage of copy
		// Use resourceComponent from above

		// Create a new instance of ResourceComponent and copy contents
		ResourceComponent copyOutput = new ResourceComponent();
		copyOutput.copy(resourceComponent);

		// Check contents
		assertEquals(resourceComponent.getDescription(),
				copyOutput.getDescription());
		assertEquals(resourceComponent.getId(), copyOutput.getId());
		assertEquals(resourceComponent.getName(), copyOutput.getName());
		assertEquals(resourceComponent.getResources(),
				copyOutput.getResources());

		// check listener - sleep to make sure thread updates
		File file = new File("testA");
		ICEResource resource = null;
		try {
			resource = new ICEResource(file);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// Test to show an invalid use of copy - null args

		// Call copy with null, which should not change anything
		copyOutput.copy(null);

		// Check contents - nothing has changed - check with resourceComponent
		assertEquals(resourceComponent.getDescription(),
				copyOutput.getDescription());
		assertEquals(resourceComponent.getId(), copyOutput.getId());
		assertEquals(resourceComponent.getName(), copyOutput.getName());
		assertEquals(resourceComponent.getResources(),
				copyOutput.getResources());

		// check listener - sleep to make sure thread updates
		file = new File("testA");
		resource = null;
		try {
			resource = new ICEResource(file);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return;
		// end-user-code
	}

	/**
	 * <!-- begin-UML-doc -->
	 * <p>
	 * This operation checks the ability of the ResourceComponent class to
	 * persist itself to XML and to load itself from an XML input stream.
	 * </p>
	 * <!-- end-UML-doc -->
	 * 
	 * @generated 
	 *            "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	@Test
	public void checkXMLPersistence() {
		// begin-user-code
		// TODO Auto-generated method stub
		/*
		 * The following sets of operations will be used to test the
		 * "read and write" portion of the ResourceComponent. It will
		 * demonstrate the behavior of reading and writing from an
		 * "XML (inputStream and outputStream)" file. It will use an annotated
		 * ResourceComponent to demonstrate basic behavior.
		 */

		// Local declarations
		ResourceComponent testRC = null, testRC2 = null;
		ArrayList<ICEResource> resources = new ArrayList<ICEResource>();
		ArrayList<ICEResource> retResources = null;
		int id = 2266;
		String name = "3F 127 on Deck 9, section 2";
		String description = "Bones' Quarters";

		// Set up file path - for resources
		File file = new File("Mississippi.testFile");
		File file2 = new File("Enterprise.testFile");
		String filePath2 = null;
		String filePath = null;
		filePath = file.toURI().toASCIIString();
		filePath2 = file2.toURI().toASCIIString();

		// Create the ResourceComponent
		testRC = new ResourceComponent();

		// Create two test resources - this one themed after places where Bones
		// lived
		try {
			resources.add(new ICEResource(file));
			resources.add(new ICEResource(file2));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

		// Add the Resources to the ResourceComponent
		for (ICEResource i : resources) {
			testRC.addResource(i);
		}

		// Set the id, name and description
		testRC.setId(id);
		testRC.setDescription(description);
		testRC.setName(name);

		// Demonstrate a basic "write" to file. Should not fail

		// persist to an output stream
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		testRC.persistToXML(outputStream);

		// Load back in

		// Initialize object and pass inputStream to read()
		ByteArrayInputStream inputStream = new ByteArrayInputStream(
				outputStream.toByteArray());

		// create a new instance of a different variable to compare
		testRC2 = new ResourceComponent();

		// load into ResourceComponent();
		testRC2.loadFromXML(inputStream);

		// check contents
		assertTrue(testRC.equals(testRC2));

		// The next following tests demonstrate behavior for when you pass null
		// args for read()

		// test for read - null args
		testRC.loadFromXML(null);

		// checkContents - nothing has changed - compared to testOC2
		// check contents
		assertTrue(testRC.equals(testRC2));

		// args for write() - null args
		outputStream = null;
		testRC.persistToXML(outputStream);

		// checkContents - nothing has changed - compared to testOC2
		// check contents
		assertTrue(testRC.equals(testRC2));

		// end-user-code
	}

	/**
	 * <!-- begin-UML-doc -->
	 * <p>
	 * An operation that checks the clear operation on ResourceComponent.
	 * </p>
	 * <!-- end-UML-doc -->
	 * 
	 * @generated 
	 *            "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void checkClear() {
		// begin-user-code
		// Local declarations
		ArrayList<ICEResource> resources = new ArrayList<ICEResource>();
		ResourceComponent resComponent = null;

		// Set up file path - for resources
		File file = new File("Mississippi.testFile");
		File file2 = new File("Enterprise.testFile");

		// Create the ResourceComponent
		resComponent = new ResourceComponent();

		// Create two test resources -
		try {
			resources.add(new ICEResource(file));
			resources.add(new ICEResource(file2));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

		// Add the Resources to the ResourceComponent
		for (ICEResource i : resources) {
			resComponent.addResource(i);
		}

		// check that ResourceComponent has resources
		assertNotNull(resComponent.getResources());
		assertEquals(2, resComponent.getResources().size());

		// clear the resources
		resComponent.clearResources();

		// check that ResourceComponent has no resources
		assertNotNull(resComponent.getResources());
		assertEquals(0, resComponent.getResources().size());

		// Try to clear a ResourceComponent with no Resources
		// Should still work functionally

		// clear the resources
		resComponent.clearResources();

		// check that ResourceComponent has no resources
		assertNotNull(resComponent.getResources());
		assertEquals(0, resComponent.getResources().size());

		// end-user-code
	}
}