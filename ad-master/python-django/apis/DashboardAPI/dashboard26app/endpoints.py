import datetime
import json

from django.http import JsonResponse
from django.views.decorators.csrf import csrf_exempt

from .models import Dashboard, Question, Answer


def get_dashboards(request):
    if request.method == "GET":
        all_dashboard = Dashboard.objects.all()
        json_response = []
        for row in all_dashboard:
            json_response.append(row.to_json())
        return JsonResponse(json_response, safe=False)


@csrf_exempt
def dashboards_questions(request, dashboardId):
    if request.method == "GET":
        size = request.GET.get("size")
        older_than = request.GET.get("older_than")

        dashboard = Dashboard.objects.get(id=dashboardId)

        try:
            size = int(size) if size is not None else None
        except ValueError:
            return JsonResponse({"error": "Wrong size parameter"}, status=400)

        if size is None:
            if older_than is None:
                all_rows = Question.objects.filter(dashboard=dashboard).order_by("-publication_date")
            else:
                all_rows = Question.objects.filter(publication_date__lt=older_than, dashboard=dashboard).order_by(
                    "-publication_date")

        else:
            if older_than is None:
                all_rows = Question.objects.filter(dashboard=dashboard).order_by("-publication_date")[:size]
            else:
                all_rows = Question.objects.filter(publication_date__lt=older_than, dashboard=dashboard).order_by(
                    "-publication_date")[:size]

        json_response = []
        for row in all_rows:
            json_response.append(row.to_json())
        return JsonResponse(json_response, safe=False)

    elif request.method == "POST":
        # Aquí procesamos un POST
        client_json = json.loads(request.body)
        dashboard = Dashboard.objects.get(id=dashboardId)
        title = client_json.get("title", None)
        description = client_json.get("description", None)

        if title is None or description is None:
            return JsonResponse({"error": "The client request is missing one or many parameters in the body"},
                                status=400)
        # Creamos una nueva instancia de Entry
        new_entry = Question(dashboard=dashboard, title=title, description=description,
                             publication_date=datetime.datetime.now())
        new_entry.save()
        return JsonResponse({"The question was created correctly": True}, status=201)

    else:
        return JsonResponse({"error": "HTTP method not supported"}, status=405)


@csrf_exempt
def answers_questions(request, questionId):
    if request.method == "GET":
        size = request.GET.get("size")
        older_than = request.GET.get("older_than")

        question = Question.objects.get(id=questionId)

        all_rows = Answer.objects.filter(question=question).order_by("-publication_date")


        json_response = []
        for row in all_rows:
            json_response.append(row.to_json())
        return JsonResponse(json_response, safe=False)

    elif request.method == "POST":
        # Aquí procesamos un POST
        client_json = json.loads(request.body)
        question = Question.objects.get(id=questionId)
        description = client_json.get("description", None)

        if description is None:
            return JsonResponse({"description": "The client request is missing the required parameters in the body"},
                                status=400)
        # Creamos una nueva instancia de Entry
        new_entry = Answer(question=question, description=description,
                             publication_date=datetime.datetime.now())
        new_entry.save()
        return JsonResponse({"description": "The answer was created correctly"}, status=201)

    else:
        return JsonResponse({"error": "HTTP method not supported"}, status=405)