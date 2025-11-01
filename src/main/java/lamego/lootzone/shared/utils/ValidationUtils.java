package lamego.lootzone.shared.utils;

public class ValidationUtils {

    public static boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) return false;

        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(regex);
    }
}
