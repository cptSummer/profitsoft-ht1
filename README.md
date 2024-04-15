# profitsoft-ht1

## Сутність Photo

### Поля

- String photoName; - назва фото
- String photoFormat; - формат
- String photoPath; - шлях (локальний або шлях до хмарного-сховища)
- String photoTags; - теги (перелік тегів для опису фото, запис через кому)
- Date uploadDate; - дата завантаження на сервер


## Сутність User

## Поля

-  String username; - ім'я або нікнейм користувача
-  String email; - пошта 
-  Date joinDate; - дата реєстрації


## Опис сутностей

Сутність User відноситься до Photo як один-до-багатьох, 
тобто у одного користувача може бути багато фото файлів.
На даному етапі сутності мають базові поля такі як ім'я (photoName, username),
тобто сутності знаходяться у 2 нормальній формі.
- User (користувач) - є сутністью зареєстрованого користувача сайту, може завантажувати та змінювати сутність Photo
- Photo (фото) - сутність якою володіє зареєстрованого користувач

## Приклад вхідного файлу

[photos.json](src%2Fmain%2Fresources%2Fassets%2Fphotos.json)

```json
[
  {
    "photoName": "mountain_nature",
    "photoFormat": "jpg",
    "photoPath": "user/jpg/photo1.jpg",
    "photoTags": "happy, nature, sunset",
    "uploadDate": "2010-07-11"
  },
  {
    "photoName": "logo",
    "photoFormat": "png",
    "photoPath": "user/jpg/photo2.jpg",
    "photoTags": "logo, low_poly",
    "uploadDate": "2022-01-02"
  },
  {
    "photoName": "season_nature",
    "photoFormat": "jpg",
    "photoPath": "user/jpg/photo3.jpg",
    "photoTags": "sea, nature, day",
    "uploadDate": "2022-07-11"
  }
]
```
## Приклад вихідних файлів 
### Атрибут photoTags

[statistics_by_photoTags.xml](src%2Fmain%2Fresources%2Fassets%2Fstatistics_by_photoTags.xml)
```xml
<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<statistics>
    <item>
        <value>nature</value>
        <count>19</count>
    </item>
    <item>
        <value>sunset</value>
        <count>12</count>
    </item>
    <item>
        <value>happy</value>
        <count>9</count>
    </item>
    <item>
        <value>low_poly</value>
        <count>8</count>
    </item>
    <item>
        <value>sea</value>
        <count>8</count>
    </item>
    <item>
        <value>logo</value>
        <count>8</count>
    </item>
    <item>
        <value>day</value>
        <count>7</count>
    </item>
    <item>
        <value>colorful</value>
        <count>2</count>
    </item>
    <item>
        <value>forest</value>
        <count>1</count>
    </item>
    <item>
        <value>night</value>
        <count>1</count>
    </item>
    <item>
        <value>clouds</value>
        <count>1</count>
    </item>
    <item>
        <value>coast</value>
        <count>1</count>
    </item>
    <item>
        <value>mountain</value>
        <count>1</count>
    </item>
</statistics>

```

[statistics_by_photoFormat.xml](src%2Fmain%2Fresources%2Fassets%2Fstatistics_by_photoFormat.xml)

```xml
<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<statistics>
    <item>
        <value>jpg</value>
        <count>16</count>
    </item>
    <item>
        <value>png</value>
        <count>10</count>
    </item>
    <item>
        <value>jpeg</value>
        <count>2</count>
    </item>
</statistics>

```
###### Дані було оброблено з 8 json файлів

## Результати експериментів з кількістю потоків
Для експерименту використувувались 8 файлів json серед яких були як великі файли так і пусті

Час виконання для всіх атрибутів:
- 1 pool = 0,11457 sec
- 2 pool = 0,12470 sec
- 4 pool = 0,11260 sec
- 6 pool = 0,12450 sec
- 8 stream pool (всі файли) = 0,10856 seconds

Таким чином було виявлено що одночасна обробка усіх файлів (8 потоків) була найшвидшою
