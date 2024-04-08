/*
	EJERCICIO OPTATIVO 4
	Autores: Laura Antelo e Iago Feijóo
*/
#include <stdio.h>
#include <stdlib.h>
#include <sys/mman.h>
#include <unistd.h>
#include <fcntl.h>
#include <time.h>
#include <pthread.h>
#include <semaphore.h>
#include <sys/wait.h>

#define FILE_PATH "/P2_E1"
#define N 8
#define ITERACIONES 10

typedef struct {
    int buffer[N];
    int tope;
}datos;

sem_t *mutex, *empty, *full;

//Funciones del productor
void productor(datos *shared_data, int num);
int produce_item();
void insert_item(int item, datos *shared_data, int num);

//Funciones del consumidor
void consumidor(datos *shared_data, int num);
int remove_item(datos *shared_data);
void consume_item(int item, datos *shared_data, int num);

int main(int argc, char **argv) {
    datos *shared_data;

    if (argc != 3) {
        perror("Se debe introducir el ejecutable, el número de productores y el número de consumidores");
        return EXIT_FAILURE;
    }

    int productores = atoi(argv[1]);
    int consumidores = atoi(argv[2]);
    int total = productores + consumidores;

	//eliminamos los semáforos antes de crearlo (buena práctica)
	sem_unlink("mutex");
    sem_unlink("empty");
    sem_unlink("full");

    //Creamos los semáforos
    mutex = sem_open("mutex", O_CREAT | O_EXCL, 0700, 1);
    empty = sem_open("empty", O_CREAT | O_EXCL, 0700, N);
    full = sem_open("full", O_CREAT | O_EXCL, 0700, 0);
    
    // Crear objeto de memoria compartida
    int fd = shm_open(FILE_PATH, O_CREAT | O_RDWR, 0700);
    if (fd == -1) {
        perror("Error al crear el objeto de memoria compartida");
        return EXIT_FAILURE;
    }

    // Fijar el tamaño del objeto de memoria compartida
    if (ftruncate(fd, sizeof(datos)) == -1) {
        perror("Error al truncar el objeto de memoria compartida");
        return EXIT_FAILURE;
    }

    // Mapear la memoria compartida en el espacio o de direcciones del proceso
    shared_data = (datos *)mmap(NULL, sizeof(datos), PROT_READ | PROT_WRITE, MAP_SHARED, fd, 0);
    if (shared_data == MAP_FAILED) {
        perror("Error al mapear la memoria compartida");
        exit(EXIT_FAILURE);
    }
    
    shared_data->tope = 0;
    srand(time(NULL));

    //BUCLE PRINCIPAL
    for (int i = 0; i < total; i++) {
        pid_t pid = fork();

        if (pid < 0) {
            perror("Error al crear el hijo");
            exit(EXIT_FAILURE);
        } else if (pid == 0) {
            //creamos a los productores
            if (i < productores) productor(shared_data, i);

            //creamos a los consumidores
            else consumidor(shared_data, i-productores);

            exit(1);    //que el hijo acabe
        }
    }

    while (wait(NULL) > 0);

	// Eliminar la memoria compartida
    if (munmap(shared_data, sizeof(datos)) == -1) {
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
    
    // Destruir semáforos
     if (sem_destroy(mutex) == -1) {
        perror("Error al destruir el semáforo empty");
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
void productor(datos *shared_data, int num) {
    printf("Productor %d\n\n", num);
    for (int i = 0; i < ITERACIONES; i++) {
		sleep(rand()%4);

        int item= produce_item();

        sem_wait(empty);
        sem_wait(mutex);

        insert_item(item,shared_data, num);

		printf("Buffer productor %d: [ ", num);
        for (int i = 0; i < shared_data->tope; i++) {
            printf("%d ", shared_data->buffer[i]);
        }
        printf("]\n\n");
        
        sem_post(mutex);
        sem_post(full);
    }
}

int produce_item(){
    return (rand() % 10) + 1;
}

void insert_item(int item, datos *shared_data, int num){
    //sleep(rand() % 4);
    shared_data->buffer[shared_data->tope] = item;
    shared_data->tope++;
    //sleep(rand() % 4);
    printf("El productor %d produjo el item: %d\n", num, item);
}

//CONSUMIDOR
void consumidor(datos *shared_data, int num) {
    printf("Consumidor %d\n\n", num);
    for (int i = 0; i < ITERACIONES; i++) {
    
		sleep(rand()%4);
	
        sem_wait(full);
        sem_wait(mutex);

        int item_shared = remove_item(shared_data);

        consume_item(item_shared, shared_data, num);

        printf("Buffer consumidor %d: [ ", num);
        for (int i = 0; i < shared_data->tope; i++) {
            printf("%d ", shared_data->buffer[i]);
        }
        printf("]\n\n");

        sem_post(mutex);
        sem_post(empty);
    }
}

int remove_item(datos *shared_data){
    int b = shared_data->tope - 1;
    //sleep(rand()%4);
    int item = shared_data->buffer[b];
    (shared_data->tope)--; // Decrementa el contador para eliminar el elemento del buffer
    //sleep(rand()%4);

    return item;
}

void consume_item(int item, datos *shared_data, int num) {
    printf("El consumidor %d consumió el item: %d\n", num, item);

    printf("Suma: %d ", item);
    for (int i = 0; i < shared_data->tope; i++) {
        printf("+ %d ", shared_data->buffer[i]);
        item += shared_data->buffer[i];
    }

    printf("\nLa suma del buffer al item es: %d\n", item);
}
