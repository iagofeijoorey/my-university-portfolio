#ifndef FUNCIONES_H
#define FUNCIONES_H

#include "grafo.h"

//FUNCIONES DEL PROGRAMA DE PRUEBA DE GRAFOS
//Opción a del menú, introducir un vertice en el grafo
void introducir_vertice(grafo *G);

//Opción b del menú, eliminar un vértice del grafo
void eliminar_vertice(grafo *G);

//Opción c del menú, crear una relación entre dos vértices
void nuevo_arco(grafo *G, char tipo);

//Opción d del menú, eliminar una relación entre dos vértices
void eliminar_arco(grafo *G, char tipo);

//Opción i del menú, imprimir el grafo
void imprimir_grafo(grafo G);

///Opcion e del menú, algoritmo de floydMarshal
void floydWarshall(grafo G, char tipo);

///Opcion
void prim(grafo G);


//////////////////////////////


///Funciones de lectura y escritura en archivos
void leer_archivo(grafo* G, int nparam , char ** args);
void guardar_archivo(grafo G, int nparam , char ** args);


#endif	/* FUNCIONES_H */

