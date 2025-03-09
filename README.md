# Financial Tracker

📌 **Ссылки:**  
- 🔗 [Pull Request](https://github.com/DKowalski25/Y_LAB/pull/11)  
- 📂 [Репозиторий](https://github.com/DKowalski25/Y_LAB/tree/dev/PersonalFinancialTracker)  

**Financial Tracker** — это консольное приложение для управления личными финансами. Оно позволяет пользователям отслеживать свои доходы и расходы, устанавливать финансовые цели, управлять бюджетом и анализировать свои финансовые привычки. Также предоставляет администратору возможность управлять пользователями (блокировка, удаление).

## 📌 Содержание

- [Как скачать проект](#как-скачать-проект)
- [Как запустить проект](#как-запустить-проект)
- [Как запустить тесты](#как-запустить-тесты)
- [Описание основных методов](#описание-основных-методов)
- [Структура проекта](#структура-проекта)
- [Зависимости](#зависимости)
- [Лицензия](#лицензия)

---

## 🔹 Как скачать проект

1. Убедитесь, что у вас установлен **Git**. Если нет, скачайте и установите его с [официального сайта](https://git-scm.com/).
2. Откройте терминал или командную строку.
3. Выполните команду для клонирования репозитория:

   ```bash
   git clone https://github.com/ваш-username/ваш-репозиторий.git
   ```

4. Перейдите в директорию проекта:

   ```bash
   cd ваш-репозиторий
   ```

---

## 🚀 Как запустить проект

1. Убедитесь, что у вас установлен **JDK 17** или выше. Если нет, скачайте и установите его с [официального сайта](https://jdk.java.net/).
2. Откройте терминал или командную строку в директории проекта.
3. Соберите проект с помощью Gradle:

   ```bash
   ./gradlew build
   ```

4. Запустите приложение:

   ```bash
   ./gradlew run
   ```

После запуска в консоли появится меню для регистрации, авторизации и работы с финансами.

---

## 🛠️ Как запустить тесты

1. Убедитесь, что проект собран (см. [Как запустить проект](#как-запустить-проект)).
2. Запустите тесты командой:

   ```bash
   ./gradlew test
   ```

3. После завершения тестов можно просмотреть отчет о покрытии кода тестами (**Jacoco**).  
   Отчет находится в:

   ```
   build/jacocoHtml/index.html
   ```

   Откройте этот файл в браузере.

---

## 📌 Описание основных методов

### **UserHandler**
- `registerUser()`: Регистрирует нового пользователя (имя, email, пароль).
- `loginUser()`: Авторизует пользователя (email, пароль).
- `updateProfile()`: Обновляет профиль пользователя (имя, email, пароль).
- `deleteAccount()`: Удаляет аккаунт.

### **TransactionHandler**
- `addTransaction()`: Добавляет доход или расход.
- `editTransaction()`: Редактирует существующую транзакцию.
- `viewTransactions()`: Показывает список транзакций с фильтрацией (категория, дата, тип).
- `deleteTransaction()`: Удаляет транзакцию по ID.

### **BudgetHandler**
- `setBudget()`: Устанавливает месячный бюджет.
- `viewBudget()`: Показывает текущий бюджет.
- `calculateCurrentBalance()`: Рассчитывает текущий баланс.
- `calculateIncomeAndExpenses()`: Считает доходы и расходы за период.
- `analyzeExpensesByCategory()`: Анализирует расходы по категориям.
- `generateFinancialReport()`: Генерирует финансовый отчет.

### **GoalHandler**
- `addGoal()`: Добавляет новую финансовую цель.
- `viewGoals()`: Показывает цели и прогресс.
- `deleteGoal()`: Удаляет цель.

### **AdminHandler**
- `viewUsers()`: Показывает список пользователей.
- `blockUser()`: Блокирует пользователя по ID.
- `deleteUser()`: Удаляет пользователя по ID.

---

## 📂 Структура проекта

```
dev.personal.financial.tracker
│── controller     # Контроллеры для бизнес-логики
│── model          # Модели данных (User, Transaction, Budget, Goal)
│── repository     # Репозитории для работы с данными
│── service        # Сервисы для обработки логики
│── UI             # Пользовательский интерфейс (консольное меню)
│── util           # Вспомогательные утилиты (ConsolePrinter и др.)
```

---

## 🔧 Зависимости

- **Lombok** — для автоматической генерации геттеров, сеттеров и конструкторов.
- **JUnit 5** — для написания тестов.
- **Mockito** — для мокирования зависимостей в тестах.
- **Jacoco** — для анализа покрытия кода тестами.

---
