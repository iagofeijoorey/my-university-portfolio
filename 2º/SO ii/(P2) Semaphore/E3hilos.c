/*
     EJERCICIO OPTATIVO 3
     Autores: Laura Antelo e Iago Feijóo
*/
#include <stdio.h>
#include <stdlib.h>
#include <fcntl.h>
#include <pthread.h>
#include <semaphore.h>
#include <unistd.h>
#include <time.h>

#define N 8
#define ITERACIONES 20

typedef struct {
    int buffer[N];
    int tope;
} datos;

// Definimos los semáforos que vamos a usar
sem_t *mutex, *empty, *full;

datos shared_data;

//Funciones del productor
void *productor(void *arg);
int produce_item();
void insert_item(int item, datos *shared_data);

//Funciones del consumidor
void *consumidor(void *arg);
int remove_item(datos *shared_data);
void consume_item(int item, datos *shared_data);

int main() {
    pthread_t hiloProductor, hiloConsumidor;

    //eliminamos los semáforos antes de crearlo (buena práctica)
    sem_unlink("mutex");
    sem_unlink("empty");
    sem_unlink("full");

    //Creamos los semáforos
    mutex = sem_open("mutex", O_CREAT | O_EXCL, 0644, 1);
    empty = sem_open("empty", O_CREAT | O_EXCL, 0644, N);
    full = sem_open("full", O_CREAT | O_EXCL, 0644, 0);

    shared_data.tope = 0;
    srand(time(NULL));

    // Generamos los hilos con sus respectivas funciones
    pthread_create(&hiloProductor, NULL, productor, (void *)&shared_data);
    pthread_create(&hiloConsumidor, NULL, consumidor, (void *)&shared_data);

    // Esperamos a que terminen los hilos
    pthread_join(hiloProductor, NULL);
    pthread_join(hiloConsumidor, NULL);

    // Destruir los semáforos
    if (sem_destroy(mutex) == -1) {
        perror("Error al destruir el semáforo mutex");
        exit(EXIT_FAILURE);
    }

    if (sem_destroy(empty) == -1) {
        perror("Error al destruir el semáforo empty");
        exit(EXIT_FAILURE);
    }

    if (sem_destroy(full) == -1) {
        perror("Error al destruir el semáforo full");
        exit(EXIT_FAILURE);
    }

    return 0;
}

//PRODUCTOR
void *productor(void *arg) {
    for(int i = 0; i < ITERACIONES; i++) {
        sleep(rand() % 4);

        int item_shared = produce_item();

        sem_wait(empty);
        sem_wait(mutex);

        insert_item(item_shared, (datos *)arg);
        
        printf("Buffer productor: [ ");
        for (int j = 0; j < ((datos *)arg)->tope; j++) {
            printf("%d ", ((datos *)arg)->buffer[j]);
        }
        printf("]\n\n");

        sem_post(mutex);
        sem_post(full);
    }

    pthread_exit(NULL);
}

int produce_item() {
    return (rand() % 10) + 1;
}

void insert_item(int item, datos *shared_data) {
    //sleep(rand() % 4);
    shared_data->buffer[shared_data->tope] = item;
    shared_data->tope++;
    //sleep(rand() % 4);
    printf("El productor produjo el item: %d\n", item);
}

//CONSUMIDOR
void *consumidor(void *arg) {
    datos *shared_data = (datos *)arg;

    for(int i = 0; i < ITERACIONES; i++) {
        sleep(rand() % 4);
        
        sem_wait(full);
        sem_wait(mutex);

        int item_shared = remove_item(shared_data);

        consume_item(item_shared, shared_data);

        printf("Buffer consumidor: [ ");
        for (int j = 0; j < shared_data->tope; j++) {
            printf("%d ", shared_data->buffer[j]);
        }
        printf("]\n\n");

        sem_post(mutex);
        sem_post(empty);
    }

    pthread_exit(NULL);
}

int remove_item(datos *shared_data) {
    int item;

    int b = shared_data->tope - 1;
    //sleep(rand() % 4);
    item = shared_data->buffer[b];
    (shared_data->tope)--; // Decrementa el contador para eliminar el elemento del buffer
    //sleep(rand() % 4);

    return item;
}

void consume_item(int item, datos *shared_data) {
    printf("El consumidor consumió el item: %d\n", item);

    printf("Suma: %d ", item);
    for (int i = 0; i < shared_data->tope; i++) {
        printf("+ %d ", shared_data->buffer[i]);
        item += shared_data->buffer[i];
    }

    printf("\nLa suma del buffer al item es: %d\n", item);
}
