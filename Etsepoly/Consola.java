/* Esta interfaz debe declarar la funcionalidad de imprimir mensajes
y de pedir datos. De esta forma, para mostrar mensajes al usuario no podrá haber
llamadas al método System.out.println a lo largo del código del programa, salvo en
el método de la clase que implementa el siguiente método:
• public void consola.imprimir(String mensaje)
donde mensaje es el mensaje que se desea mostrar al usuario. De la misma forma,
cada vez que se tenga que preguntar al usuario por un dato, se utilizará el siguiente
método:
• public String consola.leer(String descripcion)
donde descripcion es el mensaje que se le muestra al usuario antes de esperar a
que introduzca los datos. Por ejemplo, consola.leer("Introduce nombre: ") debería
de imprimir por pantalla "Introduce nombre: ", leer con Scanner lo que indique el
usuario y devolver los datos introducidos como un String.
Se proporcionará al menos una implementación de este interfaz, es decir, una
clase que implemente los métodos leer e imprimir y que se llamará ConsolaNormal.
En esta implementación se imprimirá usando System.out y se leerá utilizando, por
ejemplo, la clase Scanner de Java (o la que se prefiera).
Finalmente, una de los atributos de la clase Juego será un atributo estático del tipo
ConsolaNormal para que los métodos de imprimir y leer se puedan invocar desde
cualquier clase del programa sin tener que instanciar continuamente dicha clase.*/

public interface Consola {

    void imprimir(String mensaje);
    String leer(String descripcion);
}
