package centroeducativo;

import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Alumno extends Persona
{
    private String curso = "";
    private TreeMap<String, Notas> tmAsignaturasAlumno = new TreeMap<String, Notas>();

    public String pideDatos(Scanner sc) throws Exception
    {
        String key = super.pideDatos(sc);

        System.out.println("Proceso de matriculación del alumno.\n");

        while (curso == "")
        {
            try
            {
                curso = TablasCursos.readCourse(sc);
            }

            catch (Exception e)
            {
                System.out.println(e.getMessage());
            }
        }

        System.out.printf("\nAsignaturas para el curso %s (introducir 0 para terminar).\n\n", curso);

        String s = "";

        while (tmAsignaturasAlumno.isEmpty() || !s.equals("0"))
        {
            try
            {
                s = TablasCursos.readSubject(curso, sc);
            }

            catch (Exception e)
            {
                System.out.println(e.getMessage());
                continue;
            }

            if (tmAsignaturasAlumno.containsKey(s))
            {
                System.out.println("El alumno ya está matriculado en esa asignatura.");
            }

            else if (!s.equals("0"))
            {
                tmAsignaturasAlumno.put(s, new Notas());
                System.out.println("El alumno ha sido matriculado en: " + TablasCursos.tmCCASIGNA.get(s));
            }

            else if (s.equals("0") && tmAsignaturasAlumno.isEmpty())
            {
                System.out.println("El alumno debe estar matriculado en al menos una asignatura.");
            }
        }

        return key;
    }

    @Override
    public String toString()
    {
        return new StringBuilder(super.toString()).append("\nCurso: ").append(curso).append("/n").toString();
    }

    public String boletinNotas(int eva)
    {
        StringBuilder grades = new StringBuilder("Alumno: ");

        grades.append(super.getApellidos()).append(", ").append(super.getNombre()).append("\n\n")
              .append(Notas.getEv(eva));

        for (Map.Entry<String, Notas> entry : tmAsignaturasAlumno.entrySet())
        {
            grades.append("\n")
                  .append(TablasCursos.tmCCASIGNA.get(entry.getKey()))
                  .append(": ")
                  .append(entry.getValue().getNotas()[eva]);
        }

        return grades.append("\n").toString();
    }

    public String getCurso()
    {
        return curso;
    }

    public void setCurso(String curso)
    {
        this.curso = curso;
    }

    public TreeMap<String, Notas> getTmAsignaturasAlumno()
    {
        return tmAsignaturasAlumno;
    }

    public void setTmAsignaturasAlumno(TreeMap<String, Notas> tmAsignaturasAlumno)
    {
        this.tmAsignaturasAlumno = tmAsignaturasAlumno;
    }
}
