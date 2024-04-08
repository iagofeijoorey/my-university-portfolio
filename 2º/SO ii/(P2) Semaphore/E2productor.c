/*
	PRODUCTOR EJERCICIO 2
	Autores: Laura Antelo e Iago Feijóo
*/
#include <stdio.h>
#include <stdlib.h>
#include <sys/mman.h>
#include <fcntl.h>
#include <unistd.h>
#include <time.h>
#include <pthread.h>
#include <semaphore.h>

#define FILE_PATH "/P2_E1"
#define N 8
#define ITERACIONES 20

struct datos {
    int buffer[N];
    int tope;
};

int produce_item();
void insert_item(int item, struct datos *shared_data);

int main() {
    int fd;
    struct datos *shared_data;
    sem_t *mutex, *empty, *full;

    //eliminamos los semáforos antes de crearlo (buena práctica)
    sem_unlink("mutex");
    sem_unlink("empty");
    sem_unlink("full");

    //Creamos los semáforos
    mutex = sem_open("mutex", O_CREAT | O_EXCL, 0700, 1);
    empty = sem_open("empty", O_CREAT | O_EXCL, 0700, N);
    full = sem_open("full", O_CREAT | O_EXCL, 0700, 0);

    // Crear un descriptor de archivo para la memoria compartida
    fd = shm_open(FILE_PATH, O_CREAT | O_RDWR, S_IRUSR | S_IWUSR);
    if (fd == -1) {
        perror("Error al abrir el archivo de memoria compartida");
        exit(EXIT_FAILURE);
    }

    // Configurar el tamaño de la memoria compartida
    if (ftruncate(fd, sizeof(struct datos)) == -1) {
        perror("Error al truncar el archivo de memoria compartida");
        exit(EXIT_FAILURE);
    }

    // Mapear la memoria compartida en el espacio o de direcciones del proceso
    shared_data = (struct datos *)mmap(NULL, sizeof(struct datos), PROT_READ | PROT_WRITE, MAP_SHARED, fd, 0);
    if (shared_data == MAP_FAILED) {
        perror("Error al mapear la memoria compartida");
        exit(EXIT_FAILURE);
    }

    // Ahora shared_data es una variable que puede ser compartida entre procesos

    shared_data->tope = 0;  //Inicializamos la pila
    srand(time(NULL));

    for (int i = 0; i < ITERACIONES; i++) {
	sleep(rand() % 4);

        int item_shared = produce_item();

        sem_wait(empty);
        sem_wait(mutex);

        insert_item(item_shared, shared_data);
        
        printf("Buffer productor: [ ");
        for (int j = 0; j < shared_data->tope; j++) {
            printf("%d ", shared_data->buffer[j]);
        }
        printf("]\n\n");

        sem_post(mutex);
        sem_post(full);
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

    // Eliminar el archivo de memoria compartida
    if (shm_unlink(FILE_PATH) == -1) {
        perror("Error al eliminar el archivo de memoria compartida");
        exit(EXIT_FAILURE);
    }

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

int produce_item(){
    return (rand() % 10) + 1;
}

void insert_item(int item, struct datos *shared_data){
    //sleep(rand() % 4);
    shared_data->buffer[shared_data->tope] = item;
    shared_data->tope++;
    //sleep(rand() % 4);
    printf("El productor produjo el item: %d\n", item);
}
