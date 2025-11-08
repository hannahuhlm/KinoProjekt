/**
 */
package kino;

import java.util.Date;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Auffuehrung</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link kino.Auffuehrung#getStartzeitpunkt <em>Startzeitpunkt</em>}</li>
 *   <li>{@link kino.Auffuehrung#getSaal <em>Saal</em>}</li>
 *   <li>{@link kino.Auffuehrung#getReservierungen <em>Reservierungen</em>}</li>
 *   <li>{@link kino.Auffuehrung#getBuchungen <em>Buchungen</em>}</li>
 *   <li>{@link kino.Auffuehrung#getFilm <em>Film</em>}</li>
 * </ul>
 *
 * @see kino.KinoPackage#getAuffuehrung()
 * @model
 * @generated
 */
public interface Auffuehrung extends EObject {
	/**
	 * Returns the value of the '<em><b>Startzeitpunkt</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Startzeitpunkt</em>' attribute.
	 * @see #setStartzeitpunkt(Date)
	 * @see kino.KinoPackage#getAuffuehrung_Startzeitpunkt()
	 * @model
	 * @generated
	 */
	Date getStartzeitpunkt();

	/**
	 * Sets the value of the '{@link kino.Auffuehrung#getStartzeitpunkt <em>Startzeitpunkt</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Startzeitpunkt</em>' attribute.
	 * @see #getStartzeitpunkt()
	 * @generated
	 */
	void setStartzeitpunkt(Date value);

	/**
	 * Returns the value of the '<em><b>Saal</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Saal</em>' reference.
	 * @see #setSaal(Kinosaal)
	 * @see kino.KinoPackage#getAuffuehrung_Saal()
	 * @model required="true"
	 * @generated
	 */
	Kinosaal getSaal();

	/**
	 * Sets the value of the '{@link kino.Auffuehrung#getSaal <em>Saal</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Saal</em>' reference.
	 * @see #getSaal()
	 * @generated
	 */
	void setSaal(Kinosaal value);

	/**
	 * Returns the value of the '<em><b>Reservierungen</b></em>' reference list.
	 * The list contents are of type {@link kino.Reservierung}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Reservierungen</em>' reference list.
	 * @see kino.KinoPackage#getAuffuehrung_Reservierungen()
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
	 * @see kino.KinoPackage#getAuffuehrung_Buchungen()
	 * @model
	 * @generated
	 */
	EList<Buchung> getBuchungen();

	/**
	 * Returns the value of the '<em><b>Film</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Film</em>' reference.
	 * @see #setFilm(Film)
	 * @see kino.KinoPackage#getAuffuehrung_Film()
	 * @model required="true"
	 * @generated
	 */
	Film getFilm();

	/**
	 * Sets the value of the '{@link kino.Auffuehrung#getFilm <em>Film</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Film</em>' reference.
	 * @see #getFilm()
	 * @generated
	 */
	void setFilm(Film value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	EList<Sitzplatz> verfuegbarePlaetze(SitzreihenKategorie kategorie);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	double gesamtEinnahmen();

} // Auffuehrung
