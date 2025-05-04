package de.fherfurt.core.database;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Provides a static way to obtain an EntityManager via the "myPU" persistence unit.
 * This is typically used in a Java SE context (standalone).
 */
public class DatabaseConnection {

    private static final String PERSISTENCE_UNIT_NAME = "myPU";
    private static EntityManagerFactory emf;

    // Statischer Initialisierungsblock: Wird beim Laden der Klasse einmal ausgeführt
    static {
        try {
            // Hier den Namen deiner Persistence-Unit angeben
            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        } catch (Exception e) {
            e.printStackTrace();
            // Optional: Hier könntest du Logging oder ein spezielles Error Handling machen
        }
    }

    /**
     * Liefert einen neuen EntityManager.
     *
     * @return EntityManager zum Ausführen von JPA-Operationen
     */
    public static EntityManager getEntityManager() {
        if (emf == null) {
            throw new IllegalStateException("EntityManagerFactory wurde nicht initialisiert!");
        }
        return emf.createEntityManager();
    }

    /**
     * Schließt das EntityManagerFactory (z. B. am Ende der Anwendung).
     * Danach können keine neuen EntityManager mehr erzeugt werden.
     */
    public static void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
