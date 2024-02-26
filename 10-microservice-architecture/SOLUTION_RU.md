# Архитектура Игры

![Диаграмма](Game_Architecture.jpg)

[Диаграмма в PNG](Game_Architecture.png)

[Диаграмма в Sketchboard.me](https://sketchboard.me/BEeyMRWFMgW)

Когда пользователю нужно создать игру, он запрашивает `POST /games` сервиса `Gateway` через `Agent`. Запрос содержит имена пользователей игроков, которые будут приглашены.

Пользователь, которого хотят пригласить, регулярно запрашивает REST-endpoint `GET /invitations` через `Agent` и однажды получает ответ с `id` игры, на которую он приглашен.

Если он не приглашен, он может запросить список игр через endpoint `GET /games` `Gateway`. Затем он запрашивает `POST /games/{id}/join`, чтобы присоединиться. Создатель игры регулярно запрашивает `GET /games/{id}/requests`. Если поле не пустое, он может одобрить запросы через `PATCH /games/{id}/approve`.

После того, как придет время запускать, служба `Игры` запрашивает `Игровой сервер` запросом `POST /start`. После этого `Игровой сервер` создает область действия игры, запускает цикл выполнения для текущей игры, получает команды от пользователей, создает и выполняет собственные команды и обновляет состояние игры с их помощью.

На этот раз пользователь регулярно отправляет на сервер через шлюз gRPC запросы с командами управления для своего танка / космического корабля. В то же время он регулярно получает обновление состояния игры через endpoint gRPC stream `Gateway`.

Игровой сервер регулярно после каждого обновления отправляет в службу `Game records` для сохранения.

Чтобы воспроизвести записанную игру, пользователь может запросить `GET /records` для получения состояний игры, а `Агент` отобразит игру с его помощью.

Все запросы аутентифицируются. Таким образом, `Шлюз` запрашивает endpoint `POST /verify` для проверки пользовательского JWT-токена.

### Узкие места и их решения

1. Для проверки пользователя шлюз должен запрашивать службу `Пользователи и аутентификация` каждый раз, когда запрашивается сам. Это создает ненужную нагрузку на службу `Пользователи и аутентификация`. Чтобы избежать этого, я создал endpoint `GET /keys`. `Шлюз` запрашивает его регулярно, но не очень часто, сохраняет ключи и использует их для проверки JWT пользователя.
2. Сохранение записей - это очень частая операция. При большой рабочей нагрузке она может выполняться не так быстро и порождать такие проблемы, как длительная задержка ответа, потерянные соединения из-за нехватки потоков и так далее. Чтобы избежать этих проблем, я решил отправлять сообщения о событиях асинхронно, используя MQ (Kafka, Rabbit и т.д.). Также хранилище для них не является реляционным. Например, Mongo.

   Если рабочая нагрузка увеличивается, мы можем распределять сегменты и экземпляры служб.
3. У `игрового сервера` может быть большое количество клиентов, поэтому ему необходимо быстро обрабатывать запросы. Поскольку разбор JSON не быстрый, я решил сделать endpoint управляющей команды не REST, а gRPC. Это должно быть синхронно, поэтому мы не должны использовать там MQ.

   Если количество клиентов и рабочая нагрузка возрастут, мы можем размножить экземпляры этого сервера и поместить `Балансировщик игрового сервера` перед `Игровым сервером`. Он определит по `Идентификатору игры`, куда передавать запрос. Nginx поддерживает gRPC для балансировки нагрузки, так что, похоже, все в порядке. В случае, если мы не можем сбалансировать поток gRPC, возможно, вместо этого нужно использовать MQ.

   Чтобы ускорить каждое взаимодействие, endpoint gRPC `putCommand` запрашивается только в том случае, если какая-либо команда оценена пользователем. В то же время изменение состояния игры в endpoint потока gRPC `gameStateUpdate` отправляется как исправление текущего состояния. Таким образом, оно отправляется только в том случае, если состояние изменено и сообщение невелико.

### Изменения требований
1. Требования будут меняться чаще для `Игрового сервиса` и `Агента`. Объекты состояния, управляющие команды меняются. И, похоже, все в порядке, потому что шлюз и балансировщик не зависят от структуры сообщения или состояния.
2. Требования к аутентификации могут быть изменены. В случае, если шлюз запрашивает `POST /verify`, изменения не влияют на шлюз. В противном случае это влияет.