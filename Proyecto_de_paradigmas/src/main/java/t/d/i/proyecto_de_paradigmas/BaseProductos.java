package t.d.i.proyecto_de_paradigmas;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.Scanner;
import java.nio.file.Paths;

/**
 * @author Tomas1201
 */
public class BaseProductos {
    Scanner sc = new Scanner(System.in);
    JSONArray productos = new JSONArray();
    private final String rutaBase = Paths.get("").toAbsolutePath().toString();
    private final String fileName = rutaBase + "/src/main/resources/Productos.json";
    private int x = 0;

    public void agregarproducto(String tipo, int cantidad, int precio) {
        JSONObject Producto = new JSONObject();
        Producto.put("tipo", tipo);
        Producto.put("cantidad", cantidad);
        Producto.put("precio", precio);
        productos.add(Producto);
        actualizarBase();
    }

    //Borra el archivo y sobreescribe la nueva lista actualizada
    public void actualizarBase() {
        try {
            PrintWriter pw = new PrintWriter(fileName);
            pw.close();
            FileWriter Base = new FileWriter(fileName);
            Base.write(productos.toJSONString());
            Base.flush();
        } catch (IOException e) {
            System.out.println("Ocurrio un fallo al actualizar la base de datos");
        }
    }

    //Carga en las variables la lista en el archivo .JSON
    public void Inicializador() throws FileNotFoundException {
        JSONParser parser = new JSONParser();
        try {
            Object JSON = parser.parse(new FileReader(fileName));

            productos = (JSONArray) JSON;

        } catch (FileNotFoundException e) {
            System.out.print("fallo1");//manejo de error
        } catch (IOException | ParseException e) {
            System.out.print("fallo2");//manejo de error
        }
    }

    //Busca si hay un objeto con el nombre que se le pasa
    public JSONObject BuscarObjeto(String nombre) {
        JSONObject Objeto;
        JSONParser parser = new JSONParser();
        try {
            Object fileparser = parser.parse(new FileReader(fileName));
            JSONArray listaobjetos = (JSONArray) fileparser;
            for (int i = 0; i < listaobjetos.size(); i++) {
                Objeto = (JSONObject) listaobjetos.get(i);
                if (nombre.equals(Objeto.get("tipo"))) {
                    x = i;
                    return Objeto;

                }
            }
        } catch (IOException | ParseException e) {
            //manejo de error
        }
        return null;
    }


    public void MostrarLista() {
        for (int i = 0; i < productos.size(); i++) {
            JSONObject productom = (JSONObject) productos.get(i);
            System.out.print(productom.get("tipo").toString() + "    ");
            System.out.print("Precio: $" + productom.get("precio").toString() + "c/u    ");
            System.out.println("Cantidad: " + productom.get("cantidad").toString() + "    ");

        }


    }

    public boolean Chequeador(String dato, String key, int i) {
        JSONObject Usuario = (JSONObject) productos.get(i);
        return dato.equals(Usuario.get(key));
    }

    public JSONObject ComprarObjeto(String tipo) {
        boolean n = false;
        JSONObject o = BuscarObjeto(tipo);
        JSONObject co;
        for (int i = 0; i < productos.size(); i++) {
            if (Chequeador(tipo, "tipo", i)) {
                n = true;
                break;
            }

        }
        boolean g = true;
        while (true) {
            if (n) {

                System.out.println("Ingrese la cantidad que decea comprar: ");
                int c = sc.nextInt();

                int a = Integer.parseInt(o.get("cantidad").toString());
                if (c > a) {
                    System.out.println("Lo sentimos no contamos con la cantidad que solicita");
                    return null;
                } else if (c > 0) {
                    a -= c;
                    o.replace("cantidad", a);
                    productos.set(x, o);
                    actualizarBase();
                    co = (JSONObject) o.clone();
                    co.replace("cantidad", c);
                    g = false;
                    return co;
                } else {

                    return null;
                }

            } else {
                return null;

            }

        }

    }
}