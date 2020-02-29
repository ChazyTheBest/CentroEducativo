package centroeducativo;

import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class TablasCursos
{
    static TreeMap<String, String> tmCC       = new TreeMap<String, String>();
    static TreeMap<String, String> tmCCASIGNA = new TreeMap<String, String>();

    static String readCourse(Scanner sc) throws Exception
    {
        System.out.print("Curso: ");
        String curso = sc.next().toUpperCase();;

        if (!tmCC.containsKey(curso))
        {
            throw new Exception("El curso no existe.");
        }

        return curso;
    }

    static String readSubject(String curso, Scanner sc) throws Exception
    {
        System.out.print("Código de asignatura: ");
        String key = sc.next().toUpperCase();

        if (curso == null && tmCCASIGNA.containsKey(key))
        {
            return key;
        }

        else if ((curso != null && key.length() > 2 && key.substring(0, 2).equals(curso) && tmCCASIGNA.containsKey(key)) || key.equals("0"))
        {
            return key;
        }

        else
        {
            throw new Exception("La asignatura no existe.");
        }
    }

    static void cargaCursos()
    {
        tmCC.put("1E", "SMR Mañana");
        tmCC.put("1G", "SMR Tarde");
        tmCC.put("1F", "SMR Tarde");
        tmCC.put("1W", "DAW Tarde");
        tmCC.put("1S", "DAW Mañana");
    }

    static String coursesToString()
    {
        StringBuilder courses = new StringBuilder();

        for (Map.Entry<String, String> entry : tmCC.entrySet())
        {
            courses.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }

        return courses.toString();
    }

    static void cargaCursosAsignaturas()
    {
        tmCCASIGNA.put("1EREDES", "Redes de Área Local");
        tmCCASIGNA.put("1EOFIMA", "Ofimática");
        tmCCASIGNA.put("1ESISOP", "Sistemas Operativos");
        tmCCASIGNA.put("1EFOL", "Formación y Orientación Laboral");
        tmCCASIGNA.put("1EINGLES", "Inglés");
        tmCCASIGNA.put("1GREDES", "Redes de Área Local");
        tmCCASIGNA.put("1GOFIMA", "Ofimática");
        tmCCASIGNA.put("1GSISOP", "Sistemas Operativos");
        tmCCASIGNA.put("1GFOL", "Formación y Orientación Laboral");
        tmCCASIGNA.put("1FREDES", "Redes de Área Local");
        tmCCASIGNA.put("1FOFIMA", "Ofimática");
        tmCCASIGNA.put("1FSISOP", "Sistemas Operativos");
        tmCCASIGNA.put("1FFOL", "Formación y Orientación Laboral");
        tmCCASIGNA.put("1FINGLES", "Inglés");
        tmCCASIGNA.put("1WPROGRAM", "Programación");
        tmCCASIGNA.put("1WLENGMAR", "Lenguajes de Marcas");
        tmCCASIGNA.put("1WENTORNOS", "Entornos de Desarrollo");
        tmCCASIGNA.put("1WSISOPE", "Sistemas Operativos");
        tmCCASIGNA.put("1WFOL", "Formación y Orientación Laboral");
        tmCCASIGNA.put("1WINGLES", "Inglés");
        tmCCASIGNA.put("1SPROGRAM", "Programación");
        tmCCASIGNA.put("1SLENGMAR", "Lenguajes de Marcas");
        tmCCASIGNA.put("1SENTORNOS", "Entornos de Desarrollo");
        tmCCASIGNA.put("1SSISOPE", "Sistemas Operativos");
        tmCCASIGNA.put("1SFOL", "Formación y Orientación Laboral");
        tmCCASIGNA.put("1SINGLES", "Inglés");
    }

    static String subjectsToString()
    {
        StringBuilder subjects = new StringBuilder();

        for (Map.Entry<String, String> entry : tmCCASIGNA.entrySet())
        {
            subjects.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }

        return subjects.toString();
    }
}
