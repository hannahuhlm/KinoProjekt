/**
 */
package kino;

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
 *   <li>{@link kino.Kino#getKunden <em>Kunden</em>}</li>
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
	 * Returns the value of the '<em><b>Kunden</b></em>' reference list.
	 * The list contents are of type {@link kino.Kunde}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Kunden</em>' reference list.
	 * @see kino.KinoPackage#getKino_Kunden()
	 * @model required="true"
	 * @generated
	 */
	EList<Kunde> getKunden();

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
	double einnahmenBerechnen(Auffuehrung auffuehrung);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	double einnahmenBerechnen(Film film);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	Sitzreihe sitzreiheAnlegen(int nummer, SitzreihenKategorie kategorie, int sitzAnzahl, Kinosaal saal);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	void kundeAnlegen(String name, String email);

} // Kino
