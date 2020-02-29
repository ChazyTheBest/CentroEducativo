package centroeducativo;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class CentroEducativo
{
    private static String curso;
    private static double pagoPorHoraExtra = 0;

    final String ERROR_MENU = "La opción debe ser un número entre 0 y %d.\n";

    static TreeMap<String, Persona> lista = new TreeMap<String, Persona>();

    CentroEducativo()
    {
        Cuenta.cargaEntidadesBancarias();
        Cuenta.cargaSucursalesBancarias();
        TablasCursos.cargaCursos();
        TablasCursos.cargaCursosAsignaturas();
    }

    public static void main(String[] args)
    {
        // init the app
        new CentroEducativo().go();
    }

    void test()
    {
        Scanner sc = new Scanner(System.in);

        // test case

        sc.close();
    }

    int readMonth(String text, Scanner sc)
    {
        int mes = -1;

        while (mes == -1)
        {
            System.out.print(text);

            try
            {
                mes = sc.nextInt() - 1; // array index starts at 0

                if (mes > -1 && mes < 12)
                {
                    System.out.println("Has seleccionado: " + Month.of(mes + 1).getDisplayName(TextStyle.FULL, Locale.getDefault()));
                }

                else
                {
                    mes = -1;
                    throw new Exception("El mes debe ser un número entre 1 y 12.");
                }
            }

            catch (InputMismatchException e)
            {
                System.out.println("Solo puedes introducir números.");
                sc.next();
            }

            catch (Exception e)
            {
                System.out.println(e.getMessage());
            }
        }

        return mes;
    }

    String getPKey(String persona, String texto, Scanner sc) throws Exception
    {
        StringBuilder name = new StringBuilder();

        System.out.printf("Nombre del %s: ", persona);
        name.append(sc.next());

        System.out.print("Apellidos: ");
        name.insert(0, sc.next() + ", ");

        String key = name.toString();

        if ((persona.equals("profesor") && !(lista.get(key) instanceof Profesor)) ||
            (persona.equals("alumno")   && !(lista.get(key) instanceof Alumno)))
        {
            throw new Exception(String.format("Este %s no existe." + texto, persona));
        }

        return key;
    }

    void mainMenu(Scanner sc)
    {
        int menu = -1;

        while (menu != 0)
        {
            System.out.println(" *************** MENU PRINCIPAL ***************\n");
            System.out.println("\t1. MANTENIMIENTO ALUMNOS");
            System.out.println("\t2. MANTENIMIENTO PROFESORES");
            System.out.println("\t3. LISTADO DE NOMBRES DE PROFESORES Y ALUMNOS");
            System.out.println("\t4. LISTADO DE NOMBRES DE PROFESORES");
            System.out.println("\t5. LISTADO DE NOMBRES DE ALUMNOS");
            System.out.println("\t0. FIN DE LA APLICACION");
            System.out.print("\n\t   Opcion seleccionada: ");

            try
            {
                menu = sc.nextInt();
            }

            catch (Exception e)
            {
                System.out.printf(ERROR_MENU, 5);
                sc.next();
                continue;
            }

            switch (menu)
            {
                case 0: break;
                case 1: // manage students
                {
                    subMenuAlumnos(sc);

                    break;
                }
                case 2: // manage professors
                {
                    subMenuProfesores(sc);

                    break;
                }
                case 3: // print all data
                {
                    System.out.printf("Curso Académico: %s\nLISTADO DE PROFESORES Y ALUMNOS\nAPELLIDOS/NOMBRE\n", curso);

                    for (Map.Entry<String, Persona> entry : lista.entrySet())
                    {
                        if (entry.getValue() instanceof Profesor)
                        {
                            System.out.printf("(P) %s, %s\n", entry.getValue().getApellidos(), entry.getValue().getNombre());
                        }

                        else if (entry.getValue() instanceof Alumno)
                        {
                            Alumno a = Alumno.class.cast(entry.getValue());

                            System.out.printf("(%s) %s, %s", a.getTmAsignaturasAlumno().firstKey().substring(0, 2), a.getApellidos(),
                                                                                                                    a.getNombre());
                        }
                    }

                    break;
                }
                case 4: // print professors' data
                {
                    System.out.printf("Curso Académico: %s\nLISTADO DE PROFESORES\nAPELLIDOS/NOMBRE\n", curso);

                    for (Map.Entry<String, Persona> entry : lista.entrySet())
                    {
                        if (entry.getValue() instanceof Profesor)
                        {
                            System.out.printf("(P) %s, %s\n", entry.getValue().getApellidos(), entry.getValue().getNombre());
                        }
                    }

                    break;
                }
                case 5: // print students' data
                {
                    System.out.printf("Curso Académico: %s\nLISTADO DE ALUMNOS\nAPELLIDOS/NOMBRE\n", curso);

                    for (Map.Entry<String, Persona> entry : lista.entrySet())
                    {
                        if (entry.getValue() instanceof Alumno)
                        {
                            Alumno a = Alumno.class.cast(entry.getValue());

                            System.out.printf("(%s) %s, %s", a.getTmAsignaturasAlumno().firstKey().substring(0, 2), a.getApellidos(),
                                                                                                                    a.getNombre());
                        }
                    }

                    break;
                }
                default:
                {
                    System.out.printf(ERROR_MENU, 5);
                }
            }
        }
    }

    void subMenuAlumnos(Scanner sc)
    {
        int submenu = -1;

        while (submenu != 0)
        {
            String persona = "alumno";

            System.out.println("\n\n\n *************** MANTENIMIENTO DE ALUMNOS ***************\n");
            System.out.println("\t1. ALTA DE UN ALUMNO");
            System.out.println("\t2. BAJA DE UN ALUMNO");
            System.out.println("\t3. CONSULTA DE DATOS PERSONALES DE UN ALUMNO");
            System.out.println("\t4. INTRODUCIR NOTAS DE UNA ASIGNATURA Y EVALUACION A TODOS LOS MATRICULADOS");
            System.out.println("\t5. LISTADO DE ALUMNOS DE UN GRUPO. DATOS PERSONALES");
            System.out.println("\t6. LISTADO DE ALUMNOS MATRICULADOS EN UNA ASIGNATURA");
            System.out.println("\t7. LISTADO DE BOLETINES DE NOTAS DE UNA EVALUACION Y CURSO");
            System.out.println("\t0. VUELTA AL MENU PRINCIPAL");
            System.out.print("\n\t   Opcion seleccionada: ");

            try
            {
                submenu = sc.nextInt();
            }

            catch (Exception e)
            {
                System.out.printf(ERROR_MENU, 7);
                sc.next();
                continue;
            }

            switch (submenu)
            {
                case 0: break;
                case 1: // new student
                {
                    Alumno alu = new Alumno();

                    try
                    {
                        lista.put(alu.pideDatos(sc), alu);
                    }

                    catch (Exception e)
                    {
                        System.out.println(e.getMessage());
                    }

                    System.out.println("El alumno ha sido dado de alta correctamente.");

                    break;
                }
                case 2: // remove student
                {
                    try
                    {
                        lista.remove(getPKey(persona, "No puedo darlo de baja.", sc));
                    }

                    catch (Exception e)
                    {
                        System.out.println(e.getMessage());
                    }

                    System.out.println("El alumno ha sido dado de baja.");

                    break;
                }
                case 3: // print student data
                {
                    try
                    {
                        System.out.println(lista.get(getPKey(persona, "No puedo mostrarlo.", sc)).toString());
                    }

                    catch (Exception e)
                    {
                        System.out.println(e.getMessage());
                    }

                    break;
                }
                case 4: // grade students by subject
                {
                    String s;
                    int e;

                    try
                    {
                        s = TablasCursos.readSubject(null, sc);
                        e = Notas.readEv(sc);
                    }

                    catch (Exception ex)
                    {
                        System.out.println(ex.getMessage());
                        break;
                    }

                    for (Map.Entry<String, Persona> entry : lista.entrySet())
                    {
                        if (entry.getValue() instanceof Alumno)
                        {
                            Alumno a = Alumno.class.cast(entry.getValue());

                            if (a.getTmAsignaturasAlumno().containsKey(s))
                            {
                                System.out.print("Introduce la nota [1-10]: ");
                                int notas = sc.nextInt();

                                if (notas > 0 && notas < 11)
                                {
                                    a.getTmAsignaturasAlumno().get(s).getNotas()[e] = notas;
                                }

                                else
                                {
                                    System.out.println("La nota debe ser un número entre 1 y el 10 (ambos incluidos).");
                                }
                            }
                        }
                    }

                    break;
                }
                case 5: // print students by course
                {
                    String course;

                    try
                    {
                        course = TablasCursos.readCourse(sc);
                    }

                    catch (Exception e)
                    {
                        System.out.println(e.getMessage());
                        break;
                    }

                    for (Map.Entry<String, Persona> entry : lista.entrySet())
                    {
                        if (entry.getValue() instanceof Alumno)
                        {
                            Alumno a = Alumno.class.cast(entry.getValue());

                            if (a.getCurso().equals(course))
                            {
                                System.out.println(a.toString());
                            }
                        }
                    }

                    break;
                }
                case 6: // print students by subject
                {
                    String subject;

                    try
                    {
                        subject = TablasCursos.readSubject(null, sc);
                    }

                    catch (Exception e)
                    {
                        System.out.println(e.getMessage());
                        break;
                    }

                    for (Map.Entry<String, Persona> entry : lista.entrySet())
                    {
                        if (entry.getValue() instanceof Alumno)
                        {
                            Alumno a = Alumno.class.cast(entry.getValue());

                            if (a.getTmAsignaturasAlumno().containsKey(subject))
                            {
                                System.out.println(a.toString());
                            }
                        }
                    }

                    break;
                }
                case 7: // print students' grades by course
                {
                    String c;
                    int e;

                    try
                    {
                        c = TablasCursos.readCourse(sc);
                        e = Notas.readEv(sc);
                    }

                    catch (Exception ex)
                    {
                        System.out.println(ex.getMessage());
                        break;
                    }

                    System.out.println("\nCurso Académico: " + curso);
                    System.out.println("LISTADO DE BOLETINES DE NOTAS DEL CURSO: " + TablasCursos.tmCC.get(c));
                    System.out.println(Notas.getEv(e));

                    for (Map.Entry<String, Persona> entry : lista.entrySet())
                    {
                        if (entry.getValue() instanceof Alumno)
                        {
                            Alumno a = Alumno.class.cast(entry.getValue());

                            if (a.getCurso().equals(c))
                            {
                                System.out.println(a.boletinNotas(c, e));
                            }
                        }
                    }

                    break;
                }
                default:
                {
                    System.out.printf(ERROR_MENU, 7);
                }
            }
        }
    }

    void subMenuProfesores(Scanner sc)
    {
        int submenu = -1;

        while (submenu != 0)
        {
            String persona = "profesor";

            System.out.println("\n\n\n *************** MANTENIMIENTO DE PROFESORES ***************\n");
            System.out.println("\t1. ALTA DE UN PROFESOR");
            System.out.println("\t2. BAJA DE UN PROFESOR");
            System.out.println("\t3. CONSULTA DE DATOS PERSONALES DE UN PROFESOR");
            System.out.println("\t4. INTRODUCIR HORAS EXTRAORDINARIAS DE UN MES");
            System.out.println("\t5. LISTADO DE PROFESORES. DATOS PERSONALES");
            System.out.println("\t6. LISTADO DE PROFESORES. CLASES QUE IMPARTEN");
            System.out.println("\t7. LISTADO DE NOMINAS DE UN MES");
            System.out.println("\t8. MANTENIMIENTO DE ASIGNATURAS IMPARTIDAS POR CADA PROFESOR");
            System.out.println("\t0. VUELTA AL MENU PRINCIPAL");
            System.out.print("\n\t   Opcion seleccionada: ");

            try
            {
                submenu = sc.nextInt();
            }

            catch (Exception e)
            {
                System.out.printf(ERROR_MENU, 8);
                sc.next();
                continue;
            }

            switch (submenu)
            {
                case 0: break;
                case 1: // new professor
                {
                    Profesor profe = new Profesor();

                    try
                    {
                        lista.put(profe.pideDatos(sc), profe);
                    }

                    catch (Exception e)
                    {
                        System.out.println(e.getMessage());
                    }

                    System.out.println("El profesor ha sido dado de alta correctamente.");

                    break;
                }
                case 2: // remove professor
                {
                    try
                    {
                        lista.remove(getPKey(persona, "No puedo darlo de baja.", sc));
                    }

                    catch (Exception e)
                    {
                        System.out.println(e.getMessage());
                    }

                    System.out.println("El profesor ha sido dado de baja.");

                    break;
                }
                case 3: // print professor data
                {
                    try
                    {
                        System.out.println(lista.get(getPKey(persona, "No puedo mostrarlo.", sc)).toString());
                    }

                    catch (Exception e)
                    {
                        System.out.println(e.getMessage());
                    }

                    break;
                }
                case 4: // enter extra hours done by all professors by month
                {
                    int mes = readMonth("Horas extraordinarias realizadas por los profesores en el mes [1-12]: ", sc);

                    for (Map.Entry<String, Persona> entry : lista.entrySet())
                    {
                        if (entry.getValue() instanceof Profesor)
                        {
                            Profesor p = Profesor.class.cast(entry.getValue());

                            p.setHorasExtras(mes, -1);

                            while (p.getHorasExtras()[mes] == -1)
                            {
                                System.out.printf("\nNombre del profesor: %s, %s\n", p.getApellidos(), p.getNombre());
                                System.out.print("\nHoras realizadas (máximo 20): ");

                                try
                                {
                                    int horas = sc.nextInt();

                                    if (horas > -1 && horas < 21)
                                    {
                                        p.setHorasExtras(mes, horas);
                                    }

                                    else
                                    {
                                        throw new Exception("El número de horas extra debe ser entre 1 y 19 (ambos incluidos).");
                                    }
                                }

                                catch (InputMismatchException e)
                                {
                                    System.out.println("Solo puedes introducir números.");
                                    sc.next();
                                }

                                catch (Exception e)
                                {
                                    System.out.println(e.getMessage());
                                }
                            }
                        }
                    }

                    break;
                }
                case 5: // print every professors' data
                {
                    for (Map.Entry<String, Persona> entry : lista.entrySet())
                    {
                        if (entry.getValue() instanceof Profesor)
                        {
                            System.out.println(entry.getValue().toString());
                        }
                    }

                    break;
                }
                case 6: // print every professors' subjects
                {
                    for (Map.Entry<String, Persona> entry : lista.entrySet())
                    {
                        if (entry.getValue() instanceof Profesor)
                        {
                            System.out.println(Profesor.class.cast(entry.getValue()).imprimeAsignaturas());
                        }
                    }

                    break;
                }
                case 7: // print all payrolls by month
                {
                    int mes = readMonth("Nóminas del mes: ", sc);

                    for (Map.Entry<String, Persona> entry : lista.entrySet())
                    {
                        if (entry.getValue() instanceof Profesor)
                        {
                            System.out.println(Profesor.class.cast(entry.getValue()).imprimirNominas(mes));
                        }
                    }

                    break;
                }
                case 8: // add/remove a professor's subjects
                {
                    Profesor p;

                    try
                    {
                        p = Profesor.class.cast(lista.get(getPKey(persona, "No puedo mostrarlo.", sc)));
                    }

                    catch (Exception e)
                    {
                        System.out.println(e.getMessage());
                        break;
                    }

                    System.out.println("\n" + p.toString());

                    p.asignaturasProfesor(sc);

                    break;
                }
                default:
                {
                    System.out.printf(ERROR_MENU, 8);
                }
            }
        }
    }

    void go()
    {
        Scanner sc = new Scanner(System.in);

        System.out.print("Curso académico: ");
        curso = sc.nextLine();

        while (pagoPorHoraExtra == 0)
        {
            System.out.print("Importe Horas Extra: ");

            try
            {
                pagoPorHoraExtra = Double.parseDouble(sc.nextLine().replace(',','.'));
            }

            catch (Exception e)
            {
                System.out.println("El número introducido no es válido.");
            }
        }

        mainMenu(sc);

        sc.close();
    }

    public static String getCurso()
    {
        return curso;
    }

    public static void setCurso(String curso)
    {
        CentroEducativo.curso = curso;
    }

    public static double getPagoPorHoraExtra()
    {
        return pagoPorHoraExtra;
    }

    public static void setPagoPorHoraExtra(double pagoPorHoraExtra)
    {
        CentroEducativo.pagoPorHoraExtra = pagoPorHoraExtra;
    }
}
