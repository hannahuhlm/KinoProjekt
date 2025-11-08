/**
 */
package kino;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Kunde</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link kino.Kunde#getName <em>Name</em>}</li>
 *   <li>{@link kino.Kunde#getEmail <em>Email</em>}</li>
 *   <li>{@link kino.Kunde#getReservierungen <em>Reservierungen</em>}</li>
 *   <li>{@link kino.Kunde#getBuchungen <em>Buchungen</em>}</li>
 * </ul>
 *
 * @see kino.KinoPackage#getKunde()
 * @model
 * @generated
 */
public interface Kunde extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see kino.KinoPackage#getKunde_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link kino.Kunde#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Email</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Email</em>' attribute.
	 * @see #setEmail(String)
	 * @see kino.KinoPackage#getKunde_Email()
	 * @model
	 * @generated
	 */
	String getEmail();

	/**
	 * Sets the value of the '{@link kino.Kunde#getEmail <em>Email</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Email</em>' attribute.
	 * @see #getEmail()
	 * @generated
	 */
	void setEmail(String value);

	/**
	 * Returns the value of the '<em><b>Reservierungen</b></em>' reference list.
	 * The list contents are of type {@link kino.Reservierung}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Reservierungen</em>' reference list.
	 * @see kino.KinoPackage#getKunde_Reservierungen()
	 * @model
	 * @generated
	 */
	EList<Reservierung> getReservierungen();

	/**
	 * Returns the value of the '<em><b>Buchungen</b></em>' reference list.
	 * The list contents are of type {@link kino.Buchung}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Buchungen</em>' reference list.
	 * @see kino.KinoPackage#getKunde_Buchungen()
	 * @model
	 * @generated
	 */
	EList<Buchung> getBuchungen();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model plaetzeRequired="true" plaetzeMany="true"
	 * @generated
	 */
	Reservierung reservieren(Auffuehrung auffuehrung, EList<Sitzplatz> plaetze);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	void reservierungStornieren(Reservierung reservierung);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model plaetzeRequired="true" plaetzeMany="true"
	 * @generated
	 */
	Buchung direktBuchung(Auffuehrung auffuehrung, EList<Sitzplatz> plaetze);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	Buchung reservierungZuBuchungVerarbeiten(Reservierung reservierung);

} // Kunde
