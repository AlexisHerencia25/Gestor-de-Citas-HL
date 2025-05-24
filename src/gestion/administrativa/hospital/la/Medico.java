
package gestion.administrativa.hospital.la;


public class Medico {
    private int id;
    private String nombre;
    private String apellido;
    private String especialidad;
    private String horarioDisponible;

    public Medico(int id, String nombre, String apellido, String especialidad, String horarioDisponible) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.especialidad = especialidad;
        this.horarioDisponible = horarioDisponible;
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getHorarioDisponible() {
        return horarioDisponible;
    }

    public void setHorarioDisponible(String horarioDisponible) {
        this.horarioDisponible = horarioDisponible;
    }
    
    public String buscarMedico(){
        if(especialidad.equals("Pediatría")){
            return "ID: MED001\nNombre: Dra. Ana Torres\nHorario Disponible: Lunes->08:00-12:00, Miercoles->10:00-14:00, Viernes->08:00-12:00";
        }
        if(especialidad.equals("Cardiología")){
            return "ID:MED002\nNombre: Dr. Luis Ramírez\nHorario Disponible: Martes->14:00-18:00, Jueves:->08:00-12:00";
        }
        if(especialidad.equals("Dermatología")){
            return "ID:MED003\nNombre: Dra. Camila Soto\nHorario Disponible: Lunes->14:00-13:00, Miercoles:->09:00-13:00";
        }
        if(especialidad.equals("Traumatología")){
            return "ID:MED004\nNombre: Dr. Esteban Mora\nHorario Disponible: Martes->10:00-14:00, Viernes:->12:00-16:00";
        }
        if(especialidad.equals("Neurología")){
            return "ID:MED005\nNombre: Dra. Mariana Díaz\nHorario Disponible: Jueves->08:00-11:00, Viernes:->10:00-13:00";
        }
        if(especialidad.equals("Gastroentología")){
            return "ID:MED006\nNombre: Dr. Javier Gómez\nHorario Disponible: Lunes->14:00-18:00, Miercoles:->08:00-12:00";
        }
        if(especialidad.equals("Ginecología")){
            return "ID:MED007\nNombre: Dra. Silvia Ríos\nHorario Disponible: Martes->09:00-13:00, Miercoles:->14:00-18:00";
        }
        if(especialidad.equals("Urología")){
            return "ID:MED008\nNombre: Dr. Fernando Morales\nHorario Disponible: Miercoles->10:00-14:00, Viernes:->08:00-12:00";
        }
        if(especialidad.equals("Oftalmología")){
            return "ID:MED009\nNombre: Dra. Isabel Castillo\nHorario Disponible: Lunes->13:00-17:00, Jueves:->08:00-12:00";
        }
        if(especialidad.equals("Psiquiatría")){
            return "ID:MED010\nNombre: Dr. Ricardo Mejía\nHorario Disponible: Martes->15:00-19:00, Miercoles:->09:00-13:00";
        }
        return "Especialidad no escontrada";
    }
}
