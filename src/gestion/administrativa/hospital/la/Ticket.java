/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestion.administrativa.hospital.la;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.File;

import java.io.FileOutputStream;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.nio.file.Paths;

import java.security.SecureRandom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONObject;

import java.time.*;
import java.time.format.DateTimeFormatter;
/**
 *
 * @author Alexis
 */
public class Ticket {
    private File jsoncitas = new File("src/gestion/administrativa/hospital/la/citas.json");
    
    public static void generarCitaPDF(
            String paciente, String id, String fechaHora,
            String area, String medico, int numeroCita, String rutaPDF
    ) {
        try {
            Document documento = new Document(new Rectangle(700, 300)); // Tamaño horizontal
            PdfWriter writer = PdfWriter.getInstance(documento, new FileOutputStream(rutaPDF));
            documento.open();

            // Agregar bordes decorativos (simulación con líneas)
            PdfContentByte canvas = writer.getDirectContent();
            canvas.setLineWidth(1f);
            canvas.setColorStroke(BaseColor.BLACK);
            canvas.setLineDash(5, 5); // línea discontinua

            // Bordes derecho e izquierdo
            canvas.moveTo(20, 20);
            canvas.lineTo(20, 280);
            canvas.stroke();

            canvas.moveTo(680, 20);
            canvas.lineTo(680, 280);
            canvas.stroke();

            // Agregar imagen/logo
            Image logo = Image.getInstance("src/gestion/administrativa/hospital/la/logo.jpeg"); // Cambia a tu ruta real
            logo.setAbsolutePosition(30, 200);
            logo.scaleAbsolute(100, 80);
            documento.add(logo);

            // Fuente título
            Font fuenteTitulo = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
            Paragraph titulo = new Paragraph("HOSPITAL ARZOBISPO LOAYZA NACIONAL\nCITA MEDICA", fuenteTitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            documento.add(titulo);

            documento.add(new Paragraph("\n"));

            // Fuente texto general
            Font fuenteTexto = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);

            // Información de cita
            documento.add(new Paragraph("PACIENTE: " + paciente, fuenteTexto));
            documento.add(new Paragraph("ID: " + id, fuenteTexto));
            documento.add(new Paragraph("FECHA Y HORA: " + fechaHora, fuenteTexto));
            documento.add(new Paragraph("AREA: " + area, fuenteTexto));
            documento.add(new Paragraph("MEDICO: " + medico, fuenteTexto));

            documento.add(new Paragraph("\n"));

            // Número de cita
            Paragraph numero = new Paragraph("NUMERO DE CITA " + String.format("%03d", numeroCita), fuenteTexto);
            numero.setAlignment(Element.ALIGN_LEFT);
            documento.add(numero);

            // Código de barras simulado
            Barcode128 codigo = new Barcode128();
            codigo.setCode(id);
            Image imagenCodigo = codigo.createImageWithBarcode(writer.getDirectContent(), null, null);
            imagenCodigo.setAbsolutePosition(640, 35);
            imagenCodigo.scalePercent(100);
            imagenCodigo.setRotationDegrees(90);
            documento.add(imagenCodigo);

            documento.close();
            System.out.println("PDF generado correctamente en " + rutaPDF);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public String ObtenerIDPaciente(String nombre){
        File jsonpacientes = new File("src/gestion/administrativa/hospital/la/pacientes.json");
        try{
            String contenido = new String(Files.readAllBytes(jsonpacientes.toPath()));
            JSONArray pacientes = new JSONArray(contenido);
            for (Object obj : pacientes) {
                JSONObject cita = (JSONObject) obj;
                JSONObject info = (JSONObject) cita.get("Información personal");
                String id = info.optString("Nombres completos", null);
                if (id.equals(nombre)) {
                    return id;
                }
            }
            return "";
        } catch (IOException e){
            e.printStackTrace();
            return "";
        }
    }
    
    public boolean RegistrarNuevaCita(String nombre_medico, String id_medico, String dia, String hora, 
            String especialidad, String nombre_paciente, String id_paciente){
        try {
            String contenido = new String(Files.readAllBytes(jsoncitas.toPath()));
            JSONArray citas = new JSONArray(contenido);

            JSONObject paciente = new JSONObject();
            paciente.put("nombres_completos", nombre_paciente);
            paciente.put("id", "P-" + id_paciente);

            JSONObject medico = new JSONObject();
            medico.put("nombres_completos", nombre_medico);
            medico.put("id", id_medico);
            medico.put("horario_disponible", dia + " / " + hora);

            JSONObject nuevaCita = new JSONObject();
            nuevaCita.put("cita_id", "CITA-" + generarIDAlfanumerico());
            
            LocalDate fecha = obtenerProximaFechaDesdeTexto(dia);
            if(fecha != null){
                DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                nuevaCita.put("fecha", fecha.format(formato));
            }
            nuevaCita.put("hora", hora);
            nuevaCita.put("dia", dia);
            nuevaCita.put("especialidad", especialidad);
            nuevaCita.put("medico", medico);
            nuevaCita.put("paciente", paciente);
            nuevaCita.put("estado", "Programada");

            citas.put(nuevaCita);

            Files.write(jsoncitas.toPath(), citas.toString(2).getBytes(),
                StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);

            System.out.println("Paciente y cita registrada correctamente con estado 'Programada'.");
            
            String medicos = new String(Files.readAllBytes(Paths.get("src/gestion/administrativa/hospital/la/medicos.json")));
            JSONArray xd = new JSONArray(medicos);
            for (Object obj : xd) {
                JSONObject cita = (JSONObject) obj;
                JSONObject horario = cita.getJSONObject("horario_disponible");
                if (horario.has(dia)){
                    String horaDisponible = horario.getString(dia);
                    if (horaDisponible.equals(hora))
                        horario.remove(dia);
                }
            }
            
            return true;

        } catch (IOException e) {
            System.err.println("Error al acceder al archivo citas.json: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Error general: " + e.getMessage());
            return false;
        }
    }
    
    public String generarIDAlfanumerico() {
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        int longitud = 10;
        SecureRandom random = new SecureRandom();
        ArrayList<String> idsExistentes = obtenerIDsOrdenados();
        Collections.sort(idsExistentes);

        String nuevoID;
        do {
            StringBuilder id = new StringBuilder();
            for (int i = 0; i < longitud; i++) {
                int indice = random.nextInt(caracteres.length());
                id.append(caracteres.charAt(indice));
            }
            nuevoID = id.toString();
        } while (Collections.binarySearch(idsExistentes, nuevoID) >= 0);
        return nuevoID;
    }
    
    private ArrayList<String> obtenerIDsOrdenados() {
            ArrayList<String> ids = new ArrayList<>();
            try{
                String contenido = new String(Files.readAllBytes(jsoncitas.toPath()));
                JSONArray pacientes = new JSONArray(contenido);
                for (Object obj : pacientes) {
                    JSONObject cita = (JSONObject) obj;
                    String id = cita.optString("cita_id", null);
                    if (id != null) {
                        ids.add(id);
                    }
                }
            } catch (IOException e){
                e.printStackTrace();
            }
        return ids;
    }
    
    public Integer CantidadCitas(){
        try {
            String contenido = new String(Files.readAllBytes(jsoncitas.toPath()));
            JSONArray citas = new JSONArray(contenido);
            return citas.length();
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    public static LocalDate obtenerProximaFechaDesdeTexto(String texto) {
        texto = texto.toLowerCase(Locale.ROOT);

        // Mapa de días en español a DayOfWeek
        HashMap<String, DayOfWeek> diasSemana = new HashMap<>();
        diasSemana.put("lunes", DayOfWeek.MONDAY);
        diasSemana.put("martes", DayOfWeek.TUESDAY);
        diasSemana.put("miércoles", DayOfWeek.WEDNESDAY);
        diasSemana.put("miercoles", DayOfWeek.WEDNESDAY); // sin tilde
        diasSemana.put("jueves", DayOfWeek.THURSDAY);
        diasSemana.put("viernes", DayOfWeek.FRIDAY);
        diasSemana.put("sábado", DayOfWeek.SATURDAY);
        diasSemana.put("sabado", DayOfWeek.SATURDAY); // sin tilde
        diasSemana.put("domingo", DayOfWeek.SUNDAY);

        for (String clave : diasSemana.keySet()) {
            if (texto.contains(clave)) {
                return calcularProximaFecha(diasSemana.get(clave));
            }
        }
        return null;
    }

    public static LocalDate calcularProximaFecha(DayOfWeek diaDeseado) {
        LocalDate hoy = LocalDate.now();
        DayOfWeek diaActual = hoy.getDayOfWeek();

        int diasDiferencia = diaDeseado.getValue() - diaActual.getValue();
        if (diasDiferencia <= 0) {
            diasDiferencia += 7; // Ya pasó o es hoy, se busca el siguiente
        }

        return hoy.plusDays(diasDiferencia);
    }
}
