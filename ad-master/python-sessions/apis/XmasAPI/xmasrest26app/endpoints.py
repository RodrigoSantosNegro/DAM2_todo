from django.http import JsonResponse


def gifts(request):
    if request.method == 'GET':
        response_data = {
            "mensaje": "Hola, Papá Noel, he sido una persona excelente, trabajadora, estudiosa, amable y buena. Te envío mi lista de regalos. Más vale que no falte ni uno o te vas a enterar",
            "solicitudes": [
                "PlayStation 8",
                "Ordenador i9 con gráfica potente",
                "Yoyó"
            ]
        }
        return JsonResponse(response_data, status=200)
