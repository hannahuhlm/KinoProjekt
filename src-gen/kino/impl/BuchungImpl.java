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
 * An implementation of the model object '<em><b>Buchung</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link kino.impl.BuchungImpl#getBuchungsnummer <em>Buchungsnummer</em>}</li>
 *   <li>{@link kino.impl.BuchungImpl#getBuchungsZeitstempel <em>Buchungs Zeitstempel</em>}</li>
 *   <li>{@link kino.impl.BuchungImpl#isBezahlt <em>Bezahlt</em>}</li>
 *   <li>{@link kino.impl.BuchungImpl#getKunde <em>Kunde</em>}</li>
 *   <li>{@link kino.impl.BuchungImpl#getAuffuehrung <em>Auffuehrung</em>}</li>
 *   <li>{@link kino.impl.BuchungImpl#getPlaetze <em>Plaetze</em>}</li>
 *   <li>{@link kino.impl.BuchungImpl#getGesamtpreis <em>Gesamtpreis</em>}</li>
 * </ul>
 *
 * @generated
 */
public class BuchungImpl extends MinimalEObjectImpl.Container implements Buchung {
	/**
	 * The default value of the '{@link #getBuchungsnummer() <em>Buchungsnummer</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBuchungsnummer()
	 * @generated
	 * @ordered
	 */
	protected static final String BUCHUNGSNUMMER_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getBuchungsnummer() <em>Buchungsnummer</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBuchungsnummer()
	 * @generated
	 * @ordered
	 */
	protected String buchungsnummer = BUCHUNGSNUMMER_EDEFAULT;

	/**
	 * The default value of the '{@link #getBuchungsZeitstempel() <em>Buchungs Zeitstempel</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBuchungsZeitstempel()
	 * @generated
	 * @ordered
	 */
	protected static final Date BUCHUNGS_ZEITSTEMPEL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getBuchungsZeitstempel() <em>Buchungs Zeitstempel</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBuchungsZeitstempel()
	 * @generated
	 * @ordered
	 */
	protected Date buchungsZeitstempel = BUCHUNGS_ZEITSTEMPEL_EDEFAULT;

	/**
	 * The default value of the '{@link #isBezahlt() <em>Bezahlt</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isBezahlt()
	 * @generated
	 * @ordered
	 */
	protected static final boolean BEZAHLT_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isBezahlt() <em>Bezahlt</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isBezahlt()
	 * @generated
	 * @ordered
	 */
	protected boolean bezahlt = BEZAHLT_EDEFAULT;

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
	 * The default value of the '{@link #getGesamtpreis() <em>Gesamtpreis</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGesamtpreis()
	 * @generated
	 * @ordered
	 */
	protected static final double GESAMTPREIS_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getGesamtpreis() <em>Gesamtpreis</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGesamtpreis()
	 * @generated
	 * @ordered
	 */
	protected double gesamtpreis = GESAMTPREIS_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BuchungImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return KinoPackage.Literals.BUCHUNG;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getBuchungsnummer() {
		return buchungsnummer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setBuchungsnummer(String newBuchungsnummer) {
		String oldBuchungsnummer = buchungsnummer;
		buchungsnummer = newBuchungsnummer;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KinoPackage.BUCHUNG__BUCHUNGSNUMMER, oldBuchungsnummer, buchungsnummer));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Date getBuchungsZeitstempel() {
		return buchungsZeitstempel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setBuchungsZeitstempel(Date newBuchungsZeitstempel) {
		Date oldBuchungsZeitstempel = buchungsZeitstempel;
		buchungsZeitstempel = newBuchungsZeitstempel;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KinoPackage.BUCHUNG__BUCHUNGS_ZEITSTEMPEL, oldBuchungsZeitstempel, buchungsZeitstempel));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isBezahlt() {
		return bezahlt;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setBezahlt(boolean newBezahlt) {
		boolean oldBezahlt = bezahlt;
		bezahlt = newBezahlt;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KinoPackage.BUCHUNG__BEZAHLT, oldBezahlt, bezahlt));
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, KinoPackage.BUCHUNG__KUNDE, oldKunde, kunde));
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
			eNotify(new ENotificationImpl(this, Notification.SET, KinoPackage.BUCHUNG__KUNDE, oldKunde, kunde));
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, KinoPackage.BUCHUNG__AUFFUEHRUNG, oldAuffuehrung, auffuehrung));
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
			eNotify(new ENotificationImpl(this, Notification.SET, KinoPackage.BUCHUNG__AUFFUEHRUNG, oldAuffuehrung, auffuehrung));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Sitzplatz> getPlaetze() {
		if (plaetze == null) {
			plaetze = new EObjectResolvingEList<Sitzplatz>(Sitzplatz.class, this, KinoPackage.BUCHUNG__PLAETZE);
		}
		return plaetze;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public double getGesamtpreis() {
		return gesamtpreis;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setGesamtpreis(double newGesamtpreis) {
		double oldGesamtpreis = gesamtpreis;
		gesamtpreis = newGesamtpreis;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KinoPackage.BUCHUNG__GESAMTPREIS, oldGesamtpreis, gesamtpreis));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public void plaetzeHinzufuegen(EList<Sitzplatz> plaetze) {
		this.plaetze= plaetze;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case KinoPackage.BUCHUNG__BUCHUNGSNUMMER:
				return getBuchungsnummer();
			case KinoPackage.BUCHUNG__BUCHUNGS_ZEITSTEMPEL:
				return getBuchungsZeitstempel();
			case KinoPackage.BUCHUNG__BEZAHLT:
				return isBezahlt();
			case KinoPackage.BUCHUNG__KUNDE:
				if (resolve) return getKunde();
				return basicGetKunde();
			case KinoPackage.BUCHUNG__AUFFUEHRUNG:
				if (resolve) return getAuffuehrung();
				return basicGetAuffuehrung();
			case KinoPackage.BUCHUNG__PLAETZE:
				return getPlaetze();
			case KinoPackage.BUCHUNG__GESAMTPREIS:
				return getGesamtpreis();
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
			case KinoPackage.BUCHUNG__BUCHUNGSNUMMER:
				setBuchungsnummer((String)newValue);
				return;
			case KinoPackage.BUCHUNG__BUCHUNGS_ZEITSTEMPEL:
				setBuchungsZeitstempel((Date)newValue);
				return;
			case KinoPackage.BUCHUNG__BEZAHLT:
				setBezahlt((Boolean)newValue);
				return;
			case KinoPackage.BUCHUNG__KUNDE:
				setKunde((Kunde)newValue);
				return;
			case KinoPackage.BUCHUNG__AUFFUEHRUNG:
				setAuffuehrung((Auffuehrung)newValue);
				return;
			case KinoPackage.BUCHUNG__PLAETZE:
				getPlaetze().clear();
				getPlaetze().addAll((Collection<? extends Sitzplatz>)newValue);
				return;
			case KinoPackage.BUCHUNG__GESAMTPREIS:
				setGesamtpreis((Double)newValue);
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
			case KinoPackage.BUCHUNG__BUCHUNGSNUMMER:
				setBuchungsnummer(BUCHUNGSNUMMER_EDEFAULT);
				return;
			case KinoPackage.BUCHUNG__BUCHUNGS_ZEITSTEMPEL:
				setBuchungsZeitstempel(BUCHUNGS_ZEITSTEMPEL_EDEFAULT);
				return;
			case KinoPackage.BUCHUNG__BEZAHLT:
				setBezahlt(BEZAHLT_EDEFAULT);
				return;
			case KinoPackage.BUCHUNG__KUNDE:
				setKunde((Kunde)null);
				return;
			case KinoPackage.BUCHUNG__AUFFUEHRUNG:
				setAuffuehrung((Auffuehrung)null);
				return;
			case KinoPackage.BUCHUNG__PLAETZE:
				getPlaetze().clear();
				return;
			case KinoPackage.BUCHUNG__GESAMTPREIS:
				setGesamtpreis(GESAMTPREIS_EDEFAULT);
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
			case KinoPackage.BUCHUNG__BUCHUNGSNUMMER:
				return BUCHUNGSNUMMER_EDEFAULT == null ? buchungsnummer != null : !BUCHUNGSNUMMER_EDEFAULT.equals(buchungsnummer);
			case KinoPackage.BUCHUNG__BUCHUNGS_ZEITSTEMPEL:
				return BUCHUNGS_ZEITSTEMPEL_EDEFAULT == null ? buchungsZeitstempel != null : !BUCHUNGS_ZEITSTEMPEL_EDEFAULT.equals(buchungsZeitstempel);
			case KinoPackage.BUCHUNG__BEZAHLT:
				return bezahlt != BEZAHLT_EDEFAULT;
			case KinoPackage.BUCHUNG__KUNDE:
				return kunde != null;
			case KinoPackage.BUCHUNG__AUFFUEHRUNG:
				return auffuehrung != null;
			case KinoPackage.BUCHUNG__PLAETZE:
				return plaetze != null && !plaetze.isEmpty();
			case KinoPackage.BUCHUNG__GESAMTPREIS:
				return gesamtpreis != GESAMTPREIS_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case KinoPackage.BUCHUNG___PLAETZE_HINZUFUEGEN__ELIST:
				plaetzeHinzufuegen((EList<Sitzplatz>)arguments.get(0));
				return null;
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
		result.append(" (buchungsnummer: ");
		result.append(buchungsnummer);
		result.append(", buchungsZeitstempel: ");
		result.append(buchungsZeitstempel);
		result.append(", bezahlt: ");
		result.append(bezahlt);
		result.append(", gesamtpreis: ");
		result.append(gesamtpreis);
		result.append(')');
		return result.toString();
	}

} //BuchungImpl
