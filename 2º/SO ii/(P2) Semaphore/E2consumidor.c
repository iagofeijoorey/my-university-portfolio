/*
	CONSUMIDOR EJERCICIO 2
	Autores: Laura Antelo e Iago Feijóo
*/

#include <stdio.h>
#include <stdlib.h>
#include <sys/mman.h>
#include <fcntl.h>
#include <unistd.h>
#include <time.h>
#include <pthread.h>
#include "semaphore.h"

#define FILE_PATH "/P2_E1"
#define N 8
#define ITERACIONES 20

struct datos {
    int buffer[N];
    int tope;
};

int remove_item(struct datos *shared_data);
void consume_item(int item, struct datos *shared_data);

int main() {
    sem_t *mutex, *empty, *full;
    int fd;
    struct datos *shared_data;

    //Abrimos los semáforos (NO SE CREAN AQUÍ)
    mutex = sem_open("mutex", 0);
    empty = sem_open("empty", 0);
    full = sem_open("full", 0);

    // Crear un descriptor de archivo para la memoria compartida
    fd = shm_open(FILE_PATH, O_CREAT | O_RDWR, S_IRUSR | S_IWUSR);
    if (fd == -1) {
        perror("Error al abrir el archivo de memoria compartida");
        exit(EXIT_FAILURE);
    }

    // Mapear la memoria compartida en el espacio de direcciones del proceso
    shared_data = (struct datos *)mmap(NULL, sizeof(struct datos), PROT_READ | PROT_WRITE, MAP_SHARED, fd, 0);
    if (shared_data == MAP_FAILED) {
        perror("Error al mapear la memoria compartida");
        exit(EXIT_FAILURE);
    }

    srand(time(NULL));

    /// Consumidor
    for (int i = 0; i < ITERACIONES; i++) {
    	sleep(rand()%4);
    	
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

    // Desmapear y cerrar el descriptor de archivo
    if (munmap(shared_data, sizeof(struct datos)) == -1) {
        perror("Error al desmapear la memoria compartida");
        exit(EXIT_FAILURE);
    }

    if (close(fd) == -1) {
        perror("Error al cerrar el descriptor de archivo");
        exit(EXIT_FAILURE);
    }

    return 0;
}

int remove_item(struct datos *shared_data){
    int b = shared_data->tope - 1;
    //sleep(rand()%4);
    int item = shared_data->buffer[b];
    (shared_data->tope)--; // Decrementa el contador para eliminar el elemento del buffer
    //sleep(rand()%4);

    return item;
}

void consume_item(int item, struct datos *shared_data) {
    printf("El consumidor consumió el item: %d\n", item);

    printf("Suma: %d ", item);
    for (int i = 0; i < shared_data->tope; i++) {
        printf("+ %d ", shared_data->buffer[i]);
        item += shared_data->buffer[i];
    }

    printf("\nLa suma del buffer al item es: %d\n", item);
}
