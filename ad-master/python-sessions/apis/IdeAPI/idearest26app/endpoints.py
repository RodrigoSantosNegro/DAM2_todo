import json
import secrets

import bcrypt
from django.http import JsonResponse
from django.views.decorators.csrf import csrf_exempt

from .models import CustomUser, UserSession, Category, Idea, Comment


def health_check(request):
    http_response = {"is_happy": True}
    return JsonResponse(http_response)


@csrf_exempt
def users(request):
    if request.method != 'POST':
        return JsonResponse({'error': 'Unsupported HTTP method'}, status=405)
    try:
        body_json = json.loads(request.body)
        json_username = body_json['username']
        json_email = body_json['email']
        json_password = body_json['password']
    except KeyError:
        return JsonResponse({"error": "Missing parameter in JSON"}, status=400)

    if '@' not in json_email or len(json_email) < 9:
        return JsonResponse({"error": "Email not valid"}, status=400)

    if CustomUser.objects.filter(e_mail=json_email).exists():
        return JsonResponse({"error": "Email already registered"}, status=409)

    salted_and_hashed_pass = bcrypt.hashpw(json_password.encode('utf8'), bcrypt.gensalt()).decode('utf8')

    user_object = CustomUser(e_mail=json_email, username=json_username, encrypted_password=salted_and_hashed_pass)
    user_object.save()
    return JsonResponse({"is_created": True}, status=201)


@csrf_exempt
def sessions(request):
    if request.method != 'POST':
        return JsonResponse({'error': 'HTTP method not supported'}, status=405)
    try:
        body_json = json.loads(request.body)
        json_email = body_json['user_email']
        json_password = body_json['password']
    except KeyError:
        return JsonResponse({"error": "Missing parameter in body"}, status=400)

    try:
        db_user = CustomUser.objects.get(e_mail=json_email)
    except CustomUser.DoesNotExist:
        return JsonResponse({"error": "User not in database"}, status=404)

    if bcrypt.checkpw(json_password.encode('utf8'), db_user.encrypted_password.encode('utf8')):
        # json_password y db_user.encrypted_password coinciden
        random_token = secrets.token_hex(10)
        session = UserSession(person=db_user, token=random_token)
        session.save()
        return JsonResponse({"token": random_token}, status=201)
    else:
        return JsonResponse({"error": "Invalid password"}, status=401)


def categories(request):
    if request.method == "GET":
        all_categories = Category.objects.all()
        json_response = []
        for row in all_categories:
            json_response.append(row.to_json())
        return JsonResponse(json_response, status=200, safe=False)
    else:
        return JsonResponse({"error": "HTTP method not supported"}, status=405)


@csrf_exempt
def ideas(request, categoryId):
    if request.method == 'POST':
        # El usuario va a añadir una idea a la base de datos
        try:
            category = Category.objects.get(id=categoryId)
        except Category.DoesNotExist:
            return JsonResponse({"error": "Category not found"}, status=404)

        body_json = json.loads(request.body)
        try:
            json_title = body_json['idea_name']
            json_description = body_json['content']
        except KeyError:
            return JsonResponse({"error": "Missing parameter in request"}, status=400)

        idea = Idea()

        authenticated_user = __get_request_user(request)
        if authenticated_user is None:
            return JsonResponse({"error": "Invalid authentication"}, status=401)
        idea.user = authenticated_user

        idea.title = json_title
        idea.description = json_description
        idea.category = category
        idea.save()
        return JsonResponse({"uploaded": True}, status=201)


    elif request.method == 'GET':
        # El usuario quiere consultar las ideas de la categoría con id == category_id
        try:
            category = Category.objects.get(id=categoryId)
        except Category.DoesNotExist:
            return JsonResponse({"error": "Category not found"}, status=404)

        ideas_in_category = Idea.objects.filter(category=category)
        json_response = []
        for idea in ideas_in_category:
            json_response.append({
                "idea_id": idea.id,
                "author_id": idea.user.id,
                "idea_name": idea.title,
                "content": idea.description
            })

        return JsonResponse(json_response, status=200, safe=False)
    else:
        return JsonResponse({'error': 'HTTP method not supported'}, status=405)


def __get_request_user(request):
    header_token = request.headers.get('Api-Session-Token', None)
    if header_token is None:
        return None
    try:
        db_session = UserSession.objects.get(token=header_token)
        return db_session.person
    except UserSession.DoesNotExist:
        return None


@csrf_exempt
def comments(request, ideaId):
    if request.method == 'POST':
        try:
            idea = Idea.objects.get(id=ideaId)
        except Idea.DoesNotExist:
            return JsonResponse({"error": "Idea not found"}, status=404)

        body_json = json.loads(request.body.decode('utf-8'))
        try:
            comment = body_json['comment_text']
        except KeyError:
            return JsonResponse({"error": "Missing parameter in request"}, status=400)

        comment = Comment(content=comment, idea=idea)

        authenticated_user = __get_request_user(request)
        if authenticated_user is None:
            return JsonResponse({"error": "Invalid authentication"}, status=401)
        comment.user = authenticated_user

        comment.save()
        return JsonResponse({"created": True}, status=201)

    elif request.method == 'GET':
        try:
            idea = Idea.objects.get(id=ideaId)
        except Idea.DoesNotExist:
            return JsonResponse({"error": "Idea not found"}, status=404)

        comments_list = Comment.objects.filter(idea=idea)
        json_response = []
        for comment in comments_list:
            json_response.append({
                "id": comment.id,
                "author_id": comment.user.id,
                "comment_text": comment.content,
            })

        return JsonResponse(json_response, status=200, safe=False)
    else:
        return JsonResponse({'error': 'HTTP method not supported'}, status=405)
