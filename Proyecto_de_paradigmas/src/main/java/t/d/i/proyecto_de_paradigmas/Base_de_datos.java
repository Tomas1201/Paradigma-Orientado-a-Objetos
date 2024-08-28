
package t.d.i.proyecto_de_paradigmas;

import java.io.FileNotFoundException;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

import java.util.Scanner;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.nio.file.Paths;

/**
 * @author Tomas1201
 */
public class Base_de_datos {
    static Scanner sc = new Scanner(System.in);
    private int usuarioAC = 0;
    private JSONObject usuarioA = new JSONObject();
    private static JSONArray Usuarios = new JSONArray();
    private final String rutaBase = Paths.get("").toAbsolutePath().toString();
    private final String archivo = rutaBase + "/src/main/resources/Usuarios.json";

    public JSONObject getUsuarioA() {
        return usuarioA;
    }


    public void setUsuarioAC(int usuarioAC) {
        this.usuarioAC = usuarioAC;
    }

    public void borrarCarrito() {
        JSONArray re = (JSONArray) usuarioA.get("Carrito");
        re.clear();
        usuarioA.put("Carrito", re);
        Usuarios.set(usuarioAC, usuarioA);
        actualizarBase();
    }

    public void actualizarBase() {
        try {
            PrintWriter pw = new PrintWriter(archivo);
            pw.close();
            FileWriter Base = new FileWriter(archivo);
            Base.write(Usuarios.toJSONString());
            Base.flush();
        } catch (IOException e) {
            System.out.println("Ocurrio un fallo al actualizar la base de datos");
        }
    }

    public void MostrarCarrito() {
        System.out.println("Mi carrito:");
        JSONArray mi = (JSONArray) usuarioA.get("Carrito");
        for (int i = 0; i < mi.size(); i++) {
            JSONObject pro = (JSONObject) mi.get(i);
            System.out.print(pro.get("tipo").toString() + "    ");
            System.out.print("Precio: $" + pro.get("precio").toString() + "c/u    ");
            System.out.println("Cantidad: " + pro.get("cantidad").toString() + "    ");
        }
    }

    public void MontoTotal() {
        JSONArray mi = (JSONArray) usuarioA.get("Carrito");
        int monto = 0;
        for (int i = 0; i < mi.size(); i++) {
            JSONObject pro = (JSONObject) mi.get(i);
            int n1 = Integer.parseInt(pro.get("precio").toString());
            int n2 = Integer.parseInt(pro.get("cantidad").toString());
            monto += n1 * n2;
        }
        System.out.println("Monto a pagar: $" + monto);
    }

    public boolean iniciarS() {
        System.out.print("Ingrese nombre de usuario: ");
        String nombre = sc.nextLine();
        System.out.print("Ingrese su contraseña: ");
        String contraseña = sc.nextLine();
        if (usuario_existe(nombre, contraseña)) {

            usuarioA = (JSONObject) Usuarios.get((usuarioAC));

            System.out.println("Se inicio sesion correctamente");
            return true;
        } else {
            System.out.println("Usuario o contraseña incorrecta");
            return false;
        }

    }

    public boolean usuario_existe(String nombre, String Contraseña) {
        for (int i = 0; i < Usuarios.size(); i++) {
            if (Chequeador(nombre, "nombre", i)) {
                if (Chequeador(Contraseña, "contraseña", i)) {
                    usuarioAC = i;
                    return true;
                }
            }
        }
        return false;
    }

    public void Inicializador() throws FileNotFoundException {


        JSONParser parser = new JSONParser();
        try {
            Object JSON = parser.parse(new FileReader(archivo));

            Usuarios = (JSONArray) JSON;

        } catch (FileNotFoundException e) {
            System.out.print("fallo1");//manejo de error
        } catch (IOException | ParseException e) {
            System.out.print("fallo2");//manejo de error//manejo de error
        }
    }

    public boolean Chequeador(String dato, String key, int i) {
        JSONObject Usuario = (JSONObject) Usuarios.get(i);
        return dato.equals(Usuario.get(key));
    }

    public void Registrar() {
        System.out.println("Ingrese su nombre de usuario");
        String nombre = sc.nextLine();
        String correo;
        boolean r = false;
        for (int i = 0; i < Usuarios.size(); i++) {
            if (Chequeador(nombre, "nombre", i)) {
                r = true;
                break;
            }

        }
        if (r) {
            System.out.println("Lo sentimos ya existe una cuenta con ese nombre");
        } else {
            boolean d = true;
            while (d) {
                System.out.println("Ingrese su correo electronico");
                correo = sc.nextLine();
                boolean pepe = validarCorreo(correo);

                if (pepe) {
                    for (int i = 0; i < Usuarios.size(); i++) {
                        if (Chequeador(correo, "correo", i)) {
                            r = true;

                            break;
                        }
                    }
                    if (r) {
                        System.out.println("Ya existe una cuenta con ese correo");
                        r = false;
                    } else {
                        CreadorDeCuentainador(nombre, correo);
                        d = false;

                    }
                } else {
                    System.out.println("Correo invalido");
                }

            }

        }
        sc.nextLine();
    }

    public void AgregarCarrito(JSONObject a) {
        JSONArray re = (JSONArray) usuarioA.get("Carrito");
        if (!re.isEmpty()) {
            for (int i = 0; i < re.size(); i++) {
                JSONObject rep = (JSONObject) re.get(i);
                //Si se encuentra un producto con el mismo nombre le suma las cantidades sin mas
                if (a.get("tipo").toString().equals(rep.get("tipo").toString())) {
                    //cantidad de productos en el catalogo
                    int b = Integer.parseInt(a.get("cantidad").toString());
                    //saca el producto en el carrito
                    JSONObject pe = (JSONObject) re.get(i);
                    //cantidad de productos en mi carrito
                    int q = Integer.parseInt(pe.get("cantidad").toString());
                    q += b;
                    //remplazo la cantidad por la suma de la nueva compra
                    pe.replace("cantidad", q);
                    re.set(i, pe);

                } else {
                    re.add(a);
                    break;
                }
            }
        } else {
            re.add(a);
        }
        usuarioA.put("Carrito", re);
        Usuarios.set(usuarioAC, usuarioA);
        actualizarBase();


    }

    public static boolean validarCorreo(String correo) {
        if (correo == null || correo.isEmpty()) {
            return false;
        }

        // Verificar que solo haya un arroba
        int contadorArrobas = 0;
        for (char c : correo.toCharArray()) {
            if (c == '@') {
                contadorArrobas++;
            }
        }
        if (contadorArrobas != 1) {
            return false;
        }

        // Verificar que termine en .com
        return correo.toLowerCase().endsWith(".com");
    }

    public void CreadorDeCuentainador(String nombre, String Correo) {
        System.out.println("Ingrese una contraseña:");
        String c = sc.nextLine();
        JSONArray pepe = new JSONArray();
        JSONObject p = new JSONObject();
        p.put("nombre", nombre);
        p.put("correo", Correo);
        p.put("Carrito", pepe);
        p.put("contraseña", c);
        Usuarios.add(p);
        actualizarBase();
        System.out.println("Cuenta agregada exitosamente");

    }

}