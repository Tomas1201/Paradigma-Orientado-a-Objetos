package t.d.i.proyecto_de_paradigmas;

import java.util.Scanner;

/**
 * @author Tomas1201
 */
public class Menus {
    static Scanner sc = new Scanner(System.in);


    public int menu1() {
        int eleccion = -1;
        boolean o = true;
        System.out.println("              ╔══════════════════════════════════════════════════╗");
        System.out.println("              ║         Bienvenido al servicio de compra         ║");
        System.out.println("              ╚══════════════════════════════════════════════════╝");
        System.out.println("             1.Iniciar Sesion|2.Registrarse|3.Ver productos|4.salir");
        while (o) {
            System.out.println("ingrese su opción");
            try {
                eleccion = sc.nextInt();
                if (eleccion < 1 || eleccion > 4) {
                    System.out.println("Opcion invalida intente de nuevo");
                } else {
                    o = false;
                }
            } catch (Exception e) {
                System.out.println("Opcion invalida intente de nuevo");
                sc.nextLine();
            }
        }
        return eleccion;
    }

    public int menu2() {
        int eleccion = -1;
        boolean o = true;
        System.out.println("1.Hacer pedido|2.Ver catalogo|3.Mi carrito|4.Cerrar pedido|5.Salir");
        while (o) {
            System.out.println("ingrese su opción");
            try {
                eleccion = sc.nextInt();
                if (eleccion < 1 || eleccion > 5) {
                    System.out.println("Opcion invalida intente de nuevo");
                } else {
                    o = false;
                }
            } catch (Exception e) {
                System.out.println("Opción invalida intente de nuevo");
                sc.nextLine();
            }
        }
        return eleccion;
    }


    public int menu3() {
        boolean a = true;
        int eleccion = -1;
        while (a) {
            try {

                System.out.println("1.Elegir producto | 2.Finalizar compra |3.Cancelar compra|4.volver");
                System.out.println("Por favor elija la accion que desea realizar");
                eleccion = sc.nextInt();
                if (eleccion < 1 || eleccion > 4) {
                    System.out.println("Opción invalida");
                } else {
                    a = false;
                }
            } catch (Exception e) {
                System.out.println("Opción invalida");
                sc.nextLine();
            }
        }
        return eleccion;


    }
}
