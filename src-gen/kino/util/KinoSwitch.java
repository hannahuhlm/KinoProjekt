/**
 */
package kino.util;

import kino.*;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see kino.KinoPackage
 * @generated
 */
public class KinoSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static KinoPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public KinoSwitch() {
		if (modelPackage == null) {
			modelPackage = KinoPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case KinoPackage.KINO: {
				Kino kino = (Kino)theEObject;
				T result = caseKino(kino);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KinoPackage.KINOSAAL: {
				Kinosaal kinosaal = (Kinosaal)theEObject;
				T result = caseKinosaal(kinosaal);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KinoPackage.FILM: {
				Film film = (Film)theEObject;
				T result = caseFilm(film);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KinoPackage.AUFFUEHRUNG: {
				Auffuehrung auffuehrung = (Auffuehrung)theEObject;
				T result = caseAuffuehrung(auffuehrung);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KinoPackage.SITZREIHE: {
				Sitzreihe sitzreihe = (Sitzreihe)theEObject;
				T result = caseSitzreihe(sitzreihe);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KinoPackage.SITZPLATZ: {
				Sitzplatz sitzplatz = (Sitzplatz)theEObject;
				T result = caseSitzplatz(sitzplatz);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KinoPackage.KUNDE: {
				Kunde kunde = (Kunde)theEObject;
				T result = caseKunde(kunde);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KinoPackage.RESERVIERUNG: {
				Reservierung reservierung = (Reservierung)theEObject;
				T result = caseReservierung(reservierung);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case KinoPackage.BUCHUNG: {
				Buchung buchung = (Buchung)theEObject;
				T result = caseBuchung(buchung);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Kino</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Kino</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseKino(Kino object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Kinosaal</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Kinosaal</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseKinosaal(Kinosaal object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Film</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Film</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFilm(Film object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Auffuehrung</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Auffuehrung</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAuffuehrung(Auffuehrung object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Sitzreihe</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Sitzreihe</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSitzreihe(Sitzreihe object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Sitzplatz</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Sitzplatz</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSitzplatz(Sitzplatz object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Kunde</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Kunde</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseKunde(Kunde object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Reservierung</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Reservierung</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseReservierung(Reservierung object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Buchung</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Buchung</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBuchung(Buchung object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} //KinoSwitch
