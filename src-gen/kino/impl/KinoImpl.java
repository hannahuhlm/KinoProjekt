/**
 */
package kino.impl;

import java.lang.reflect.InvocationTargetException;

import java.util.Collection;
import kino.Auffuehrung;
import kino.Film;
import kino.Kino;
import kino.KinoPackage;
import kino.Kinosaal;
import kino.Kunde;
import kino.Sitzreihe;
import kino.SitzreihenKategorie;
import kino.KinoFactory;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Kino</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link kino.impl.KinoImpl#getName <em>Name</em>}</li>
 *   <li>{@link kino.impl.KinoImpl#getSaele <em>Saele</em>}</li>
 *   <li>{@link kino.impl.KinoImpl#getKunden <em>Kunden</em>}</li>
 * </ul>
 *
 * @generated
 */
public class KinoImpl extends MinimalEObjectImpl.Container implements Kino {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getSaele() <em>Saele</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSaele()
	 * @generated
	 * @ordered
	 */
	protected EList<Kinosaal> saele;

	/**
	 * The cached value of the '{@link #getKunden() <em>Kunden</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKunden()
	 * @generated
	 * @ordered
	 */
	protected EList<Kunde> kunden;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected KinoImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return KinoPackage.Literals.KINO;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KinoPackage.KINO__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Kinosaal> getSaele() {
		if (saele == null) {
			saele = new EObjectResolvingEList<Kinosaal>(Kinosaal.class, this, KinoPackage.KINO__SAELE);
		}
		return saele;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Kunde> getKunden() {
		if (kunden == null) {
			kunden = new EObjectResolvingEList<Kunde>(Kunde.class, this, KinoPackage.KINO__KUNDEN);
		}
		return kunden;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public Kinosaal saalAnlegen(String name) {
		Kinosaal neuerSaal = KinoFactory.eINSTANCE.createKinosaal();
	    neuerSaal.setName(name);
	    // In die Liste der Säle des Kinos einfügen
	    getSaele().add(neuerSaal);
	    return neuerSaal;		
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public Film filmAnlegen(String titel, int dauer, String beschreibung) {
		Film neuerFilm = KinoFactory.eINSTANCE.createFilm();
		
		//attribute setzen
		neuerFilm.setTitel(titel);
		neuerFilm.setDauer(dauer);
		neuerFilm.setBeschreibung(beschreibung);

		return neuerFilm;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public double einnahmenBerechnen(Auffuehrung auffuehrung) {
		return auffuehrung.getAktuelleEinnahmen();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public double einnahmenBerechnen(Film film) {
		return film.gesamtEinnahmen();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public Sitzreihe sitzreiheAnlegen(int nummer, SitzreihenKategorie kategorie, int sitzAnzahl, Kinosaal saal) {
		Sitzreihe reihe = new SitzreiheImpl();
		reihe.setReihennummer(nummer);
		reihe.setKategorie(kategorie);
		reihe.setAnzahlSitze(sitzAnzahl);
		reihe.setSaal(saal);
		//auch dem Saal die Reihe hinzufügen
		saal.reiheHinzufügen(reihe);
		return reihe;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public void kundeAnlegen(String name, String email) {
		Kunde kunde = new KundeImpl();
		kunde.setName(name);
		kunde.setEmail(email);
		
		this.kunden.add(kunde);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case KinoPackage.KINO__NAME:
				return getName();
			case KinoPackage.KINO__SAELE:
				return getSaele();
			case KinoPackage.KINO__KUNDEN:
				return getKunden();
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
			case KinoPackage.KINO__NAME:
				setName((String)newValue);
				return;
			case KinoPackage.KINO__SAELE:
				getSaele().clear();
				getSaele().addAll((Collection<? extends Kinosaal>)newValue);
				return;
			case KinoPackage.KINO__KUNDEN:
				getKunden().clear();
				getKunden().addAll((Collection<? extends Kunde>)newValue);
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
			case KinoPackage.KINO__NAME:
				setName(NAME_EDEFAULT);
				return;
			case KinoPackage.KINO__SAELE:
				getSaele().clear();
				return;
			case KinoPackage.KINO__KUNDEN:
				getKunden().clear();
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
			case KinoPackage.KINO__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case KinoPackage.KINO__SAELE:
				return saele != null && !saele.isEmpty();
			case KinoPackage.KINO__KUNDEN:
				return kunden != null && !kunden.isEmpty();
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
			case KinoPackage.KINO___SAAL_ANLEGEN__STRING:
				return saalAnlegen((String)arguments.get(0));
			case KinoPackage.KINO___FILM_ANLEGEN__STRING_INT_STRING:
				return filmAnlegen((String)arguments.get(0), (Integer)arguments.get(1), (String)arguments.get(2));
			case KinoPackage.KINO___EINNAHMEN_BERECHNEN__AUFFUEHRUNG:
				return einnahmenBerechnen((Auffuehrung)arguments.get(0));
			case KinoPackage.KINO___EINNAHMEN_BERECHNEN__FILM:
				return einnahmenBerechnen((Film)arguments.get(0));
			case KinoPackage.KINO___SITZREIHE_ANLEGEN__INT_SITZREIHENKATEGORIE_INT_KINOSAAL:
				return sitzreiheAnlegen((Integer)arguments.get(0), (SitzreihenKategorie)arguments.get(1), (Integer)arguments.get(2), (Kinosaal)arguments.get(3));
			case KinoPackage.KINO___KUNDE_ANLEGEN__STRING_STRING:
				kundeAnlegen((String)arguments.get(0), (String)arguments.get(1));
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
		result.append(" (name: ");
		result.append(name);
		result.append(')');
		return result.toString();
	}

} //KinoImpl
