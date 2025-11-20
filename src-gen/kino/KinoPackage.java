/**
 */
package kino;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see kino.KinoFactory
 * @model kind="package"
 * @generated
 */
public interface KinoPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "kino";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.beispiel.de/kino";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "kino";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	KinoPackage eINSTANCE = kino.impl.KinoPackageImpl.init();

	/**
	 * The meta object id for the '{@link kino.impl.KinoImpl <em>Kino</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see kino.impl.KinoImpl
	 * @see kino.impl.KinoPackageImpl#getKino()
	 * @generated
	 */
	int KINO = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int KINO__NAME = 0;

	/**
	 * The feature id for the '<em><b>Saele</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int KINO__SAELE = 1;

	/**
	 * The feature id for the '<em><b>Kunden</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int KINO__KUNDEN = 2;

	/**
	 * The feature id for the '<em><b>Programm</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int KINO__PROGRAMM = 3;

	/**
	 * The number of structural features of the '<em>Kino</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int KINO_FEATURE_COUNT = 4;

	/**
	 * The operation id for the '<em>Saal Anlegen</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int KINO___SAAL_ANLEGEN__STRING = 0;

	/**
	 * The operation id for the '<em>Film Anlegen</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int KINO___FILM_ANLEGEN__STRING_INT_STRING = 1;

	/**
	 * The operation id for the '<em>Einnahmen Berechnen</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int KINO___EINNAHMEN_BERECHNEN__AUFFUEHRUNG = 2;

	/**
	 * The operation id for the '<em>Einnahmen Berechnen</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int KINO___EINNAHMEN_BERECHNEN__FILM = 3;

	/**
	 * The operation id for the '<em>Sitzreihe Anlegen</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int KINO___SITZREIHE_ANLEGEN__INT_SITZREIHENKATEGORIE_INT_KINOSAAL = 4;

	/**
	 * The operation id for the '<em>Kunde Anlegen</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int KINO___KUNDE_ANLEGEN__STRING_STRING = 5;

	/**
	 * The number of operations of the '<em>Kino</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int KINO_OPERATION_COUNT = 6;

	/**
	 * The meta object id for the '{@link kino.impl.KinosaalImpl <em>Kinosaal</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see kino.impl.KinosaalImpl
	 * @see kino.impl.KinoPackageImpl#getKinosaal()
	 * @generated
	 */
	int KINOSAAL = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int KINOSAAL__NAME = 0;

	/**
	 * The feature id for the '<em><b>Freigegeben</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int KINOSAAL__FREIGEGEBEN = 1;

	/**
	 * The feature id for the '<em><b>Reihen</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int KINOSAAL__REIHEN = 2;

	/**
	 * The feature id for the '<em><b>Auffuehrungen</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int KINOSAAL__AUFFUEHRUNGEN = 3;

	/**
	 * The number of structural features of the '<em>Kinosaal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int KINOSAAL_FEATURE_COUNT = 4;

	/**
	 * The operation id for the '<em>Reihe Hinzufügen</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int KINOSAAL___REIHE_HINZUFÜGEN__SITZREIHE = 0;

	/**
	 * The operation id for the '<em>Freigeben</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int KINOSAAL___FREIGEBEN = 1;

	/**
	 * The operation id for the '<em>Ist Freigegeben</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int KINOSAAL___IST_FREIGEGEBEN = 2;

	/**
	 * The number of operations of the '<em>Kinosaal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int KINOSAAL_OPERATION_COUNT = 3;

	/**
	 * The meta object id for the '{@link kino.impl.FilmImpl <em>Film</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see kino.impl.FilmImpl
	 * @see kino.impl.KinoPackageImpl#getFilm()
	 * @generated
	 */
	int FILM = 2;

	/**
	 * The feature id for the '<em><b>Titel</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILM__TITEL = 0;

	/**
	 * The feature id for the '<em><b>Dauer</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILM__DAUER = 1;

	/**
	 * The feature id for the '<em><b>Beschreibung</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILM__BESCHREIBUNG = 2;

	/**
	 * The feature id for the '<em><b>Auffuehrungen</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILM__AUFFUEHRUNGEN = 3;

	/**
	 * The number of structural features of the '<em>Film</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILM_FEATURE_COUNT = 4;

	/**
	 * The operation id for the '<em>Gesamt Einnahmen</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILM___GESAMT_EINNAHMEN = 0;

	/**
	 * The number of operations of the '<em>Film</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILM_OPERATION_COUNT = 1;

	/**
	 * The meta object id for the '{@link kino.impl.AuffuehrungImpl <em>Auffuehrung</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see kino.impl.AuffuehrungImpl
	 * @see kino.impl.KinoPackageImpl#getAuffuehrung()
	 * @generated
	 */
	int AUFFUEHRUNG = 3;

	/**
	 * The feature id for the '<em><b>Startzeitpunkt</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUFFUEHRUNG__STARTZEITPUNKT = 0;

	/**
	 * The feature id for the '<em><b>Saal</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUFFUEHRUNG__SAAL = 1;

	/**
	 * The feature id for the '<em><b>Reservierungen</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUFFUEHRUNG__RESERVIERUNGEN = 2;

	/**
	 * The feature id for the '<em><b>Buchungen</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUFFUEHRUNG__BUCHUNGEN = 3;

	/**
	 * The feature id for the '<em><b>Film</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUFFUEHRUNG__FILM = 4;

	/**
	 * The feature id for the '<em><b>Aktuelle Einnahmen</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUFFUEHRUNG__AKTUELLE_EINNAHMEN = 5;

	/**
	 * The number of structural features of the '<em>Auffuehrung</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUFFUEHRUNG_FEATURE_COUNT = 6;

	/**
	 * The operation id for the '<em>Verfuegbare Plaetze</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUFFUEHRUNG___VERFUEGBARE_PLAETZE__SITZREIHENKATEGORIE = 0;

	/**
	 * The operation id for the '<em>Reservierung Hinzufuegen</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUFFUEHRUNG___RESERVIERUNG_HINZUFUEGEN__RESERVIERUNG = 1;

	/**
	 * The operation id for the '<em>Buchung Hinzufuegen</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUFFUEHRUNG___BUCHUNG_HINZUFUEGEN__BUCHUNG = 2;

	/**
	 * The operation id for the '<em>Reservierung Loeschen</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUFFUEHRUNG___RESERVIERUNG_LOESCHEN__RESERVIERUNG = 3;

	/**
	 * The number of operations of the '<em>Auffuehrung</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUFFUEHRUNG_OPERATION_COUNT = 4;

	/**
	 * The meta object id for the '{@link kino.impl.SitzreiheImpl <em>Sitzreihe</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see kino.impl.SitzreiheImpl
	 * @see kino.impl.KinoPackageImpl#getSitzreihe()
	 * @generated
	 */
	int SITZREIHE = 4;

	/**
	 * The feature id for the '<em><b>Reihennummer</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SITZREIHE__REIHENNUMMER = 0;

	/**
	 * The feature id for the '<em><b>Kategorie</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SITZREIHE__KATEGORIE = 1;

	/**
	 * The feature id for the '<em><b>Anzahl Sitze</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SITZREIHE__ANZAHL_SITZE = 2;

	/**
	 * The feature id for the '<em><b>Plaetze</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SITZREIHE__PLAETZE = 3;

	/**
	 * The feature id for the '<em><b>Saal</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SITZREIHE__SAAL = 4;

	/**
	 * The number of structural features of the '<em>Sitzreihe</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SITZREIHE_FEATURE_COUNT = 5;

	/**
	 * The operation id for the '<em>Plaetze Anlegen</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SITZREIHE___PLAETZE_ANLEGEN = 0;

	/**
	 * The number of operations of the '<em>Sitzreihe</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SITZREIHE_OPERATION_COUNT = 1;

	/**
	 * The meta object id for the '{@link kino.impl.SitzplatzImpl <em>Sitzplatz</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see kino.impl.SitzplatzImpl
	 * @see kino.impl.KinoPackageImpl#getSitzplatz()
	 * @generated
	 */
	int SITZPLATZ = 5;

	/**
	 * The feature id for the '<em><b>Platznummer</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SITZPLATZ__PLATZNUMMER = 0;

	/**
	 * The feature id for the '<em><b>Reihe</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SITZPLATZ__REIHE = 1;

	/**
	 * The feature id for the '<em><b>Reservierung</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SITZPLATZ__RESERVIERUNG = 2;

	/**
	 * The feature id for the '<em><b>Buchung</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SITZPLATZ__BUCHUNG = 3;

	/**
	 * The feature id for the '<em><b>Is Frei</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SITZPLATZ__IS_FREI = 4;

	/**
	 * The number of structural features of the '<em>Sitzplatz</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SITZPLATZ_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>Sitzplatz</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SITZPLATZ_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link kino.impl.KundeImpl <em>Kunde</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see kino.impl.KundeImpl
	 * @see kino.impl.KinoPackageImpl#getKunde()
	 * @generated
	 */
	int KUNDE = 6;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int KUNDE__NAME = 0;

	/**
	 * The feature id for the '<em><b>Email</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int KUNDE__EMAIL = 1;

	/**
	 * The feature id for the '<em><b>Reservierungen</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int KUNDE__RESERVIERUNGEN = 2;

	/**
	 * The feature id for the '<em><b>Buchungen</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int KUNDE__BUCHUNGEN = 3;

	/**
	 * The number of structural features of the '<em>Kunde</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int KUNDE_FEATURE_COUNT = 4;

	/**
	 * The operation id for the '<em>Reservieren</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int KUNDE___RESERVIEREN__AUFFUEHRUNG_ELIST = 0;

	/**
	 * The operation id for the '<em>Reservierung Stornieren</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int KUNDE___RESERVIERUNG_STORNIEREN__RESERVIERUNG_AUFFUEHRUNG = 1;

	/**
	 * The operation id for the '<em>Direkt Buchung</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int KUNDE___DIREKT_BUCHUNG__AUFFUEHRUNG_ELIST = 2;

	/**
	 * The operation id for the '<em>Reservierung Zu Buchung Verarbeiten</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int KUNDE___RESERVIERUNG_ZU_BUCHUNG_VERARBEITEN__RESERVIERUNG = 3;

	/**
	 * The number of operations of the '<em>Kunde</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int KUNDE_OPERATION_COUNT = 4;

	/**
	 * The meta object id for the '{@link kino.impl.ReservierungImpl <em>Reservierung</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see kino.impl.ReservierungImpl
	 * @see kino.impl.KinoPackageImpl#getReservierung()
	 * @generated
	 */
	int RESERVIERUNG = 7;

	/**
	 * The feature id for the '<em><b>Reservierungsnummer</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESERVIERUNG__RESERVIERUNGSNUMMER = 0;

	/**
	 * The feature id for the '<em><b>Start Zeitstempel</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESERVIERUNG__START_ZEITSTEMPEL = 1;

	/**
	 * The feature id for the '<em><b>Kunde</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESERVIERUNG__KUNDE = 2;

	/**
	 * The feature id for the '<em><b>Auffuehrung</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESERVIERUNG__AUFFUEHRUNG = 3;

	/**
	 * The feature id for the '<em><b>Plaetze</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESERVIERUNG__PLAETZE = 4;

	/**
	 * The number of structural features of the '<em>Reservierung</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESERVIERUNG_FEATURE_COUNT = 5;

	/**
	 * The operation id for the '<em>Plaetze Hinzufuegen</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESERVIERUNG___PLAETZE_HINZUFUEGEN__ELIST = 0;

	/**
	 * The number of operations of the '<em>Reservierung</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESERVIERUNG_OPERATION_COUNT = 1;

	/**
	 * The meta object id for the '{@link kino.impl.BuchungImpl <em>Buchung</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see kino.impl.BuchungImpl
	 * @see kino.impl.KinoPackageImpl#getBuchung()
	 * @generated
	 */
	int BUCHUNG = 8;

	/**
	 * The feature id for the '<em><b>Buchungsnummer</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUCHUNG__BUCHUNGSNUMMER = 0;

	/**
	 * The feature id for the '<em><b>Buchungs Zeitstempel</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUCHUNG__BUCHUNGS_ZEITSTEMPEL = 1;

	/**
	 * The feature id for the '<em><b>Bezahlt</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUCHUNG__BEZAHLT = 2;

	/**
	 * The feature id for the '<em><b>Kunde</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUCHUNG__KUNDE = 3;

	/**
	 * The feature id for the '<em><b>Auffuehrung</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUCHUNG__AUFFUEHRUNG = 4;

	/**
	 * The feature id for the '<em><b>Plaetze</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUCHUNG__PLAETZE = 5;

	/**
	 * The feature id for the '<em><b>Gesamtpreis</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUCHUNG__GESAMTPREIS = 6;

	/**
	 * The number of structural features of the '<em>Buchung</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUCHUNG_FEATURE_COUNT = 7;

	/**
	 * The operation id for the '<em>Plaetze Hinzufuegen</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUCHUNG___PLAETZE_HINZUFUEGEN__ELIST = 0;

	/**
	 * The number of operations of the '<em>Buchung</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUCHUNG_OPERATION_COUNT = 1;

	/**
	 * The meta object id for the '{@link kino.SitzreihenKategorie <em>Sitzreihen Kategorie</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see kino.SitzreihenKategorie
	 * @see kino.impl.KinoPackageImpl#getSitzreihenKategorie()
	 * @generated
	 */
	int SITZREIHEN_KATEGORIE = 9;


	/**
	 * Returns the meta object for class '{@link kino.Kino <em>Kino</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Kino</em>'.
	 * @see kino.Kino
	 * @generated
	 */
	EClass getKino();

	/**
	 * Returns the meta object for the attribute '{@link kino.Kino#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see kino.Kino#getName()
	 * @see #getKino()
	 * @generated
	 */
	EAttribute getKino_Name();

	/**
	 * Returns the meta object for the reference list '{@link kino.Kino#getSaele <em>Saele</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Saele</em>'.
	 * @see kino.Kino#getSaele()
	 * @see #getKino()
	 * @generated
	 */
	EReference getKino_Saele();

	/**
	 * Returns the meta object for the reference list '{@link kino.Kino#getKunden <em>Kunden</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Kunden</em>'.
	 * @see kino.Kino#getKunden()
	 * @see #getKino()
	 * @generated
	 */
	EReference getKino_Kunden();

	/**
	 * Returns the meta object for the reference list '{@link kino.Kino#getProgramm <em>Programm</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Programm</em>'.
	 * @see kino.Kino#getProgramm()
	 * @see #getKino()
	 * @generated
	 */
	EReference getKino_Programm();

	/**
	 * Returns the meta object for the '{@link kino.Kino#saalAnlegen(java.lang.String) <em>Saal Anlegen</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Saal Anlegen</em>' operation.
	 * @see kino.Kino#saalAnlegen(java.lang.String)
	 * @generated
	 */
	EOperation getKino__SaalAnlegen__String();

	/**
	 * Returns the meta object for the '{@link kino.Kino#filmAnlegen(java.lang.String, int, java.lang.String) <em>Film Anlegen</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Film Anlegen</em>' operation.
	 * @see kino.Kino#filmAnlegen(java.lang.String, int, java.lang.String)
	 * @generated
	 */
	EOperation getKino__FilmAnlegen__String_int_String();

	/**
	 * Returns the meta object for the '{@link kino.Kino#einnahmenBerechnen(kino.Auffuehrung) <em>Einnahmen Berechnen</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Einnahmen Berechnen</em>' operation.
	 * @see kino.Kino#einnahmenBerechnen(kino.Auffuehrung)
	 * @generated
	 */
	EOperation getKino__EinnahmenBerechnen__Auffuehrung();

	/**
	 * Returns the meta object for the '{@link kino.Kino#einnahmenBerechnen(kino.Film) <em>Einnahmen Berechnen</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Einnahmen Berechnen</em>' operation.
	 * @see kino.Kino#einnahmenBerechnen(kino.Film)
	 * @generated
	 */
	EOperation getKino__EinnahmenBerechnen__Film();

	/**
	 * Returns the meta object for the '{@link kino.Kino#sitzreiheAnlegen(int, kino.SitzreihenKategorie, int, kino.Kinosaal) <em>Sitzreihe Anlegen</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Sitzreihe Anlegen</em>' operation.
	 * @see kino.Kino#sitzreiheAnlegen(int, kino.SitzreihenKategorie, int, kino.Kinosaal)
	 * @generated
	 */
	EOperation getKino__SitzreiheAnlegen__int_SitzreihenKategorie_int_Kinosaal();

	/**
	 * Returns the meta object for the '{@link kino.Kino#kundeAnlegen(java.lang.String, java.lang.String) <em>Kunde Anlegen</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Kunde Anlegen</em>' operation.
	 * @see kino.Kino#kundeAnlegen(java.lang.String, java.lang.String)
	 * @generated
	 */
	EOperation getKino__KundeAnlegen__String_String();

	/**
	 * Returns the meta object for class '{@link kino.Kinosaal <em>Kinosaal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Kinosaal</em>'.
	 * @see kino.Kinosaal
	 * @generated
	 */
	EClass getKinosaal();

	/**
	 * Returns the meta object for the attribute '{@link kino.Kinosaal#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see kino.Kinosaal#getName()
	 * @see #getKinosaal()
	 * @generated
	 */
	EAttribute getKinosaal_Name();

	/**
	 * Returns the meta object for the attribute '{@link kino.Kinosaal#isFreigegeben <em>Freigegeben</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Freigegeben</em>'.
	 * @see kino.Kinosaal#isFreigegeben()
	 * @see #getKinosaal()
	 * @generated
	 */
	EAttribute getKinosaal_Freigegeben();

	/**
	 * Returns the meta object for the reference list '{@link kino.Kinosaal#getReihen <em>Reihen</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Reihen</em>'.
	 * @see kino.Kinosaal#getReihen()
	 * @see #getKinosaal()
	 * @generated
	 */
	EReference getKinosaal_Reihen();

	/**
	 * Returns the meta object for the reference list '{@link kino.Kinosaal#getAuffuehrungen <em>Auffuehrungen</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Auffuehrungen</em>'.
	 * @see kino.Kinosaal#getAuffuehrungen()
	 * @see #getKinosaal()
	 * @generated
	 */
	EReference getKinosaal_Auffuehrungen();

	/**
	 * Returns the meta object for the '{@link kino.Kinosaal#reiheHinzufügen(kino.Sitzreihe) <em>Reihe Hinzufügen</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Reihe Hinzufügen</em>' operation.
	 * @see kino.Kinosaal#reiheHinzufügen(kino.Sitzreihe)
	 * @generated
	 */
	EOperation getKinosaal__ReiheHinzufügen__Sitzreihe();

	/**
	 * Returns the meta object for the '{@link kino.Kinosaal#freigeben() <em>Freigeben</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Freigeben</em>' operation.
	 * @see kino.Kinosaal#freigeben()
	 * @generated
	 */
	EOperation getKinosaal__Freigeben();

	/**
	 * Returns the meta object for the '{@link kino.Kinosaal#istFreigegeben() <em>Ist Freigegeben</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Ist Freigegeben</em>' operation.
	 * @see kino.Kinosaal#istFreigegeben()
	 * @generated
	 */
	EOperation getKinosaal__IstFreigegeben();

	/**
	 * Returns the meta object for class '{@link kino.Film <em>Film</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Film</em>'.
	 * @see kino.Film
	 * @generated
	 */
	EClass getFilm();

	/**
	 * Returns the meta object for the attribute '{@link kino.Film#getTitel <em>Titel</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Titel</em>'.
	 * @see kino.Film#getTitel()
	 * @see #getFilm()
	 * @generated
	 */
	EAttribute getFilm_Titel();

	/**
	 * Returns the meta object for the attribute '{@link kino.Film#getDauer <em>Dauer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Dauer</em>'.
	 * @see kino.Film#getDauer()
	 * @see #getFilm()
	 * @generated
	 */
	EAttribute getFilm_Dauer();

	/**
	 * Returns the meta object for the attribute '{@link kino.Film#getBeschreibung <em>Beschreibung</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Beschreibung</em>'.
	 * @see kino.Film#getBeschreibung()
	 * @see #getFilm()
	 * @generated
	 */
	EAttribute getFilm_Beschreibung();

	/**
	 * Returns the meta object for the reference list '{@link kino.Film#getAuffuehrungen <em>Auffuehrungen</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Auffuehrungen</em>'.
	 * @see kino.Film#getAuffuehrungen()
	 * @see #getFilm()
	 * @generated
	 */
	EReference getFilm_Auffuehrungen();

	/**
	 * Returns the meta object for the '{@link kino.Film#gesamtEinnahmen() <em>Gesamt Einnahmen</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Gesamt Einnahmen</em>' operation.
	 * @see kino.Film#gesamtEinnahmen()
	 * @generated
	 */
	EOperation getFilm__GesamtEinnahmen();

	/**
	 * Returns the meta object for class '{@link kino.Auffuehrung <em>Auffuehrung</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Auffuehrung</em>'.
	 * @see kino.Auffuehrung
	 * @generated
	 */
	EClass getAuffuehrung();

	/**
	 * Returns the meta object for the attribute '{@link kino.Auffuehrung#getStartzeitpunkt <em>Startzeitpunkt</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Startzeitpunkt</em>'.
	 * @see kino.Auffuehrung#getStartzeitpunkt()
	 * @see #getAuffuehrung()
	 * @generated
	 */
	EAttribute getAuffuehrung_Startzeitpunkt();

	/**
	 * Returns the meta object for the reference '{@link kino.Auffuehrung#getSaal <em>Saal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Saal</em>'.
	 * @see kino.Auffuehrung#getSaal()
	 * @see #getAuffuehrung()
	 * @generated
	 */
	EReference getAuffuehrung_Saal();

	/**
	 * Returns the meta object for the reference list '{@link kino.Auffuehrung#getReservierungen <em>Reservierungen</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Reservierungen</em>'.
	 * @see kino.Auffuehrung#getReservierungen()
	 * @see #getAuffuehrung()
	 * @generated
	 */
	EReference getAuffuehrung_Reservierungen();

	/**
	 * Returns the meta object for the reference list '{@link kino.Auffuehrung#getBuchungen <em>Buchungen</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Buchungen</em>'.
	 * @see kino.Auffuehrung#getBuchungen()
	 * @see #getAuffuehrung()
	 * @generated
	 */
	EReference getAuffuehrung_Buchungen();

	/**
	 * Returns the meta object for the reference '{@link kino.Auffuehrung#getFilm <em>Film</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Film</em>'.
	 * @see kino.Auffuehrung#getFilm()
	 * @see #getAuffuehrung()
	 * @generated
	 */
	EReference getAuffuehrung_Film();

	/**
	 * Returns the meta object for the attribute '{@link kino.Auffuehrung#getAktuelleEinnahmen <em>Aktuelle Einnahmen</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Aktuelle Einnahmen</em>'.
	 * @see kino.Auffuehrung#getAktuelleEinnahmen()
	 * @see #getAuffuehrung()
	 * @generated
	 */
	EAttribute getAuffuehrung_AktuelleEinnahmen();

	/**
	 * Returns the meta object for the '{@link kino.Auffuehrung#verfuegbarePlaetze(kino.SitzreihenKategorie) <em>Verfuegbare Plaetze</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Verfuegbare Plaetze</em>' operation.
	 * @see kino.Auffuehrung#verfuegbarePlaetze(kino.SitzreihenKategorie)
	 * @generated
	 */
	EOperation getAuffuehrung__VerfuegbarePlaetze__SitzreihenKategorie();

	/**
	 * Returns the meta object for the '{@link kino.Auffuehrung#reservierungHinzufuegen(kino.Reservierung) <em>Reservierung Hinzufuegen</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Reservierung Hinzufuegen</em>' operation.
	 * @see kino.Auffuehrung#reservierungHinzufuegen(kino.Reservierung)
	 * @generated
	 */
	EOperation getAuffuehrung__ReservierungHinzufuegen__Reservierung();

	/**
	 * Returns the meta object for the '{@link kino.Auffuehrung#buchungHinzufuegen(kino.Buchung) <em>Buchung Hinzufuegen</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Buchung Hinzufuegen</em>' operation.
	 * @see kino.Auffuehrung#buchungHinzufuegen(kino.Buchung)
	 * @generated
	 */
	EOperation getAuffuehrung__BuchungHinzufuegen__Buchung();

	/**
	 * Returns the meta object for the '{@link kino.Auffuehrung#reservierungLoeschen(kino.Reservierung) <em>Reservierung Loeschen</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Reservierung Loeschen</em>' operation.
	 * @see kino.Auffuehrung#reservierungLoeschen(kino.Reservierung)
	 * @generated
	 */
	EOperation getAuffuehrung__ReservierungLoeschen__Reservierung();

	/**
	 * Returns the meta object for class '{@link kino.Sitzreihe <em>Sitzreihe</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Sitzreihe</em>'.
	 * @see kino.Sitzreihe
	 * @generated
	 */
	EClass getSitzreihe();

	/**
	 * Returns the meta object for the attribute '{@link kino.Sitzreihe#getReihennummer <em>Reihennummer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Reihennummer</em>'.
	 * @see kino.Sitzreihe#getReihennummer()
	 * @see #getSitzreihe()
	 * @generated
	 */
	EAttribute getSitzreihe_Reihennummer();

	/**
	 * Returns the meta object for the attribute '{@link kino.Sitzreihe#getKategorie <em>Kategorie</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Kategorie</em>'.
	 * @see kino.Sitzreihe#getKategorie()
	 * @see #getSitzreihe()
	 * @generated
	 */
	EAttribute getSitzreihe_Kategorie();

	/**
	 * Returns the meta object for the attribute '{@link kino.Sitzreihe#getAnzahlSitze <em>Anzahl Sitze</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Anzahl Sitze</em>'.
	 * @see kino.Sitzreihe#getAnzahlSitze()
	 * @see #getSitzreihe()
	 * @generated
	 */
	EAttribute getSitzreihe_AnzahlSitze();

	/**
	 * Returns the meta object for the reference list '{@link kino.Sitzreihe#getPlaetze <em>Plaetze</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Plaetze</em>'.
	 * @see kino.Sitzreihe#getPlaetze()
	 * @see #getSitzreihe()
	 * @generated
	 */
	EReference getSitzreihe_Plaetze();

	/**
	 * Returns the meta object for the reference '{@link kino.Sitzreihe#getSaal <em>Saal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Saal</em>'.
	 * @see kino.Sitzreihe#getSaal()
	 * @see #getSitzreihe()
	 * @generated
	 */
	EReference getSitzreihe_Saal();

	/**
	 * Returns the meta object for the '{@link kino.Sitzreihe#plaetzeAnlegen() <em>Plaetze Anlegen</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Plaetze Anlegen</em>' operation.
	 * @see kino.Sitzreihe#plaetzeAnlegen()
	 * @generated
	 */
	EOperation getSitzreihe__PlaetzeAnlegen();

	/**
	 * Returns the meta object for class '{@link kino.Sitzplatz <em>Sitzplatz</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Sitzplatz</em>'.
	 * @see kino.Sitzplatz
	 * @generated
	 */
	EClass getSitzplatz();

	/**
	 * Returns the meta object for the attribute '{@link kino.Sitzplatz#getPlatznummer <em>Platznummer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Platznummer</em>'.
	 * @see kino.Sitzplatz#getPlatznummer()
	 * @see #getSitzplatz()
	 * @generated
	 */
	EAttribute getSitzplatz_Platznummer();

	/**
	 * Returns the meta object for the reference '{@link kino.Sitzplatz#getReihe <em>Reihe</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Reihe</em>'.
	 * @see kino.Sitzplatz#getReihe()
	 * @see #getSitzplatz()
	 * @generated
	 */
	EReference getSitzplatz_Reihe();

	/**
	 * Returns the meta object for the reference '{@link kino.Sitzplatz#getReservierung <em>Reservierung</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Reservierung</em>'.
	 * @see kino.Sitzplatz#getReservierung()
	 * @see #getSitzplatz()
	 * @generated
	 */
	EReference getSitzplatz_Reservierung();

	/**
	 * Returns the meta object for the reference '{@link kino.Sitzplatz#getBuchung <em>Buchung</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Buchung</em>'.
	 * @see kino.Sitzplatz#getBuchung()
	 * @see #getSitzplatz()
	 * @generated
	 */
	EReference getSitzplatz_Buchung();

	/**
	 * Returns the meta object for the attribute '{@link kino.Sitzplatz#isIsFrei <em>Is Frei</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is Frei</em>'.
	 * @see kino.Sitzplatz#isIsFrei()
	 * @see #getSitzplatz()
	 * @generated
	 */
	EAttribute getSitzplatz_IsFrei();

	/**
	 * Returns the meta object for class '{@link kino.Kunde <em>Kunde</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Kunde</em>'.
	 * @see kino.Kunde
	 * @generated
	 */
	EClass getKunde();

	/**
	 * Returns the meta object for the attribute '{@link kino.Kunde#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see kino.Kunde#getName()
	 * @see #getKunde()
	 * @generated
	 */
	EAttribute getKunde_Name();

	/**
	 * Returns the meta object for the attribute '{@link kino.Kunde#getEmail <em>Email</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Email</em>'.
	 * @see kino.Kunde#getEmail()
	 * @see #getKunde()
	 * @generated
	 */
	EAttribute getKunde_Email();

	/**
	 * Returns the meta object for the reference list '{@link kino.Kunde#getReservierungen <em>Reservierungen</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Reservierungen</em>'.
	 * @see kino.Kunde#getReservierungen()
	 * @see #getKunde()
	 * @generated
	 */
	EReference getKunde_Reservierungen();

	/**
	 * Returns the meta object for the reference list '{@link kino.Kunde#getBuchungen <em>Buchungen</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Buchungen</em>'.
	 * @see kino.Kunde#getBuchungen()
	 * @see #getKunde()
	 * @generated
	 */
	EReference getKunde_Buchungen();

	/**
	 * Returns the meta object for the '{@link kino.Kunde#reservieren(kino.Auffuehrung, org.eclipse.emf.common.util.EList) <em>Reservieren</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Reservieren</em>' operation.
	 * @see kino.Kunde#reservieren(kino.Auffuehrung, org.eclipse.emf.common.util.EList)
	 * @generated
	 */
	EOperation getKunde__Reservieren__Auffuehrung_EList();

	/**
	 * Returns the meta object for the '{@link kino.Kunde#reservierungStornieren(kino.Reservierung, kino.Auffuehrung) <em>Reservierung Stornieren</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Reservierung Stornieren</em>' operation.
	 * @see kino.Kunde#reservierungStornieren(kino.Reservierung, kino.Auffuehrung)
	 * @generated
	 */
	EOperation getKunde__ReservierungStornieren__Reservierung_Auffuehrung();

	/**
	 * Returns the meta object for the '{@link kino.Kunde#direktBuchung(kino.Auffuehrung, org.eclipse.emf.common.util.EList) <em>Direkt Buchung</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Direkt Buchung</em>' operation.
	 * @see kino.Kunde#direktBuchung(kino.Auffuehrung, org.eclipse.emf.common.util.EList)
	 * @generated
	 */
	EOperation getKunde__DirektBuchung__Auffuehrung_EList();

	/**
	 * Returns the meta object for the '{@link kino.Kunde#reservierungZuBuchungVerarbeiten(kino.Reservierung) <em>Reservierung Zu Buchung Verarbeiten</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Reservierung Zu Buchung Verarbeiten</em>' operation.
	 * @see kino.Kunde#reservierungZuBuchungVerarbeiten(kino.Reservierung)
	 * @generated
	 */
	EOperation getKunde__ReservierungZuBuchungVerarbeiten__Reservierung();

	/**
	 * Returns the meta object for class '{@link kino.Reservierung <em>Reservierung</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Reservierung</em>'.
	 * @see kino.Reservierung
	 * @generated
	 */
	EClass getReservierung();

	/**
	 * Returns the meta object for the attribute '{@link kino.Reservierung#getReservierungsnummer <em>Reservierungsnummer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Reservierungsnummer</em>'.
	 * @see kino.Reservierung#getReservierungsnummer()
	 * @see #getReservierung()
	 * @generated
	 */
	EAttribute getReservierung_Reservierungsnummer();

	/**
	 * Returns the meta object for the attribute '{@link kino.Reservierung#getStartZeitstempel <em>Start Zeitstempel</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Start Zeitstempel</em>'.
	 * @see kino.Reservierung#getStartZeitstempel()
	 * @see #getReservierung()
	 * @generated
	 */
	EAttribute getReservierung_StartZeitstempel();

	/**
	 * Returns the meta object for the reference '{@link kino.Reservierung#getKunde <em>Kunde</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Kunde</em>'.
	 * @see kino.Reservierung#getKunde()
	 * @see #getReservierung()
	 * @generated
	 */
	EReference getReservierung_Kunde();

	/**
	 * Returns the meta object for the reference '{@link kino.Reservierung#getAuffuehrung <em>Auffuehrung</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Auffuehrung</em>'.
	 * @see kino.Reservierung#getAuffuehrung()
	 * @see #getReservierung()
	 * @generated
	 */
	EReference getReservierung_Auffuehrung();

	/**
	 * Returns the meta object for the reference list '{@link kino.Reservierung#getPlaetze <em>Plaetze</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Plaetze</em>'.
	 * @see kino.Reservierung#getPlaetze()
	 * @see #getReservierung()
	 * @generated
	 */
	EReference getReservierung_Plaetze();

	/**
	 * Returns the meta object for the '{@link kino.Reservierung#plaetzeHinzufuegen(org.eclipse.emf.common.util.EList) <em>Plaetze Hinzufuegen</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Plaetze Hinzufuegen</em>' operation.
	 * @see kino.Reservierung#plaetzeHinzufuegen(org.eclipse.emf.common.util.EList)
	 * @generated
	 */
	EOperation getReservierung__PlaetzeHinzufuegen__EList();

	/**
	 * Returns the meta object for class '{@link kino.Buchung <em>Buchung</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Buchung</em>'.
	 * @see kino.Buchung
	 * @generated
	 */
	EClass getBuchung();

	/**
	 * Returns the meta object for the attribute '{@link kino.Buchung#getBuchungsnummer <em>Buchungsnummer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Buchungsnummer</em>'.
	 * @see kino.Buchung#getBuchungsnummer()
	 * @see #getBuchung()
	 * @generated
	 */
	EAttribute getBuchung_Buchungsnummer();

	/**
	 * Returns the meta object for the attribute '{@link kino.Buchung#getBuchungsZeitstempel <em>Buchungs Zeitstempel</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Buchungs Zeitstempel</em>'.
	 * @see kino.Buchung#getBuchungsZeitstempel()
	 * @see #getBuchung()
	 * @generated
	 */
	EAttribute getBuchung_BuchungsZeitstempel();

	/**
	 * Returns the meta object for the attribute '{@link kino.Buchung#isBezahlt <em>Bezahlt</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Bezahlt</em>'.
	 * @see kino.Buchung#isBezahlt()
	 * @see #getBuchung()
	 * @generated
	 */
	EAttribute getBuchung_Bezahlt();

	/**
	 * Returns the meta object for the reference '{@link kino.Buchung#getKunde <em>Kunde</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Kunde</em>'.
	 * @see kino.Buchung#getKunde()
	 * @see #getBuchung()
	 * @generated
	 */
	EReference getBuchung_Kunde();

	/**
	 * Returns the meta object for the reference '{@link kino.Buchung#getAuffuehrung <em>Auffuehrung</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Auffuehrung</em>'.
	 * @see kino.Buchung#getAuffuehrung()
	 * @see #getBuchung()
	 * @generated
	 */
	EReference getBuchung_Auffuehrung();

	/**
	 * Returns the meta object for the reference list '{@link kino.Buchung#getPlaetze <em>Plaetze</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Plaetze</em>'.
	 * @see kino.Buchung#getPlaetze()
	 * @see #getBuchung()
	 * @generated
	 */
	EReference getBuchung_Plaetze();

	/**
	 * Returns the meta object for the attribute '{@link kino.Buchung#getGesamtpreis <em>Gesamtpreis</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Gesamtpreis</em>'.
	 * @see kino.Buchung#getGesamtpreis()
	 * @see #getBuchung()
	 * @generated
	 */
	EAttribute getBuchung_Gesamtpreis();

	/**
	 * Returns the meta object for the '{@link kino.Buchung#plaetzeHinzufuegen(org.eclipse.emf.common.util.EList) <em>Plaetze Hinzufuegen</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Plaetze Hinzufuegen</em>' operation.
	 * @see kino.Buchung#plaetzeHinzufuegen(org.eclipse.emf.common.util.EList)
	 * @generated
	 */
	EOperation getBuchung__PlaetzeHinzufuegen__EList();

	/**
	 * Returns the meta object for enum '{@link kino.SitzreihenKategorie <em>Sitzreihen Kategorie</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Sitzreihen Kategorie</em>'.
	 * @see kino.SitzreihenKategorie
	 * @generated
	 */
	EEnum getSitzreihenKategorie();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	KinoFactory getKinoFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link kino.impl.KinoImpl <em>Kino</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see kino.impl.KinoImpl
		 * @see kino.impl.KinoPackageImpl#getKino()
		 * @generated
		 */
		EClass KINO = eINSTANCE.getKino();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute KINO__NAME = eINSTANCE.getKino_Name();

		/**
		 * The meta object literal for the '<em><b>Saele</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference KINO__SAELE = eINSTANCE.getKino_Saele();

		/**
		 * The meta object literal for the '<em><b>Kunden</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference KINO__KUNDEN = eINSTANCE.getKino_Kunden();

		/**
		 * The meta object literal for the '<em><b>Programm</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference KINO__PROGRAMM = eINSTANCE.getKino_Programm();

		/**
		 * The meta object literal for the '<em><b>Saal Anlegen</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation KINO___SAAL_ANLEGEN__STRING = eINSTANCE.getKino__SaalAnlegen__String();

		/**
		 * The meta object literal for the '<em><b>Film Anlegen</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation KINO___FILM_ANLEGEN__STRING_INT_STRING = eINSTANCE.getKino__FilmAnlegen__String_int_String();

		/**
		 * The meta object literal for the '<em><b>Einnahmen Berechnen</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation KINO___EINNAHMEN_BERECHNEN__AUFFUEHRUNG = eINSTANCE.getKino__EinnahmenBerechnen__Auffuehrung();

		/**
		 * The meta object literal for the '<em><b>Einnahmen Berechnen</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation KINO___EINNAHMEN_BERECHNEN__FILM = eINSTANCE.getKino__EinnahmenBerechnen__Film();

		/**
		 * The meta object literal for the '<em><b>Sitzreihe Anlegen</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation KINO___SITZREIHE_ANLEGEN__INT_SITZREIHENKATEGORIE_INT_KINOSAAL = eINSTANCE.getKino__SitzreiheAnlegen__int_SitzreihenKategorie_int_Kinosaal();

		/**
		 * The meta object literal for the '<em><b>Kunde Anlegen</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation KINO___KUNDE_ANLEGEN__STRING_STRING = eINSTANCE.getKino__KundeAnlegen__String_String();

		/**
		 * The meta object literal for the '{@link kino.impl.KinosaalImpl <em>Kinosaal</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see kino.impl.KinosaalImpl
		 * @see kino.impl.KinoPackageImpl#getKinosaal()
		 * @generated
		 */
		EClass KINOSAAL = eINSTANCE.getKinosaal();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute KINOSAAL__NAME = eINSTANCE.getKinosaal_Name();

		/**
		 * The meta object literal for the '<em><b>Freigegeben</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute KINOSAAL__FREIGEGEBEN = eINSTANCE.getKinosaal_Freigegeben();

		/**
		 * The meta object literal for the '<em><b>Reihen</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference KINOSAAL__REIHEN = eINSTANCE.getKinosaal_Reihen();

		/**
		 * The meta object literal for the '<em><b>Auffuehrungen</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference KINOSAAL__AUFFUEHRUNGEN = eINSTANCE.getKinosaal_Auffuehrungen();

		/**
		 * The meta object literal for the '<em><b>Reihe Hinzufügen</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation KINOSAAL___REIHE_HINZUFÜGEN__SITZREIHE = eINSTANCE.getKinosaal__ReiheHinzufügen__Sitzreihe();

		/**
		 * The meta object literal for the '<em><b>Freigeben</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation KINOSAAL___FREIGEBEN = eINSTANCE.getKinosaal__Freigeben();

		/**
		 * The meta object literal for the '<em><b>Ist Freigegeben</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation KINOSAAL___IST_FREIGEGEBEN = eINSTANCE.getKinosaal__IstFreigegeben();

		/**
		 * The meta object literal for the '{@link kino.impl.FilmImpl <em>Film</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see kino.impl.FilmImpl
		 * @see kino.impl.KinoPackageImpl#getFilm()
		 * @generated
		 */
		EClass FILM = eINSTANCE.getFilm();

		/**
		 * The meta object literal for the '<em><b>Titel</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FILM__TITEL = eINSTANCE.getFilm_Titel();

		/**
		 * The meta object literal for the '<em><b>Dauer</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FILM__DAUER = eINSTANCE.getFilm_Dauer();

		/**
		 * The meta object literal for the '<em><b>Beschreibung</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FILM__BESCHREIBUNG = eINSTANCE.getFilm_Beschreibung();

		/**
		 * The meta object literal for the '<em><b>Auffuehrungen</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FILM__AUFFUEHRUNGEN = eINSTANCE.getFilm_Auffuehrungen();

		/**
		 * The meta object literal for the '<em><b>Gesamt Einnahmen</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation FILM___GESAMT_EINNAHMEN = eINSTANCE.getFilm__GesamtEinnahmen();

		/**
		 * The meta object literal for the '{@link kino.impl.AuffuehrungImpl <em>Auffuehrung</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see kino.impl.AuffuehrungImpl
		 * @see kino.impl.KinoPackageImpl#getAuffuehrung()
		 * @generated
		 */
		EClass AUFFUEHRUNG = eINSTANCE.getAuffuehrung();

		/**
		 * The meta object literal for the '<em><b>Startzeitpunkt</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute AUFFUEHRUNG__STARTZEITPUNKT = eINSTANCE.getAuffuehrung_Startzeitpunkt();

		/**
		 * The meta object literal for the '<em><b>Saal</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference AUFFUEHRUNG__SAAL = eINSTANCE.getAuffuehrung_Saal();

		/**
		 * The meta object literal for the '<em><b>Reservierungen</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference AUFFUEHRUNG__RESERVIERUNGEN = eINSTANCE.getAuffuehrung_Reservierungen();

		/**
		 * The meta object literal for the '<em><b>Buchungen</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference AUFFUEHRUNG__BUCHUNGEN = eINSTANCE.getAuffuehrung_Buchungen();

		/**
		 * The meta object literal for the '<em><b>Film</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference AUFFUEHRUNG__FILM = eINSTANCE.getAuffuehrung_Film();

		/**
		 * The meta object literal for the '<em><b>Aktuelle Einnahmen</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute AUFFUEHRUNG__AKTUELLE_EINNAHMEN = eINSTANCE.getAuffuehrung_AktuelleEinnahmen();

		/**
		 * The meta object literal for the '<em><b>Verfuegbare Plaetze</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation AUFFUEHRUNG___VERFUEGBARE_PLAETZE__SITZREIHENKATEGORIE = eINSTANCE.getAuffuehrung__VerfuegbarePlaetze__SitzreihenKategorie();

		/**
		 * The meta object literal for the '<em><b>Reservierung Hinzufuegen</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation AUFFUEHRUNG___RESERVIERUNG_HINZUFUEGEN__RESERVIERUNG = eINSTANCE.getAuffuehrung__ReservierungHinzufuegen__Reservierung();

		/**
		 * The meta object literal for the '<em><b>Buchung Hinzufuegen</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation AUFFUEHRUNG___BUCHUNG_HINZUFUEGEN__BUCHUNG = eINSTANCE.getAuffuehrung__BuchungHinzufuegen__Buchung();

		/**
		 * The meta object literal for the '<em><b>Reservierung Loeschen</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation AUFFUEHRUNG___RESERVIERUNG_LOESCHEN__RESERVIERUNG = eINSTANCE.getAuffuehrung__ReservierungLoeschen__Reservierung();

		/**
		 * The meta object literal for the '{@link kino.impl.SitzreiheImpl <em>Sitzreihe</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see kino.impl.SitzreiheImpl
		 * @see kino.impl.KinoPackageImpl#getSitzreihe()
		 * @generated
		 */
		EClass SITZREIHE = eINSTANCE.getSitzreihe();

		/**
		 * The meta object literal for the '<em><b>Reihennummer</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SITZREIHE__REIHENNUMMER = eINSTANCE.getSitzreihe_Reihennummer();

		/**
		 * The meta object literal for the '<em><b>Kategorie</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SITZREIHE__KATEGORIE = eINSTANCE.getSitzreihe_Kategorie();

		/**
		 * The meta object literal for the '<em><b>Anzahl Sitze</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SITZREIHE__ANZAHL_SITZE = eINSTANCE.getSitzreihe_AnzahlSitze();

		/**
		 * The meta object literal for the '<em><b>Plaetze</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SITZREIHE__PLAETZE = eINSTANCE.getSitzreihe_Plaetze();

		/**
		 * The meta object literal for the '<em><b>Saal</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SITZREIHE__SAAL = eINSTANCE.getSitzreihe_Saal();

		/**
		 * The meta object literal for the '<em><b>Plaetze Anlegen</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation SITZREIHE___PLAETZE_ANLEGEN = eINSTANCE.getSitzreihe__PlaetzeAnlegen();

		/**
		 * The meta object literal for the '{@link kino.impl.SitzplatzImpl <em>Sitzplatz</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see kino.impl.SitzplatzImpl
		 * @see kino.impl.KinoPackageImpl#getSitzplatz()
		 * @generated
		 */
		EClass SITZPLATZ = eINSTANCE.getSitzplatz();

		/**
		 * The meta object literal for the '<em><b>Platznummer</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SITZPLATZ__PLATZNUMMER = eINSTANCE.getSitzplatz_Platznummer();

		/**
		 * The meta object literal for the '<em><b>Reihe</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SITZPLATZ__REIHE = eINSTANCE.getSitzplatz_Reihe();

		/**
		 * The meta object literal for the '<em><b>Reservierung</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SITZPLATZ__RESERVIERUNG = eINSTANCE.getSitzplatz_Reservierung();

		/**
		 * The meta object literal for the '<em><b>Buchung</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SITZPLATZ__BUCHUNG = eINSTANCE.getSitzplatz_Buchung();

		/**
		 * The meta object literal for the '<em><b>Is Frei</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SITZPLATZ__IS_FREI = eINSTANCE.getSitzplatz_IsFrei();

		/**
		 * The meta object literal for the '{@link kino.impl.KundeImpl <em>Kunde</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see kino.impl.KundeImpl
		 * @see kino.impl.KinoPackageImpl#getKunde()
		 * @generated
		 */
		EClass KUNDE = eINSTANCE.getKunde();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute KUNDE__NAME = eINSTANCE.getKunde_Name();

		/**
		 * The meta object literal for the '<em><b>Email</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute KUNDE__EMAIL = eINSTANCE.getKunde_Email();

		/**
		 * The meta object literal for the '<em><b>Reservierungen</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference KUNDE__RESERVIERUNGEN = eINSTANCE.getKunde_Reservierungen();

		/**
		 * The meta object literal for the '<em><b>Buchungen</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference KUNDE__BUCHUNGEN = eINSTANCE.getKunde_Buchungen();

		/**
		 * The meta object literal for the '<em><b>Reservieren</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation KUNDE___RESERVIEREN__AUFFUEHRUNG_ELIST = eINSTANCE.getKunde__Reservieren__Auffuehrung_EList();

		/**
		 * The meta object literal for the '<em><b>Reservierung Stornieren</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation KUNDE___RESERVIERUNG_STORNIEREN__RESERVIERUNG_AUFFUEHRUNG = eINSTANCE.getKunde__ReservierungStornieren__Reservierung_Auffuehrung();

		/**
		 * The meta object literal for the '<em><b>Direkt Buchung</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation KUNDE___DIREKT_BUCHUNG__AUFFUEHRUNG_ELIST = eINSTANCE.getKunde__DirektBuchung__Auffuehrung_EList();

		/**
		 * The meta object literal for the '<em><b>Reservierung Zu Buchung Verarbeiten</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation KUNDE___RESERVIERUNG_ZU_BUCHUNG_VERARBEITEN__RESERVIERUNG = eINSTANCE.getKunde__ReservierungZuBuchungVerarbeiten__Reservierung();

		/**
		 * The meta object literal for the '{@link kino.impl.ReservierungImpl <em>Reservierung</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see kino.impl.ReservierungImpl
		 * @see kino.impl.KinoPackageImpl#getReservierung()
		 * @generated
		 */
		EClass RESERVIERUNG = eINSTANCE.getReservierung();

		/**
		 * The meta object literal for the '<em><b>Reservierungsnummer</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RESERVIERUNG__RESERVIERUNGSNUMMER = eINSTANCE.getReservierung_Reservierungsnummer();

		/**
		 * The meta object literal for the '<em><b>Start Zeitstempel</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RESERVIERUNG__START_ZEITSTEMPEL = eINSTANCE.getReservierung_StartZeitstempel();

		/**
		 * The meta object literal for the '<em><b>Kunde</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RESERVIERUNG__KUNDE = eINSTANCE.getReservierung_Kunde();

		/**
		 * The meta object literal for the '<em><b>Auffuehrung</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RESERVIERUNG__AUFFUEHRUNG = eINSTANCE.getReservierung_Auffuehrung();

		/**
		 * The meta object literal for the '<em><b>Plaetze</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RESERVIERUNG__PLAETZE = eINSTANCE.getReservierung_Plaetze();

		/**
		 * The meta object literal for the '<em><b>Plaetze Hinzufuegen</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation RESERVIERUNG___PLAETZE_HINZUFUEGEN__ELIST = eINSTANCE.getReservierung__PlaetzeHinzufuegen__EList();

		/**
		 * The meta object literal for the '{@link kino.impl.BuchungImpl <em>Buchung</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see kino.impl.BuchungImpl
		 * @see kino.impl.KinoPackageImpl#getBuchung()
		 * @generated
		 */
		EClass BUCHUNG = eINSTANCE.getBuchung();

		/**
		 * The meta object literal for the '<em><b>Buchungsnummer</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BUCHUNG__BUCHUNGSNUMMER = eINSTANCE.getBuchung_Buchungsnummer();

		/**
		 * The meta object literal for the '<em><b>Buchungs Zeitstempel</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BUCHUNG__BUCHUNGS_ZEITSTEMPEL = eINSTANCE.getBuchung_BuchungsZeitstempel();

		/**
		 * The meta object literal for the '<em><b>Bezahlt</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BUCHUNG__BEZAHLT = eINSTANCE.getBuchung_Bezahlt();

		/**
		 * The meta object literal for the '<em><b>Kunde</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BUCHUNG__KUNDE = eINSTANCE.getBuchung_Kunde();

		/**
		 * The meta object literal for the '<em><b>Auffuehrung</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BUCHUNG__AUFFUEHRUNG = eINSTANCE.getBuchung_Auffuehrung();

		/**
		 * The meta object literal for the '<em><b>Plaetze</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BUCHUNG__PLAETZE = eINSTANCE.getBuchung_Plaetze();

		/**
		 * The meta object literal for the '<em><b>Gesamtpreis</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BUCHUNG__GESAMTPREIS = eINSTANCE.getBuchung_Gesamtpreis();

		/**
		 * The meta object literal for the '<em><b>Plaetze Hinzufuegen</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation BUCHUNG___PLAETZE_HINZUFUEGEN__ELIST = eINSTANCE.getBuchung__PlaetzeHinzufuegen__EList();

		/**
		 * The meta object literal for the '{@link kino.SitzreihenKategorie <em>Sitzreihen Kategorie</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see kino.SitzreihenKategorie
		 * @see kino.impl.KinoPackageImpl#getSitzreihenKategorie()
		 * @generated
		 */
		EEnum SITZREIHEN_KATEGORIE = eINSTANCE.getSitzreihenKategorie();

	}

} //KinoPackage
