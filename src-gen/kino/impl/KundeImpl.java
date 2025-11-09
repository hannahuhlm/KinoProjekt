/**
 */
package kino.impl;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Date;
import java.util.Random;

import kino.Auffuehrung;
import kino.Buchung;
import kino.KinoPackage;
import kino.Kunde;
import kino.Reservierung;
import kino.Sitzplatz;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Kunde</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link kino.impl.KundeImpl#getName <em>Name</em>}</li>
 *   <li>{@link kino.impl.KundeImpl#getEmail <em>Email</em>}</li>
 *   <li>{@link kino.impl.KundeImpl#getReservierungen <em>Reservierungen</em>}</li>
 *   <li>{@link kino.impl.KundeImpl#getBuchungen <em>Buchungen</em>}</li>
 * </ul>
 *
 * @generated
 */
public class KundeImpl extends MinimalEObjectImpl.Container implements Kunde {
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
	 * The default value of the '{@link #getEmail() <em>Email</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEmail()
	 * @generated
	 * @ordered
	 */
	protected static final String EMAIL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getEmail() <em>Email</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEmail()
	 * @generated
	 * @ordered
	 */
	protected String email = EMAIL_EDEFAULT;

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected KundeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return KinoPackage.Literals.KUNDE;
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
			eNotify(new ENotificationImpl(this, Notification.SET, KinoPackage.KUNDE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getEmail() {
		return email;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setEmail(String newEmail) {
		String oldEmail = email;
		email = newEmail;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KinoPackage.KUNDE__EMAIL, oldEmail, email));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Reservierung> getReservierungen() {
		if (reservierungen == null) {
			reservierungen = new EObjectResolvingEList<Reservierung>(Reservierung.class, this, KinoPackage.KUNDE__RESERVIERUNGEN);
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
			buchungen = new EObjectResolvingEList<Buchung>(Buchung.class, this, KinoPackage.KUNDE__BUCHUNGEN);
		}
		return buchungen;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public Reservierung reservieren(Auffuehrung auffuehrung, EList<Sitzplatz> plaetze) {
		Reservierung reservierung= new ReservierungImpl();

		//fehler wenn sitzplatz nicht verfügbar
		for (Sitzplatz sitzplatz : plaetze) {
			if (!sitzplatz.isIsFrei()) {
				//TODO: fehlerhandling und so dass buchung nicht greift wenn später einer belegt
			}else {
				//sitz reservieren
				sitzplatz.setIsFrei(false);
				sitzplatz.setReservierung(reservierung);
			}
		}
		// Nummer festlegen
		LocalTime now = LocalTime.now();
        int time = now.getHour() * 10000 + now.getMinute() * 100 + now.getSecond(); 
        int random = new Random().nextInt(90) + 10; 
        int nummer = (time * 100) + random; 
        
		//Attribute setzen
		reservierung.setStartZeitstempel(new Date());
		reservierung.setReservierungsnummer(nummer);
		reservierung.setKunde(this);
		reservierung.setAuffuehrung(auffuehrung);
		reservierung.plaetzeHinzufuegen(plaetze);
		
		
		//die reservierung der Liste an Reservierungen des Kunden und der Aufführung hinzufügen 
		this.reservierungen.add(reservierung);
		auffuehrung.reservierungHinzufuegen(reservierung);
		
		return reservierung;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public void reservierungStornieren(Reservierung reservierung, Auffuehrung auffuehrung) {
		this.reservierungen.remove(reservierung);
		auffuehrung.reservierungLoeschen(reservierung);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public Buchung direktBuchung(Auffuehrung auffuehrung, EList<Sitzplatz> plaetze) {
		Buchung buchung= new BuchungImpl();
		//fehler wenn sitzplatz nicht verfügbar
		for (Sitzplatz sitzplatz : plaetze) {
			if (!sitzplatz.isIsFrei()) {
				//TODO: fehlerhandling und so dass buchung nicht greift wenn später einer belegt
			}else {
				//sitz reservieren
				sitzplatz.setIsFrei(false);
				sitzplatz.setBuchung(buchung);
			}
		}
		//Buchungscode generieren:
		LocalTime now = LocalTime.now();
        int time = now.getHour() * 10000 + now.getMinute() * 100 + now.getSecond(); 
        int random = new Random().nextInt(90) + 10; 
        int nummer = (time * 100) + random; 
        String buchungscode = "B" + String.valueOf(nummer);
        
		//Attribute setzen
		buchung.setBuchungsZeitstempel(new Date());
		buchung.setBuchungsnummer(buchungscode);
		buchung.setKunde(this);
		buchung.setAuffuehrung(auffuehrung);
		buchung.plaetzeHinzufuegen(plaetze);
		
		//Buchung der Liste an Buchungen von Kunde/Aufführung hinzufügen
		this.buchungen.add(buchung);
		auffuehrung.getBuchungen().add(buchung);
		
		return buchung;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public Buchung reservierungZuBuchungVerarbeiten(Reservierung reservierung) {
		Buchung buchung= new BuchungImpl();
		
		//buchungscode erstellen
		String buchungscode= "B" + String.valueOf(reservierung.getReservierungsnummer());
		
		//Attribute setzen
		buchung.setBuchungsZeitstempel(new Date());
		buchung.setBuchungsnummer(buchungscode);
		buchung.setKunde(reservierung.getKunde());
		buchung.setAuffuehrung(reservierung.getAuffuehrung());
		buchung.plaetzeHinzufuegen(reservierung.getPlaetze());
		
		//Buchung der Liste an Buchungen von Kunde/Aufführung hinzufügen
		this.buchungen.add(buchung);
		reservierung.getAuffuehrung().getBuchungen().add(buchung);
		
		return buchung;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case KinoPackage.KUNDE__NAME:
				return getName();
			case KinoPackage.KUNDE__EMAIL:
				return getEmail();
			case KinoPackage.KUNDE__RESERVIERUNGEN:
				return getReservierungen();
			case KinoPackage.KUNDE__BUCHUNGEN:
				return getBuchungen();
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
			case KinoPackage.KUNDE__NAME:
				setName((String)newValue);
				return;
			case KinoPackage.KUNDE__EMAIL:
				setEmail((String)newValue);
				return;
			case KinoPackage.KUNDE__RESERVIERUNGEN:
				getReservierungen().clear();
				getReservierungen().addAll((Collection<? extends Reservierung>)newValue);
				return;
			case KinoPackage.KUNDE__BUCHUNGEN:
				getBuchungen().clear();
				getBuchungen().addAll((Collection<? extends Buchung>)newValue);
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
			case KinoPackage.KUNDE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case KinoPackage.KUNDE__EMAIL:
				setEmail(EMAIL_EDEFAULT);
				return;
			case KinoPackage.KUNDE__RESERVIERUNGEN:
				getReservierungen().clear();
				return;
			case KinoPackage.KUNDE__BUCHUNGEN:
				getBuchungen().clear();
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
			case KinoPackage.KUNDE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case KinoPackage.KUNDE__EMAIL:
				return EMAIL_EDEFAULT == null ? email != null : !EMAIL_EDEFAULT.equals(email);
			case KinoPackage.KUNDE__RESERVIERUNGEN:
				return reservierungen != null && !reservierungen.isEmpty();
			case KinoPackage.KUNDE__BUCHUNGEN:
				return buchungen != null && !buchungen.isEmpty();
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
			case KinoPackage.KUNDE___RESERVIEREN__AUFFUEHRUNG_ELIST:
				return reservieren((Auffuehrung)arguments.get(0), (EList<Sitzplatz>)arguments.get(1));
			case KinoPackage.KUNDE___RESERVIERUNG_STORNIEREN__RESERVIERUNG_AUFFUEHRUNG:
				reservierungStornieren((Reservierung)arguments.get(0), (Auffuehrung)arguments.get(1));
				return null;
			case KinoPackage.KUNDE___DIREKT_BUCHUNG__AUFFUEHRUNG_ELIST:
				return direktBuchung((Auffuehrung)arguments.get(0), (EList<Sitzplatz>)arguments.get(1));
			case KinoPackage.KUNDE___RESERVIERUNG_ZU_BUCHUNG_VERARBEITEN__RESERVIERUNG:
				return reservierungZuBuchungVerarbeiten((Reservierung)arguments.get(0));
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
		result.append(", email: ");
		result.append(email);
		result.append(')');
		return result.toString();
	}

} //KundeImpl
