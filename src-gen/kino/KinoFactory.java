/**
 */
package kino;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see kino.KinoPackage
 * @generated
 */
public interface KinoFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	KinoFactory eINSTANCE = kino.impl.KinoFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Kino</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Kino</em>'.
	 * @generated
	 */
	Kino createKino();

	/**
	 * Returns a new object of class '<em>Kinosaal</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Kinosaal</em>'.
	 * @generated
	 */
	Kinosaal createKinosaal();

	/**
	 * Returns a new object of class '<em>Film</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Film</em>'.
	 * @generated
	 */
	Film createFilm();

	/**
	 * Returns a new object of class '<em>Auffuehrung</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Auffuehrung</em>'.
	 * @generated
	 */
	Auffuehrung createAuffuehrung();

	/**
	 * Returns a new object of class '<em>Sitzreihe</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Sitzreihe</em>'.
	 * @generated
	 */
	Sitzreihe createSitzreihe();

	/**
	 * Returns a new object of class '<em>Sitzplatz</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Sitzplatz</em>'.
	 * @generated
	 */
	Sitzplatz createSitzplatz();

	/**
	 * Returns a new object of class '<em>Kunde</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Kunde</em>'.
	 * @generated
	 */
	Kunde createKunde();

	/**
	 * Returns a new object of class '<em>Reservierung</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Reservierung</em>'.
	 * @generated
	 */
	Reservierung createReservierung();

	/**
	 * Returns a new object of class '<em>Buchung</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Buchung</em>'.
	 * @generated
	 */
	Buchung createBuchung();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	KinoPackage getKinoPackage();

} //KinoFactory
