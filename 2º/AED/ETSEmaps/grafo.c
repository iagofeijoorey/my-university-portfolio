#include "grafo.h"
#include "stdio.h"
#include "stdlib.h"
#include "string.h"

/////////////////////////////////////////////////////////// TIPOS DE DATOS

/*
 * Declaramos la estructura "verticeGrafo" que nos servirá para las matrices de distancias del grafo. Tiene dos tipos de datos:
 *      dAu: Distancia por autopista
 *      dCa: Distancia por carretera
 */
typedef struct {
    float dAu;
    float dCa;
}verticeGrafo;


// Estructura privada
struct tipografo {
    int N; //número de vértices del grafo
    tipovertice VERTICES[MAXVERTICES]; //vector de vértices
    verticeGrafo matrixAd[MAXVERTICES][MAXVERTICES]; /*matriz de adyacencia*/ ///--> cambiamos el dato de int a "verticeGrafo"
};

//////////////////////////////////////////////////////////////// FUNCIONES

//HAY QUE MODIFICAR ESTA FUNCIÓN SI SE CAMBIA EL TIPO DE DATO tipovertice
/* 
 * Esta función devuelve 0 si los dos nodos son iguales
 * -1 si V1 está antes de V2 o 1 en otro caso.
 */
int _comparar_vertices(tipovertice V1, tipovertice V2)
{
    return (strcmp(V1.nombrePoblacion, V2.nombrePoblacion)==0) ? 0 : (strcmp(V1.nombrePoblacion, V2.nombrePoblacion)>0) ? 1 : -1;
}


//Creación del grafo con 0 nodos
void crear_grafo(grafo *G) {
    *G = (struct tipografo*) malloc(sizeof (struct tipografo));
    (*G)->N = 0;
}


//Devuelve la posición del vértice Vert en el vector VERTICES del grafo G
//Si devuelve -1 es porque no encontró el vértice
int posicion(grafo G, tipovertice V) {
    int contador = 0;
    //comparo V con todos los vertices almacenados en VERTICES 
    while (contador < G->N) {
        //if (G->VERTICES[contador]==V)  //encontré la posicion de V
		if (_comparar_vertices(G->VERTICES[contador], V) == 0){
            return contador; 
        }
        contador++;
    }
    return -1;
}


//Devuelve 1 si el grafo G existe y 0 en caso contrario
int existe(grafo G) {
    return (G != NULL);
}


//Devuelve 1 si el vértice Vert existe en el grafo G
int existe_vertice(grafo G, tipovertice V) {
    return (posicion(G, V) >= 0);
}


//Inserta un vértice en el grafo, devuelve -1 si no ha podido insertarlo por estar el grafo lleno
///Modificamos la implementación dada para que genere vertice tanto para "dCa" como para "dAu"
int insertar_vertice(grafo *G, tipovertice Vert)
{
    int i;
    if ((*G)->N == MAXVERTICES)
    {
        // Se ha llegado al maximo numero de vertices
        return -1;
    }

    (*G)->N++;
    (*G)->VERTICES[((*G)->N) - 1] = Vert;
    for (i = 0; i < (*G)->N; i++)
    {
        ///Distancia carretera
        (*G)->matrixAd[i][((*G)->N) - 1].dCa = 0;
        (*G)->matrixAd[((*G)->N) - 1][i].dCa = 0;

        ///Distancia Autopista
        (*G)->matrixAd[i][((*G)->N) - 1].dAu = 0;
        (*G)->matrixAd[((*G)->N) - 1][i].dAu = 0;
    }
    return (*G)->N-1;
}


//Borra un vertice del grafo
///Modificamos la implementación dada para que genere vertice tanto para "dCa" como para "dAu"
void borrar_vertice(grafo *G, tipovertice Vert)
{
    int F, C, Posi, N = (*G)->N;
    Posi = posicion(*G, Vert);
    if(Posi == -1)
    {
        return;
    }

    //if (P >= 0 && P < (*G)->N) {
    for (F = Posi; F < N - 1; F++)
    {
        (*G)->VERTICES[F] = (*G)->VERTICES[F + 1];
    }
    for (C = Posi; C < N - 1; C++)
    {
        for (F = 0; F < N; F++)
        {
            ///Distancia carretera
            (*G)->matrixAd[F][C].dCa = (*G)->matrixAd[F][C + 1].dCa;

            ///Distancia autopista
            (*G)->matrixAd[F][C].dAu = (*G)->matrixAd[F][C + 1].dAu;
        }
    }
    for (F = Posi; F < N - 1; F++)
    {
        for (C = 0; C < N; C++)
        {
            ///Distancia carretera
            (*G)->matrixAd[F][C].dCa = (*G)->matrixAd[F][C + 1].dCa;

            ///Distancia autopista
            (*G)->matrixAd[F][C].dAu = (*G)->matrixAd[F][C + 1].dAu;
        }
    }
    (*G)->N--;
}


//Crea el arco de relación entre VERTICES(pos1) y VERTICES(pos2)
void crear_arco(grafo *G, int pos1, int pos2, char tipo, float dist) {  ///Añadimos como argumentos el tipo de distancia y la distancia del arco
    /* Añadimos un check del tipo
     *      Si tipo = c ---> La distancia se mete en dCa
     *      Si tipo = a ---> La distancia se mete en dAu
     */
    if(tipo == 'c' || tipo == 'C')
    {
        (*G)->matrixAd[pos1][pos2].dCa = dist;
        (*G)->matrixAd[pos2][pos1].dCa = dist; //Como el grafo no es dirigido se debe añadir en ambas posicion del grafo la distancia
    }
    else if(tipo == 'a' || tipo == 'A')
    {
        (*G)->matrixAd[pos1][pos2].dAu = dist;
        (*G)->matrixAd[pos2][pos1].dAu = dist;
    }
}


//Borra el arco de relación entre VERTICES(pos1) y VERTICES(pos2)
void borrar_arco(grafo *G, int pos1, int pos2, char tipo) {  ///Añadimos como argumentos el tipo de distancia
    /* Añadimos un check del tipo
     *      Si tipo = c ---> La distancia se mete en dCa
     *      Si tipo = a ---> La distancia se mete en dAu
     */
    if(tipo == 'c' || tipo == 'C')
    {
        (*G)->matrixAd[pos1][pos2].dCa = 0;
        (*G)->matrixAd[pos2][pos1].dCa = 0; //Como el grafo no es dirigido se debe eliminar de ambas posicion del grafo la distancia
    }
    else if(tipo == 'a' || tipo == 'A')
    {
        (*G)->matrixAd[pos1][pos2].dAu = 0;
        (*G)->matrixAd[pos2][pos1].dAu = 0;
    }
}


//Devuelve distancia si VERTICES(pos1) y VERTICES(pos2) son vértices adyacentes
///devuelve 0 si no son adyacentes
float checkDistancia(grafo G, int pos1, int pos2, char tipo)
{
    /* Añadimos un check del tipo
     *      Si tipo = c ---> Se devuelve al distancia almacenada en dCa
     *      Si tipo = a ---> Se devuelve al distancia almacenada en dAu
     */
    if(tipo == 'c' || tipo == 'C') {              ///Si se pide adyacencia por carretera
        return (G->matrixAd[pos1][pos2].dCa);
    }
    else if(tipo == 'a' || tipo == 'A') {         ///Si se pide adyacencia por autopista
        return (G->matrixAd[pos1][pos2].dAu);
    }else {
        return 0;
    }
}


//Destruye el grafo
void borrar_grafo(grafo *G) {
    free(*G);
    *G = NULL;
}


//Devuelve el número de vértices del grafo G
int num_vertices(grafo G) {
    return G->N;
}


//Devuelve el vector de vértices VERTICES del grafo G
tipovertice* array_vertices(grafo G) {
    return G->VERTICES;
}

