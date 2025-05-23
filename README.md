# 🧮 Scientific Calculator (Java Swing + PostgreSQL)

A desktop scientific calculator built with **Java Swing** for the user interface and integrated with **PostgreSQL** to log all calculations performed.

---

## 🚀 Features

- Basic arithmetic operations: `+`, `-`, `*`, `/`
- Scientific functions: `sin`, `cos`, `tan`, `log`, `sqrt`, `exp`, `abs`, factorial (`!`), power (`^`)
- Button-based input with an intuitive GUI
- Expression evaluation using JavaScript engine
- Logs every calculation (expression and result) into a PostgreSQL database

---

## 🛠 Requirements

- Java 8 or higher (tested up to Java 21)
- PostgreSQL 13 or higher (tested with PostgreSQL 17.5)
- PostgreSQL JDBC Driver (e.g., `postgresql-42.7.1.jar`)  
  Download from: [https://jdbc.postgresql.org/download.html](https://jdbc.postgresql.org/download.html)

---

## 🗃 PostgreSQL Setup

1. Open terminal and switch to the `postgres` user:
   ```bash
   sudo -u postgres psql
