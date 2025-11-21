package kino.application.data;

/**
 * Vereinfachtes Enum für die Sitzreihen-Kategorie.
 *
 * Diese Variante kommt ohne EMF-Hilfsfelder/-Methoden aus
 * und eignet sich, wenn du die Persistence-Schicht klar
 * vom EMF-Modell trennen willst.
 */
public enum SitzreihenKategorie {
    PARKETT,
    LOGE,
    LOGE_MIT_SERVICE
}
