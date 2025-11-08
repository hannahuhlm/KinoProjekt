/**
 */
package kino.impl;

import java.lang.reflect.InvocationTargetException;

import java.util.Collection;

import kino.Auffuehrung;
import kino.Film;
import kino.KinoPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Film</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link kino.impl.FilmImpl#getTitel <em>Titel</em>}</li>
 *   <li>{@link kino.impl.FilmImpl#getDauer <em>Dauer</em>}</li>
 *   <li>{@link kino.impl.FilmImpl#getBeschreibung <em>Beschreibung</em>}</li>
 *   <li>{@link kino.impl.FilmImpl#getAuffuehrungen <em>Auffuehrungen</em>}</li>
 * </ul>
 *
 * @generated
 */
public class FilmImpl extends MinimalEObjectImpl.Container implements Film {
	/**
	 * The default value of the '{@link #getTitel() <em>Titel</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTitel()
	 * @generated
	 * @ordered
	 */
	protected static final String TITEL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTitel() <em>Titel</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTitel()
	 * @generated
	 * @ordered
	 */
	protected String titel = TITEL_EDEFAULT;

	/**
	 * The default value of the '{@link #getDauer() <em>Dauer</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDauer()
	 * @generated
	 * @ordered
	 */
	protected static final int DAUER_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getDauer() <em>Dauer</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDauer()
	 * @generated
	 * @ordered
	 */
	protected int dauer = DAUER_EDEFAULT;

	/**
	 * The default value of the '{@link #getBeschreibung() <em>Beschreibung</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBeschreibung()
	 * @generated
	 * @ordered
	 */
	protected static final String BESCHREIBUNG_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getBeschreibung() <em>Beschreibung</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBeschreibung()
	 * @generated
	 * @ordered
	 */
	protected String beschreibung = BESCHREIBUNG_EDEFAULT;

	/**
	 * The cached value of the '{@link #getAuffuehrungen() <em>Auffuehrungen</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAuffuehrungen()
	 * @generated
	 * @ordered
	 */
	protected EList<Auffuehrung> auffuehrungen;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FilmImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return KinoPackage.Literals.FILM;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getTitel() {
		return titel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getDauer() {
		return dauer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDauer(int newDauer) {
		int oldDauer = dauer;
		dauer = newDauer;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KinoPackage.FILM__DAUER, oldDauer, dauer));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getBeschreibung() {
		return beschreibung;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setBeschreibung(String newBeschreibung) {
		String oldBeschreibung = beschreibung;
		beschreibung = newBeschreibung;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KinoPackage.FILM__BESCHREIBUNG, oldBeschreibung, beschreibung));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Auffuehrung> getAuffuehrungen() {
		if (auffuehrungen == null) {
			auffuehrungen = new EObjectResolvingEList<Auffuehrung>(Auffuehrung.class, this, KinoPackage.FILM__AUFFUEHRUNGEN);
		}
		return auffuehrungen;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public double gesamtEinnahmen() {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case KinoPackage.FILM__TITEL:
				return getTitel();
			case KinoPackage.FILM__DAUER:
				return getDauer();
			case KinoPackage.FILM__BESCHREIBUNG:
				return getBeschreibung();
			case KinoPackage.FILM__AUFFUEHRUNGEN:
				return getAuffuehrungen();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case KinoPackage.FILM__DAUER:
				setDauer((Integer)newValue);
				return;
			case KinoPackage.FILM__BESCHREIBUNG:
				setBeschreibung((String)newValue);
				return;
			case KinoPackage.FILM__AUFFUEHRUNGEN:
				getAuffuehrungen().clear();
				getAuffuehrungen().addAll((Collection<? extends Auffuehrung>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case KinoPackage.FILM__DAUER:
				setDauer(DAUER_EDEFAULT);
				return;
			case KinoPackage.FILM__BESCHREIBUNG:
				setBeschreibung(BESCHREIBUNG_EDEFAULT);
				return;
			case KinoPackage.FILM__AUFFUEHRUNGEN:
				getAuffuehrungen().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case KinoPackage.FILM__TITEL:
				return TITEL_EDEFAULT == null ? titel != null : !TITEL_EDEFAULT.equals(titel);
			case KinoPackage.FILM__DAUER:
				return dauer != DAUER_EDEFAULT;
			case KinoPackage.FILM__BESCHREIBUNG:
				return BESCHREIBUNG_EDEFAULT == null ? beschreibung != null : !BESCHREIBUNG_EDEFAULT.equals(beschreibung);
			case KinoPackage.FILM__AUFFUEHRUNGEN:
				return auffuehrungen != null && !auffuehrungen.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case KinoPackage.FILM___GESAMT_EINNAHMEN:
				return gesamtEinnahmen();
		}
		return super.eInvoke(operationID, arguments);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (titel: ");
		result.append(titel);
		result.append(", dauer: ");
		result.append(dauer);
		result.append(", beschreibung: ");
		result.append(beschreibung);
		result.append(')');
		return result.toString();
	}

} //FilmImpl
