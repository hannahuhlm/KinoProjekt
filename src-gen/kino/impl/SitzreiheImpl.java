/**
 */
package kino.impl;

import java.util.Collection;

import kino.KinoPackage;
import kino.Kinosaal;
import kino.Sitzplatz;
import kino.Sitzreihe;
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
 * An implementation of the model object '<em><b>Sitzreihe</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link kino.impl.SitzreiheImpl#getReihennummer <em>Reihennummer</em>}</li>
 *   <li>{@link kino.impl.SitzreiheImpl#getKategorie <em>Kategorie</em>}</li>
 *   <li>{@link kino.impl.SitzreiheImpl#getAnzahlSitze <em>Anzahl Sitze</em>}</li>
 *   <li>{@link kino.impl.SitzreiheImpl#getPlaetze <em>Plaetze</em>}</li>
 *   <li>{@link kino.impl.SitzreiheImpl#getSaal <em>Saal</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SitzreiheImpl extends MinimalEObjectImpl.Container implements Sitzreihe {
	/**
	 * The default value of the '{@link #getReihennummer() <em>Reihennummer</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReihennummer()
	 * @generated
	 * @ordered
	 */
	protected static final int REIHENNUMMER_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getReihennummer() <em>Reihennummer</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReihennummer()
	 * @generated
	 * @ordered
	 */
	protected int reihennummer = REIHENNUMMER_EDEFAULT;

	/**
	 * The default value of the '{@link #getKategorie() <em>Kategorie</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKategorie()
	 * @generated
	 * @ordered
	 */
	protected static final SitzreihenKategorie KATEGORIE_EDEFAULT = SitzreihenKategorie.PARKETT;

	/**
	 * The cached value of the '{@link #getKategorie() <em>Kategorie</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKategorie()
	 * @generated
	 * @ordered
	 */
	protected SitzreihenKategorie kategorie = KATEGORIE_EDEFAULT;

	/**
	 * The default value of the '{@link #getAnzahlSitze() <em>Anzahl Sitze</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAnzahlSitze()
	 * @generated
	 * @ordered
	 */
	protected static final int ANZAHL_SITZE_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getAnzahlSitze() <em>Anzahl Sitze</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAnzahlSitze()
	 * @generated
	 * @ordered
	 */
	protected int anzahlSitze = ANZAHL_SITZE_EDEFAULT;

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
	 * The cached value of the '{@link #getSaal() <em>Saal</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSaal()
	 * @generated
	 * @ordered
	 */
	protected Kinosaal saal;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SitzreiheImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return KinoPackage.Literals.SITZREIHE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getReihennummer() {
		return reihennummer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setReihennummer(int newReihennummer) {
		int oldReihennummer = reihennummer;
		reihennummer = newReihennummer;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KinoPackage.SITZREIHE__REIHENNUMMER, oldReihennummer, reihennummer));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SitzreihenKategorie getKategorie() {
		return kategorie;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setKategorie(SitzreihenKategorie newKategorie) {
		SitzreihenKategorie oldKategorie = kategorie;
		kategorie = newKategorie == null ? KATEGORIE_EDEFAULT : newKategorie;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KinoPackage.SITZREIHE__KATEGORIE, oldKategorie, kategorie));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getAnzahlSitze() {
		return anzahlSitze;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setAnzahlSitze(int newAnzahlSitze) {
		int oldAnzahlSitze = anzahlSitze;
		anzahlSitze = newAnzahlSitze;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KinoPackage.SITZREIHE__ANZAHL_SITZE, oldAnzahlSitze, anzahlSitze));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Sitzplatz> getPlaetze() {
		if (plaetze == null) {
			plaetze = new EObjectResolvingEList<Sitzplatz>(Sitzplatz.class, this, KinoPackage.SITZREIHE__PLAETZE);
		}
		return plaetze;
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, KinoPackage.SITZREIHE__SAAL, oldSaal, saal));
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
			eNotify(new ENotificationImpl(this, Notification.SET, KinoPackage.SITZREIHE__SAAL, oldSaal, saal));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case KinoPackage.SITZREIHE__REIHENNUMMER:
				return getReihennummer();
			case KinoPackage.SITZREIHE__KATEGORIE:
				return getKategorie();
			case KinoPackage.SITZREIHE__ANZAHL_SITZE:
				return getAnzahlSitze();
			case KinoPackage.SITZREIHE__PLAETZE:
				return getPlaetze();
			case KinoPackage.SITZREIHE__SAAL:
				if (resolve) return getSaal();
				return basicGetSaal();
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
			case KinoPackage.SITZREIHE__REIHENNUMMER:
				setReihennummer((Integer)newValue);
				return;
			case KinoPackage.SITZREIHE__KATEGORIE:
				setKategorie((SitzreihenKategorie)newValue);
				return;
			case KinoPackage.SITZREIHE__ANZAHL_SITZE:
				setAnzahlSitze((Integer)newValue);
				return;
			case KinoPackage.SITZREIHE__PLAETZE:
				getPlaetze().clear();
				getPlaetze().addAll((Collection<? extends Sitzplatz>)newValue);
				return;
			case KinoPackage.SITZREIHE__SAAL:
				setSaal((Kinosaal)newValue);
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
			case KinoPackage.SITZREIHE__REIHENNUMMER:
				setReihennummer(REIHENNUMMER_EDEFAULT);
				return;
			case KinoPackage.SITZREIHE__KATEGORIE:
				setKategorie(KATEGORIE_EDEFAULT);
				return;
			case KinoPackage.SITZREIHE__ANZAHL_SITZE:
				setAnzahlSitze(ANZAHL_SITZE_EDEFAULT);
				return;
			case KinoPackage.SITZREIHE__PLAETZE:
				getPlaetze().clear();
				return;
			case KinoPackage.SITZREIHE__SAAL:
				setSaal((Kinosaal)null);
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
			case KinoPackage.SITZREIHE__REIHENNUMMER:
				return reihennummer != REIHENNUMMER_EDEFAULT;
			case KinoPackage.SITZREIHE__KATEGORIE:
				return kategorie != KATEGORIE_EDEFAULT;
			case KinoPackage.SITZREIHE__ANZAHL_SITZE:
				return anzahlSitze != ANZAHL_SITZE_EDEFAULT;
			case KinoPackage.SITZREIHE__PLAETZE:
				return plaetze != null && !plaetze.isEmpty();
			case KinoPackage.SITZREIHE__SAAL:
				return saal != null;
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
		result.append(" (reihennummer: ");
		result.append(reihennummer);
		result.append(", kategorie: ");
		result.append(kategorie);
		result.append(", anzahlSitze: ");
		result.append(anzahlSitze);
		result.append(')');
		return result.toString();
	}

} //SitzreiheImpl
