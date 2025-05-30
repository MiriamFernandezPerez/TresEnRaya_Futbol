import java.util.List;

public class MainApp { //

    public static void main(String[] args) {
        TicTacToeDB db = null;
        try {
            // Instanciar nuestra clase de acceso a la base de datos
            db = new TicTacToeDB();

            System.out.println("\n--- Probando la función getAllPlayerNames() ---");
            List<String> allPlayerNames = db.getAllPlayerNames();
            System.out.println("Nombres de jugadores en la DB (primeros 10):");
            for (int i = 0; i < Math.min(10, allPlayerNames.size()); i++) {
                System.out.println("- " + allPlayerNames.get(i));
            }
            if (allPlayerNames.size() > 10) {
                System.out.println("... y " + (allPlayerNames.size() - 10) + " más.");
            }


            System.out.println("\n--- Probando consulta compleja: Jugadores del Real Madrid que son DC ---");
            List<String> jugadoresRealMadridDC = db.getPlayersByCategories(
                "EQUIPO", "Real Madrid",
                "POSICION", "DC"
            );
            System.out.println("Jugadores del Real Madrid que son DC:");
            if (jugadoresRealMadridDC.isEmpty()) {
                System.out.println("No se encontraron jugadores con estas características.");
            } else {
                for (String nombre : jugadoresRealMadridDC) {
                    System.out.println("- " + nombre);
                }
            }

            System.out.println("\n--- Probando consulta compleja: Jugadores de España y con edad mayor a 30 ---");
            List<String> jugadoresEspanaMas30 = db.getPlayersByCategories(
                "PAIS", "España",
                "EDAD_MAYOR_QUE", "30"
            );
            System.out.println("Jugadores de España con más de 30 años:");
            if (jugadoresEspanaMas30.isEmpty()) {
                System.out.println("No se encontraron jugadores con estas características.");
            } else {
                for (String nombre : jugadoresEspanaMas30) {
                    System.out.println("- " + nombre);
                }
            }

            System.out.println("\n--- Probando consulta con categorías no existentes (debería dar lista vacía) ---");
            List<String> jugadoresInvalidos = db.getPlayersByCategories(
                "EQUIPO", "EquipoInventado",
                "POSICION", "POSICIONINVENTADA"
            );
            System.out.println("Jugadores de EquipoInventado que son POSICIONINVENTADA (esperado: 0): " + jugadoresInvalidos.size());


        } catch (Exception e) {
            System.err.println("Error general en MainApp: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Asegúrate de cerrar la conexión a la base de datos cuando la aplicación termine
            if (db != null) {
                db.closeConnection();
            }
        }
    }
}