/**
 */
package kino.impl;

import kino.Auffuehrung;
import kino.Buchung;
import kino.Film;
import kino.Kino;
import kino.KinoFactory;
import kino.KinoPackage;
import kino.Kinosaal;
import kino.Kunde;
import kino.Reservierung;
import kino.Sitzplatz;
import kino.Sitzreihe;
import kino.SitzreihenKategorie;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class KinoPackageImpl extends EPackageImpl implements KinoPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass kinoEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass kinosaalEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass filmEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass auffuehrungEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass sitzreiheEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass sitzplatzEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass kundeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass reservierungEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass buchungEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum sitzreihenKategorieEEnum = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see kino.KinoPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private KinoPackageImpl() {
		super(eNS_URI, KinoFactory.eINSTANCE);
	}
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 *
	 * <p>This method is used to initialize {@link KinoPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static KinoPackage init() {
		if (isInited) return (KinoPackage)EPackage.Registry.INSTANCE.getEPackage(KinoPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredKinoPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		KinoPackageImpl theKinoPackage = registeredKinoPackage instanceof KinoPackageImpl ? (KinoPackageImpl)registeredKinoPackage : new KinoPackageImpl();

		isInited = true;

		// Create package meta-data objects
		theKinoPackage.createPackageContents();

		// Initialize created meta-data
		theKinoPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theKinoPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(KinoPackage.eNS_URI, theKinoPackage);
		return theKinoPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getKino() {
		return kinoEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getKino_Name() {
		return (EAttribute)kinoEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getKino_Saele() {
		return (EReference)kinoEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getKino_Kunden() {
		return (EReference)kinoEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getKino__SaalAnlegen__String() {
		return kinoEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getKino__FilmAnlegen__String_int_String() {
		return kinoEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getKino__EinnahmenBerechnen__Auffuehrung() {
		return kinoEClass.getEOperations().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getKino__EinnahmenBerechnen__Film() {
		return kinoEClass.getEOperations().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getKino__SitzreiheAnlegen__int_SitzreihenKategorie_int_Kinosaal() {
		return kinoEClass.getEOperations().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getKino__KundeAnlegen__String_String() {
		return kinoEClass.getEOperations().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getKinosaal() {
		return kinosaalEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getKinosaal_Name() {
		return (EAttribute)kinosaalEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getKinosaal_Freigegeben() {
		return (EAttribute)kinosaalEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getKinosaal_Reihen() {
		return (EReference)kinosaalEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getKinosaal_Auffuehrungen() {
		return (EReference)kinosaalEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getKinosaal__ReiheHinzufügen__Sitzreihe() {
		return kinosaalEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getKinosaal__Freigeben() {
		return kinosaalEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getKinosaal__IstFreigegeben() {
		return kinosaalEClass.getEOperations().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getFilm() {
		return filmEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getFilm_Titel() {
		return (EAttribute)filmEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getFilm_Dauer() {
		return (EAttribute)filmEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getFilm_Beschreibung() {
		return (EAttribute)filmEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getFilm_Auffuehrungen() {
		return (EReference)filmEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getFilm__GesamtEinnahmen() {
		return filmEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAuffuehrung() {
		return auffuehrungEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAuffuehrung_Startzeitpunkt() {
		return (EAttribute)auffuehrungEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getAuffuehrung_Saal() {
		return (EReference)auffuehrungEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getAuffuehrung_Reservierungen() {
		return (EReference)auffuehrungEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getAuffuehrung_Buchungen() {
		return (EReference)auffuehrungEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getAuffuehrung_Film() {
		return (EReference)auffuehrungEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAuffuehrung_AktuelleEinnahmen() {
		return (EAttribute)auffuehrungEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getAuffuehrung__VerfuegbarePlaetze__SitzreihenKategorie() {
		return auffuehrungEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getAuffuehrung__ReservierungHinzufuegen__Reservierung() {
		return auffuehrungEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getAuffuehrung__BuchungHinzufuegen__Buchung() {
		return auffuehrungEClass.getEOperations().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getAuffuehrung__ReservierungLoeschen__Reservierung() {
		return auffuehrungEClass.getEOperations().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSitzreihe() {
		return sitzreiheEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getSitzreihe_Reihennummer() {
		return (EAttribute)sitzreiheEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getSitzreihe_Kategorie() {
		return (EAttribute)sitzreiheEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getSitzreihe_AnzahlSitze() {
		return (EAttribute)sitzreiheEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSitzreihe_Plaetze() {
		return (EReference)sitzreiheEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSitzreihe_Saal() {
		return (EReference)sitzreiheEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getSitzreihe__PlaetzeAnlegen() {
		return sitzreiheEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSitzplatz() {
		return sitzplatzEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getSitzplatz_Platznummer() {
		return (EAttribute)sitzplatzEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSitzplatz_Reihe() {
		return (EReference)sitzplatzEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSitzplatz_Reservierung() {
		return (EReference)sitzplatzEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSitzplatz_Buchung() {
		return (EReference)sitzplatzEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getSitzplatz_IsFrei() {
		return (EAttribute)sitzplatzEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getKunde() {
		return kundeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getKunde_Name() {
		return (EAttribute)kundeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getKunde_Email() {
		return (EAttribute)kundeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getKunde_Reservierungen() {
		return (EReference)kundeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getKunde_Buchungen() {
		return (EReference)kundeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getKunde__Reservieren__Auffuehrung_EList() {
		return kundeEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getKunde__ReservierungStornieren__Reservierung_Auffuehrung() {
		return kundeEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getKunde__DirektBuchung__Auffuehrung_EList() {
		return kundeEClass.getEOperations().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getKunde__ReservierungZuBuchungVerarbeiten__Reservierung() {
		return kundeEClass.getEOperations().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getReservierung() {
		return reservierungEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getReservierung_Reservierungsnummer() {
		return (EAttribute)reservierungEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getReservierung_StartZeitstempel() {
		return (EAttribute)reservierungEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getReservierung_Kunde() {
		return (EReference)reservierungEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getReservierung_Auffuehrung() {
		return (EReference)reservierungEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getReservierung_Plaetze() {
		return (EReference)reservierungEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getReservierung__PlaetzeHinzufuegen__EList() {
		return reservierungEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getBuchung() {
		return buchungEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getBuchung_Buchungsnummer() {
		return (EAttribute)buchungEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getBuchung_BuchungsZeitstempel() {
		return (EAttribute)buchungEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getBuchung_Bezahlt() {
		return (EAttribute)buchungEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getBuchung_Kunde() {
		return (EReference)buchungEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getBuchung_Auffuehrung() {
		return (EReference)buchungEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getBuchung_Plaetze() {
		return (EReference)buchungEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getBuchung_Gesamtpreis() {
		return (EAttribute)buchungEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getBuchung__PlaetzeHinzufuegen__EList() {
		return buchungEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getSitzreihenKategorie() {
		return sitzreihenKategorieEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public KinoFactory getKinoFactory() {
		return (KinoFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		kinoEClass = createEClass(KINO);
		createEAttribute(kinoEClass, KINO__NAME);
		createEReference(kinoEClass, KINO__SAELE);
		createEReference(kinoEClass, KINO__KUNDEN);
		createEOperation(kinoEClass, KINO___SAAL_ANLEGEN__STRING);
		createEOperation(kinoEClass, KINO___FILM_ANLEGEN__STRING_INT_STRING);
		createEOperation(kinoEClass, KINO___EINNAHMEN_BERECHNEN__AUFFUEHRUNG);
		createEOperation(kinoEClass, KINO___EINNAHMEN_BERECHNEN__FILM);
		createEOperation(kinoEClass, KINO___SITZREIHE_ANLEGEN__INT_SITZREIHENKATEGORIE_INT_KINOSAAL);
		createEOperation(kinoEClass, KINO___KUNDE_ANLEGEN__STRING_STRING);

		kinosaalEClass = createEClass(KINOSAAL);
		createEAttribute(kinosaalEClass, KINOSAAL__NAME);
		createEAttribute(kinosaalEClass, KINOSAAL__FREIGEGEBEN);
		createEReference(kinosaalEClass, KINOSAAL__REIHEN);
		createEReference(kinosaalEClass, KINOSAAL__AUFFUEHRUNGEN);
		createEOperation(kinosaalEClass, KINOSAAL___REIHE_HINZUFÜGEN__SITZREIHE);
		createEOperation(kinosaalEClass, KINOSAAL___FREIGEBEN);
		createEOperation(kinosaalEClass, KINOSAAL___IST_FREIGEGEBEN);

		filmEClass = createEClass(FILM);
		createEAttribute(filmEClass, FILM__TITEL);
		createEAttribute(filmEClass, FILM__DAUER);
		createEAttribute(filmEClass, FILM__BESCHREIBUNG);
		createEReference(filmEClass, FILM__AUFFUEHRUNGEN);
		createEOperation(filmEClass, FILM___GESAMT_EINNAHMEN);

		auffuehrungEClass = createEClass(AUFFUEHRUNG);
		createEAttribute(auffuehrungEClass, AUFFUEHRUNG__STARTZEITPUNKT);
		createEReference(auffuehrungEClass, AUFFUEHRUNG__SAAL);
		createEReference(auffuehrungEClass, AUFFUEHRUNG__RESERVIERUNGEN);
		createEReference(auffuehrungEClass, AUFFUEHRUNG__BUCHUNGEN);
		createEReference(auffuehrungEClass, AUFFUEHRUNG__FILM);
		createEAttribute(auffuehrungEClass, AUFFUEHRUNG__AKTUELLE_EINNAHMEN);
		createEOperation(auffuehrungEClass, AUFFUEHRUNG___VERFUEGBARE_PLAETZE__SITZREIHENKATEGORIE);
		createEOperation(auffuehrungEClass, AUFFUEHRUNG___RESERVIERUNG_HINZUFUEGEN__RESERVIERUNG);
		createEOperation(auffuehrungEClass, AUFFUEHRUNG___BUCHUNG_HINZUFUEGEN__BUCHUNG);
		createEOperation(auffuehrungEClass, AUFFUEHRUNG___RESERVIERUNG_LOESCHEN__RESERVIERUNG);

		sitzreiheEClass = createEClass(SITZREIHE);
		createEAttribute(sitzreiheEClass, SITZREIHE__REIHENNUMMER);
		createEAttribute(sitzreiheEClass, SITZREIHE__KATEGORIE);
		createEAttribute(sitzreiheEClass, SITZREIHE__ANZAHL_SITZE);
		createEReference(sitzreiheEClass, SITZREIHE__PLAETZE);
		createEReference(sitzreiheEClass, SITZREIHE__SAAL);
		createEOperation(sitzreiheEClass, SITZREIHE___PLAETZE_ANLEGEN);

		sitzplatzEClass = createEClass(SITZPLATZ);
		createEAttribute(sitzplatzEClass, SITZPLATZ__PLATZNUMMER);
		createEReference(sitzplatzEClass, SITZPLATZ__REIHE);
		createEReference(sitzplatzEClass, SITZPLATZ__RESERVIERUNG);
		createEReference(sitzplatzEClass, SITZPLATZ__BUCHUNG);
		createEAttribute(sitzplatzEClass, SITZPLATZ__IS_FREI);

		kundeEClass = createEClass(KUNDE);
		createEAttribute(kundeEClass, KUNDE__NAME);
		createEAttribute(kundeEClass, KUNDE__EMAIL);
		createEReference(kundeEClass, KUNDE__RESERVIERUNGEN);
		createEReference(kundeEClass, KUNDE__BUCHUNGEN);
		createEOperation(kundeEClass, KUNDE___RESERVIEREN__AUFFUEHRUNG_ELIST);
		createEOperation(kundeEClass, KUNDE___RESERVIERUNG_STORNIEREN__RESERVIERUNG_AUFFUEHRUNG);
		createEOperation(kundeEClass, KUNDE___DIREKT_BUCHUNG__AUFFUEHRUNG_ELIST);
		createEOperation(kundeEClass, KUNDE___RESERVIERUNG_ZU_BUCHUNG_VERARBEITEN__RESERVIERUNG);

		reservierungEClass = createEClass(RESERVIERUNG);
		createEAttribute(reservierungEClass, RESERVIERUNG__RESERVIERUNGSNUMMER);
		createEAttribute(reservierungEClass, RESERVIERUNG__START_ZEITSTEMPEL);
		createEReference(reservierungEClass, RESERVIERUNG__KUNDE);
		createEReference(reservierungEClass, RESERVIERUNG__AUFFUEHRUNG);
		createEReference(reservierungEClass, RESERVIERUNG__PLAETZE);
		createEOperation(reservierungEClass, RESERVIERUNG___PLAETZE_HINZUFUEGEN__ELIST);

		buchungEClass = createEClass(BUCHUNG);
		createEAttribute(buchungEClass, BUCHUNG__BUCHUNGSNUMMER);
		createEAttribute(buchungEClass, BUCHUNG__BUCHUNGS_ZEITSTEMPEL);
		createEAttribute(buchungEClass, BUCHUNG__BEZAHLT);
		createEReference(buchungEClass, BUCHUNG__KUNDE);
		createEReference(buchungEClass, BUCHUNG__AUFFUEHRUNG);
		createEReference(buchungEClass, BUCHUNG__PLAETZE);
		createEAttribute(buchungEClass, BUCHUNG__GESAMTPREIS);
		createEOperation(buchungEClass, BUCHUNG___PLAETZE_HINZUFUEGEN__ELIST);

		// Create enums
		sitzreihenKategorieEEnum = createEEnum(SITZREIHEN_KATEGORIE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes, features, and operations; add parameters
		initEClass(kinoEClass, Kino.class, "Kino", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getKino_Name(), ecorePackage.getEString(), "name", null, 0, 1, Kino.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getKino_Saele(), this.getKinosaal(), null, "saele", null, 1, -1, Kino.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getKino_Kunden(), this.getKunde(), null, "kunden", null, 1, -1, Kino.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		EOperation op = initEOperation(getKino__SaalAnlegen__String(), this.getKinosaal(), "saalAnlegen", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "name", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getKino__FilmAnlegen__String_int_String(), this.getFilm(), "filmAnlegen", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "titel", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEInt(), "dauer", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "beschreibung", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getKino__EinnahmenBerechnen__Auffuehrung(), ecorePackage.getEDouble(), "einnahmenBerechnen", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getAuffuehrung(), "auffuehrung", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getKino__EinnahmenBerechnen__Film(), ecorePackage.getEDouble(), "einnahmenBerechnen", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getFilm(), "film", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getKino__SitzreiheAnlegen__int_SitzreihenKategorie_int_Kinosaal(), this.getSitzreihe(), "sitzreiheAnlegen", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEInt(), "nummer", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getSitzreihenKategorie(), "kategorie", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEInt(), "sitzAnzahl", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getKinosaal(), "saal", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getKino__KundeAnlegen__String_String(), null, "kundeAnlegen", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "name", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "email", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(kinosaalEClass, Kinosaal.class, "Kinosaal", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getKinosaal_Name(), ecorePackage.getEString(), "name", null, 0, 1, Kinosaal.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getKinosaal_Freigegeben(), ecorePackage.getEBoolean(), "freigegeben", "false", 0, 1, Kinosaal.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getKinosaal_Reihen(), this.getSitzreihe(), null, "reihen", null, 1, -1, Kinosaal.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getKinosaal_Auffuehrungen(), this.getAuffuehrung(), null, "auffuehrungen", null, 0, -1, Kinosaal.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = initEOperation(getKinosaal__ReiheHinzufügen__Sitzreihe(), null, "reiheHinzufügen", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getSitzreihe(), "reihe", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEOperation(getKinosaal__Freigeben(), null, "freigeben", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEOperation(getKinosaal__IstFreigegeben(), ecorePackage.getEBoolean(), "istFreigegeben", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(filmEClass, Film.class, "Film", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getFilm_Titel(), ecorePackage.getEString(), "titel", null, 0, 1, Film.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFilm_Dauer(), ecorePackage.getEInt(), "dauer", null, 0, 1, Film.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFilm_Beschreibung(), ecorePackage.getEString(), "beschreibung", null, 0, 1, Film.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getFilm_Auffuehrungen(), this.getAuffuehrung(), null, "auffuehrungen", null, 0, -1, Film.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getFilm__GesamtEinnahmen(), ecorePackage.getEDouble(), "gesamtEinnahmen", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(auffuehrungEClass, Auffuehrung.class, "Auffuehrung", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAuffuehrung_Startzeitpunkt(), ecorePackage.getEDate(), "startzeitpunkt", null, 0, 1, Auffuehrung.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAuffuehrung_Saal(), this.getKinosaal(), null, "saal", null, 1, 1, Auffuehrung.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAuffuehrung_Reservierungen(), this.getReservierung(), null, "reservierungen", null, 0, -1, Auffuehrung.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAuffuehrung_Buchungen(), this.getBuchung(), null, "buchungen", null, 0, -1, Auffuehrung.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAuffuehrung_Film(), this.getFilm(), null, "film", null, 1, 1, Auffuehrung.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAuffuehrung_AktuelleEinnahmen(), ecorePackage.getEDouble(), "aktuelleEinnahmen", null, 0, 1, Auffuehrung.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = initEOperation(getAuffuehrung__VerfuegbarePlaetze__SitzreihenKategorie(), this.getSitzplatz(), "verfuegbarePlaetze", 0, -1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getSitzreihenKategorie(), "kategorie", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getAuffuehrung__ReservierungHinzufuegen__Reservierung(), null, "reservierungHinzufuegen", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getReservierung(), "reservierung", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getAuffuehrung__BuchungHinzufuegen__Buchung(), null, "buchungHinzufuegen", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getBuchung(), "buchung", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getAuffuehrung__ReservierungLoeschen__Reservierung(), null, "reservierungLoeschen", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getReservierung(), "reservierung", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(sitzreiheEClass, Sitzreihe.class, "Sitzreihe", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSitzreihe_Reihennummer(), ecorePackage.getEInt(), "reihennummer", null, 0, 1, Sitzreihe.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSitzreihe_Kategorie(), this.getSitzreihenKategorie(), "kategorie", null, 0, 1, Sitzreihe.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSitzreihe_AnzahlSitze(), ecorePackage.getEInt(), "anzahlSitze", null, 0, 1, Sitzreihe.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSitzreihe_Plaetze(), this.getSitzplatz(), null, "plaetze", null, 1, -1, Sitzreihe.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSitzreihe_Saal(), this.getKinosaal(), null, "saal", null, 1, 1, Sitzreihe.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getSitzreihe__PlaetzeAnlegen(), null, "plaetzeAnlegen", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(sitzplatzEClass, Sitzplatz.class, "Sitzplatz", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSitzplatz_Platznummer(), ecorePackage.getEInt(), "platznummer", null, 0, 1, Sitzplatz.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSitzplatz_Reihe(), this.getSitzreihe(), null, "reihe", null, 1, 1, Sitzplatz.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSitzplatz_Reservierung(), this.getReservierung(), null, "reservierung", null, 0, 1, Sitzplatz.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSitzplatz_Buchung(), this.getBuchung(), null, "buchung", null, 0, 1, Sitzplatz.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSitzplatz_IsFrei(), ecorePackage.getEBoolean(), "isFrei", "true", 0, 1, Sitzplatz.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(kundeEClass, Kunde.class, "Kunde", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getKunde_Name(), ecorePackage.getEString(), "name", null, 0, 1, Kunde.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getKunde_Email(), ecorePackage.getEString(), "email", null, 0, 1, Kunde.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getKunde_Reservierungen(), this.getReservierung(), null, "reservierungen", null, 0, -1, Kunde.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getKunde_Buchungen(), this.getBuchung(), null, "buchungen", null, 0, -1, Kunde.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = initEOperation(getKunde__Reservieren__Auffuehrung_EList(), this.getReservierung(), "reservieren", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getAuffuehrung(), "auffuehrung", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getSitzplatz(), "plaetze", 1, -1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getKunde__ReservierungStornieren__Reservierung_Auffuehrung(), null, "reservierungStornieren", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getReservierung(), "reservierung", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getAuffuehrung(), "auffuehrung", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getKunde__DirektBuchung__Auffuehrung_EList(), this.getBuchung(), "direktBuchung", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getAuffuehrung(), "auffuehrung", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getSitzplatz(), "plaetze", 1, -1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getKunde__ReservierungZuBuchungVerarbeiten__Reservierung(), this.getBuchung(), "reservierungZuBuchungVerarbeiten", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getReservierung(), "reservierung", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(reservierungEClass, Reservierung.class, "Reservierung", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getReservierung_Reservierungsnummer(), ecorePackage.getEInt(), "reservierungsnummer", null, 0, 1, Reservierung.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getReservierung_StartZeitstempel(), ecorePackage.getEDate(), "startZeitstempel", null, 0, 1, Reservierung.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getReservierung_Kunde(), this.getKunde(), null, "kunde", null, 1, 1, Reservierung.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getReservierung_Auffuehrung(), this.getAuffuehrung(), null, "auffuehrung", null, 1, 1, Reservierung.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getReservierung_Plaetze(), this.getSitzplatz(), null, "plaetze", null, 1, -1, Reservierung.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = initEOperation(getReservierung__PlaetzeHinzufuegen__EList(), null, "plaetzeHinzufuegen", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getSitzplatz(), "plaetze", 1, -1, IS_UNIQUE, IS_ORDERED);

		initEClass(buchungEClass, Buchung.class, "Buchung", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getBuchung_Buchungsnummer(), ecorePackage.getEString(), "buchungsnummer", null, 0, 1, Buchung.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBuchung_BuchungsZeitstempel(), ecorePackage.getEDate(), "buchungsZeitstempel", null, 0, 1, Buchung.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBuchung_Bezahlt(), ecorePackage.getEBoolean(), "bezahlt", "false", 0, 1, Buchung.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getBuchung_Kunde(), this.getKunde(), null, "kunde", null, 1, 1, Buchung.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getBuchung_Auffuehrung(), this.getAuffuehrung(), null, "auffuehrung", null, 1, 1, Buchung.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getBuchung_Plaetze(), this.getSitzplatz(), null, "plaetze", null, 1, -1, Buchung.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBuchung_Gesamtpreis(), ecorePackage.getEDouble(), "gesamtpreis", null, 0, 1, Buchung.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = initEOperation(getBuchung__PlaetzeHinzufuegen__EList(), null, "plaetzeHinzufuegen", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getSitzplatz(), "plaetze", 1, -1, IS_UNIQUE, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(sitzreihenKategorieEEnum, SitzreihenKategorie.class, "SitzreihenKategorie");
		addEEnumLiteral(sitzreihenKategorieEEnum, SitzreihenKategorie.PARKETT);
		addEEnumLiteral(sitzreihenKategorieEEnum, SitzreihenKategorie.LOGE);
		addEEnumLiteral(sitzreihenKategorieEEnum, SitzreihenKategorie.LOGE_MIT_SERVICE);

		// Create resource
		createResource(eNS_URI);
	}

} //KinoPackageImpl
