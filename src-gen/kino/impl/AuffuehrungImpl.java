/**
 */
package kino.impl;

import java.lang.reflect.InvocationTargetException;

import java.util.Collection;
import java.util.Date;

import kino.Auffuehrung;
import kino.Buchung;
import kino.Film;
import kino.KinoPackage;
import kino.Kinosaal;
import kino.Reservierung;
import kino.Sitzplatz;
import kino.SitzreihenKategorie;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Auffuehrung</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link kino.impl.AuffuehrungImpl#getStartzeitpunkt <em>Startzeitpunkt</em>}</li>
 *   <li>{@link kino.impl.AuffuehrungImpl#getSaal <em>Saal</em>}</li>
 *   <li>{@link kino.impl.AuffuehrungImpl#getReservierungen <em>Reservierungen</em>}</li>
 *   <li>{@link kino.impl.AuffuehrungImpl#getBuchungen <em>Buchungen</em>}</li>
 *   <li>{@link kino.impl.AuffuehrungImpl#getFilm <em>Film</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AuffuehrungImpl extends MinimalEObjectImpl.Container implements Auffuehrung {
	/**
	 * The default value of the '{@link #getStartzeitpunkt() <em>Startzeitpunkt</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStartzeitpunkt()
	 * @generated
	 * @ordered
	 */
	protected static final Date STARTZEITPUNKT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getStartzeitpunkt() <em>Startzeitpunkt</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStartzeitpunkt()
	 * @generated
	 * @ordered
	 */
	protected Date startzeitpunkt = STARTZEITPUNKT_EDEFAULT;

	/**
	 * The cached value of the '{@link #getSaal() <em>Saal</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSaal()
	 * @generated
	 * @ordered
	 */
	protected Kinosaal saal;

	/**
	 * The cached value of the '{@link #getReservierungen() <em>Reservierungen</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReservierungen()
	 * @generated
	 * @ordered
	 */
	protected EList<Reservierung> reservierungen;

	/**
	 * The cached value of the '{@link #getBuchungen() <em>Buchungen</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBuchungen()
	 * @generated
	 * @ordered
	 */
	protected EList<Buchung> buchungen;

	/**
	 * The cached value of the '{@link #getFilm() <em>Film</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFilm()
	 * @generated
	 * @ordered
	 */
	protected Film film;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AuffuehrungImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return KinoPackage.Literals.AUFFUEHRUNG;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Date getStartzeitpunkt() {
		return startzeitpunkt;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setStartzeitpunkt(Date newStartzeitpunkt) {
		Date oldStartzeitpunkt = startzeitpunkt;
		startzeitpunkt = newStartzeitpunkt;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KinoPackage.AUFFUEHRUNG__STARTZEITPUNKT, oldStartzeitpunkt, startzeitpunkt));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Kinosaal getSaal() {
		if (saal != null && saal.eIsProxy()) {
			InternalEObject oldSaal = (InternalEObject)saal;
			saal = (Kinosaal)eResolveProxy(oldSaal);
			if (saal != oldSaal) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, KinoPackage.AUFFUEHRUNG__SAAL, oldSaal, saal));
			}
		}
		return saal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Kinosaal basicGetSaal() {
		return saal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSaal(Kinosaal newSaal) {
		Kinosaal oldSaal = saal;
		saal = newSaal;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KinoPackage.AUFFUEHRUNG__SAAL, oldSaal, saal));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Reservierung> getReservierungen() {
		if (reservierungen == null) {
			reservierungen = new EObjectResolvingEList<Reservierung>(Reservierung.class, this, KinoPackage.AUFFUEHRUNG__RESERVIERUNGEN);
		}
		return reservierungen;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Buchung> getBuchungen() {
		if (buchungen == null) {
			buchungen = new EObjectResolvingEList<Buchung>(Buchung.class, this, KinoPackage.AUFFUEHRUNG__BUCHUNGEN);
		}
		return buchungen;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Film getFilm() {
		if (film != null && film.eIsProxy()) {
			InternalEObject oldFilm = (InternalEObject)film;
			film = (Film)eResolveProxy(oldFilm);
			if (film != oldFilm) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, KinoPackage.AUFFUEHRUNG__FILM, oldFilm, film));
			}
		}
		return film;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Film basicGetFilm() {
		return film;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setFilm(Film newFilm) {
		Film oldFilm = film;
		film = newFilm;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KinoPackage.AUFFUEHRUNG__FILM, oldFilm, film));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Sitzplatz> verfuegbarePlaetze(SitzreihenKategorie kategorie) {
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
			case KinoPackage.AUFFUEHRUNG__STARTZEITPUNKT:
				return getStartzeitpunkt();
			case KinoPackage.AUFFUEHRUNG__SAAL:
				if (resolve) return getSaal();
				return basicGetSaal();
			case KinoPackage.AUFFUEHRUNG__RESERVIERUNGEN:
				return getReservierungen();
			case KinoPackage.AUFFUEHRUNG__BUCHUNGEN:
				return getBuchungen();
			case KinoPackage.AUFFUEHRUNG__FILM:
				if (resolve) return getFilm();
				return basicGetFilm();
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
			case KinoPackage.AUFFUEHRUNG__STARTZEITPUNKT:
				setStartzeitpunkt((Date)newValue);
				return;
			case KinoPackage.AUFFUEHRUNG__SAAL:
				setSaal((Kinosaal)newValue);
				return;
			case KinoPackage.AUFFUEHRUNG__RESERVIERUNGEN:
				getReservierungen().clear();
				getReservierungen().addAll((Collection<? extends Reservierung>)newValue);
				return;
			case KinoPackage.AUFFUEHRUNG__BUCHUNGEN:
				getBuchungen().clear();
				getBuchungen().addAll((Collection<? extends Buchung>)newValue);
				return;
			case KinoPackage.AUFFUEHRUNG__FILM:
				setFilm((Film)newValue);
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
			case KinoPackage.AUFFUEHRUNG__STARTZEITPUNKT:
				setStartzeitpunkt(STARTZEITPUNKT_EDEFAULT);
				return;
			case KinoPackage.AUFFUEHRUNG__SAAL:
				setSaal((Kinosaal)null);
				return;
			case KinoPackage.AUFFUEHRUNG__RESERVIERUNGEN:
				getReservierungen().clear();
				return;
			case KinoPackage.AUFFUEHRUNG__BUCHUNGEN:
				getBuchungen().clear();
				return;
			case KinoPackage.AUFFUEHRUNG__FILM:
				setFilm((Film)null);
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
			case KinoPackage.AUFFUEHRUNG__STARTZEITPUNKT:
				return STARTZEITPUNKT_EDEFAULT == null ? startzeitpunkt != null : !STARTZEITPUNKT_EDEFAULT.equals(startzeitpunkt);
			case KinoPackage.AUFFUEHRUNG__SAAL:
				return saal != null;
			case KinoPackage.AUFFUEHRUNG__RESERVIERUNGEN:
				return reservierungen != null && !reservierungen.isEmpty();
			case KinoPackage.AUFFUEHRUNG__BUCHUNGEN:
				return buchungen != null && !buchungen.isEmpty();
			case KinoPackage.AUFFUEHRUNG__FILM:
				return film != null;
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
			case KinoPackage.AUFFUEHRUNG___VERFUEGBARE_PLAETZE__SITZREIHENKATEGORIE:
				return verfuegbarePlaetze((SitzreihenKategorie)arguments.get(0));
			case KinoPackage.AUFFUEHRUNG___GESAMT_EINNAHMEN:
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
		result.append(" (startzeitpunkt: ");
		result.append(startzeitpunkt);
		result.append(')');
		return result.toString();
	}

} //AuffuehrungImpl
