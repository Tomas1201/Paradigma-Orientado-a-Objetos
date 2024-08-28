
package t.d.i.proyecto_de_paradigmas;

import org.json.simple.JSONObject;

import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Scanner;

/**
 * @author Tomas1201
 */
public class Proyecto_de_paradigmas {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        Menus menu = new Menus();
        Base_de_datos datos = new Base_de_datos();
        BaseProductos Productos = new BaseProductos();

        //Carga los datos del archivo JSON al objeto
        try {
            datos.Inicializador();
            Productos.Inicializador();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Proyecto_de_paradigmas.class.getName()).log(Level.SEVERE, null, ex);
        }
        boolean var = true;

        while (var) {
            int eleccion = menu.menu1();

            switch (eleccion) {
                case 1 -> {

                    boolean a = datos.iniciarS();
                    if (a) {


                        boolean var2 = true;
                        while (var2) {
                            System.out.println("Bienvenido " + datos.getUsuarioA().get("nombre").toString());
                            try {
                                int pepe = menu.menu2();


                                switch (pepe) {
                                    case 1 -> {
                                        Productos.MostrarLista();
                                        int b = menu.menu3();

                                        switch (b) {
                                            case 1 -> {
                                                System.out.println("ingrese el nombre del producto");
                                                JSONObject com = Productos.ComprarObjeto(sc.nextLine());
                                                if (com == null) {
                                                    System.out.println("Lo sentimos no contamos con ese producto");
                                                } else {
                                                    datos.AgregarCarrito(com);
                                                    System.out.println("Articulo agregado al carrito");
                                                }
                                            }
                                            case 2 -> {
                                                datos.MostrarCarrito();
                                                datos.MontoTotal();
                                                System.out.println("1.Confirmar |2.Cancelar");
                                                try {
                                                    int e = sc.nextInt();
                                                    switch (e) {
                                                        case 1 -> {
                                                            System.out.println("Muchas Gracias por su compra");
                                                            datos.borrarCarrito();
                                                        }
                                                        case 2 -> {

                                                        }
                                                        default -> System.out.println("Opción invalida");

                                                    }
                                                } catch (Exception e) {
                                                    System.out.println("Opcion invalida");
                                                }
                                            }

                                        }


                                    }
                                    case 2 -> Productos.MostrarLista();

                                    case 3 -> datos.MostrarCarrito();

                                    case 4 -> {
                                        datos.setUsuarioAC(-1);
                                        var2 = false;
                                    }
                                    case 5 -> {
                                        System.out.println("Hasta la proxima");
                                        var2 = false;
                                        var = false;
                                    }
                                }
                            } catch (Exception e) {
                                System.out.println("Opción invalida");
                                sc.nextLine();
                            }
                        }
                    }

                }

                case 2 -> datos.Registrar();

                case 3 -> Productos.MostrarLista();//Ver Catalogo

                case 4 -> {
                    System.out.println("Hasta la proxima");
                    var = false;
                }
                default -> System.out.print("Opción invalida");
            }
        }

    }
}
  
    

