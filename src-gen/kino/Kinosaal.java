/**
 */
package kino;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Kinosaal</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link kino.Kinosaal#getName <em>Name</em>}</li>
 *   <li>{@link kino.Kinosaal#isFreigegeben <em>Freigegeben</em>}</li>
 *   <li>{@link kino.Kinosaal#getReihen <em>Reihen</em>}</li>
 *   <li>{@link kino.Kinosaal#getAuffuehrungen <em>Auffuehrungen</em>}</li>
 * </ul>
 *
 * @see kino.KinoPackage#getKinosaal()
 * @model
 * @generated
 */
public interface Kinosaal extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see kino.KinoPackage#getKinosaal_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link kino.Kinosaal#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Freigegeben</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Freigegeben</em>' attribute.
	 * @see #setFreigegeben(boolean)
	 * @see kino.KinoPackage#getKinosaal_Freigegeben()
	 * @model default="false"
	 * @generated
	 */
	boolean isFreigegeben();

	/**
	 * Sets the value of the '{@link kino.Kinosaal#isFreigegeben <em>Freigegeben</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Freigegeben</em>' attribute.
	 * @see #isFreigegeben()
	 * @generated
	 */
	void setFreigegeben(boolean value);

	/**
	 * Returns the value of the '<em><b>Reihen</b></em>' reference list.
	 * The list contents are of type {@link kino.Sitzreihe}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Reihen</em>' reference list.
	 * @see kino.KinoPackage#getKinosaal_Reihen()
	 * @model required="true"
	 * @generated
	 */
	EList<Sitzreihe> getReihen();

	/**
	 * Returns the value of the '<em><b>Auffuehrungen</b></em>' reference list.
	 * The list contents are of type {@link kino.Auffuehrung}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Auffuehrungen</em>' reference list.
	 * @see kino.KinoPackage#getKinosaal_Auffuehrungen()
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
	void reiheHinzufügen(Sitzreihe reihe);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	void freigeben();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	boolean istFreigegeben();

} // Kinosaal
