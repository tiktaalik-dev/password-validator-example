package dev.tiktaalik.passwordvalidator.data.model;

/**
 * Clase de datos que captura una contraseña ingresada por el usuario y determina si es válida
 */
public class ValidPassword {

    private final String password;
    private final Integer passwordLength;
    private Integer requiredPasswordLength = 5;
    private Integer requiredUppercase = 1;
    private Integer requiredSpecialCharacters = 0;
    private Integer requiredNumbers = 0;

    public ValidPassword(String password) {
        this.password = password;
        this.passwordLength = password.length();
    }

    public String getPassword() {
        return password;
    }

    public Integer getRequiredPasswordLength() {
        return requiredPasswordLength;
    }

    public void setRequiredPasswordLength(Integer requiredPasswordLength) {
        this.requiredPasswordLength = requiredPasswordLength;
    }

    public Integer getRequiredUppercase() {
        return requiredUppercase;
    }

    public void setRequiredUppercase(Integer requiredUppercase) {
        this.requiredUppercase = requiredUppercase;
    }

    public Integer getRequiredSpecialCharacters() {
        return requiredSpecialCharacters;
    }

    public void setRequiredSpecialCharacters(Integer requiredSpecialCharacters) {
        this.requiredSpecialCharacters = requiredSpecialCharacters;
    }

    public Integer getRequiredNumbers() {
        return requiredNumbers;
    }

    public void setRequiredNumbers(Integer requiredNumbers) {
        this.requiredNumbers = requiredNumbers;
    }

    public boolean isLongEnough() {

        // Retornar si el largo de la contraseña es mayor o igual al requerido
        return passwordLength >= requiredPasswordLength;
    }

    public boolean isValidPassword() {
        // Crear variables de trabajo
        int uppercaseCount = 0;
        int specialCharactersCount = 0;
        int numbersCount = 0;
        boolean result = false;

        // Primero, comprobar el largo de la contraseña
        if (isLongEnough()) {

            // Si el largo es correcto, fijar result como verdadero
            result = true;

            // Luego, comprobar si es necesario contar las mayúsculas
            if (requiredUppercase > 0) {
                uppercaseCount = (int) password.chars().filter(Character::isUpperCase).count();

                // Comparar si hay mayúsculas suficientes
                result = uppercaseCount >= requiredUppercase;
            }

            // Luego, comprobar si es necesario contar caracteres especiales
            if (result && requiredSpecialCharacters > 0) {

                // Contar los caracteres especiales
                specialCharactersCount = (int) password.chars().filter(c -> !Character.isLetterOrDigit(c)).count();

                // Comparar si hay caracteres especiales suficientes
                result = specialCharactersCount >= requiredSpecialCharacters;
            }

            // Luego, comprobar si es necesario contar números
            if (result && requiredNumbers > 0) {

                // Contar los números
                numbersCount = (int) password.chars().filter(Character::isDigit).count();

                // Comparar si hay números suficientes
                result = numbersCount >= requiredNumbers;
            }

        }

        // Por defecto retornar falso
        return result;
    }
}