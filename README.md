# Backend Eindopdracht Library API

## Inhoudsopgave
1. [Inleiding](#inleiding)
2. [Belangrijkste functionaliteiten](#belangrijkste-functionaliteiten)
3. [Benodigdheden](#benodigdheden)
4. [Projectstructuur](#projectstructuur)
5. [Gebruikte technieken en frameworks](#gebruikte-technieken-en-frameworks)
6. [Project lokaal opzetten en draaien](#project-lokaal-opzetten-en-draaien)
7. [Configuratie](#configuratie)
8. [Database en seeddata](#database-en-seeddata)
9. [Applicatie starten in IntelliJ](#applicatie-starten-in-intellij)
10. [Tests uitvoeren](#tests-uitvoeren-in-intellij)
11. [Authenticatie en autorisatie met Keycloak](#authenticatie-en-autorisatie-met-keycloak)


---

## Inleiding
Deze repository bevat een **Java/Spring Boot Web-API** voor een bibliotheek-applicatie.  
Het doel van deze API is het beheren van bibliotheekgegevens en het aanbieden van endpoints voor CRUD-operaties en gerelateerde businesslogica.

## Belangrijkste functionaliteiten
- Beheren van bibliotheek-entiteiten (bijv. boeken, gebruikers, uitleningen — afhankelijk van de implementatie).
- Aanbieden van REST-endpoints voor data-opvraging en mutaties.
- Validatie en foutafhandeling op API-niveau.
- Integratie met een database via Spring Data/JPA.
- Geautomatiseerde tests voor kwaliteitsborging.


---

## Benodigdheden
Om dit project lokaal te draaien heb je nodig:

- **Git**
- Een IDE zoals IntelliJ IDEA
- **Java Development Kit (JDK) 25**  
  (in `pom.xml` staat: `<java.version>25</java.version`)
- **PostgreSQL** (lokaal of remote bereikbaar)
- Optioneel: **Postman** om endpoints te testen

Je hoeft Maven niet apart te installeren als je de Maven wrapper gebruikt (`mvnw` / `mvnw.cmd`).

---

## Projectstructuur

```text
Backend-Eindopdracht-Library/
├── .mvn/                         # Maven wrapper configuratie
├── mvnw                          # Maven wrapper (Linux/macOS)
├── mvnw.cmd                      # Maven wrapper (Windows)
├── pom.xml                       # Dependencies, plugins en Java-versie
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── nl/...            # Applicatiecode (controllers/services/entities/etc.)
│   │   └── resources/
│   │       ├── application.properties
│   │       └── data.sql
│   └── test/
│       └── java/
│           └── nl/...            # Tests
└── README.md
```

---

## Gebruikte technieken en frameworks

- **Java 25**
- **Spring Boot** (parent in Maven)
- **Spring Boot Starter Data JPA**  
  voor ORM en repository-laag
- **Spring Boot Starter Web MVC**  
  voor REST endpoints
- **Spring Boot Starter Validation**  
  voor request-validatie
- **PostgreSQL driver**  
  runtime databaseconnectie
- **Spring Boot test starters (JPA/Web MVC test)**  
  voor geautomatiseerde tests
- **Maven + Spring Boot Maven plugin**  
  voor build/run/package

---

## Project lokaal opzetten en draaien
### 1) Repository clonen
1. Open IntelliJ IDEA.
2. Kies **Get from VCS**.
3. Plak de repository URL:  
   `https://github.com/Marjolijn-v/Backend-Eindopdracht-Library.git`
4. Kies een lokale map.
5. Klik op **Clone**.

### 2) Maven project laden
Na openen van het project:
- IntelliJ detecteert automatisch `pom.xml`.
- Klik op **Load Maven Project** als dit gevraagd wordt.
- Wacht tot dependencies zijn gedownload en geïndexeerd.

### 3) JDK instellen
1. Ga naar **File → Project Structure → Project**.
2. Zet **Project SDK** op **JDK 25**.
3. Controleer dat **Project language level** passend staat bij de Java-versie.

### 4) PostgreSQL database aanmaken
Maak in PostgreSQL een database aan, bijvoorbeeld:
- `library_db`

---

## Configuratie

Open:
`src/main/resources/application.properties`

Controleer en/of pas de datasource-instellingen aan zodat ze naar jouw PostgreSQL verwijzen.

Voorbeeld:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/library_db
spring.datasource.username=postgres
spring.datasource.password=your_password
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

### Environment variables in IntelliJ (optioneel)
Als je liever geen wachtwoord in `application.properties` zet:

1. Ga naar **Run → Edit Configurations...**
2. Kies je Spring Boot run-configuratie.
3. Vul bij **Environment variables** in:  
   `DB_URL=jdbc:postgresql://localhost:5432/library_db;DB_USERNAME=postgres;DB_PASSWORD=your_password`
4. Gebruik in `application.properties`:
```properties
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
```

---

## Database en seeddata

In `src/main/resources/data.sql` staat seeddata voor de applicatie.

Bij opstarten kan Spring deze data inladen (afhankelijk van je SQL-init instellingen).  
Dit is handig om direct met testdata te werken.

---

## Applicatie starten in IntelliJ

### Methode 1 (aanbevolen): via main class
1. Navigeer naar de klasse met `@SpringBootApplication` (de hoofdklasse).
2. Klik op de groene **Run**-knop naast de class of `main` methode.
3. Controleer in de Run-console of de applicatie succesvol start.

### Methode 2: via Maven tool window
1. Open rechts het **Maven** venster.
2. Ga naar **Plugins → spring-boot → spring-boot:run**.
3. Dubbelklik om te starten.

Na succesvolle start draait de API meestal op:
`http://localhost:8080`

---

## Tests uitvoeren in IntelliJ

### Alle tests draaien
- Rechtsklik op de map `src/test/java`
- Kies **Run 'All Tests'**

### Één testklasse draaien
- Open de testklasse
- Klik op de groene Run-knop naast de klassenaam

### Via Maven in IntelliJ
- Open **Maven** venster
- Kies lifecycle **test** (of **verify**)

---
## Authenticatie en autorisatie met Keycloak

Deze API maakt gebruik van **Keycloak** voor authenticatie en autorisatie.  
Gebruikers loggen in via Keycloak en krijgen een token met rollen mee. De API controleert op basis van deze rollen welke acties zijn toegestaan.

### Keycloak-concept in dit project
- **Authenticatie**: inloggen via Keycloak (username/wachtwoord).
- **Autorisatie**: toegang op basis van rollen (bijv. `admin`, `employee`, `member`).
- **Token-based security**: de client stuurt een Bearer token mee in de `Authorization` header.

---

### Standaardgebruikers en autorisatieniveaus



| Gebruiker | Rol(len)        | Autorisatieniveau | Toegestane acties (voorbeeld) |
|---|-----------------|---|---|
| admin | `ROLE_ADMIN`    | Volledig beheer | CRUD op alle resources, beheerfuncties |
| librarian | `ROLE_EMPLOYEE` | Uitgebreid beheer | Boeken beheren, uitleningen beheren |
| member | `ROLE_MEMBER`   | Beperkte toegang | Eigen gegevens bekijken, boeken opvragen |

---

### Keycloak lokaal opzetten (IntelliJ workflow)

#### 1) Keycloak opzetten en starten
Je kunt Keycloak lokaal draaien (bijv. via PowerShell of Docker) en daarna inloggen op de admin console.
- Download Keycloak op de website: https://www.keycloak.org/getting-started/getting-started-zip
- Pak het zip bestand uit en navigeer in de terminal naar deze map: 
- - Voor Mac/Linux: "cd Users/pad/naar/de/map" 
- - Voor Windows: "cd c:\pad\naar\de\map"
- Keycloak opstarten met volgende commando:
- - Voor Mac/Linux: `bin/kc.sh start-dev --http-port 9090`
- - Voor Windows: `bin\kc.bat start-dev --http-port 9090`

Nu kan je navigeren naar `localhost:9090` en maak je een admin account aan, waar je op een later moment weer mee kan inloggen.



#### 2) Realm aanmaken/importeren
- Maak een realm aan (bijv. `library-realm`)
- Of importeer het realm-exportbestand: `EindopdrachtBackendLibrary-realm.json`

#### 3) Client aanmaken
Maak een client voor je backend-app, bijvoorbeeld:
- Client ID: `library-api`
- Access type/protocol volgens jouw setup (meestal OpenID Connect)
- Stel redirect/configuratie in indien nodig

#### 4) Rollen aanmaken
Maak minimaal de rollen aan die je in de API gebruikt, bijvoorbeeld:
- `ROLE_ADMIN`
- `ROLE_EMPLOYEE`
- `ROLE_MEMBER`

#### 5) Gebruikers aanmaken en rollen koppelen
- Maak standaardgebruikers aan en ken de juiste rollen toe.

---

### Applicatieconfiguratie (koppeling met Keycloak)

Voeg in `application.properties` de Keycloak/OAuth2-instellingen toe die bij jouw project passen.  
Bijvoorbeeld (indicatief):

```properties
spring.security.oauth2.resourceserver.jwt.issuer-uri=http://localhost:9090/realms/library-realm
spring.security.oauth2.resourceserver.jwt.jwk-set-uri=http://localhost:9090/realms/library-realm/protocol/openid-connect/certs
```

> Gebruik de exacte poort, realm-naam en endpoints van jouw Keycloak-instantie.

---


### Testen met token
Bij requests naar beveiligde endpoints:

1. Vraag een access token op via Keycloak (met een standaardgebruiker).
2. Voeg de header toe:
    - `Authorization: Bearer <access_token>`
3. Controleer of toegang klopt per rol (member/librarian/admin).

---

### Aanbeveling
Houd gebruikersgegevens (wachtwoorden/secrets) buiten de repository en documenteer alleen:
- gebruikersnamen voor testaccounts
- bijbehorende rollen
- verwachte autorisaties per endpoint
---

## Auteur
Marjolijn Verspiek  
Repository: https://github.com/Marjolijn-v/Backend-Eindopdracht-Library