from django.db import models


# Create your models here.
class Entry(models.Model):
    title = models.CharField(max_length=180)
    content = models.CharField(max_length=4500)
    publication_date = models.DateTimeField(auto_now=True)

    def to_json(self):
        return {
            "title": self.title,
            "content": self.content,
            "created": self.publication_date,
        }


class Comment(models.Model): # Nuevo modelo
    content = models.CharField(max_length=1200)
    entry = models.ForeignKey(Entry, on_delete=models.CASCADE)  # Clave foránea a Entry

    def to_json(self):
        return {
            "user_data": self.content,
        }

