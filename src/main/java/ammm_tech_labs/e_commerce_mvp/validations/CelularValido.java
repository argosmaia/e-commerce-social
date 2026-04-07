package ammm_tech_labs.e_commerce_mvp.validations;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class CelularValido {
    private static final Set<Integer> DDD = Set.of(
            11, 12, 13, 14, 15, 16, 17, 18, 19,
            21, 22, 24, 27, 28,
            31, 32, 33, 34, 35, 37, 38,
            41, 42, 43, 44, 45, 46, 47, 48, 49,
            51, 53, 54, 55,
            61, 62, 63, 64, 65, 66, 67, 68, 69,
            71, 73, 74, 75, 77, 79,
            81, 82, 83, 84, 85, 86, 87, 88, 89,
            91, 92, 93, 94, 95, 96, 97, 98, 99);

    public static boolean validar(String celular) {
        if (celular == null)
            return false;
        String numeros = celular.replaceAll("\\D", "");

        if (numeros.length() != 13)
            return false;

        if (!numeros.startsWith("55"))
            return false;

        int ddd = Integer.parseInt(numeros.substring(2, 4));

        if (!DDD.contains(ddd))
            return false;

        char primeiroDigito = numeros.charAt(4);
        if (primeiroDigito != '9')
            return false;

        return true;
    }

    public static String gerar() {
        ThreadLocalRandom random = ThreadLocalRandom.current();

        int ddd = DDD.toArray(new Integer[0])[random.nextInt(DDD.size())];

        int numero = random.nextInt(10000000, 99999999);

        return String.format("+55 (%02d) 9%08d", ddd, numero);
    }

}
