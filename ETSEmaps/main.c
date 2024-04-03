#include <stdio.h>
#include <stdlib.h>
#include "ETSEmaps.h"


/// Predeclaración de funciones
//////////////////////////////////

void crearUnionFunc(grafo G);

void floydWarshallFunc(grafo G);

void eliminarArcoFunc(grafo G);

/// Main
//////////////////////////////////

int main(int argc, char** argv) {
    //Grafo de números enteros
    grafo G; //grafo
    char opcion, opcion2;
    char tipo;

    ///Nada mas empezar con el programa creo el grafo con el que vamos a trabajar
    crear_grafo(&G);
    ///Se comprueba si se ha introducido como argumento un archivo con el estado inicial del grafo. Si hay un estado inicial, se introducen los datos en nuestro grafo.
    leer_archivo(&G, argc, argv);


    ///Menu de opciones modificado para añadir las nuevas opciones
    do
    {
        printf("\n\n=============================================================="
               "\n\nOPCIONES DE EDICION DE MAPA:\n"
               "\ta. Insertar nuevo vertice\n"
               "\tb. Eliminar vertice\n"
               "\tc. Crear arco\n"
               "\td. Eliminar arco\n"
               "\nOPCIONES DE BUSQUEDA DE RUTA:\n"
               "\te. Encontrar rutas\n"
               "\tf. Minima infraestructura para conectar dos ciudades\n"
               "\nOPCIONES DE GUARDADO DE MAPA:\n"
               "\tg. Guardar grafo en archivo\n"
               "\ti. Imprimir grafo\n"
               "\ns. Salir\n\n"
               "==============================================================\n\n"
               " Opcion: ");
        scanf(" %c", &opcion);

        switch (opcion)
        {
            //Crear poblacion
            case 'a':case'A':
                introducir_vertice(&G);
                break;

                //Eliminar poblacion
            case 'b':case 'B':
                eliminar_vertice(&G);
                break;

                //Crear union entre poblaciones
            case 'c': case 'C':
                crearUnionFunc(G);
                break;

                //Borrar union entre poblaciones
            case 'd': case 'D':
                eliminarArcoFunc(G);
                break;

                //Encontrar rutas --> fLOYDwARSHAL
            case 'e':
                floydWarshallFunc(G);
                break;

                //Encontrar rutas --> PRIM
            case 'f':
                printf("Algoritmo de Prim\n");
                prim(G);
                break;

                //guardar grafo
            case 'g': case 'G':
                guardar_archivo(G, argc, argv);
                break;

                //Imprimir grafo
            case 'i': case 'I':
                printf("Guardando en archivo\n");
                imprimir_grafo(G);
                break;

                //Salir
            case 's': case 'S':
                break;

                //Default
            default:
                printf("Opción equivocada\n");
                break;
        }
    } while (opcion != 's' && opcion != 'S');



    //Al salir, liberamos la memoria del TAD, lo destruimos
    //guardar_archivo(G, argc, argv);

    guardar_archivo(G, argc, argv);
    borrar_grafo(&G);

    return (EXIT_SUCCESS);
}




/// Declaracion funciones
////////////////////////////

void crearUnionFunc(grafo G){
    //Variables
    char tipo;

    printf("\n\nComo deseas que esten unidas las poblaciones: por carretera o por autopista? (c/a)");
    do
    {
        scanf(" %c", &tipo);
        // Verificar si el tipo de arco ingresado es válido
        if (tipo != 'c' && tipo != 'C' && tipo != 'a' && tipo != 'A') {
            printf("Tipo de arco invalido. Debe ser 'c' (carretera) o 'a' (autopista). Vuelva a introducir el tipo de union.\n");
        }
    }while(tipo != 'c' && tipo != 'C' && tipo != 'a' && tipo != 'A');

    nuevo_arco(&G, tipo);
}

void eliminarArcoFunc(grafo G){
    //Variables
    char tipo;

    //Código funcion
    printf("\n\nDe que tipo es la union entre las poblaciones que deseas eliminar: por carretera o por autopista? (c/a)");
    do
    {
        scanf(" %c", &tipo);
        if(tipo != 'c' && tipo != 'C' && tipo != 'a' && tipo != 'A')
        {
            printf("\nTipo de union no valida, intentelo de nuevo: ");
        }
    }while(tipo != 'c' && tipo != 'C' && tipo != 'a' && tipo != 'A');

    eliminar_arco(&G, tipo);
}

void floydWarshallFunc(grafo G){
    //Variables
    char opcion;

    //Código función
    printf("\nQue tipo de ruta quieres encontrar?\n"
           "\ta) Mas corta\n"
           "\tb) Mas rapida\n"
           "\tc) Mas economica\n");
    do
    {
        scanf(" %c", &opcion);
        if(opcion != 'a' && opcion != 'b' && opcion != 'c' && opcion != 'A' && opcion != 'B' && opcion != 'C')
        {
            printf("\nTipo de ruta no valida, intentelo de nuevo: ");
        }
    }while(opcion != 'a' && opcion != 'b' && opcion != 'c' && opcion != 'A' && opcion != 'B' && opcion != 'C');

    floydWarshall(G, opcion);
}


