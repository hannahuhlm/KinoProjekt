/**
 */
package kino.impl;

import kino.Buchung;
import kino.KinoPackage;
import kino.Reservierung;
import kino.Sitzplatz;
import kino.Sitzreihe;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Sitzplatz</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link kino.impl.SitzplatzImpl#getPlatznummer <em>Platznummer</em>}</li>
 *   <li>{@link kino.impl.SitzplatzImpl#getReihe <em>Reihe</em>}</li>
 *   <li>{@link kino.impl.SitzplatzImpl#getReservierung <em>Reservierung</em>}</li>
 *   <li>{@link kino.impl.SitzplatzImpl#getBuchung <em>Buchung</em>}</li>
 *   <li>{@link kino.impl.SitzplatzImpl#isIsFrei <em>Is Frei</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SitzplatzImpl extends MinimalEObjectImpl.Container implements Sitzplatz {
	/**
	 * The default value of the '{@link #getPlatznummer() <em>Platznummer</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPlatznummer()
	 * @generated
	 * @ordered
	 */
	protected static final int PLATZNUMMER_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getPlatznummer() <em>Platznummer</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPlatznummer()
	 * @generated
	 * @ordered
	 */
	protected int platznummer = PLATZNUMMER_EDEFAULT;

	/**
	 * The cached value of the '{@link #getReihe() <em>Reihe</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReihe()
	 * @generated
	 * @ordered
	 */
	protected Sitzreihe reihe;

	/**
	 * The cached value of the '{@link #getReservierung() <em>Reservierung</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReservierung()
	 * @generated
	 * @ordered
	 */
	protected Reservierung reservierung;

	/**
	 * The cached value of the '{@link #getBuchung() <em>Buchung</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBuchung()
	 * @generated
	 * @ordered
	 */
	protected Buchung buchung;

	/**
	 * The default value of the '{@link #isIsFrei() <em>Is Frei</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIsFrei()
	 * @generated
	 * @ordered
	 */
	protected static final boolean IS_FREI_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isIsFrei() <em>Is Frei</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIsFrei()
	 * @generated
	 * @ordered
	 */
	protected boolean isFrei = IS_FREI_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SitzplatzImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return KinoPackage.Literals.SITZPLATZ;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getPlatznummer() {
		return platznummer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setPlatznummer(int newPlatznummer) {
		int oldPlatznummer = platznummer;
		platznummer = newPlatznummer;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KinoPackage.SITZPLATZ__PLATZNUMMER, oldPlatznummer, platznummer));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Sitzreihe getReihe() {
		if (reihe != null && reihe.eIsProxy()) {
			InternalEObject oldReihe = (InternalEObject)reihe;
			reihe = (Sitzreihe)eResolveProxy(oldReihe);
			if (reihe != oldReihe) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, KinoPackage.SITZPLATZ__REIHE, oldReihe, reihe));
			}
		}
		return reihe;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Sitzreihe basicGetReihe() {
		return reihe;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setReihe(Sitzreihe newReihe) {
		Sitzreihe oldReihe = reihe;
		reihe = newReihe;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KinoPackage.SITZPLATZ__REIHE, oldReihe, reihe));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Reservierung getReservierung() {
		if (reservierung != null && reservierung.eIsProxy()) {
			InternalEObject oldReservierung = (InternalEObject)reservierung;
			reservierung = (Reservierung)eResolveProxy(oldReservierung);
			if (reservierung != oldReservierung) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, KinoPackage.SITZPLATZ__RESERVIERUNG, oldReservierung, reservierung));
			}
		}
		return reservierung;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Reservierung basicGetReservierung() {
		return reservierung;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setReservierung(Reservierung newReservierung) {
		Reservierung oldReservierung = reservierung;
		reservierung = newReservierung;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KinoPackage.SITZPLATZ__RESERVIERUNG, oldReservierung, reservierung));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Buchung getBuchung() {
		if (buchung != null && buchung.eIsProxy()) {
			InternalEObject oldBuchung = (InternalEObject)buchung;
			buchung = (Buchung)eResolveProxy(oldBuchung);
			if (buchung != oldBuchung) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, KinoPackage.SITZPLATZ__BUCHUNG, oldBuchung, buchung));
			}
		}
		return buchung;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Buchung basicGetBuchung() {
		return buchung;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setBuchung(Buchung newBuchung) {
		Buchung oldBuchung = buchung;
		buchung = newBuchung;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KinoPackage.SITZPLATZ__BUCHUNG, oldBuchung, buchung));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isIsFrei() {
		return isFrei;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setIsFrei(boolean newIsFrei) {
		boolean oldIsFrei = isFrei;
		isFrei = newIsFrei;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KinoPackage.SITZPLATZ__IS_FREI, oldIsFrei, isFrei));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case KinoPackage.SITZPLATZ__PLATZNUMMER:
				return getPlatznummer();
			case KinoPackage.SITZPLATZ__REIHE:
				if (resolve) return getReihe();
				return basicGetReihe();
			case KinoPackage.SITZPLATZ__RESERVIERUNG:
				if (resolve) return getReservierung();
				return basicGetReservierung();
			case KinoPackage.SITZPLATZ__BUCHUNG:
				if (resolve) return getBuchung();
				return basicGetBuchung();
			case KinoPackage.SITZPLATZ__IS_FREI:
				return isIsFrei();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case KinoPackage.SITZPLATZ__PLATZNUMMER:
				setPlatznummer((Integer)newValue);
				return;
			case KinoPackage.SITZPLATZ__REIHE:
				setReihe((Sitzreihe)newValue);
				return;
			case KinoPackage.SITZPLATZ__RESERVIERUNG:
				setReservierung((Reservierung)newValue);
				return;
			case KinoPackage.SITZPLATZ__BUCHUNG:
				setBuchung((Buchung)newValue);
				return;
			case KinoPackage.SITZPLATZ__IS_FREI:
				setIsFrei((Boolean)newValue);
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
			case KinoPackage.SITZPLATZ__PLATZNUMMER:
				setPlatznummer(PLATZNUMMER_EDEFAULT);
				return;
			case KinoPackage.SITZPLATZ__REIHE:
				setReihe((Sitzreihe)null);
				return;
			case KinoPackage.SITZPLATZ__RESERVIERUNG:
				setReservierung((Reservierung)null);
				return;
			case KinoPackage.SITZPLATZ__BUCHUNG:
				setBuchung((Buchung)null);
				return;
			case KinoPackage.SITZPLATZ__IS_FREI:
				setIsFrei(IS_FREI_EDEFAULT);
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
			case KinoPackage.SITZPLATZ__PLATZNUMMER:
				return platznummer != PLATZNUMMER_EDEFAULT;
			case KinoPackage.SITZPLATZ__REIHE:
				return reihe != null;
			case KinoPackage.SITZPLATZ__RESERVIERUNG:
				return reservierung != null;
			case KinoPackage.SITZPLATZ__BUCHUNG:
				return buchung != null;
			case KinoPackage.SITZPLATZ__IS_FREI:
				return isFrei != IS_FREI_EDEFAULT;
		}
		return super.eIsSet(featureID);
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
		result.append(" (platznummer: ");
		result.append(platznummer);
		result.append(", isFrei: ");
		result.append(isFrei);
		result.append(')');
		return result.toString();
	}

} //SitzplatzImpl
