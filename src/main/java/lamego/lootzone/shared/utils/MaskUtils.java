package lamego.lootzone.shared.utils;

import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Classe utilitária para aplicar máscaras e formatações
 * em campos de texto(CPF, CNPJ, Telefone, Moeda).
 */
public final class MaskUtils {

    private MaskUtils() {
        // Evita instanciação
    }

    /**
     * Aplica máscara dinâmica para CPF: ###.###.###-##
     */
    public static void aplicarMascaraCPF(TextField tfCPF) {
        tfCPF.setTextFormatter(new TextFormatter<String>(change -> {
            if (change.isAdded() || change.isReplaced()) {
                String newText = change.getControlNewText().replaceAll("[^0-9]", "");
                if (newText.length() > 11) return null;

                StringBuilder sb = new StringBuilder();
                int len = newText.length();

                if (len >= 1) sb.append(newText, 0, Math.min(3, len));
                if (len >= 4) sb.insert(3, '.');
                if (len >= 4) sb.append(newText, 3, Math.min(6, len));
                if (len >= 7) sb.insert(7, '.');
                if (len >= 7) sb.append(newText, 6, Math.min(9, len));
                if (len >= 10) sb.insert(11, '-');
                if (len >= 10) sb.append(newText, 9, len);

                change.setText(sb.toString());
                change.setRange(0, change.getControlText().length());
                change.selectRange(sb.length(), sb.length());
            }
            return change;
        }));
    }

    /**
     * Aplica máscara dinâmica para CNPJ: ##.###.###/####-##
     */
    public static void aplicarMascaraCNPJ(TextField tfCNPJ) {
        tfCNPJ.setTextFormatter(new TextFormatter<String>(change -> {
            if (change.isAdded() || change.isReplaced()) {
                String newText = change.getControlNewText().replaceAll("[^0-9]", "");
                if (newText.length() > 14) return null;

                StringBuilder sb = new StringBuilder();
                int len = newText.length();

                if (len >= 1) sb.append(newText, 0, Math.min(2, len));
                if (len >= 3) sb.insert(2, '.');
                if (len >= 3) sb.append(newText, 2, Math.min(5, len));
                if (len >= 6) sb.insert(6, '.');
                if (len >= 6) sb.append(newText, 5, Math.min(8, len));
                if (len >= 9) sb.insert(10, '/');
                if (len >= 9) sb.append(newText, 8, Math.min(12, len));
                if (len >= 13) sb.insert(15, '-');
                if (len >= 13) sb.append(newText, 12, len);

                change.setText(sb.toString());
                change.setRange(0, change.getControlText().length());
                change.selectRange(sb.length(), sb.length());
            }
            return change;
        }));
    }

    /**
     * Aplica máscara dinâmica para Telefone: (##) #####-####
     */
    public static void aplicarMascaraTelefone(TextField tfTelefone) {
        tfTelefone.setTextFormatter(new TextFormatter<String>(change -> {
            if (change.isAdded() || change.isReplaced()) {
                String newText = change.getControlNewText().replaceAll("[^0-9]", "");
                if (newText.length() > 11) return null;

                StringBuilder sb = new StringBuilder();
                int len = newText.length();

                if (len > 0) sb.append("(");
                if (len >= 1) sb.append(newText.substring(0, Math.min(2, len)));
                if (len >= 3) sb.append(") ");
                if (len >= 3) sb.append(newText.substring(2, Math.min(7, len)));
                if (len >= 8) sb.append("-").append(newText.substring(7, len));

                change.setText(sb.toString());
                change.setRange(0, change.getControlText().length());
                change.selectRange(sb.length(), sb.length());
            }
            return change;
        }));
    }

    /**
     * Aplica mascara dinâmica para Data: ##/##/####
     */
    public static void aplicarMascaraData(DatePicker datePicker){
        datePicker.getEditor().setTextFormatter(new TextFormatter<String>(change -> {
            String newText = change.getControlNewText();

            // Permite apenas números e "/"
            if (!newText.matches("[0-9/]*")) {
                return null;
            }

            // Limita a 10 caracteres (DD/MM/YYYY)
            if (newText.length() > 10) {
                return null;
            }

            // Aplica máscara automaticamente se desejar (opcional)
            // Exemplo simples: adiciona "/" após dia e mês
            String digits = newText.replaceAll("[^0-9]", "");
            StringBuilder formatted = new StringBuilder();
            int len = digits.length();

            if (len > 0) formatted.append(digits.substring(0, Math.min(2, len))); // dia
            if (len >= 3) formatted.append("/").append(digits.substring(2, Math.min(4, len))); // mês
            if (len >= 5) formatted.append("/").append(digits.substring(4, Math.min(8, len))); // ano

            change.setText(formatted.toString());
            change.setRange(0, change.getControlText().length());

            // Cursor no final
            change.selectRange(change.getControlNewText().length(), change.getControlNewText().length());

            return change;
        }));
    }

    /**
     * Aplica formatação monetária no padrão brasileiro (R$).
     */
    public static void aplicarMascaraMonetaria(TextField tfValor) {
        Locale brasil = Locale.forLanguageTag("pt-BR");
        NumberFormat formatoMoeda = NumberFormat.getCurrencyInstance(brasil);

        tfValor.setTextFormatter(new TextFormatter<String>(change -> {
            if (change.isAdded() || change.isReplaced()) {
                String digits = change.getControlNewText().replaceAll("[^0-9]", "");
                if (digits.isEmpty()) {
                    change.setText("");
                    return change;
                }

                double valor = Double.parseDouble(digits) / 100.0;
                String formatted = formatoMoeda.format(valor);

                change.setText(formatted);
                change.setRange(0, change.getControlText().length());
                change.selectRange(formatted.length(), formatted.length());
            }
            return change;
        }));
    }

    /**
     * Remove qualquer formatação e retorna apenas números.
     */
    public static String limparFormatacao(String texto) {
        return texto == null ? "" : texto.replaceAll("[^0-9]", "");
    }

    /**
     * Converte texto formatado de moeda em float.
     */
    public static float parseValorMonetario(String texto) {
        String digits = limparFormatacao(texto);
        if (digits.isEmpty()) return 0;
        return Float.parseFloat(digits) / 100;
    }
}