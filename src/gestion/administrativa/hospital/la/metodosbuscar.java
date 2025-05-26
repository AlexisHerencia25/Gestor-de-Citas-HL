package gestion.administrativa.hospital.la;

import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Paths;



public class metodosbuscar {
     public static buscar buscarpaciente(String valorBusqueda, String rutaJSON) {
        try {
            String contenido = new String(Files.readAllBytes(Paths.get(rutaJSON)));
            JSONArray array = new JSONArray(contenido);

            for (int i = 0; i < array.length(); i++) {
                JSONObject objeto = array.getJSONObject(i);
                JSONObject infoPersonal = objeto.getJSONObject("Información personal");

                String id = infoPersonal.getString("ID");
                String dni = infoPersonal.getString("DNI");
                String nombre = infoPersonal.optString("nombres", infoPersonal.optString("nombre", ""));

                if (id.equalsIgnoreCase(valorBusqueda) || dni.equalsIgnoreCase(valorBusqueda) || nombre.toLowerCase().contains(valorBusqueda.toLowerCase())) {
                    buscar paciente = new buscar();
                    paciente.setID(id);
                    paciente.setNombres(nombre);
                    paciente.setDNI(dni);
                    paciente.setTeléfono(infoPersonal.getString("Teléfono"));
                    paciente.setGénero(infoPersonal.getString("Género"));
                    paciente.setFechaNacimiento(infoPersonal.getString("Fecha de nacimiento"));

                    JSONObject med = objeto.getJSONObject("Datos médicos");
                    paciente.setAlergias(med.getString("Alergias"));
                    paciente.setEnfermedades(med.getString("Enfermedades Crónicas"));
                    paciente.setTipoSangre(med.getString("Tipo de Sangre"));
                    paciente.setNotas(med.getString("Notas médicas urgentes"));

                    JSONObject ultima = objeto.getJSONObject("Última atención médica");
                    paciente.setUltimaAtencion(ultima.getString("Fecha"));
                    paciente.setDiagnóstico(ultima.getString("diagnóstico"));
                    paciente.setTratamiento(ultima.getString("tratamiento o receta indicada"));
                    paciente.setMedico(ultima.getString("Medico encargado"));

                    JSONObject cita = objeto.getJSONObject("Citas programadas");
                    paciente.setProximaCita(cita.getString("Próxima cita"));

                    return paciente;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
   
}
