/*
	CONSUMIDOR EJERCICIO 1
	Autores: Laura Antelo e Iago Feijóo
*/

#include <stdio.h>
#include <stdlib.h>
#include <sys/mman.h>
#include <fcntl.h>
#include <unistd.h>
#include "time.h"

#define FILE_PATH "/P2_E1"
#define N 8

struct datos {
    int buffer[N];
    int count;
};

int main() {
    int fd;
    struct datos *shared_data;

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

    // Consumidor
    while (1) {
        while (shared_data->count <= 0);

        int b = shared_data->count - 1;

        sleep(rand()%4);

        (shared_data->count)--;

        sleep(rand()%4);

        int item = shared_data->buffer[b];

        sleep(rand()%4);

        printf("El consumidor consumió el item: %d \n", item);

        sleep(rand()%4);

        printf("Buffer: [ ");
        for (int i = 0; i < shared_data->count; i++) {
            printf("%d ", shared_data->buffer[i]);
        }
        printf("]\n\n");

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
