package centroeducativo;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Profesor extends Persona
{
    private double sueldoBase = 0, tipoIRPF = 0;
    private int[] horasExtras = new int[12];
    private String cuentaIBAN = "";
    private TreeMap<String, String> tmAsignaturas = new TreeMap<String, String>();

    public String pideDatos(Scanner sc) throws Exception
    {
        String key = super.pideDatos(sc);

        while (sueldoBase == 0)
        {
            System.out.print("Sueldo Base: ");

            try
            {
                sueldoBase = Double.parseDouble(sc.nextLine().replace(',','.'));
            }

            catch (Exception e)
            {
                System.out.println("El número introducido no es válido.");
            }
        }

        while (tipoIRPF == 0)
        {
            System.out.print("Tipo de IRPF: ");

            try
            {
                tipoIRPF = Double.parseDouble(sc.nextLine().replace(',','.'));
            }

            catch (Exception e)
            {
                System.out.println("El número introducido no es válido.");
            }
        }

        while (cuentaIBAN == "")
        {
            System.out.print("IBAN: ");

            try
            {
                cuentaIBAN = Cuenta.filtroCuenta(sc.nextLine());
            }

            catch (Exception e)
            {
                System.out.println(e.getMessage());
            }
        }

        return key;
    }

    public void asignaturasProfesor(Scanner sc)
    {
        int menu = -1;
        boolean show = true;

        while (menu != 0)
        {
            if (show)
            {
                System.out.println(imprimeAsignaturas());
            }

            System.out.println("\n1. Añadir Asignatura.");
            System.out.println("2. Quitar Asignatura.");
            System.out.println("3. Ayuda: mostrar asignaturas.");
            System.out.println("0. Salir.");

            try
            {
                menu = sc.nextInt();
            }

            catch (Exception e)
            {
                System.out.println("La opción debe ser un número entre 0 y 3.");
                continue;
            }

            switch (menu)
            {
                case 0: break;
                case 1:
                {
                    String s;

                    try
                    {
                        s = TablasCursos.readSubject(null, sc);
                    }

                    catch (Exception e)
                    {
                        System.out.println(e.getMessage());
                        show = false;
                        continue;
                    }

                    tmAsignaturas.put(s, TablasCursos.tmCCASIGNA.get(s));
                    show = true;

                    break;
                }
                case 2:
                {
                    String s;

                    try
                    {
                        s = TablasCursos.readSubject(null, sc);
                    }

                    catch (Exception e)
                    {
                        System.out.println(e.getMessage());
                        show = false;
                        continue;
                    }

                    tmAsignaturas.remove(s);
                    show = true;

                    break;
                }
                case 3:
                {
                    System.out.println("\n" + TablasCursos.subjectsToString());
                    show = false;

                    break;
                }
                default:
                {
                    System.out.println("La opción debe ser un número entre 0 y 3.");
                }
            }
        }
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder(super.toString());
        NumberFormat f = new DecimalFormat("#.00");

        sb.append("\nCuenta IBAN: ").append(cuentaIBAN)
          .append("(").append(Cuenta.tmEEEE.get(cuentaIBAN).substring(4, 8))
          .append(Cuenta.tmEEEESSSS.get(cuentaIBAN).substring(4, 12)).append(")")
          .append("\nSueldo Base: ").append(f.format(sueldoBase))
          .append("\ntipo IRPF: ").append(f.format(tipoIRPF));

        return sb.append("/n").toString();
    }

    public String imprimeAsignaturas()
    {
        StringBuilder sb = new StringBuilder("\tASIGNATURAS QUE IMPARTE");

        if (tmAsignaturas.isEmpty())
        {
            sb.append("\n\nNo hay asignaturas.");
        }

        else
        {
            for (Map.Entry<String, String> entry : tmAsignaturas.entrySet())
            {
                sb.append("\n\n").append(entry.getKey()).append(": ").append(entry.getValue());
            }
        }

        return sb.append("\n").toString();
    }

    private double calcularImporteHorasExtras(int mes)
    {
        return horasExtras[mes] * CentroEducativo.getPagoPorHoraExtra();
    }

    private double calcularSueldoBruto(int mes)
    {
        return sueldoBase + calcularImporteHorasExtras(mes);
    }

    private double calcularRetencionesIrpf(int mes)
    {
        return (calcularSueldoBruto(mes) * tipoIRPF) / 100;
    }

    private double calcularSueldo(int mes)
    {
        return calcularSueldoBruto(mes) - calcularRetencionesIrpf(mes);
    }

    public String imprimirNominas(int mes)
    {
        StringBuilder sb = new StringBuilder();
        NumberFormat f = new DecimalFormat("#.00");

        sb.append("Curso Academico: ").append(CentroEducativo.getCurso())
          .append("\nNómina del mes de ").append(Month.of(mes + 1).getDisplayName(TextStyle.FULL, Locale.getDefault()))
          .append("\nNombre: ").append(super.getApellidos()).append(", ").append(super.getNombre())
          .append("\nDNI: ").append(super.getDni())
          .append("\nCuenta IBAN: ").append(cuentaIBAN)
          .append("(").append(Cuenta.tmEEEE.get(cuentaIBAN).substring(4, 8))
          .append(Cuenta.tmEEEESSSS.get(cuentaIBAN).substring(4, 12)).append(")")
          .append("\nSueldo Base: ").append(f.format(sueldoBase))
          .append("\nHoras Extra: ").append(horasExtras[mes])
          .append("\nTipo de IRPF: ").append(f.format(tipoIRPF))
          .append("\nSueldo Bruto: ").append(f.format(calcularSueldoBruto(mes)))
          .append("\nRetención por IRPF: ").append(f.format(calcularRetencionesIrpf(mes)))
          .append("\nSueldo Neto: ").append(f.format(calcularSueldo(mes)));

        return sb.append("/n").toString();
    }

    public double getSueldoBase()
    {
        return sueldoBase;
    }

    public void setSueldoBase(double sueldoBase)
    {
        this.sueldoBase = sueldoBase;
    }

    public double getTipoIRPF()
    {
        return tipoIRPF;
    }

    public void setTipoIRPF(double tipoIRPF)
    {
        this.tipoIRPF = tipoIRPF;
    }

    public int[] getHorasExtras()
    {
        return horasExtras;
    }

    public void setHorasExtras(int mes, int horasExtras)
    {
        this.horasExtras[mes] = horasExtras;
    }

    public String getCuentaIBAN()
    {
        return cuentaIBAN;
    }

    public void setCuentaIBAN(String cuentaIBAN)
    {
        this.cuentaIBAN = cuentaIBAN;
    }

    public TreeMap<String, String> getTmAsignaturas()
    {
        return tmAsignaturas;
    }

    public void setTmAsignaturas(TreeMap<String, String> tmAsignaturas)
    {
        this.tmAsignaturas = tmAsignaturas;
    }
}
