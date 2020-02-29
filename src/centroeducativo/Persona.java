package centroeducativo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public abstract class Persona
{
    private String

        nombre,
        apellidos,
        calle,
        codigoPostal,
        ciudad,
        dni,
        fechaNacimiento;


    public String pideDatos(Scanner sc) throws Exception
    {
        sc.nextLine();

        System.out.print("Nombre: ");
        nombre = sc.nextLine();

        System.out.print("Apellidos: ");
        apellidos = sc.nextLine();

        StringBuilder key = new StringBuilder(apellidos).append(", ").append(nombre);

        if (CentroEducativo.lista.containsKey(key.toString()))
        {
            throw new Exception ("Este nombre ya existe. No puedo grabarlo.");
        }

        System.out.print("Calle: ");
        calle = sc.nextLine();

        System.out.print("Codigo Postal: ");
        codigoPostal = sc.nextLine();

        System.out.print("Ciudad: ");
        ciudad = sc.nextLine();

        while (dni == null)
        {
            System.out.print("DNI: ");

            try
            {
                dni = verificaDNI(sc.nextLine());
            }

            catch (Exception e)
            {
                System.out.println(e);
            }
        }

        while (fechaNacimiento == null)
        {
            System.out.print("Fecha de Nacimiento: ");

            try
            {
                fechaNacimiento = verificaFecha(sc.nextLine());
            }

            catch (Exception e)
            {
                System.out.println(e);
            }
        }

        return key.toString();
    }

    public static String verificaDNI(String dni) throws Exception
    {
        String[] letra = { "T","R","W","A","G","M","Y","F","P","D","X","B","N","J","Z","S","Q","V","H","L","C","K","E" };
        String valid;

        try
        {
            int number = Integer.parseInt(dni.substring(0,8));

            valid = number + letra[number%23];

            if (!dni.toUpperCase().equals(valid))
            {
                throw new Exception();
            }
        }

        catch (Exception e)
        {
            throw new Exception("El DNI introducido no tiene un formato válido.");
        }

        return valid;
    }

    public static String verificaFecha(String fecha) throws Exception
    {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/uuuu");

        try
        {
            return LocalDate.parse(fecha, format).toString();
        }

        catch (DateTimeParseException e)
        {
            throw new Exception("La Fecha introducida no tiene un formato valido.");
        }
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        sb.append("Nombre: ").append(getApellidos()).append(", ").append(getNombre())
          .append("\nDNI: ").append(getDni())
          .append("\nDireccion: ").append(getCalle())
          .append("\nCiudad: ").append(getCodigoPostal()).append(" ").append(getCiudad())
          .append("\nFecha de Nacimiento: ").append(getFechaNacimiento());

        return sb.append("/n").toString();
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public String getApellidos()
    {
        return apellidos;
    }

    public void setApellidos(String apellidos)
    {
        this.apellidos = apellidos;
    }

    public String getCalle()
    {
        return calle;
    }

    public void setCalle(String calle)
    {
        this.calle = calle;
    }

    public String getCodigoPostal()
    {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal)
    {
        this.codigoPostal = codigoPostal;
    }

    public String getCiudad()
    {
        return ciudad;
    }

    public void setCiudad(String ciudad)
    {
        this.ciudad = ciudad;
    }

    public String getDni()
    {
        return dni;
    }

    public void setDni(String dni)
    {
        this.dni = dni;
    }

    public String getFechaNacimiento()
    {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento)
    {
        this.fechaNacimiento = fechaNacimiento;
    }
}
