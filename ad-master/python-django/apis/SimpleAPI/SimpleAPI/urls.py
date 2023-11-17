"""
URL configuration for SimpleAPI project.

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/4.2/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  path('', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  path('', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.urls import include, path
    2. Add a URL to urlpatterns:  path('blog/', include('blog.urls'))
"""
from django.contrib import admin
from django.urls import path
from simplerest26app import endpoints

urlpatterns = [
    path('admin/', admin.site.urls),
    path('health', endpoints.health_check),
    path('multiply_by_six', endpoints.table_of_six),
    path('multiplications/<number>', endpoints.multiplication_table),
    path('v2/multiplications/<int:number>', endpoints.multiply_number_improved),
    path('v3/multiplications', endpoints.multiplication_table_query_param),
    path('prime/<int:number>', endpoints.prime_endpoint),
    path('years_since', endpoints.years_since),
    path('resource/<int:number>', endpoints.resource_example),
    path('favorite_animal', endpoints.favorite_animal),
    path('http_methods', endpoints.http_methods_example),
]
