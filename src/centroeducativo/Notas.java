package centroeducativo;

import java.util.Scanner;

public class Notas
{
    private static String[] Ev = { "Primera", "Segunda", "Tercera", "Ordinaria", "Extraordinaria" };
    private int notas[] = new int[5];

    static int readEv(Scanner sc) throws Exception
    {
        System.out.print("Evaluación [1-5]: ");
        int eva;

        try
        {
            eva = sc.nextInt() - 1;

            if (eva < 0 || eva > 4)
            {
                throw new Exception ();
            }
        }

        catch (Exception e)
        {
            throw new Exception ("La evaluación debe ser un número entre el 1 y el 5 (ambos incluidos).");
        }

        return eva;
    }

    static String getEv(int eva)
    {
        return eva < 3 ? Ev[eva] + " evaluación." : "Evaluación " + Ev[eva];
    }

    public int[] getNotas()
    {
        return notas;
    }
}
