from django.db import models


class Dashboard(models.Model):
    title = models.CharField(max_length=255)
    description = models.CharField(max_length=255)

    def to_json(self):
        return {
            "id": self.id,
            "title": self.title,
            "description": self.description,
        }


class Question(models.Model):
    dashboard = models.ForeignKey(Dashboard, on_delete=models.CASCADE)
    title = models.CharField(max_length=255)
    description = models.CharField(max_length=255)
    publication_date = models.DateTimeField(auto_now_add=True)

    def to_json(self):
        return {
            "id": self.id,
            "title": self.title,
            "description": self.description,
            "publication_date": self.publication_date,
        }


class Answer(models.Model):
    question = models.ForeignKey(Question, on_delete=models.CASCADE)
    description = models.CharField(max_length=255)
    publication_date = models.DateTimeField(auto_now_add=True)

    def to_json(self):
        return {
            "description": self.description,
            "publication_date": self.publication_date,
        }