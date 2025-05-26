
package gestion.administrativa.hospital.la;

public class buscar {
   private String ID;
    private String nombres;
    private String DNI;
    private String Teléfono;
    private String Género;
    private String fechaNacimiento;
    private String Alergias;
    private String Enfermedades;
    private String tipoSangre;
    private String notas;
    private String ultimaAtencion;
    private String diagnóstico;
    private String tratamiento;
    private String medico;
    private String proximaCita;

    public buscar(String ID, String nombres, String DNI, String Teléfono, String Género, String fechaNacimiento, String Alergias, String Enfermedades, String tipoSangre, String notas, String ultimaAtencion, String diagnóstico, String tratamiento, String medico, String proximaCita) {
        this.ID = ID;
        this.nombres = nombres;
        this.DNI = DNI;
        this.Teléfono = Teléfono;
        this.Género = Género;
        this.fechaNacimiento = fechaNacimiento;
        this.Alergias = Alergias;
        this.Enfermedades = Enfermedades;
        this.tipoSangre = tipoSangre;
        this.notas = notas;
        this.ultimaAtencion = ultimaAtencion;
        this.diagnóstico = diagnóstico;
        this.tratamiento = tratamiento;
        this.medico = medico;
        this.proximaCita = proximaCita;
    }

    public buscar()
    {
        
    }
    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public String getTeléfono() {
        return Teléfono;
    }

    public void setTeléfono(String Teléfono) {
        this.Teléfono = Teléfono;
    }

    public String getGénero() {
        return Género;
    }

    public void setGénero(String Género) {
        this.Género = Género;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getAlergias() {
        return Alergias;
    }

    public void setAlergias(String Alergias) {
        this.Alergias = Alergias;
    }

    public String getEnfermedades() {
        return Enfermedades;
    }

    public void setEnfermedades(String Enfermedades) {
        this.Enfermedades = Enfermedades;
    }

    public String getTipoSangre() {
        return tipoSangre;
    }

    public void setTipoSangre(String tipoSangre) {
        this.tipoSangre = tipoSangre;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public String getUltimaAtencion() {
        return ultimaAtencion;
    }

    public void setUltimaAtencion(String ultimaAtencion) {
        this.ultimaAtencion = ultimaAtencion;
    }

    public String getDiagnóstico() {
        return diagnóstico;
    }

    public void setDiagnóstico(String diagnóstico) {
        this.diagnóstico = diagnóstico;
    }

    public String getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }

    public String getMedico() {
        return medico;
    }

    public void setMedico(String medico) {
        this.medico = medico;
    }

    public String getProximaCita() {
        return proximaCita;
    }

    public void setProximaCita(String proximaCita) {
        this.proximaCita = proximaCita;
    }

    
    
    
    
    
    
    
    
}
