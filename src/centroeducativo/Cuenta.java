package centroeducativo;

import java.math.BigInteger;
import java.util.TreeMap;

public class Cuenta
{
    static TreeMap<String, String> tmEEEE     = new TreeMap<String, String>();
    static TreeMap<String, String> tmEEEESSSS = new TreeMap<String, String>();

    private static String checkDigitIBAN(String nc)
    {
        StringBuilder checkDigit = new StringBuilder("ES");
        int digit = 98 - new BigInteger(nc.substring(4, 24) + "142800").mod(new BigInteger("97")).intValue();

        if (digit == 10)
        {
            checkDigit.append("00");
        }

        else if (digit < 10)
        {
            checkDigit.append("0").append(digit);
        }

        else
        {
            checkDigit.append(digit);
        }

        return checkDigit.toString();
    }

    private static String checkDigitBank(String nc)
    {
        StringBuilder checkDigit = new StringBuilder();
        final int[] f = { 1, 2, 4, 8, 5, 10, 9, 7, 3, 6 };
        int d = 0, c = 0, x;

        for (int i = 2, j = 4; i < 10; i++, j++)
        {
            d += f[i] * Character.getNumericValue(nc.charAt(j));
        }

        x = 11 - (d%11);

        d = x == 10 ? 1 : (x == 11 ? 0 : x);

        for (int i = 0, j = 14; i < 10; i++, j++)
        {
            c += f[i] * Character.getNumericValue(nc.charAt(j));
        }

        x = 11 - (c%11);

        c = x == 10 ? 1 : (x == 11 ? 0 : x);

        return checkDigit.append(d).append(c).toString();
    }

    public static String filtroCuenta(String nc) throws Exception
    {
        nc = nc.replace(" ", "");
        nc = nc.replace("-", "");

        if (nc.isEmpty() || !nc.substring(0, 2).equals("ES") || !nc.substring(2).matches("[0-9]{22}"))
        {
            throw new Exception("El IBAN no tiene un formato válido.");
        }

        if (!Cuenta.tmEEEE.containsKey(nc.substring(4, 8)))
        {
            throw new Exception("El código de banco no existe.");
        }

        if (!Cuenta.tmEEEESSSS.containsKey(nc.substring(4, 12)))
        {
            throw new Exception("El código de la sucursal no existe.");
        }

        if (!checkDigitIBAN(nc).equals(nc.substring(0, 4)))
        {
            throw new Exception("El dígito de control del IBAN no es correcto.");
        }

        if (!checkDigitBank(nc).equals(nc.substring(12, 14)))
        {
            throw new Exception("El dígito de control del banco no es correcto.");
        }

        return nc;
    }

    static void cargaEntidadesBancarias()
    {
        tmEEEE.put("2100", "Caixabank");
        tmEEEE.put("0081", "Banco Sabadell");
        tmEEEE.put("1465", "ING Bank");
        tmEEEE.put("2038", "Bankia");
        tmEEEE.put("0049", "Banco Santander");
    }

    static void cargaSucursalesBancarias()
    {
        tmEEEESSSS.put("21004231", "Elche Urbana 1");
        tmEEEESSSS.put("21004232", "Elche Urbana 2");
        tmEEEESSSS.put("21004233", "Elche Urbana 3");
        tmEEEESSSS.put("21004234", "Elche Urbana 4");
        tmEEEESSSS.put("21003894", "Elche Urbana 5");
        tmEEEESSSS.put("00816781", "Elche Urbana 1");
        tmEEEESSSS.put("00816782", "Elche Urbana 3");
        tmEEEESSSS.put("00816783", "Elche Urbana 3");
        tmEEEESSSS.put("00816784", "Elche Urbana 4");
        tmEEEESSSS.put("14654561", "Elche Urbana 1");
        tmEEEESSSS.put("14654562", "Elche Urbana 2");
        tmEEEESSSS.put("00811152", "Elche Urbana 1");
        tmEEEESSSS.put("00811153", "Elche Urbana 2");
        tmEEEESSSS.put("00811152", "Elche Urbana 3");
        tmEEEESSSS.put("20384441", "Elche Urbana 1");
        tmEEEESSSS.put("00492221", "Elche Urbana 1");
        tmEEEESSSS.put("00492222", "Elche Urbana 2");
        tmEEEESSSS.put("00491111", "Elche Urbana 1");
        tmEEEESSSS.put("00811205", "Elche A.Machado 49");
    }
}
