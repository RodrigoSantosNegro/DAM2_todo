import json
from datetime import datetime

from django.http import JsonResponse
from django.views.decorators.csrf import csrf_exempt


def health_check(request):
    http_response = {"alive": True}
    return JsonResponse(http_response)


def table_of_six(request):
    http_response = [6 * i for i in range(1, 11)]
    return JsonResponse(http_response, safe=False)


def multiplication_table(request, number):
    if number == "one":
        result = [i for i in range(1, 11)]
    elif number == "two":
        result = [i * 2 for i in range(1, 11)]
    elif number == "three":
        result = [i * 3 for i in range(1, 11)]
    elif number == "four":
        result = [i * 4 for i in range(1, 11)]
    elif number == "five":
        result = [i * 5 for i in range(1, 11)]
    elif number == "six":
        result = [i * 6 for i in range(1, 11)]
    elif number == "seven":
        result = [i * 7 for i in range(1, 11)]
    elif number == "eight":
        result = [i * 8 for i in range(1, 11)]
    elif number == "nine":
        result = [i * 9 for i in range(1, 11)]
    elif number == "ten":
        result = [i * 10 for i in range(1, 11)]
    else:
        response_data = {"error": "Number not valid. Only one-ten are supported"}
        return JsonResponse(response_data, status=404)

    return JsonResponse(result, safe=False)


def multiply_number_improved(request, number):
    result = []
    for i in range(1, 11):
        result.append(number * i)
    return JsonResponse(result, safe=False)


def multiplication_table_query_param(request):
    number = request.GET.get("n", None)

    if number is not None:
        try:
            number = int(number)
        except ValueError:
            return JsonResponse({"error": "Parameter 'n' must be a number"}, status=400)
        result = []
        for i in range(1, 11):
            result.append(number * i)
        return JsonResponse(result, safe=False)
    else:
        return JsonResponse({"error": "Missing 'n' parameter"}, status=400)


def prime_endpoint(request, number):
    try:
        number = int(number)
    except ValueError:
        return JsonResponse({"error": "Parameter must be a number"}, status=400)
    if number < 1:
        return JsonResponse({"error": "Parameter must be a number greater than zero"}, status=400)

    if number == 2:
        return JsonResponse({"is_prime": True}, safe=False)
    elif number % 2 == 0:
        return JsonResponse({"is_prime": False}, safe=False)
    for i in range(3, number):
        if number % i == 0:
            return JsonResponse({"is_prime": False}, safe=False)
    return JsonResponse({"is_prime": True}, safe=False)


def years_since(request):
    year_param = request.GET.get("k", None)

    if year_param is not None:
        try:
            year = int(year_param)
        except ValueError:
            return JsonResponse({"error": "Parameter should be a valid number"}, status=400)

        current_year = datetime.now().year

        if year > current_year:
            return JsonResponse({"error": "Years in the future are unsupported"}, status=400)

        number_of_years = current_year - year
        return JsonResponse({"number_of_years": number_of_years})
    else:
        return JsonResponse({"error": "Missing required 'k' parameter"}, status=400)


@csrf_exempt
def resource_example(request, number):
    if request.method != 'POST':
        return JsonResponse({"error": "HTTP method not supported"}, status=405)

    # Precondición comprobada: El método es POST
    if len(request.body) == 0:
        # No hay cuerpo de petición
        # Nos comportamos como antes
        return JsonResponse({"message": "You have sent a POST to the resource " + str(number)})

    http_body = json.loads(request.body)
    client_mood = http_body.get("mood", "No mood")  # "No mood" será un valor por defecto
    return JsonResponse(
        {"message": "You have sent a POST to the resource " + str(number) + " and you are " + client_mood})


@csrf_exempt
def favorite_animal(request):
    if request.method != 'POST':
        return JsonResponse({"error": "HTTP method not supported"}, status=405)

    if len(request.body) == 0:
        return JsonResponse({"message": "You have sent a POST to the resource favorite animal endpoint"}, status=400)

    http_body = json.loads(request.body)
    animal = http_body.get("name", None)
    if animal is not None:
        if animal == "Cat":
            return JsonResponse({"message": "That is great! Seven lives will be enough"})
        else:
            return JsonResponse({"message": "OK! Have a wonderful day"})
    else:
        return JsonResponse({"message": "You have sent a POST to the resource favorite animal endpoint"}, status=400)


@csrf_exempt
def http_methods_example(request):
    if request.method == "GET":
        message = "Reading some data, huh?"
    elif request.method == "POST":
        message = "This should create a new thing"
    elif request.method == "PUT":
        message = "You can update any element now"
    elif request.method == "DELETE":
        message = "This will remove one or many elements"
    else:
        # Esta línea nunca se ejecutará si el verbo HTTP no es GET, POST, PUT o DELETE
        return JsonResponse({"error": "HTTP method not allowed"}, status=405)
    # Flujo normal
    return JsonResponse({"message": message})
