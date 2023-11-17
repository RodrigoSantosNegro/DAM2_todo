from django.db import models


class CustomUser(models.Model):
    e_mail = models.CharField(max_length=230, unique=True)
    username = models.CharField(max_length=240)
    encrypted_password = models.CharField(max_length=120)


class UserSession(models.Model):
    person = models.ForeignKey(CustomUser, on_delete=models.CASCADE)
    token = models.CharField(unique=True, max_length=20)


class Category(models.Model):
    title = models.CharField(max_length=40, unique=True)

    def to_json(self):
        return {"category_id": self.id, "category_name": self.title}

    def __str__(self):
        return self.title


class Idea(models.Model):
    title = models.CharField(max_length=200)
    description = models.CharField(max_length=2200)
    user = models.ForeignKey(CustomUser, on_delete=models.CASCADE)
    category = models.ForeignKey(Category, on_delete=models.CASCADE)

    def to_json(self):
        return {"idea_id": self.id, "idea_name": self.title, "content": self.description}


class Comment(models.Model):
    content = models.CharField(max_length=1200)
    user = models.ForeignKey(CustomUser, on_delete=models.CASCADE)
    idea = models.ForeignKey(Idea, on_delete=models.CASCADE)

