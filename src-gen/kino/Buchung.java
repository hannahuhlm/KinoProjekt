/**
 */
package kino;

import java.util.Date;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Buchung</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link kino.Buchung#getBuchungsnummer <em>Buchungsnummer</em>}</li>
 *   <li>{@link kino.Buchung#getBuchungsZeitstempel <em>Buchungs Zeitstempel</em>}</li>
 *   <li>{@link kino.Buchung#isBezahlt <em>Bezahlt</em>}</li>
 *   <li>{@link kino.Buchung#getKunde <em>Kunde</em>}</li>
 *   <li>{@link kino.Buchung#getAuffuehrung <em>Auffuehrung</em>}</li>
 *   <li>{@link kino.Buchung#getPlaetze <em>Plaetze</em>}</li>
 *   <li>{@link kino.Buchung#getGesamtpreis <em>Gesamtpreis</em>}</li>
 * </ul>
 *
 * @see kino.KinoPackage#getBuchung()
 * @model
 * @generated
 */
public interface Buchung extends EObject {
	/**
	 * Returns the value of the '<em><b>Buchungsnummer</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Buchungsnummer</em>' attribute.
	 * @see #setBuchungsnummer(String)
	 * @see kino.KinoPackage#getBuchung_Buchungsnummer()
	 * @model
	 * @generated
	 */
	String getBuchungsnummer();

	/**
	 * Sets the value of the '{@link kino.Buchung#getBuchungsnummer <em>Buchungsnummer</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Buchungsnummer</em>' attribute.
	 * @see #getBuchungsnummer()
	 * @generated
	 */
	void setBuchungsnummer(String value);

	/**
	 * Returns the value of the '<em><b>Buchungs Zeitstempel</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Buchungs Zeitstempel</em>' attribute.
	 * @see #setBuchungsZeitstempel(Date)
	 * @see kino.KinoPackage#getBuchung_BuchungsZeitstempel()
	 * @model
	 * @generated
	 */
	Date getBuchungsZeitstempel();

	/**
	 * Sets the value of the '{@link kino.Buchung#getBuchungsZeitstempel <em>Buchungs Zeitstempel</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Buchungs Zeitstempel</em>' attribute.
	 * @see #getBuchungsZeitstempel()
	 * @generated
	 */
	void setBuchungsZeitstempel(Date value);

	/**
	 * Returns the value of the '<em><b>Bezahlt</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Bezahlt</em>' attribute.
	 * @see #setBezahlt(boolean)
	 * @see kino.KinoPackage#getBuchung_Bezahlt()
	 * @model default="false"
	 * @generated
	 */
	boolean isBezahlt();

	/**
	 * Sets the value of the '{@link kino.Buchung#isBezahlt <em>Bezahlt</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Bezahlt</em>' attribute.
	 * @see #isBezahlt()
	 * @generated
	 */
	void setBezahlt(boolean value);

	/**
	 * Returns the value of the '<em><b>Kunde</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Kunde</em>' reference.
	 * @see #setKunde(Kunde)
	 * @see kino.KinoPackage#getBuchung_Kunde()
	 * @model required="true"
	 * @generated
	 */
	Kunde getKunde();

	/**
	 * Sets the value of the '{@link kino.Buchung#getKunde <em>Kunde</em>}' reference.
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
	 * @see kino.KinoPackage#getBuchung_Auffuehrung()
	 * @model required="true"
	 * @generated
	 */
	Auffuehrung getAuffuehrung();

	/**
	 * Sets the value of the '{@link kino.Buchung#getAuffuehrung <em>Auffuehrung</em>}' reference.
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
	 * @see kino.KinoPackage#getBuchung_Plaetze()
	 * @model required="true"
	 * @generated
	 */
	EList<Sitzplatz> getPlaetze();

	/**
	 * Returns the value of the '<em><b>Gesamtpreis</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Gesamtpreis</em>' attribute.
	 * @see #setGesamtpreis(double)
	 * @see kino.KinoPackage#getBuchung_Gesamtpreis()
	 * @model
	 * @generated
	 */
	double getGesamtpreis();

	/**
	 * Sets the value of the '{@link kino.Buchung#getGesamtpreis <em>Gesamtpreis</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Gesamtpreis</em>' attribute.
	 * @see #getGesamtpreis()
	 * @generated
	 */
	void setGesamtpreis(double value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model plaetzeRequired="true" plaetzeMany="true"
	 * @generated
	 */
	void plaetzeHinzufuegen(EList<Sitzplatz> plaetze);

} // Buchung
