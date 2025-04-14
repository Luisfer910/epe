package com.tuempresa.hotelreservation.util;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Utilidad para operaciones de seguridad
 * @author LFMG9
 */
/**
 *
 * @author LFMG9
 */

public class SeguridadUtil {
    
    /**
     * Genera un hash de la contraseña utilizando BCrypt
     * @param contraseña Contraseña en texto plano
     * @return Hash de la contraseña
     */
    public static String hashContraseña(String contraseña) {
        return BCrypt.hashpw(contraseña, BCrypt.gensalt());
    }
    
    /**
     * Verifica si una contraseña coincide con su hash
     * @param contraseña Contraseña en texto plano
     * @param hash Hash almacenado
     * @return true si la contraseña coincide, false en caso contrario
     */
    public static boolean verificarContraseña(String contraseña, String hash) {
        return BCrypt.checkpw(contraseña, hash);
    }
    
    /**
     * Limpia la entrada para prevenir inyecciones SQL
     * @param input Entrada a limpiar
     * @return Entrada limpia
     */
    public static String limpiarEntrada(String input) {
        if (input == null) {
            return null;
        }
        // Eliminar caracteres especiales que podrían usarse en inyecciones SQL
        String limpio = input.replaceAll("['\"\\\\;]", "");
        // Eliminar intentos de comentarios SQL
        limpio = limpio.replaceAll("--", "");
        limpio = limpio.replaceAll("/\\*", "");
        // Eliminar intentos de comandos SQL comunes
        limpio = limpio.replaceAll("(?i)SELECT", "");
        limpio = limpio.replaceAll("(?i)INSERT", "");
        limpio = limpio.replaceAll("(?i)UPDATE", "");
        limpio = limpio.replaceAll("(?i)DELETE", "");
        limpio = limpio.replaceAll("(?i)DROP", "");
        limpio = limpio.replaceAll("(?i)TRUNCATE", "");
        
        return limpio;
    }
}

