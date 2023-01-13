# Курсовой проект "Сетевой чат"

Чат состоит из двух частей: Server side, Client side.

* Клиент, перед тем как попасть в чат, должен ввести уникальное имя.

* Все входящие сообщения логируются в файл log.txt.

* При инициализации, настройки порта и сокета достаются из файла settings.txt

---

## Server

<code><strong>class ChatServerSideApplication</strong></code> стартовый класс чата серверной стороны.

* Инициализирует серверный чат перед стартом, устанавливает все необходимые настройки.

* Запускает приложение, слушает порт для подключение нового клиентского сокета, создаёт для каждого соединения новый поток.

<code><strong>class ChatService</strong></code>

 * хранит участников чата.
 
 * проверяет и рассылает сообщения.
 
 * добавляет новых участников.
 
 <code><strong>interface Logger</strong></code> интерфейс для записи логов событий.
 
 <code><strong>class Loggers</strong></code> утилитный класс с фабричными методами.
 
 * служит для реализации интерфейса Logger.
 
 <code><strong>interface Client</strong></code>
 
 *  служит для инкапсуляции сокета, ввода и вывода, и других данных клиента.
 
 <code><strong>class Clients</strong></code> - Утилитный класс
 
 * служит для реализации интерфейса Client.
 
 <code><strong>class Auth</strong></code> - Утилитный класс
 
 *  служит для этапа преверки имени клиента и регистрации в системе.

---

## Client

<code><strong>class ClientSideApplication</strong></code> - корневой загрузочный объект клиента.
 * Инициализарует и запускает соединения с сервером.
 * Обрабатывает ввод сообщений чата.

<code><strong>class ChatService</strong></code>
 * класс служит для отправки сообщений на сервер по сокету.
 * создаёт и запусткает поток в котором сокет принимает сообщения от сервера форматирует и отбражает в терменале.

<code><strong>interface Logger</strong></code>
 * интерфейс для записи логов событий.

<code><strong>class Loggers</strong></code> утилитный класс с фабричными методами
 * служит для реализации интерфейса Logger.

