/**
 */
package kino;

import java.util.Date;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Kino</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link kino.Kino#getName <em>Name</em>}</li>
 *   <li>{@link kino.Kino#getSaele <em>Saele</em>}</li>
 * </ul>
 *
 * @see kino.KinoPackage#getKino()
 * @model
 * @generated
 */
public interface Kino extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see kino.KinoPackage#getKino_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link kino.Kino#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Saele</b></em>' reference list.
	 * The list contents are of type {@link kino.Kinosaal}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Saele</em>' reference list.
	 * @see kino.KinoPackage#getKino_Saele()
	 * @model required="true"
	 * @generated
	 */
	EList<Kinosaal> getSaele();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	Kinosaal saalAnlegen(String name);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	Film filmAnlegen(String titel, int dauer, String beschreibung);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	double einnahmenBerechnen(Date intervallStart, Date intervallEnde);

} // Kino
