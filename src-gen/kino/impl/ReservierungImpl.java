/**
 */
package kino.impl;

import java.lang.reflect.InvocationTargetException;

import java.util.Collection;
import java.util.Date;

import kino.Auffuehrung;
import kino.Buchung;
import kino.KinoPackage;
import kino.Kunde;
import kino.Reservierung;
import kino.Sitzplatz;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Reservierung</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link kino.impl.ReservierungImpl#getReservierungsnummer <em>Reservierungsnummer</em>}</li>
 *   <li>{@link kino.impl.ReservierungImpl#getStartZeitstempel <em>Start Zeitstempel</em>}</li>
 *   <li>{@link kino.impl.ReservierungImpl#getKunde <em>Kunde</em>}</li>
 *   <li>{@link kino.impl.ReservierungImpl#getAuffuehrung <em>Auffuehrung</em>}</li>
 *   <li>{@link kino.impl.ReservierungImpl#getPlaetze <em>Plaetze</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ReservierungImpl extends MinimalEObjectImpl.Container implements Reservierung {
	/**
	 * The default value of the '{@link #getReservierungsnummer() <em>Reservierungsnummer</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReservierungsnummer()
	 * @generated
	 * @ordered
	 */
	protected static final int RESERVIERUNGSNUMMER_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getReservierungsnummer() <em>Reservierungsnummer</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReservierungsnummer()
	 * @generated
	 * @ordered
	 */
	protected int reservierungsnummer = RESERVIERUNGSNUMMER_EDEFAULT;

	/**
	 * The default value of the '{@link #getStartZeitstempel() <em>Start Zeitstempel</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStartZeitstempel()
	 * @generated
	 * @ordered
	 */
	protected static final Date START_ZEITSTEMPEL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getStartZeitstempel() <em>Start Zeitstempel</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStartZeitstempel()
	 * @generated
	 * @ordered
	 */
	protected Date startZeitstempel = START_ZEITSTEMPEL_EDEFAULT;

	/**
	 * The cached value of the '{@link #getKunde() <em>Kunde</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKunde()
	 * @generated
	 * @ordered
	 */
	protected Kunde kunde;

	/**
	 * The cached value of the '{@link #getAuffuehrung() <em>Auffuehrung</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAuffuehrung()
	 * @generated
	 * @ordered
	 */
	protected Auffuehrung auffuehrung;

	/**
	 * The cached value of the '{@link #getPlaetze() <em>Plaetze</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPlaetze()
	 * @generated
	 * @ordered
	 */
	protected EList<Sitzplatz> plaetze;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ReservierungImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return KinoPackage.Literals.RESERVIERUNG;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getReservierungsnummer() {
		return reservierungsnummer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setReservierungsnummer(int newReservierungsnummer) {
		int oldReservierungsnummer = reservierungsnummer;
		reservierungsnummer = newReservierungsnummer;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KinoPackage.RESERVIERUNG__RESERVIERUNGSNUMMER, oldReservierungsnummer, reservierungsnummer));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Date getStartZeitstempel() {
		return startZeitstempel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setStartZeitstempel(Date newStartZeitstempel) {
		Date oldStartZeitstempel = startZeitstempel;
		startZeitstempel = newStartZeitstempel;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KinoPackage.RESERVIERUNG__START_ZEITSTEMPEL, oldStartZeitstempel, startZeitstempel));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Kunde getKunde() {
		if (kunde != null && kunde.eIsProxy()) {
			InternalEObject oldKunde = (InternalEObject)kunde;
			kunde = (Kunde)eResolveProxy(oldKunde);
			if (kunde != oldKunde) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, KinoPackage.RESERVIERUNG__KUNDE, oldKunde, kunde));
			}
		}
		return kunde;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Kunde basicGetKunde() {
		return kunde;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setKunde(Kunde newKunde) {
		Kunde oldKunde = kunde;
		kunde = newKunde;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KinoPackage.RESERVIERUNG__KUNDE, oldKunde, kunde));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Auffuehrung getAuffuehrung() {
		if (auffuehrung != null && auffuehrung.eIsProxy()) {
			InternalEObject oldAuffuehrung = (InternalEObject)auffuehrung;
			auffuehrung = (Auffuehrung)eResolveProxy(oldAuffuehrung);
			if (auffuehrung != oldAuffuehrung) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, KinoPackage.RESERVIERUNG__AUFFUEHRUNG, oldAuffuehrung, auffuehrung));
			}
		}
		return auffuehrung;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Auffuehrung basicGetAuffuehrung() {
		return auffuehrung;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setAuffuehrung(Auffuehrung newAuffuehrung) {
		Auffuehrung oldAuffuehrung = auffuehrung;
		auffuehrung = newAuffuehrung;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KinoPackage.RESERVIERUNG__AUFFUEHRUNG, oldAuffuehrung, auffuehrung));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Sitzplatz> getPlaetze() {
		if (plaetze == null) {
			plaetze = new EObjectResolvingEList<Sitzplatz>(Sitzplatz.class, this, KinoPackage.RESERVIERUNG__PLAETZE);
		}
		return plaetze;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void stornieren() {
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
	public Buchung inBuchungUmwandeln() {
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
			case KinoPackage.RESERVIERUNG__RESERVIERUNGSNUMMER:
				return getReservierungsnummer();
			case KinoPackage.RESERVIERUNG__START_ZEITSTEMPEL:
				return getStartZeitstempel();
			case KinoPackage.RESERVIERUNG__KUNDE:
				if (resolve) return getKunde();
				return basicGetKunde();
			case KinoPackage.RESERVIERUNG__AUFFUEHRUNG:
				if (resolve) return getAuffuehrung();
				return basicGetAuffuehrung();
			case KinoPackage.RESERVIERUNG__PLAETZE:
				return getPlaetze();
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
			case KinoPackage.RESERVIERUNG__RESERVIERUNGSNUMMER:
				setReservierungsnummer((Integer)newValue);
				return;
			case KinoPackage.RESERVIERUNG__START_ZEITSTEMPEL:
				setStartZeitstempel((Date)newValue);
				return;
			case KinoPackage.RESERVIERUNG__KUNDE:
				setKunde((Kunde)newValue);
				return;
			case KinoPackage.RESERVIERUNG__AUFFUEHRUNG:
				setAuffuehrung((Auffuehrung)newValue);
				return;
			case KinoPackage.RESERVIERUNG__PLAETZE:
				getPlaetze().clear();
				getPlaetze().addAll((Collection<? extends Sitzplatz>)newValue);
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
			case KinoPackage.RESERVIERUNG__RESERVIERUNGSNUMMER:
				setReservierungsnummer(RESERVIERUNGSNUMMER_EDEFAULT);
				return;
			case KinoPackage.RESERVIERUNG__START_ZEITSTEMPEL:
				setStartZeitstempel(START_ZEITSTEMPEL_EDEFAULT);
				return;
			case KinoPackage.RESERVIERUNG__KUNDE:
				setKunde((Kunde)null);
				return;
			case KinoPackage.RESERVIERUNG__AUFFUEHRUNG:
				setAuffuehrung((Auffuehrung)null);
				return;
			case KinoPackage.RESERVIERUNG__PLAETZE:
				getPlaetze().clear();
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
			case KinoPackage.RESERVIERUNG__RESERVIERUNGSNUMMER:
				return reservierungsnummer != RESERVIERUNGSNUMMER_EDEFAULT;
			case KinoPackage.RESERVIERUNG__START_ZEITSTEMPEL:
				return START_ZEITSTEMPEL_EDEFAULT == null ? startZeitstempel != null : !START_ZEITSTEMPEL_EDEFAULT.equals(startZeitstempel);
			case KinoPackage.RESERVIERUNG__KUNDE:
				return kunde != null;
			case KinoPackage.RESERVIERUNG__AUFFUEHRUNG:
				return auffuehrung != null;
			case KinoPackage.RESERVIERUNG__PLAETZE:
				return plaetze != null && !plaetze.isEmpty();
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
			case KinoPackage.RESERVIERUNG___STORNIEREN:
				stornieren();
				return null;
			case KinoPackage.RESERVIERUNG___IN_BUCHUNG_UMWANDELN:
				return inBuchungUmwandeln();
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
		result.append(" (reservierungsnummer: ");
		result.append(reservierungsnummer);
		result.append(", startZeitstempel: ");
		result.append(startZeitstempel);
		result.append(')');
		return result.toString();
	}

} //ReservierungImpl
