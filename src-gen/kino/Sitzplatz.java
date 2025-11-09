/**
 */
package kino;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Sitzplatz</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link kino.Sitzplatz#getPlatznummer <em>Platznummer</em>}</li>
 *   <li>{@link kino.Sitzplatz#getReihe <em>Reihe</em>}</li>
 *   <li>{@link kino.Sitzplatz#getReservierung <em>Reservierung</em>}</li>
 *   <li>{@link kino.Sitzplatz#getBuchung <em>Buchung</em>}</li>
 *   <li>{@link kino.Sitzplatz#isIsFrei <em>Is Frei</em>}</li>
 * </ul>
 *
 * @see kino.KinoPackage#getSitzplatz()
 * @model
 * @generated
 */
public interface Sitzplatz extends EObject {
	/**
	 * Returns the value of the '<em><b>Platznummer</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Platznummer</em>' attribute.
	 * @see #setPlatznummer(int)
	 * @see kino.KinoPackage#getSitzplatz_Platznummer()
	 * @model
	 * @generated
	 */
	int getPlatznummer();

	/**
	 * Sets the value of the '{@link kino.Sitzplatz#getPlatznummer <em>Platznummer</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Platznummer</em>' attribute.
	 * @see #getPlatznummer()
	 * @generated
	 */
	void setPlatznummer(int value);

	/**
	 * Returns the value of the '<em><b>Reihe</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Reihe</em>' reference.
	 * @see #setReihe(Sitzreihe)
	 * @see kino.KinoPackage#getSitzplatz_Reihe()
	 * @model required="true"
	 * @generated
	 */
	Sitzreihe getReihe();

	/**
	 * Sets the value of the '{@link kino.Sitzplatz#getReihe <em>Reihe</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Reihe</em>' reference.
	 * @see #getReihe()
	 * @generated
	 */
	void setReihe(Sitzreihe value);

	/**
	 * Returns the value of the '<em><b>Reservierung</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Reservierung</em>' reference.
	 * @see #setReservierung(Reservierung)
	 * @see kino.KinoPackage#getSitzplatz_Reservierung()
	 * @model
	 * @generated
	 */
	Reservierung getReservierung();

	/**
	 * Sets the value of the '{@link kino.Sitzplatz#getReservierung <em>Reservierung</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Reservierung</em>' reference.
	 * @see #getReservierung()
	 * @generated
	 */
	void setReservierung(Reservierung value);

	/**
	 * Returns the value of the '<em><b>Buchung</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Buchung</em>' reference.
	 * @see #setBuchung(Buchung)
	 * @see kino.KinoPackage#getSitzplatz_Buchung()
	 * @model
	 * @generated
	 */
	Buchung getBuchung();

	/**
	 * Sets the value of the '{@link kino.Sitzplatz#getBuchung <em>Buchung</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Buchung</em>' reference.
	 * @see #getBuchung()
	 * @generated
	 */
	void setBuchung(Buchung value);

	/**
	 * Returns the value of the '<em><b>Is Frei</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Is Frei</em>' attribute.
	 * @see #setIsFrei(boolean)
	 * @see kino.KinoPackage#getSitzplatz_IsFrei()
	 * @model default="true"
	 * @generated
	 */
	boolean isIsFrei();

	/**
	 * Sets the value of the '{@link kino.Sitzplatz#isIsFrei <em>Is Frei</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is Frei</em>' attribute.
	 * @see #isIsFrei()
	 * @generated
	 */
	void setIsFrei(boolean value);

} // Sitzplatz
