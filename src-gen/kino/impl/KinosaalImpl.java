/**
 */
package kino.impl;

import java.lang.reflect.InvocationTargetException;

import java.util.Collection;

import kino.Auffuehrung;
import kino.KinoPackage;
import kino.Kinosaal;
import kino.Sitzreihe;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Kinosaal</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link kino.impl.KinosaalImpl#getName <em>Name</em>}</li>
 *   <li>{@link kino.impl.KinosaalImpl#isFreigegeben <em>Freigegeben</em>}</li>
 *   <li>{@link kino.impl.KinosaalImpl#getReihen <em>Reihen</em>}</li>
 *   <li>{@link kino.impl.KinosaalImpl#getAuffuehrungen <em>Auffuehrungen</em>}</li>
 * </ul>
 *
 * @generated
 */
public class KinosaalImpl extends MinimalEObjectImpl.Container implements Kinosaal {
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
	 * The default value of the '{@link #isFreigegeben() <em>Freigegeben</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isFreigegeben()
	 * @generated
	 * @ordered
	 */
	protected static final boolean FREIGEGEBEN_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isFreigegeben() <em>Freigegeben</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isFreigegeben()
	 * @generated
	 * @ordered
	 */
	protected boolean freigegeben = FREIGEGEBEN_EDEFAULT;

	/**
	 * The cached value of the '{@link #getReihen() <em>Reihen</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReihen()
	 * @generated
	 * @ordered
	 */
	protected EList<Sitzreihe> reihen;

	/**
	 * The cached value of the '{@link #getAuffuehrungen() <em>Auffuehrungen</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAuffuehrungen()
	 * @generated
	 * @ordered
	 */
	protected EList<Auffuehrung> auffuehrungen;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected KinosaalImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return KinoPackage.Literals.KINOSAAL;
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
			eNotify(new ENotificationImpl(this, Notification.SET, KinoPackage.KINOSAAL__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isFreigegeben() {
		return freigegeben;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setFreigegeben(boolean newFreigegeben) {
		boolean oldFreigegeben = freigegeben;
		freigegeben = newFreigegeben;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KinoPackage.KINOSAAL__FREIGEGEBEN, oldFreigegeben, freigegeben));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Sitzreihe> getReihen() {
		if (reihen == null) {
			reihen = new EObjectResolvingEList<Sitzreihe>(Sitzreihe.class, this, KinoPackage.KINOSAAL__REIHEN);
		}
		return reihen;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Auffuehrung> getAuffuehrungen() {
		if (auffuehrungen == null) {
			auffuehrungen = new EObjectResolvingEList<Auffuehrung>(Auffuehrung.class, this, KinoPackage.KINOSAAL__AUFFUEHRUNGEN);
		}
		return auffuehrungen;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public void reiheHinzufügen(Sitzreihe reihe) {
		this.reihen.add(reihe);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public void freigeben() {
		this.freigegeben = true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public boolean istFreigegeben() {
		return this.freigegeben;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case KinoPackage.KINOSAAL__NAME:
				return getName();
			case KinoPackage.KINOSAAL__FREIGEGEBEN:
				return isFreigegeben();
			case KinoPackage.KINOSAAL__REIHEN:
				return getReihen();
			case KinoPackage.KINOSAAL__AUFFUEHRUNGEN:
				return getAuffuehrungen();
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
			case KinoPackage.KINOSAAL__NAME:
				setName((String)newValue);
				return;
			case KinoPackage.KINOSAAL__FREIGEGEBEN:
				setFreigegeben((Boolean)newValue);
				return;
			case KinoPackage.KINOSAAL__REIHEN:
				getReihen().clear();
				getReihen().addAll((Collection<? extends Sitzreihe>)newValue);
				return;
			case KinoPackage.KINOSAAL__AUFFUEHRUNGEN:
				getAuffuehrungen().clear();
				getAuffuehrungen().addAll((Collection<? extends Auffuehrung>)newValue);
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
			case KinoPackage.KINOSAAL__NAME:
				setName(NAME_EDEFAULT);
				return;
			case KinoPackage.KINOSAAL__FREIGEGEBEN:
				setFreigegeben(FREIGEGEBEN_EDEFAULT);
				return;
			case KinoPackage.KINOSAAL__REIHEN:
				getReihen().clear();
				return;
			case KinoPackage.KINOSAAL__AUFFUEHRUNGEN:
				getAuffuehrungen().clear();
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
			case KinoPackage.KINOSAAL__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case KinoPackage.KINOSAAL__FREIGEGEBEN:
				return freigegeben != FREIGEGEBEN_EDEFAULT;
			case KinoPackage.KINOSAAL__REIHEN:
				return reihen != null && !reihen.isEmpty();
			case KinoPackage.KINOSAAL__AUFFUEHRUNGEN:
				return auffuehrungen != null && !auffuehrungen.isEmpty();
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
			case KinoPackage.KINOSAAL___REIHE_HINZUFÜGEN__SITZREIHE:
				reiheHinzufügen((Sitzreihe)arguments.get(0));
				return null;
			case KinoPackage.KINOSAAL___FREIGEBEN:
				freigeben();
				return null;
			case KinoPackage.KINOSAAL___IST_FREIGEGEBEN:
				return istFreigegeben();
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
		result.append(", freigegeben: ");
		result.append(freigegeben);
		result.append(')');
		return result.toString();
	}

} //KinosaalImpl
