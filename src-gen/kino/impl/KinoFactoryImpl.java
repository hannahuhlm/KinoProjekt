/**
 */
package kino.impl;

import kino.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class KinoFactoryImpl extends EFactoryImpl implements KinoFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static KinoFactory init() {
		try {
			KinoFactory theKinoFactory = (KinoFactory)EPackage.Registry.INSTANCE.getEFactory(KinoPackage.eNS_URI);
			if (theKinoFactory != null) {
				return theKinoFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new KinoFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public KinoFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case KinoPackage.KINO: return createKino();
			case KinoPackage.KINOSAAL: return createKinosaal();
			case KinoPackage.FILM: return createFilm();
			case KinoPackage.AUFFUEHRUNG: return createAuffuehrung();
			case KinoPackage.SITZREIHE: return createSitzreihe();
			case KinoPackage.SITZPLATZ: return createSitzplatz();
			case KinoPackage.KUNDE: return createKunde();
			case KinoPackage.RESERVIERUNG: return createReservierung();
			case KinoPackage.BUCHUNG: return createBuchung();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case KinoPackage.SITZREIHEN_KATEGORIE:
				return createSitzreihenKategorieFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case KinoPackage.SITZREIHEN_KATEGORIE:
				return convertSitzreihenKategorieToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Kino createKino() {
		KinoImpl kino = new KinoImpl();
		return kino;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Kinosaal createKinosaal() {
		KinosaalImpl kinosaal = new KinosaalImpl();
		return kinosaal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Film createFilm() {
		FilmImpl film = new FilmImpl();
		return film;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Auffuehrung createAuffuehrung() {
		AuffuehrungImpl auffuehrung = new AuffuehrungImpl();
		return auffuehrung;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Sitzreihe createSitzreihe() {
		SitzreiheImpl sitzreihe = new SitzreiheImpl();
		return sitzreihe;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Sitzplatz createSitzplatz() {
		SitzplatzImpl sitzplatz = new SitzplatzImpl();
		return sitzplatz;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Kunde createKunde() {
		KundeImpl kunde = new KundeImpl();
		return kunde;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Reservierung createReservierung() {
		ReservierungImpl reservierung = new ReservierungImpl();
		return reservierung;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Buchung createBuchung() {
		BuchungImpl buchung = new BuchungImpl();
		return buchung;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SitzreihenKategorie createSitzreihenKategorieFromString(EDataType eDataType, String initialValue) {
		SitzreihenKategorie result = SitzreihenKategorie.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSitzreihenKategorieToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public KinoPackage getKinoPackage() {
		return (KinoPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static KinoPackage getPackage() {
		return KinoPackage.eINSTANCE;
	}

} //KinoFactoryImpl
