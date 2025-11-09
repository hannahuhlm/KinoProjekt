/**
 */
package kino;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Sitzreihe</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link kino.Sitzreihe#getReihennummer <em>Reihennummer</em>}</li>
 *   <li>{@link kino.Sitzreihe#getKategorie <em>Kategorie</em>}</li>
 *   <li>{@link kino.Sitzreihe#getAnzahlSitze <em>Anzahl Sitze</em>}</li>
 *   <li>{@link kino.Sitzreihe#getPlaetze <em>Plaetze</em>}</li>
 *   <li>{@link kino.Sitzreihe#getSaal <em>Saal</em>}</li>
 * </ul>
 *
 * @see kino.KinoPackage#getSitzreihe()
 * @model
 * @generated
 */
public interface Sitzreihe extends EObject {
	/**
	 * Returns the value of the '<em><b>Reihennummer</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Reihennummer</em>' attribute.
	 * @see #setReihennummer(int)
	 * @see kino.KinoPackage#getSitzreihe_Reihennummer()
	 * @model
	 * @generated
	 */
	int getReihennummer();

	/**
	 * Sets the value of the '{@link kino.Sitzreihe#getReihennummer <em>Reihennummer</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Reihennummer</em>' attribute.
	 * @see #getReihennummer()
	 * @generated
	 */
	void setReihennummer(int value);

	/**
	 * Returns the value of the '<em><b>Kategorie</b></em>' attribute.
	 * The literals are from the enumeration {@link kino.SitzreihenKategorie}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Kategorie</em>' attribute.
	 * @see kino.SitzreihenKategorie
	 * @see #setKategorie(SitzreihenKategorie)
	 * @see kino.KinoPackage#getSitzreihe_Kategorie()
	 * @model
	 * @generated
	 */
	SitzreihenKategorie getKategorie();

	/**
	 * Sets the value of the '{@link kino.Sitzreihe#getKategorie <em>Kategorie</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Kategorie</em>' attribute.
	 * @see kino.SitzreihenKategorie
	 * @see #getKategorie()
	 * @generated
	 */
	void setKategorie(SitzreihenKategorie value);

	/**
	 * Returns the value of the '<em><b>Anzahl Sitze</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Anzahl Sitze</em>' attribute.
	 * @see #setAnzahlSitze(int)
	 * @see kino.KinoPackage#getSitzreihe_AnzahlSitze()
	 * @model
	 * @generated
	 */
	int getAnzahlSitze();

	/**
	 * Sets the value of the '{@link kino.Sitzreihe#getAnzahlSitze <em>Anzahl Sitze</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Anzahl Sitze</em>' attribute.
	 * @see #getAnzahlSitze()
	 * @generated
	 */
	void setAnzahlSitze(int value);

	/**
	 * Returns the value of the '<em><b>Plaetze</b></em>' reference list.
	 * The list contents are of type {@link kino.Sitzplatz}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Plaetze</em>' reference list.
	 * @see kino.KinoPackage#getSitzreihe_Plaetze()
	 * @model required="true"
	 * @generated
	 */
	EList<Sitzplatz> getPlaetze();

	/**
	 * Returns the value of the '<em><b>Saal</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Saal</em>' reference.
	 * @see #setSaal(Kinosaal)
	 * @see kino.KinoPackage#getSitzreihe_Saal()
	 * @model required="true"
	 * @generated
	 */
	Kinosaal getSaal();

	/**
	 * Sets the value of the '{@link kino.Sitzreihe#getSaal <em>Saal</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Saal</em>' reference.
	 * @see #getSaal()
	 * @generated
	 */
	void setSaal(Kinosaal value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	void plaetzeAnlegen();

} // Sitzreihe
