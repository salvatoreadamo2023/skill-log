# Skill Log API

[![Java CI](https://github.com/salvatoreadamo2023/skill-log/actions/workflows/ci.yml/badge.svg?branch=feature/jwt-auth)](https://github.com/salvatoreadamo2023/skill-log/actions/workflows/ci.yml)

Backend REST sviluppato con Spring Boot per tracciare competenze, ore di studio, utenti e progetti. Il progetto include autenticazione JWT, autorizzazione per ruoli, DTO per separare API e modello dati, gestione errori centralizzata, test unitari e pipeline CI con GitHub Actions.

## Stack

- Java 17
- Spring Boot 3
- Spring Security
- JWT
- Spring Data JPA
- MySQL
- Maven
- JUnit 5
- Mockito
- GitHub Actions
- Docker Compose
- Swagger/OpenAPI

## Funzionalita

- Registrazione e login con token JWT
- Ruoli `USER` e `ADMIN`
- Password salvate con BCrypt
- CRUD Skill
- CRUD Utenti
- CRUD Progetti
- Ricerca skill per nome
- Ricerca progetti per stato
- Export Excel delle skill
- Invio QR code via email
- Integrazione OpenAI
- Risposte errore standardizzate
- Test automatici con JUnit e Mockito

## Architettura

Il progetto segue una struttura a layer:

```text
controller -> service -> repository -> database
```

Responsabilita principali:

- `controller`: espone gli endpoint REST e gestisce request/response HTTP.
- `service`: contiene logica applicativa e regole di business.
- `repository`: comunica con il database tramite Spring Data JPA.
- `model`: contiene le entity JPA.
- `dto`: definisce request e response pubbliche dell'API.
- `mapper`: converte entity interne in DTO pubblici.
- `exception`: centralizza eccezioni e risposte di errore.
- `config`: contiene configurazione Security e filtro JWT.

## Sicurezza

L'autenticazione principale usa JWT:

1. Il client chiama `/api/auth/register` o `/api/auth/login`.
2. Il backend valida le credenziali.
3. Il backend restituisce un token JWT.
4. Il client invia il token negli endpoint protetti:

```http
Authorization: Bearer <token>
```

Le password non vengono mai restituite dalle API. Gli endpoint utenti espongono `UtenteResponse`, che contiene solo:

```json
{
  "id": 1,
  "username": "salvatore",
  "ruolo": "ADMIN",
  "enabled": true
}
```

## Ruoli

- `USER`: puo accedere agli endpoint consentiti agli utenti autenticati.
- `ADMIN`: puo gestire utenti, skill, progetti, statistiche ed export.

## Error Handling

Le eccezioni sono gestite con `@RestControllerAdvice`. Le risposte errore hanno formato coerente:

```json
{
  "timestamp": "2026-06-18T11:39:30.3709324",
  "status": 404,
  "error": "Not Found",
  "message": "Utente non trovato con id: 999",
  "path": "/api/utenti/999",
  "validationErrors": null
}
```

## Setup Locale

### Prerequisiti

- JDK 17
- Maven Wrapper incluso nel progetto
- MySQL 8 oppure Docker
- STS/Eclipse o IDE equivalente

### Variabili Ambiente

Copia `.env.example` e imposta i valori reali nel tuo ambiente:

```text
SKILLLOG_DB_URL=jdbc:mysql://localhost:3306/skilllog?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
SKILLLOG_DB_USERNAME=skilllog_user
SKILLLOG_DB_PASSWORD=Password123!
JWT_SECRET=bXktc3VwZXItc2VjcmV0LWtleS1mb3Itand0LTI1Ni1iaXRzLTEyMzQ1Ng==
JWT_EXPIRATION_MS=86400000
OPENAI_API_KEY=
```

In STS puoi configurarle da:

```text
Run Configurations -> Environment
```

### Database con Docker

Avvia MySQL:

```bash
docker compose up -d
```

Ferma MySQL:

```bash
docker compose down
```

Elimina anche il volume dati:

```bash
docker compose down -v
```

### Avvio Applicazione

Da terminale:

```bash
./mvnw spring-boot:run
```

Su Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

Oppure da STS:

```text
Run As -> Spring Boot App
```

## Endpoint Principali

### Register

```http
POST /api/auth/register
Content-Type: application/json
```

```json
{
  "username": "salvatore",
  "password": "Password123!"
}
```

### Login

```http
POST /api/auth/login
Content-Type: application/json
```

```json
{
  "username": "salvatore",
  "password": "Password123!"
}
```

Risposta:

```json
{
  "token": "jwt-token",
  "tokenType": "Bearer",
  "username": "salvatore",
  "ruolo": "USER"
}
```

### Utente Corrente

```http
GET /api/auth/me
Authorization: Bearer <token>
```

## Swagger

Swagger UI e' disponibile a:

```text
http://localhost:8080/swagger-ui/index.html
```

Per testare endpoint protetti da Swagger:

1. esegui `/api/auth/login`;
2. copia il valore `token`;
3. clicca `Authorize`;
4. incolla il token JWT;
5. esegui gli endpoint protetti.

### Lista Utenti

Richiede ruolo `ADMIN`.

```http
GET /api/utenti
Authorization: Bearer <admin-token>
```

### Lista Skill

Richiede ruolo `ADMIN`.

```http
GET /api/skills
Authorization: Bearer <admin-token>
```

### Ricerca Skill

Richiede ruolo `USER` o `ADMIN`.

```http
GET /api/skills/search?nome=java
Authorization: Bearer <token>
```

## Test

Il progetto include test unitari con JUnit 5 e Mockito.

Esegui tutti i test:

```bash
./mvnw test
```

Su Windows:

```powershell
.\mvnw.cmd test
```

Esegui una singola classe:

```powershell
.\mvnw.cmd -Dtest=AuthServiceTest test
```

Esegui un singolo metodo:

```powershell
.\mvnw.cmd -Dtest=AuthServiceTest#login_shouldAuthenticateAndReturnToken test
```

Test attualmente presenti:

- `AuthServiceTest`
- `SkillServiceTest`
- `UtenteServiceTest`

## CI/CD

La pipeline GitHub Actions esegue automaticamente:

```bash
./mvnw test
```

Trigger:

- push su `feature/jwt-auth`
- push su `clean-main`
- push su `main`
- pull request verso `clean-main` o `main`

Se i test falliscono, la pipeline diventa rossa e segnala il problema prima di merge o deploy.

## Profili

Profilo default:

```text
dev
```

Puoi cambiare profilo con:

```text
SPRING_PROFILES_ACTIVE=prod
```

Nel profilo `prod`, `ddl-auto` usa `validate` di default per evitare modifiche automatiche allo schema.

## Decisioni Tecniche

- JWT al posto di Basic Auth per un flusso piu adatto a frontend e client esterni.
- DTO per evitare esposizione delle entity JPA e di dati sensibili come password hash.
- `@RestControllerAdvice` per risposte errore coerenti.
- Unit test sui service per verificare la business logic isolando repository e security.
- GitHub Actions per eseguire automaticamente la suite di test a ogni push.

## Possibili Miglioramenti

- Test controller con MockMvc
- Test security su ruoli e token
- Integration test con Testcontainers
- Migrazioni database con Flyway o Liquibase
- Refresh token
- Dockerfile applicativo
- Deploy cloud su AWS
