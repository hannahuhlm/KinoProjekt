/**
 */
package kino;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Film</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link kino.Film#getTitel <em>Titel</em>}</li>
 *   <li>{@link kino.Film#getDauer <em>Dauer</em>}</li>
 *   <li>{@link kino.Film#getBeschreibung <em>Beschreibung</em>}</li>
 *   <li>{@link kino.Film#getAuffuehrungen <em>Auffuehrungen</em>}</li>
 * </ul>
 *
 * @see kino.KinoPackage#getFilm()
 * @model
 * @generated
 */
public interface Film extends EObject {
	/**
	 * Returns the value of the '<em><b>Titel</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Titel</em>' attribute.
	 * @see #setTitel(String)
	 * @see kino.KinoPackage#getFilm_Titel()
	 * @model
	 * @generated
	 */
	String getTitel();

	/**
	 * Sets the value of the '{@link kino.Film#getTitel <em>Titel</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Titel</em>' attribute.
	 * @see #getTitel()
	 * @generated
	 */
	void setTitel(String value);

	/**
	 * Returns the value of the '<em><b>Dauer</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Dauer</em>' attribute.
	 * @see #setDauer(int)
	 * @see kino.KinoPackage#getFilm_Dauer()
	 * @model
	 * @generated
	 */
	int getDauer();

	/**
	 * Sets the value of the '{@link kino.Film#getDauer <em>Dauer</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Dauer</em>' attribute.
	 * @see #getDauer()
	 * @generated
	 */
	void setDauer(int value);

	/**
	 * Returns the value of the '<em><b>Beschreibung</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Beschreibung</em>' attribute.
	 * @see #setBeschreibung(String)
	 * @see kino.KinoPackage#getFilm_Beschreibung()
	 * @model
	 * @generated
	 */
	String getBeschreibung();

	/**
	 * Sets the value of the '{@link kino.Film#getBeschreibung <em>Beschreibung</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Beschreibung</em>' attribute.
	 * @see #getBeschreibung()
	 * @generated
	 */
	void setBeschreibung(String value);

	/**
	 * Returns the value of the '<em><b>Auffuehrungen</b></em>' reference list.
	 * The list contents are of type {@link kino.Auffuehrung}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Auffuehrungen</em>' reference list.
	 * @see kino.KinoPackage#getFilm_Auffuehrungen()
	 * @model
	 * @generated
	 */
	EList<Auffuehrung> getAuffuehrungen();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	double gesamtEinnahmen();

} // Film
