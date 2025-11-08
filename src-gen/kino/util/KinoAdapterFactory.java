/**
 */
package kino.util;

import kino.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see kino.KinoPackage
 * @generated
 */
public class KinoAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static KinoPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public KinoAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = KinoPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected KinoSwitch<Adapter> modelSwitch =
		new KinoSwitch<Adapter>() {
			@Override
			public Adapter caseKino(Kino object) {
				return createKinoAdapter();
			}
			@Override
			public Adapter caseKinosaal(Kinosaal object) {
				return createKinosaalAdapter();
			}
			@Override
			public Adapter caseFilm(Film object) {
				return createFilmAdapter();
			}
			@Override
			public Adapter caseAuffuehrung(Auffuehrung object) {
				return createAuffuehrungAdapter();
			}
			@Override
			public Adapter caseSitzreihe(Sitzreihe object) {
				return createSitzreiheAdapter();
			}
			@Override
			public Adapter caseSitzplatz(Sitzplatz object) {
				return createSitzplatzAdapter();
			}
			@Override
			public Adapter caseKunde(Kunde object) {
				return createKundeAdapter();
			}
			@Override
			public Adapter caseReservierung(Reservierung object) {
				return createReservierungAdapter();
			}
			@Override
			public Adapter caseBuchung(Buchung object) {
				return createBuchungAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link kino.Kino <em>Kino</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see kino.Kino
	 * @generated
	 */
	public Adapter createKinoAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link kino.Kinosaal <em>Kinosaal</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see kino.Kinosaal
	 * @generated
	 */
	public Adapter createKinosaalAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link kino.Film <em>Film</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see kino.Film
	 * @generated
	 */
	public Adapter createFilmAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link kino.Auffuehrung <em>Auffuehrung</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see kino.Auffuehrung
	 * @generated
	 */
	public Adapter createAuffuehrungAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link kino.Sitzreihe <em>Sitzreihe</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see kino.Sitzreihe
	 * @generated
	 */
	public Adapter createSitzreiheAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link kino.Sitzplatz <em>Sitzplatz</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see kino.Sitzplatz
	 * @generated
	 */
	public Adapter createSitzplatzAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link kino.Kunde <em>Kunde</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see kino.Kunde
	 * @generated
	 */
	public Adapter createKundeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link kino.Reservierung <em>Reservierung</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see kino.Reservierung
	 * @generated
	 */
	public Adapter createReservierungAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link kino.Buchung <em>Buchung</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see kino.Buchung
	 * @generated
	 */
	public Adapter createBuchungAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //KinoAdapterFactory
