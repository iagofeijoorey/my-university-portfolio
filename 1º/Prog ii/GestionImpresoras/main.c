#include <stdio.h>
#include "stdlib.h"
#include "string.h"
#include "cola.h"
#include "lista.h"

#define MAXLINEA 50
#define LEN 20
#define PLANTA 8


/// Predefinir funciones
/////////////////////////////
//ListaImpresoras
void imprimir_lista(TLISTA *lstImpresoras);
void eliminar_impresora(TLISTA lstImpresoras);
void anhadir_impresora(TLISTA lstImpresoras);

//ColaImpresion
void enviarTrabajoAImprimir(TLISTA lstImpresoras);
void verListadosImpresion(TLISTA *lstImpresoras);
void recuperarTodosElementosCola(TCOLA colaImpresion);
void buscar_impresoras_poca_carga(TLISTA *lstImpresoras);

//Lectrura escritura de archivos
void guardar_archivo(TLISTA *lstImpresoras, int nparam, char **args);
void leer_archivo(TLISTA *lstImpresoras, int nparam, char **args);




/// Main
///////////////

void main(int argc, char *argv[]) {

    //Variables
    char opcion = ' ';
    TLISTA lstImpresoras = NULL;
    crearLista(&lstImpresoras);


    //Abrimos el archivo (si es que hay)
    leer_archivo(&lstImpresoras, argc, argv);


    imprimir_lista(&lstImpresoras);
    //Código del main
    do
    {
        printf("\n\n=============================================================="
               "\n\nOPCIONES DE MODIFICACION DE INFRAESTRUCTURAS DE IMPRESORAS:\n"
               "\tz) Ver lista impresoras\n"
               "\ta) Eliminar impresora\n"
               "\tb) A%cadir impresora\n"
               "\n\nOPCIONES DE EDICION DE TRABAJOS:\n"
               "\tc) Enviar trabajo\n"
               "\td) Imprimir listado pendiente de impresi%cn\n"
               "\te) Buscar impresora\n"
               "\ns) Salir\n\n"
               "==============================================================\n\n"
               " Opci%cn: ", 164 , 162, 162 );

        scanf( " %c", &opcion );
        printf("\"OPCION ~ ");
        switch(opcion) {
            case 'z': case 'Z':
                //opcion 2
                printf("Ver lista impresoras\": \n");
                imprimir_lista(&lstImpresoras);
                break;


            case 'a': case 'A':
                //opcion 1
                printf("Eliminar impresora\": \n");
                eliminar_impresora(lstImpresoras);
                break;

            case 'b': case 'B':
                //opcion 2
                printf("A%cadir impresora\": \n", 164);
                anhadir_impresora(lstImpresoras);
                break;


            case 'c': case 'C':
                //opcion 3
                printf("Enviar trabajo\": \n");
                enviarTrabajoAImprimir(lstImpresoras);
                break;

            case 'd': case 'D':
                //opcion 3
                printf("Imprimir listado pendiente de impresi%cn\": \n", 162);
                verListadosImpresion(&lstImpresoras);
                break;

            case 'e': case 'E':
                //opcion 3
                printf("Buscar impresora con poca carga\": \n");
                buscar_impresoras_poca_carga(&lstImpresoras);
                break;

            case 's': case 'S':
                printf("Terminando programa...\n\n"
                       "==============================================================\n");
                break;

            default:
                //Default
                printf("Default\": Volviendo al menu.\n");
                break;
        }
    } while ( opcion != 's' && opcion != 'S' );

    //Guardamos el archivo con las impresoras actualizadas
    guardar_archivo(&lstImpresoras, argc, argv);

    //Ahora podemos eliminar todas las listas y colas que teniamos creadas
    destruirLista(&lstImpresoras);
}




/// MIS FUNCIONES DE LISTA IMPRESORAS
///////////////////////////////////////////
///////////////////////////////////////////
///////////////////////////////////////////
void setElem(TIPOELEMENTOLISTA *e, char nombreDado[LEN], char marcaDada[6], char modeloDado[LEN], char plantaDada[PLANTA]){
    strcpy(e->nombre, nombreDado);
    strcpy(e->marca, marcaDada);
    strcpy(e->modelo, modeloDado);
    strcpy(e->ubicacion, plantaDada);
}
int checkElem(TIPOELEMENTOLISTA *e1, TIPOELEMENTOLISTA *e2){
    if((strcmp(e1->nombre, e2->nombre) == 0) &&
       (strcmp(e1->marca, e2->marca) == 0) &&
       (strcmp(e1->modelo, e2->modelo) == 0) &&
       (strcmp(e1->ubicacion, e2->ubicacion) == 0)) {
        return 1;
    }
    else {
        return 0;
    }
}
/////////////////////////////////////////////////
void imprimir_lista(TLISTA *lstImpresoras) {
    printf("\n\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n\n"
           "Lista de impresoras:\n");
    TPOSICION pos = primeroLista(*lstImpresoras);
    while (pos != finLista(*lstImpresoras)) {
        TIPOELEMENTOLISTA elem;
        recuperarElementoLista(*lstImpresoras, pos, &elem);
        printf("\t%s %s %s %s\n", elem.nombre, elem.marca, elem.modelo, elem.ubicacion);
        pos = siguienteLista(*lstImpresoras, pos);
    }
    printf("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
}
void anhadir_impresora(TLISTA lstImpresoras) {
    // Variable función
    TIPOELEMENTOLISTA impresoraAAnhadir;
    TPOSICION posiRecuperada = primeroLista(lstImpresoras);

    // Código función
    printf("\n%cQu%c nombre tiene la impresora que deseas a%cadir?\n",168 ,130, 164);
    scanf(" %19s", impresoraAAnhadir.nombre);

    printf("\n%cQu%c marca tiene la impresora que deseas a%cadir?\n",168 ,130, 164);
    scanf(" %6s", impresoraAAnhadir.marca);

    printf("\n%cQu%c modelo tiene la impresora que deseas a%cadir?\n",168 ,130, 164);
    scanf(" %19s", impresoraAAnhadir.modelo);

    printf("\n%cEn qu%c planta est%c la impresora que deseas a%cadir?\n (EJ FORMATO: \"PLANTA0\")\n\n",168 ,130, 160, 164);
    scanf(" %7s", impresoraAAnhadir.ubicacion);

    crearCola(&impresoraAAnhadir.colaImpresion);


    while (posiRecuperada != finLista(lstImpresoras)) {
        TIPOELEMENTOLISTA elemRecuperado;
        recuperarElementoLista(lstImpresoras, posiRecuperada, &elemRecuperado);

        if (checkElem(&impresoraAAnhadir, &elemRecuperado)) {
            printf("\nYa exist%ca la impresora que quer%cas a%cadir\n", 161, 161, 164);
            return;
        }

        posiRecuperada = siguienteLista(lstImpresoras, posiRecuperada);
    }




    //printf("Checkpoint INICIO FUNCION LISTA.c\n");

    insertarElementoLista(&lstImpresoras, finLista(lstImpresoras), impresoraAAnhadir);

    //printf("Checkpoint FINAL FUNCION LISTA.c\n");
}
void eliminar_impresora(TLISTA lstImpresoras) {
    // Variables de la función
    TIPOELEMENTOLISTA impresoraABuscar;
    TPOSICION posiRecuperada = primeroLista(lstImpresoras);

    // Código función
    if (!esListaVacia(lstImpresoras)) {
        printf("\nBuscando impresoras...\n");

        printf("\n%cQu%c nombre tiene la impresora que deseas eliminar?\n",168 ,130);
        scanf(" %19s", impresoraABuscar.nombre);

        printf("\n%cQu%c marca tiene la impresora que deseas eliminar?\n",168 ,130);
        scanf(" %6s", impresoraABuscar.marca);

        printf("\n%cQu%c modelo tiene la impresora que deseas eliminar?\n",168 ,130);
        scanf(" %19s", impresoraABuscar.modelo);

        printf("\n%cEn qu%c planta est%c la impresora que deseas eliminar?\n (EJ FORMATO: \"PLANTA0\")\n\n",168 ,130, 160);
        scanf(" %7s", impresoraABuscar.ubicacion);

        while (posiRecuperada != finLista(lstImpresoras)) {
            TIPOELEMENTOLISTA elemRecuperado;
            recuperarElementoLista(lstImpresoras, posiRecuperada, &elemRecuperado);

            if (checkElem(&impresoraABuscar, &elemRecuperado)) {
                suprimirElementoLista(&lstImpresoras, posiRecuperada);
                printf("\nSe ha eliminado la impresora deseada de la lista de impresoras\n");
                return;
            }
            else {
                posiRecuperada = siguienteLista(lstImpresoras, posiRecuperada);
            }
        }
        printf("\nLa impresora proporcionada no existe\n");
    } else {
        printf("\nNo hay impresoras en la lista de impresoras\n");
    }
}




///FUNCIONES COLAS IMPRESION
///////////////////////////////////////////
///////////////////////////////////////////
///////////////////////////////////////////
int checkElemCola(TIPOELEMENTOCOLA actual, TIPOELEMENTOCOLA comparar){
    if((strcmp(actual.nombreImpresion, comparar.nombreImpresion) == 0) &&
       (actual.numLineas == comparar.numLineas) &&
       (actual.tipoColor == comparar.tipoColor)) {
        return 1;
    }
    else {
        return 0;
    }
}
///////////////////////////////////////////
void enviarTrabajoAImprimir(TLISTA lstImpresoras)
{
    // Variable función
    TIPOELEMENTOCOLA trabajoAImprimir;
    TIPOELEMENTOLISTA impresoraABuscar;
    TPOSICION posiRecuperada = primeroLista(lstImpresoras);

    // Código función
    if (esListaVacia(lstImpresoras)) {
        printf("\nNo hay impresoras en la lista de impresoras\n");
        return;
    } else {
        printf("\n%cQu%c nombre tiene la impresora en la que deseas operar?\n",168 ,130);
        scanf(" %19s", impresoraABuscar.nombre);

        printf("\n%cQu%c marca tiene la impresora en la que deseas operar?\n",168 ,130);
        scanf(" %6s", impresoraABuscar.marca);

        printf("\n%cQu%c modelo tiene la impresora en la que deseas operar?\n",168 ,130);
        scanf(" %19s", impresoraABuscar.modelo);

        printf("\n%cEn qu%c planta est%c la impresora en la que deseas operar?\n (EJ FORMATO: \"PLANTA0\")\n\n",168 ,130, 160);
        scanf(" %7s", impresoraABuscar.ubicacion);

        while (posiRecuperada != finLista(lstImpresoras)) {
            TIPOELEMENTOLISTA elemRecuperado;
            recuperarElementoLista(lstImpresoras, posiRecuperada, &elemRecuperado);

            if (!checkElem(&impresoraABuscar, &elemRecuperado)){
                posiRecuperada = siguienteLista(lstImpresoras, posiRecuperada);
            }
            else {
                char numlineasString[5];

                printf("\n%cQu%c nombre tiene el documento que deseas imprimir?\n",168 ,130);
                scanf(" %29s", trabajoAImprimir.nombreImpresion);

                printf("\n%cCu%cntas lineas tiene el documento que deseas imprimir?\n",168 ,130);
                scanf(" %s", numlineasString);
                trabajoAImprimir.numLineas = strtol(numlineasString, NULL, 10);

                char color;
                printf("\n%cEn qu%c colores deseas imprimir el docuemnto? (Color -> c / Blanco\\Negro -> b) \n",168 ,130);
                scanf(" %c", &color);
                if(color != 'c' && color != 'b') trabajoAImprimir.tipoColor = 'b';
                else trabajoAImprimir.tipoColor = color;

                anadirElementoCola(&elemRecuperado.colaImpresion, trabajoAImprimir);

                printf("\nSe ha a%cadido el trabajo a la cola de impresi%cn de la impresora seleccionada\n",164 ,171);

                return;
            }
        }
        printf("\nLa impresora proporcionada no existe\n");
    }
}
void verListadosImpresion(TLISTA *lstImpresoras){
    printf("\n\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n\n"
           "Lista de impresoras:\n");
    TPOSICION pos = primeroLista(*lstImpresoras);
    while (pos != finLista(*lstImpresoras)) {
        TIPOELEMENTOLISTA elemLista;
        recuperarElementoLista(*lstImpresoras, pos, &elemLista);
        printf("\n\t=================================\n");
        printf("\n\tImpresora: %s %s\n", elemLista.nombre, elemLista.ubicacion);
        recuperarTodosElementosCola(elemLista.colaImpresion);

        pos = siguienteLista(*lstImpresoras, pos);
    }
    printf("\n\t=================================\n");
    printf("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
}
void recuperarTodosElementosCola(TCOLA colaImpresion){
    printf("\t");
    TCOLA colaCopia;
    TIPOELEMENTOCOLA elemActual;

    if(esColaVacia(colaImpresion)){
        printf("\n\tLa impresora no tiene trabajos\n\tpendientes de imprimir\n");
        return;
    }
    else{
        int posicion = 1;
        crearCola(&colaCopia);
        while(!esColaVacia(colaImpresion)){
            consultarPrimerElementoCola(colaImpresion, &elemActual);
            printf("\n\tElemento n%cmero %d en la cola:\n", 163, posicion);
            printf("\t~ Nombre Elemento: %s\n"
                   "\t~ Numero de Lineas: %i\n"
                   "\t~ Tipo Color: %c\n", elemActual.nombreImpresion, elemActual.numLineas, elemActual.tipoColor);
            anadirElementoCola(&colaCopia, elemActual);
            suprimirElementoCola(&colaImpresion);
            posicion++;
        }
        while(!esColaVacia(colaCopia)){
            consultarPrimerElementoCola(colaCopia, &elemActual);
            anadirElementoCola(&colaImpresion, elemActual);
            suprimirElementoCola(&colaCopia);
        }
        destruirCola(&colaCopia);
    }
}
void buscar_impresoras_poca_carga(TLISTA *lstImpresoras){
    printf("\n\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n\n");
    TPOSICION pos = primeroLista(*lstImpresoras);
    TIPOELEMENTOLISTA elem;
    recuperarElementoLista(*lstImpresoras, pos, &elem);
    int cargaMasBaja  = getNumElemCola(elem.colaImpresion);
    //printf("Carga inicial: (%d):\n", cargaMasBaja);

    while (pos != finLista(*lstImpresoras)) {
        recuperarElementoLista(*lstImpresoras, pos, &elem);

        //printf("La carga en esta it es: %d\n", getNumElemCola(elem.colaImpresion));

        if(getNumElemCola(elem.colaImpresion) < cargaMasBaja) cargaMasBaja = getNumElemCola(elem.colaImpresion);

        pos = siguienteLista(*lstImpresoras, pos);
    }

    printf("Lista de impresoras con menos carga (%d):\n", cargaMasBaja);
    pos = primeroLista(*lstImpresoras);

    while (pos != finLista(*lstImpresoras)) {
        recuperarElementoLista(lstImpresoras, pos, &elem);
        if(getNumElemCola(elem.colaImpresion) <= cargaMasBaja) printf("\t%s %s %s %s\n", elem.nombre, elem.marca, elem.modelo, elem.ubicacion);

        pos = siguienteLista(*lstImpresoras, pos);
    }
    printf("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
}





///FUNCIONES LECTURA Y ESCRITURA DE ARCHIVOS
///////////////////////////////////////////
///////////////////////////////////////////
///////////////////////////////////////////
void leer_archivo(TLISTA *lstImpresoras, int nparam, char **args) {
    // Declaramos las variables
    char linea[MAXLINEA];    // variable temporal para almacenar una linea completa del archivo
    FILE *fichero;

    // Comprobamos que el número de parametros introducido es correcto.
    if (nparam < 3 || strcmp(args[1], "-f") != 0) {
        // Si no es correcto abrimos el archivo por defecto.
        printf("\nNo hay documentos para abrir. Abriendo \"lstImpresoras.txt\" por defecto.\n");
        fichero = fopen("lstImpresoras.txt", "r");
    } else {
        // Si lo son abrimos el archivo cuyo nombre está almacenado en args[2].
        fichero = fopen(args[2], "r");
    }

    // Comprobamos que el archivo abierto existe. Si no existe terminamos la función.
    if (fichero == NULL) {
        printf("\nError al abrir el archivo.\n");
        return;
    }
    else {
        // Leemos el archivo línea por línea y almacenamos la información en la lista de impresoras.
        while (fgets(linea, sizeof(linea), fichero)) {
            TIPOELEMENTOLISTA impresora; // = malloc (sizeof(TIPOELEMENTOCOLA));
            sscanf(linea, " %s %s %s %s", impresora.nombre, impresora.marca, impresora.modelo, impresora.ubicacion);
            crearCola(&(impresora).colaImpresion);
            insertarElementoLista(lstImpresoras, finLista(*lstImpresoras), impresora);
            //free(impresora);
        }
        if(longitudLista(*lstImpresoras) < 1){
            ///Cierro el archivo
            printf("\n\nNo hay impresoras en archivo para a%cadir a la lista de impresoras.\n\n"
                   "==============================================================\n\n", 164);
        }
        else{
            printf("\n\nSe han guardado los datos en la lista de impresoras.\n\n"
                   "==============================================================\n\n");
        }

        fclose(fichero);
    }


}
void guardar_archivo( TLISTA *lstImpresoras, int nparam , char ** args )
{
    ///Declaramos las variables
    char nombrefichero[MAXLINEA];
    FILE *fichero;


    ///Comprobamos que el número de parametros introduido es correcto.
    if (nparam < 3 || strcmp(args[1], "-f") != 0) {
        //Si no es correcto abrimos el archivo por defecto.
        printf("\nNo hay documentos para guardar la lista de impresoras. Guardando en \"lstImpresorasSAVE.txt\" por defecto.\n");
        fichero = fopen("lstImpresorasSAVE.txt", "w+");
        strcpy(nombrefichero, "lstImpresorasSAVE.txt");
    } else {
        //Si lo son abrimos el archivo cuyo nombre está almacenado en args[2].
        fichero = fopen(args[2], "w+");
        ///Comprobamos que el archivo abierto existe. Si no existe guardamos en lstImpresoras.txt por defecto
        if (fichero == NULL) {
            printf("\nNo hay documentos para guardar la lista de impresoras. Guardando en \"lstImpresorasSAVE.txt\" por defecto.\n");
            fichero = fopen("lstImpresorasSAVE.txt", "r");
            strcpy(nombrefichero, "lstImpresorasSAVE.txt");

        }
        else strcpy(nombrefichero, args[2]);
    }

    ///Código Función
    printf("\nComprobando fichero %s donde guardar la lista de impresoras...\n", nombrefichero);

    if(longitudLista(*lstImpresoras) < 1){
        ///Cierro el archivo
        printf("\n\nNo hay impresoras en la lista de impresoras.\nCerrando el programa...\n\n"
               "==============================================================\n\n");
    }
    else{
        // Recorrer la lista e ir escribiendo los datos de cada impresora en el archivo
        TPOSICION p;
        for(p = primeroLista(*lstImpresoras); p < finLista(*lstImpresoras); siguienteLista(*lstImpresoras, p)){
            TIPOELEMENTOLISTA *impresora = malloc(sizeof(TIPOELEMENTOLISTA));
            recuperarElementoLista(*lstImpresoras, p, impresora);
            fprintf(fichero, "%s %s %s %s\n", impresora->nombre, impresora->marca, impresora->modelo, impresora->ubicacion);
            if(siguienteLista(*lstImpresoras, p) != NULL){
                p = siguienteLista(*lstImpresoras, p); // Avanzar a la siguiente posición
            }
            free(impresora);
        }

        ///Cierro el archivo
        printf("\n\nSe ha guardado la lista de impresoras en %s.\n\n"
               "==============================================================\n\n", nombrefichero);
    }

    //Cerramos el archivo
    fclose(fichero);
}