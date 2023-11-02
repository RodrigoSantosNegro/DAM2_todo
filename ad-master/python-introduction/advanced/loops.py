def simple_for():
    for i in range(60):
        print(i)


def simple_for_with_begin():
    for i in range(93, 146):
        print(i)


def simple_for_with_increment():
    for i in range(93, 146, 3):
        print(i)


def show_multiples():
    for i in range(24, 66, 4):
        print(i)


def print_table(num):
    for i in range(1, 11):
        print(num*i)


def search_for_dino(aDino):
    dinos = ["Triceratops", "Tiranosaurio", "Diplodocus", "Pterodáctilo", "Velocirraptor"]
    for d in dinos:
        if d == aDino:
            return True

    return False


def find_dino2(dinosaur):
    return dinosaur in ["Triceratops", "Diplodocus", "Pterodáctilo"]


def example_length():
    list1 = ["Alice", "Bob"]
    size1 = len(list1)
    print("El tamaño de la primera lista es: " + str(size1))

    list2 = [None, None, None, None, None, None]
    size2 = len(list2)
    print("El tamaño de la segunda lista es: " + str(size2))

    list3 = [[1, 2], [3, 4]]
    size3 = len(list3)
    print("El tamaño de la tercera lista es: " + str(size3))


def retrieve_value(index):
    l = [4, 8, -35, "Pepe Depura", 39]
    if index > len(l):
        return None
    return l[index]

if __name__ == '__main__':
    print_table(2)
