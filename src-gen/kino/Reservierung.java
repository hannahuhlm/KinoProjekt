/**
 */
package kino;

import java.util.Date;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Reservierung</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link kino.Reservierung#getReservierungsnummer <em>Reservierungsnummer</em>}</li>
 *   <li>{@link kino.Reservierung#getStartZeitstempel <em>Start Zeitstempel</em>}</li>
 *   <li>{@link kino.Reservierung#getKunde <em>Kunde</em>}</li>
 *   <li>{@link kino.Reservierung#getAuffuehrung <em>Auffuehrung</em>}</li>
 *   <li>{@link kino.Reservierung#getPlaetze <em>Plaetze</em>}</li>
 * </ul>
 *
 * @see kino.KinoPackage#getReservierung()
 * @model
 * @generated
 */
public interface Reservierung extends EObject {
	/**
	 * Returns the value of the '<em><b>Reservierungsnummer</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Reservierungsnummer</em>' attribute.
	 * @see #setReservierungsnummer(int)
	 * @see kino.KinoPackage#getReservierung_Reservierungsnummer()
	 * @model
	 * @generated
	 */
	int getReservierungsnummer();

	/**
	 * Sets the value of the '{@link kino.Reservierung#getReservierungsnummer <em>Reservierungsnummer</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Reservierungsnummer</em>' attribute.
	 * @see #getReservierungsnummer()
	 * @generated
	 */
	void setReservierungsnummer(int value);

	/**
	 * Returns the value of the '<em><b>Start Zeitstempel</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Start Zeitstempel</em>' attribute.
	 * @see #setStartZeitstempel(Date)
	 * @see kino.KinoPackage#getReservierung_StartZeitstempel()
	 * @model
	 * @generated
	 */
	Date getStartZeitstempel();

	/**
	 * Sets the value of the '{@link kino.Reservierung#getStartZeitstempel <em>Start Zeitstempel</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Start Zeitstempel</em>' attribute.
	 * @see #getStartZeitstempel()
	 * @generated
	 */
	void setStartZeitstempel(Date value);

	/**
	 * Returns the value of the '<em><b>Kunde</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Kunde</em>' reference.
	 * @see #setKunde(Kunde)
	 * @see kino.KinoPackage#getReservierung_Kunde()
	 * @model required="true"
	 * @generated
	 */
	Kunde getKunde();

	/**
	 * Sets the value of the '{@link kino.Reservierung#getKunde <em>Kunde</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Kunde</em>' reference.
	 * @see #getKunde()
	 * @generated
	 */
	void setKunde(Kunde value);

	/**
	 * Returns the value of the '<em><b>Auffuehrung</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Auffuehrung</em>' reference.
	 * @see #setAuffuehrung(Auffuehrung)
	 * @see kino.KinoPackage#getReservierung_Auffuehrung()
	 * @model required="true"
	 * @generated
	 */
	Auffuehrung getAuffuehrung();

	/**
	 * Sets the value of the '{@link kino.Reservierung#getAuffuehrung <em>Auffuehrung</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Auffuehrung</em>' reference.
	 * @see #getAuffuehrung()
	 * @generated
	 */
	void setAuffuehrung(Auffuehrung value);

	/**
	 * Returns the value of the '<em><b>Plaetze</b></em>' reference list.
	 * The list contents are of type {@link kino.Sitzplatz}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Plaetze</em>' reference list.
	 * @see kino.KinoPackage#getReservierung_Plaetze()
	 * @model required="true"
	 * @generated
	 */
	EList<Sitzplatz> getPlaetze();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model plaetzeRequired="true" plaetzeMany="true"
	 * @generated
	 */
	void plaetzeHinzufuegen(EList<Sitzplatz> plaetze);

} // Reservierung
