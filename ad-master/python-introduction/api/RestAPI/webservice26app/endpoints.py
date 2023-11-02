from django.http import JsonResponse


def my_first_endpoint(request):
    json_dictionary = {
        "everything_ok": True,
        "message": "Todo correcto"
    }
    return JsonResponse(json_dictionary)


def my_not_found_endpoint(request):
    json_dictionary = {
        "message": "Eso que buscas no existe"
    }
    return JsonResponse(json_dictionary, status=404)


def my_list_endpoint(request):
    animals = [
        "Foca monje (Monachus monachus)",
        "Fartet (Aphanius iberus)",
        "Lince ibérico (Lynx pardinus)"
    ]
    return JsonResponse(animals, safe=False)
