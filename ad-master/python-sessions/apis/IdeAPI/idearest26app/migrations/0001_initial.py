# Generated by Django 4.0.10 on 2023-11-15 10:47

from django.db import migrations, models


class Migration(migrations.Migration):

    initial = True

    dependencies = [
    ]

    operations = [
        migrations.CreateModel(
            name='CustomUser',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('e_mail', models.CharField(max_length=230, unique=True)),
                ('username', models.CharField(max_length=240)),
                ('encrypted_password', models.CharField(max_length=120)),
            ],
        ),
    ]
