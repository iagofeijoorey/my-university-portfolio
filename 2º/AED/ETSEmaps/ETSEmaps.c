#include <stdio.h>
#include <stdlib.h>
#include "grafo.h"
#include "string.h"
#include "math.h"


#define LEN 30
#define MAXLINEA 300



typedef struct
{
    int verticeP;
    char tipo;
}DatoP;

///FUNCIONES PRIVADAS DEL PROGRAMA DE PRUEBA DE GRAFOS
////////////////////////////////////////////////////

void _printMatrix(int V, double matrix[][V])
{
    int i,j;
    for(i=0;i<V;i++){
        for(j=0;j<V;j++){
            if (matrix[i][j]==INFINITY)
                printf("%10s","INF");
            else
                printf("%10.2f",matrix[i][j]);
        }
        printf("\n");
    }
}                                                                    //no REVISADA

void _imprimir_camino(int numVert, DatoP MatrizP[][numVert], int origen, int destino, tipovertice *vector)
{

    if (origen != destino && MatrizP[origen][destino].tipo != 'd')
    {
        _imprimir_camino( numVert, MatrizP,  origen,  MatrizP[origen][destino].verticeP, vector);
    }
    switch (MatrizP[origen][destino].tipo)
    {
        case 'c':
            printf(" --> ");
            break;
        case 'a':
            printf(" ==> ");
            break;
    }

    printf("%s", vector[destino].nombrePoblacion);

}      //no REVISADA

                            //no REVISADA
                            //no REVISADA

void _nuevo_arco_priv(grafo *G, char tipo, char* nombrePoblasao1, char* nombrePoblasao2, float distanciaArco)
{
    tipovertice v1;
    tipovertice v2;


    ///Vértice origen del arco
    strcpy(v1.nombrePoblacion, nombrePoblasao1);
    if (!existe_vertice(*G, v1))
    {
        printf("El vertice 1: %s no existe en el grafo\n", v1.nombrePoblacion);
        return;
    }

    ///Vértice destino del arco
    strcpy(v2.nombrePoblacion, nombrePoblasao2);
    if (!existe_vertice(*G, v2))
    {
        printf("El vertice 2: %s no existe en el grafo\n", v2.nombrePoblacion);
        return;
    }

    ///Creación del arco
    if (!checkDistancia(*G, posicion(*G, v1), posicion(*G, v2), tipo))
    {
        crear_arco(G, posicion(*G, v1), posicion(*G, v2), tipo, distanciaArco);
    }

}   //no REVISADA




/// FUNCIONES DEL PROGRAMA DE PRUEBA DE GRAFOS
////////////////////////////////////////////////

//Opción a del menú, introducir un vertice en el grafo                                                                  REVISADA
void introducir_vertice(grafo *G) {
    tipovertice v1;

    // Obtener el nombre del vértice del usuario
    printf("Introduce el nombre del vertice que deseas anhadir: ");
    scanf("%s", v1.nombrePoblacion);

    if (existe_vertice(*G, v1)) printf("Ese vertice ya esta en el grafo\n");
    else {
        insertar_vertice(G, v1);
        printf("El vertice '%s' ha sido anhadido correctamente al grafo.\n", v1.nombrePoblacion);
    }
}


//Opción b del menú, eliminar un vértice del grafo                                                                      REVISADA
void eliminar_vertice(grafo *G) {
    tipovertice v1;

    // Obtener el nombre del vértice del usuario
    printf("Introduce el nombre del vertice que deseas eliminar: ");
    scanf("%s", v1.nombrePoblacion);

    if (existe_vertice(*G, v1)){
        borrar_vertice(G, v1);
        printf("El vertice '%s' ha sido eliminado correctamente del grafo.\n", v1.nombrePoblacion);
    }
    else printf("Ese vertice no existe en el grafo\n");
}


//Opción c del menú, crear una relación entre dos vértices                                                              REVISADA
void nuevo_arco(grafo *G, char tipo) {
    tipovertice v1, v2;
    float distanciaArco = 0;

    //Insertamos una nueva relación pidiendo los datos al usuario controlando que existan los vértices
    printf("Nueva relacion vertice1-->vertice2\n");

    //Vértice origen del arco
    printf("Introduce vertice origen: ");
    scanf("%s", v1.nombrePoblacion);
    if (!existe_vertice(*G, v1)) {
        printf("El vertice %s no existe en el grafo\n", v1.nombrePoblacion);
        return;
    }

    //Vértice destino del arco
    printf("Introduce vertice destino: ");
    scanf("%s", v2.nombrePoblacion);
    if (!existe_vertice(*G, v2)) {
        printf("El vertice %s no existe en el grafo\n", v2.nombrePoblacion);
        return;
    }


    ///Distancia entre vertices del arco
    printf("Introduce distancia entre vertice origen y vertice destino: ");
    if (scanf("%f", &distanciaArco) != 1 || distanciaArco <= 1) { //Comprobamos que el scanf ha tenido exito y que la distancia sea mayor que 0.
        printf("Distancia invalida. Debe ser un numero positivo mayor que cero.\n");
        return;
    }

    ///Creación del arco
    if (!checkDistancia(*G, posicion(*G, v1), posicion(*G, v2), tipo)) {
        crear_arco(G, posicion(*G, v1), posicion(*G, v2), tipo, distanciaArco);
        printf("Arco creado correctamente entre %s y %s con distancia %f.\n", v1.nombrePoblacion, v2.nombrePoblacion, distanciaArco);
    } else {
        printf("Ya existe un arco entre %s y %s con el mismo tipo.\n", v1.nombrePoblacion, v2.nombrePoblacion);
    }
}


//Opción d del menú, eliminar una relación entre dos vértices                                                           REVISADA
void eliminar_arco(grafo *G, char tipo) {
    tipovertice v1, v2;

    //Eliminamos una relación pidiendo los datos al usuario controlando que existan los vértices
    printf("Eliminar relacion vertice1-->vertice2\n");

    //Vértice origen del arco
    printf("Introduce vertice origen: ");
    scanf("%s", v1.nombrePoblacion);
    if (!existe_vertice(*G, v1)) {
        printf("El vertice %s no existe en el grafo\n", v1.nombrePoblacion);
        return;
    }
    //Vértice destino del arco
    printf("Introduce vertice destino: ");
    scanf("%s", v2.nombrePoblacion);
    if (!existe_vertice(*G, v2)) {
        printf("El vertice %s no existe en el grafo\n", v2.nombrePoblacion);
        return;
    }

    ///Eliminación del arco
    if (checkDistancia(*G, posicion(*G, v1), posicion(*G, v2), tipo)) {
        borrar_arco(G, posicion(*G, v1), posicion(*G, v2), tipo);
        printf("Arco eliminado correctamente entre %s y %s.\n", v1.nombrePoblacion, v2.nombrePoblacion);
    } else {
        printf("No existe un arco del tipo especificado entre %s y %s.\n", v1.nombrePoblacion, v2.nombrePoblacion);
    }
}


//Opción i del menú, imprimir el grafo                                                                                  REVISADA
void imprimir_grafo(grafo G) {
    /*
     * Función que imprime el grafo utilizando num_vertices para saber cuántos vértices tiene
     * y array_vertices para recuperar el vector de vértices y recorrerlo
     */

    ///Variables
    tipovertice *VECTOR; //Para almacenar el vector de vértices del grafo
    int N; //número de vértices del grafo

    //Para recorrerla, simplemente vamos a recorrer la matriz de adyacencia
    N = num_vertices(G);
    VECTOR = array_vertices(G);

    int i = 0, j = 0;

    ///Código función
    printf("El grafo actual es:\n");

    //Recorrer los vértices
    for (i = 0; i < N; i++) {
        printf("Vertice(%d): %s\n", i+1, VECTOR[i]);

        //Recorrer sus arcos
        for (j = 0; j < N; j++) {
            float distanciaCarretera = checkDistancia(G, i, j, 'c');
            float distanciAutopista = checkDistancia(G, i, j, 'a');

            if (distanciaCarretera != 0 && distanciAutopista != 0) {
                printf("\t%s-->%s ~> %0.1f Km. (c)\n"
                       "\t%s-->%s ~> %0.1f Km. (a)\n", VECTOR[i], VECTOR[j], distanciaCarretera, VECTOR[i], VECTOR[j], distanciAutopista);
            } else if (distanciAutopista != 0) {
                printf("\t%s-->%s ~> %0.1f Km. (a)\n", VECTOR[i], VECTOR[j], distanciAutopista);
            } else if (distanciaCarretera != 0) {
            printf("\t%s-->%s ~> %0.1f Km. (a)\n", VECTOR[i], VECTOR[j], distanciaCarretera);
        }
        }
    }
}



/// FUNCIONES QUE HAY QUE IMPLEMENTAR
////////////////////////////////////////

//Warshal                                                                                                               no REVISADA
void floydWarshall(grafo G, char tipo)
{
    //Declaramos variables
    double tol = 1e-9;

    int numVert = num_vertices(G);
    double distanciaC, distanciaA;
    double factorCarretera, factorAutopista;

    double MatrizD[numVert][numVert];
    DatoP MatrizP[numVert][numVert];

    tipovertice *vecVert = array_vertices(G);

    //Factores para el cálculo de distancia o tiempo o dinero
    switch (tipo) {
        case 'a': case 'A':
            factorCarretera = 1;
            factorAutopista = 1;
            break;
        case 'b': case 'B':
            factorCarretera = (double) 1.0/70;
            factorAutopista = (double) 1.0/120;
            break;
        case 'c': case 'C':
            factorCarretera = 0.01;
            factorAutopista = 0.07;
            break;
        default:
            return;
    }

    //Inicializacion de las matrices: Forma antigua
    /*/Recorro los valores de la matriz
    for(int num1 = 0; num1<numVert; num1++)
    {
        for(int num2 = 0; num2<numVert; num2 ++)
        {
            //Si diagonal
            if(num1 == num2)
            {
                MatrizD[num1][num2] = 0;

                MatrizP[num1][num2].verticeP = 0;
                MatrizP[num1][num2].tipo = 'd';
            }
                //si no es diagonal
            else
            {
                distanciaC = checkDistancia(G, num1, num2, 'c');
                distanciaA = checkDistancia(G, num1, num2, 'a');
                switch (tipo)
                {
                    case 'a': case 'A':
                        //codigo por distancia
                        //La distancia es la que es
                        break;

                    case 'b': case 'B':
                        //codigo por tiempo
                        distanciaC = distanciaC/70;     //Dividimos la distancia por la velocidad media por autopista (120km/h) y nos da el tiempo del tramo
                        distanciaA = distanciaA/120;    //Dividimos la distancia por la velocidad media por carretera (70km/h) y nos da el tiempo del tramo
                        break;

                    case 'c': case 'C':
                        //codigo por precio
                        distanciaA = distanciaA*0.07;     //Mulitiplicamos la distancia por el precio medio por km por autopista (0.07) y nos da el precio del tramo
                        distanciaC = distanciaC*0.01;     //Mulitiplicamos la distancia por el precio medio por km por carretera (0.01) y nos da el precio del tramo
                        break;
                }

                //Si hay un arco por carretera
                if(distanciaC != 0 && distanciaA>distanciaC)
                {
                    MatrizD[num1][num2] = distanciaC;

                    MatrizP[num1][num2].verticeP = num1;
                    MatrizP[num1][num2].tipo = 'c';
                }
                //Si hay un arco por autopista
                if(distanciaA != 0 && distanciaA<distanciaC)
                {
                    MatrizD[num1][num2] = distanciaA;

                    MatrizP[num1][num2].verticeP = num1;
                    MatrizP[num1][num2].tipo = 'a';
                }
                //Si no existe arco ni por carretera ni autopista
                if(distanciaA == 0 && distanciaC == 0)
                {
                    //Matriz D tiene distancia 0
                    MatrizD[num1][num2] = INFINITY;

                    //Matriz P no tiene vertice por el que pasar
                    MatrizP[num1][num2].verticeP = 0;
                    MatrizP[num1][num2].tipo = 'n';

                }
            }
        }
    }


    //_printMatrix(numVert, MatrizD);*/

    //Inicializacion de las matrices: Forma revisada
    for (int i=0 ; i<numVert ; i++) {
        for (int j = 0; j < numVert; j++) {

            distanciaC = checkDistancia(G, posicion(G, vecVert[i]), posicion(G, vecVert[j]), 'c') * factorCarretera;
            distanciaA = checkDistancia(G, posicion(G, vecVert[i]), posicion(G, vecVert[j]), 'a') * factorAutopista;

            if (i == j) {
                MatrizD[i][j] = 0;
                MatrizP[i][j].verticeP = 0;
                MatrizP[i][j].tipo = ' ';
            }else {
                if (distanciaC == 0 && distanciaA == 0) {
                    MatrizD[i][j] = INFINITY;
                    MatrizP[i][j].verticeP = 0;
                    MatrizP[i][j].tipo = ' ';
                } else {
                    if ((distanciaC == 0 || distanciaA < distanciaC) && distanciaA != 0) {
                        MatrizD[i][j] = distanciaA;
                        MatrizP[i][j].verticeP = i;
                        MatrizP[i][j].tipo = 'a';
                    }
                    else if ((distanciaA == 0 || distanciaA > distanciaC)) {
                        MatrizD[i][j] = distanciaC;
                        MatrizP[i][j].verticeP = i;
                        MatrizP[i][j].tipo = 'c';
                    }
                }
            }
        }
    }

    for(int k=0; k<numVert; k++)//Analizamos matriz Dk
    {
        for(int i=0;i<numVert;i++)//arco ik
        {
            for(int j = 0; j < numVert; j++)//arco kj
            {
                if(MatrizD[i][j] > MatrizD[i][k] + MatrizD[k][j])
                {
                    MatrizD[i][j] = MatrizD[i][k] + MatrizD[k][j];
                    MatrizP[i][j] = MatrizP[k][j];
                }
            }
        }
    }

    //_printMatrix(numVert, MatrizD);

    tipovertice v1, v2;
    printf("Entre que ciudades pretendes buscar la ruta?\n");
    printf("Ciudad 1: \n");
    scanf("%s", v1.nombrePoblacion);
    if (!existe_vertice(G, v1)) {
        printf("El vertice %s no existe en el grafo\n", v1.nombrePoblacion);
        return;
    }

    printf("Ciudad 2: \n");
    scanf("%s", v2.nombrePoblacion);
    if (!existe_vertice(G, v2)) {
        printf("El vertice %s no existe en el grafo\n", v2.nombrePoblacion);
        return;
    }

    int pos1 = posicion(G, v1);
    int pos2 = posicion(G, v2);

    if(tipo == 'a') printf("\nA distancia total desde %s ata %s e de %.2f kms\n", v1.nombrePoblacion, v2.nombrePoblacion, MatrizD[pos1][pos2]);
    else if(tipo == 'b') printf("\nO tempo total desde %s ata %s e de %.2f horas\n", v1.nombrePoblacion, v2.nombrePoblacion, MatrizD[pos1][pos2]);
    else if(tipo == 'c') printf("\nO custe economico total desde %s ata %s e de %.2f Euros\n", v1.nombrePoblacion, v2.nombrePoblacion, MatrizD[pos1][pos2]);

    _imprimir_camino(numVert, MatrizP, pos1,  pos2, array_vertices(G));
}


//Prim                                                                                                                  no REVISADA
void prim(grafo G)
{
    int N = num_vertices(G), Selected[N], numArcos = 0, vx, vy;
    float distanciaMin = 0, distanciaTotal = 0,  tiempoC = 0, tiempoA = 0, tiempoT = 0;
    tipovertice *vecVert = array_vertices(G);
    char tipo;

    // Inicialización de conjunto de vértices seleccionados
    for (int i = 0; i < N; i++)
    {
        Selected[i] = 0;
    }

    // Iniciamos el algoritmo seleccionando el primer vértice
    Selected[0] = 1;

    while (numArcos < N - 1)
    {
        distanciaMin = INFINITY;
        vx = 0;
        vy = 0;

        // Busco el arco x-y con valor mínimo, con Selected(vx)=1, Selected(vy)=0
        for (int i = 0; i < N; i++)
        {
            if (Selected[i] == 1)
            {
                for (int j = 0; j < N; j++)
                {
                    tiempoC = checkDistancia(G, i, j, 'c')/70;
                    tiempoA = checkDistancia(G, i, j, 'a')/120;

                    //Antiguo
                    /*/Si no existe arco ni por carretera ni autopista la velocidad es 0
                    if(tiempoA == 0 && tiempoC == 0)
                    {
                        distanciaMin = -1;
                        tipo = '#';
                        //printf("No hay arco\n");
                    }
                        //Si es mas rapido por carretera
                    else if(tiempoC != 0 && tiempoA>tiempoC)
                    {
                        tipo = 'c';
                        distanciaMin = tiempoC;
                        //printf("Carretera mas rapido\n");
                    }
                        //Si es mas rapido por autopista
                    else if(tiempoA != 0 && tiempoA<tiempoC)
                    {
                        tipo = 'a';
                        distanciaMin = tiempoA;
                        //printf("Autopista mas rapido\n");
                    }

                    //////////////

                    if (Selected[j] != 1 && distanciaMin != 0)
                    {
                        if (minimo > distanciaMin)
                        {
                            minimo = distanciaMin;
                            vx = i;
                            vy = j;
                        }
                    }*/

                    //Corregido
                    if ((tiempoC || tiempoA) && Selected[j] != 1) {
                        if ((!tiempoC || tiempoA < tiempoC) && tiempoA) {
                            if (distanciaMin > tiempoA) {
                                distanciaMin = tiempoA;
                                vx = i;
                                vy = j;
                                tipo = '=';
                            }
                        } else if ((!tiempoA || tiempoA > tiempoC)) {
                            if (distanciaMin > tiempoC) {
                                distanciaMin = tiempoC;
                                vx = i;
                                vy = j;
                                tipo = '-';
                            }
                        }
                    }
                }
            }
        }

        // vx-vy es el arco con valor mínimo que añade vy al conjunto Selected
        Selected[vy] = 1;
        numArcos++;

        // Imprimir VECTOR(x)VECTOR(y): A(x,y)

        switch (tipo)
        {
            case 'a':
                tipo = '=';
                break;
            case 'c':
                tipo = '-';
                break;
        }
        printf(" %10s %c%c %-10s:  %10.2f horas\n", vecVert[vx].nombrePoblacion, tipo, tipo, vecVert[vy].nombrePoblacion, distanciaMin);


        // DistanciaTotal=distanciaTotal+A(x,y)
        distanciaTotal += distanciaMin;

    }
    printf("\n   Tempo da arbore de expansion de custe mifnimo = %.2f", distanciaTotal);
}


//Funciones de lectura del archivo                                                                                      REVISADA
void leer_archivo(grafo *G, int nparam, char **args) {
    ///Declaramos las variables
    char linea[MAXLINEA];    // variable temporal para almacenar una linea completa del archivo
    char ci1[LEN] = "", ci2[LEN] = "";
    char tipo = ' ';
    float valor;
    FILE *fichero;


    ///Comprobamos que el número de parametros introduido es correcto.
    if (nparam < 3 || strcmp(args[1], "-f") != 0) {
        //Si no es correcto abrimos el archivo por defecto.
        printf("\nNo hay documentos para abrir. Abriendo \"ETSEmaps.txt\" por defecto.\n");
        fichero = fopen("ETSEmaps.txt", "r");
    } else {
        //Si lo son abrimos el archivo cuyo nombre está almacenado en args[2].
        fichero = fopen(args[2], "r");
    }

    ///Comprobamos que el archivo abierto existe. Si no existe terminamos la función
    if (fichero == NULL) {
        printf("\nError al abrir el archivo.\n");
        return;
    }

    printf("\nLeyendo el archivo.\n");

    // Leer ciudades
    fgets(linea, MAXLINEA, fichero);
    char *token = strtok(linea, ";");
    while (token != NULL) {
        // Verificar si hay texto después del punto y coma
        if (strspn(token, " \t\n") != strlen(token)) {
            // Si hay texto, introducir el vértice
            tipovertice v1;

            strcpy(v1.nombrePoblacion, token);
            if (existe_vertice(*G, v1))
                printf("Ese vertice ya esta en el grafo\n");
            else
                insertar_vertice(G, v1);
        }
        token = strtok(NULL, ";");
    }

    // Leer arcos
    while (fgets(linea, MAXLINEA, fichero) != NULL) {
        sscanf(linea, " %[^-=]%c%[^;];%f", ci1, &tipo, ci2, &valor);
        switch (tipo) {
            case '-': // Carretera
                _nuevo_arco_priv(G, 'c', ci1, ci2, valor);
                break;
            case '=': // Autopista
                _nuevo_arco_priv(G, 'a', ci1, ci2, valor);
                break;
            default:
                break;
        }
    }

    printf("\nSe han guardado los datos en el grafo de ciudades.\n");
    fclose(fichero);
}


//Guardar archivo en el documento de texto                                                                              REVISADA
void guardar_archivo (grafo G, int nparam , char ** args )
{
    ///Declaramos las variables
    double tol = 1e-9;

    char nombrefichero[50];
    FILE *fichero;
    int i, j;
    char auxString[25];
    float distanciaArco;

    tipovertice *tipovert = array_vertices(G);

    ///Comprobamos que el número de parametros introduido es correcto.
    if (nparam < 3 || strcmp(args[1], "-f") != 0) {
        //Si no es correcto abrimos el archivo por defecto.
        printf("\nNo hay documentos para guardar la base de datos. Guardando en \"ETSEmaps.txt\" por defecto.\n");
        fichero = fopen("ETSEmapsSAVE.txt", "w+");
        strcpy(nombrefichero, "ETSEmapsSAVE.txt");
    } else {
        //Si lo son abrimos el archivo cuyo nombre está almacenado en args[2].
        fichero = fopen(args[2], "w+");
        ///Comprobamos que el archivo abierto existe. Si no existe guardamos en ETSEmaps.txt por defecto
        if (fichero == NULL) {
            printf("\nNo hay documentos para guardar la base de datos. Guardando en \"ETSEmaps.txt\" por defecto.\n");
            fichero = fopen("ETSEmapsSAVE.txt", "r");
            strcpy(nombrefichero, "ETSEmapsSAVE.txt");

        }
        else strcpy(nombrefichero, args[2]);
    }

    ///Código Función
    printf("\nComprobando fichero %s donde guardar la base de datos...", nombrefichero);

    //Escribimos primera linea -> Vertices
    for(i = 0; i <  num_vertices(G); i++)
    {
        fprintf(fichero,"%s;" ,tipovert[i].nombrePoblacion);
    }
    fprintf(fichero,"\n");

    // Escribir las distancias entre los vértices en el archivo
    for (i = 0; i < num_vertices(G); i++) {
        for (j = i + 1; j < num_vertices(G); j++) {
            distanciaArco = checkDistancia(G, i, j, 'c');
            if (distanciaArco > tol) {
                snprintf(auxString, sizeof(auxString), "%.1f", distanciaArco);
                fprintf(fichero, "%s-%s;%.1f\n", tipovert[i].nombrePoblacion, tipovert[j].nombrePoblacion, distanciaArco);
            }
            distanciaArco = checkDistancia(G, i, j, 'a');
            if (distanciaArco > tol) {
                snprintf(auxString, sizeof(auxString), "%.1f", distanciaArco);
                fprintf(fichero, "%s=%s;%.1f\n", tipovert[i].nombrePoblacion, tipovert[j].nombrePoblacion, distanciaArco);
            }
        }
    }

    ///Cierro el archivo
    printf("\n\nSe ha guardado la base de datos en %s.\n\n", nombrefichero);
    fclose(fichero);
}






///////Cosas para hacer:
/*
 *      Modifcar ambos algoritmos ---------------->
 *          -> Prim               ~~~~>
 *          -> Warshal            ~~~~>
 *
 *      Modificar los strcat de guardararchivo ---> Hecho
 *
 *      checkear todas las funciones ------------->
 */