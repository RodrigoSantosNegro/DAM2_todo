from django.shortcuts import render
from django.http import HttpResponse


def my_first_view(request):
    return HttpResponse("Hola, mundo, usando un HttpResponse")


def my_second_view(request):
    return HttpResponse("""
<html>
  <body>
    <h1>1, 2, probando...</h1>
    <p>Ahí van tres emperadores romanos</p>
    <ul>
      <li>Augusto</li>
      <li>Claudio</li>
      <li>Nerón</li>
    <ul>
  </body>
</html>
""")

