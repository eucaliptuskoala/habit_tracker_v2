# Habit Tracker v2 — Project Overview

## 1. Project Overview

A full-stack **habit tracking** application that lets users create habits, log daily progress, take notes with markdown, discover public notes via a "For You" feed, and visualize their consistency over time.

### Tech Stack

| Layer | Technology |
|---|---|
| **Backend** | Java 17, Spring Boot 3.5.5, Spring Security, Spring Data JPA |
| **Frontend** | React 19, Vite 7, Tailwind CSS 4, Recharts, Nivo Calendar |
| **Database** | MySQL 8 (via Docker), Flyway migrations |
| **Auth** | JWT (jjwt 0.11.5) |
| **Build** | Gradle (backend), Vite (frontend) |
| **Testing** | JUnit 5 + JaCoCo (backend), Vitest + Testing Library (frontend), Playwright (e2e) |
| **Infra** | Docker Compose (MySQL + backend), AWS references (Dockerrun, GitLab CI) |

### Key Dependencies (Backend)

- `spring-boot-starter-web`, `data-jpa`, `security`, `validation`, `actuator`
- `io.jsonwebtoken:jjwt` for JWT creation/validation
- `mysql-connector-j` for MySQL connectivity
- `flyway-core` + `flyway-mysql` for schema migrations
- `lombok` to reduce boilerplate

### Key Dependencies (Frontend)

- `react-router-dom` for routing
- `axios` for HTTP + request interceptor (auto-attaches JWT)
- `@uiw/react-md-editor` for markdown note editing/rendering
- `recharts` + `@nivo/calendar` for progress charts and heatmap calendar
- `jwt-decode` for extracting `userId` from JWT on the client
- `@mui/material`, `@material-tailwind/react` (installed but minimally used)
- `tailwindcss` v4 for styling

---

## 2. Architecture

### Folder Structure

```
backend/
├── src/main/java/org/example/habit_tracker/
│   ├── HabitTrackerApplication.java       # Entry point
│   ├── domain/                             # Pure domain POJOs (no framework annotations)
│   │   ├── users/User.java
│   │   ├── habits/Habit.java, HabitTemplate.java
│   │   ├── notes/Note.java
│   │   └── progress/HabitProgress.java
│   ├── business/                           # Use cases & business logic
│   │   ├── repos/                          # Repository interfaces (port)
│   │   ├── exceptions/                     # Custom exceptions
│   │   ├── usercases/                      # User CRUD use cases
│   │   ├── signincases/                    # Authentication use case
│   │   ├── habitcases/                     # Habit CRUD + streak update
│   │   │   └── creationstrategy/           # Strategy pattern for habit creation
│   │   ├── habitprogress/                  # Progress tracking
│   │   ├── habittemplatecases/             # Template management + popularity
│   │   └── noteusecases/                   # Note CRUD + FYP recommendation
│   │       └── fypstrategy/               # Strategy pattern for recommendations
│   ├── persistence/                        # Data access (adapter)
│   │   ├── entities/                       # JPA entity classes
│   │   ├── jparepos/                       # Spring Data JPA interfaces
│   │   ├── converters/                     # Domain <-> Entity converters
│   │   └── repositories/                   # Repository implementations
│   ├── controller/                         # REST endpoints
│   │   ├── dto/                            # Request/Response DTOs
│   │   └── mappers/                        # Domain <-> DTO mappers
│   └── configuration/                      # Security, JWT, CORS, exception handling
│       └── security/SecurityConfig.java
├── src/main/resources/
│   ├── application.properties
│   ├── db/migration/                       # Flyway V1-V6
│   └── requests.json                       # Sample request body
├── Dockerfile, docker-compose.yml, Dockerrun.aws.json
└── build.gradle, settings.gradle

frontend-web/
├── src/
│   ├── main.jsx, App.jsx, index.css
│   ├── pages/                              # Route-level components
│   ├── components/
│   │   ├── habit/                          # Habit cards, modals, empty state
│   │   ├── habitprogress/                  # Charts & calendar
│   │   ├── note/                           # Note cards, create/update modals
│   │   ├── template/                       # Habit template card
│   │   ├── fyp/                            # For You note display
│   │   └── visual/                         # Header, Navbar, Toasts
│   ├── apis/                               # Axios API wrappers
│   └── assets/                             # SVG illustrations
├── e2e/                                    # Playwright tests
└── package.json, vite.config.js
```

### Layers (Hexagonal / Clean Architecture Lite)

```
Controller (REST)  →  Use Case (business logic)  →  Repository Interface (port)
                                                        ↓
                                               Repository Impl (adapter)  →  JPA Repo  →  DB
```

- **Domain layer**: Pure POJOs with Lombok, zero framework dependencies
- **Business layer**: Use case interfaces in `business/` with `Impl` classes containing all business rules. Uses Strategy pattern for habit creation and note recommendations.
- **Persistence layer**: JPA entities (framework-coupled), converters (map domain ↔ entity), repository implementations (implement business-layer repository interfaces)
- **Controller layer**: Thin REST endpoints, delegates to use cases, uses mappers for DTO conversion

### Design Patterns Used

| Pattern | Where |
|---|---|
| **Strategy** | `HabitCreationStrategyService` — routes between `CustomHabitCreationStrategy` and `TemplateHabitCreationStrategy` based on whether `habitTemplateId` is provided |
| **Strategy** | `RecommendationService` — routes between `HabitBasedRecommendation` and `DefaultRecommendationStrategy` based on whether user has habits |
| **Converter** | `persistence/converters/` — separate classes to map between domain objects and JPA entities, keeping layers decoupled |
| **Repository (Port-Adapter)** | `business/repos/` interfaces define the contract; `persistence/repositories/` implement them |
| **Dependency Injection** | Constructor injection throughout, `@Qualifier` for strategy beans |

---

## 3. Data Models

### User (`users` table)

| Field | Type | Constraints |
|---|---|---|
| id | BIGINT | PK, AUTO_INCREMENT |
| name | VARCHAR(20) | NOT NULL |
| email | VARCHAR(80) | NOT NULL, UNIQUE |
| password | VARCHAR(255) | NOT NULL (BCrypt encoded) |
| is_admin | BOOLEAN | NOT NULL |

### HabitTemplate (`habit_templates` table)

| Field | Type | Constraints |
|---|---|---|
| id | BIGINT | PK, AUTO_INCREMENT |
| name | VARCHAR(20) | NOT NULL |
| popularity | INT | NOT NULL (incremented each time a habit is created from this template) |

### Habit (`habits` table)

| Field | Type | Constraints |
|---|---|---|
| id | BIGINT | PK, AUTO_INCREMENT |
| name | VARCHAR(20) | NOT NULL |
| description | VARCHAR(255) | NOT NULL |
| streak | INT | NOT NULL |
| last_updated_streak | DATETIME | NULLABLE |
| threshold_days | INT | NOT NULL (default 1) |
| habit_template_id | BIGINT | FK → habit_templates(id), NULLABLE |
| creator_id | BIGINT | FK → users(id) ON DELETE CASCADE |

### Note (`notes` table)

| Field | Type | Constraints |
|---|---|---|
| id | BIGINT | PK, AUTO_INCREMENT |
| title | VARCHAR(255) | NOT NULL |
| content | TEXT | NOT NULL (markdown) |
| personal_feeling | INT | NOT NULL (0-10 scale?) |
| is_public | BOOLEAN | NOT NULL |
| creator_id | BIGINT | FK → users(id) ON DELETE CASCADE |
| habit_id | BIGINT | FK → habits(id) ON DELETE CASCADE |
| created_at | DATETIME | NOT NULL |
| updated_at | DATETIME | NOT NULL |

### HabitProgress (`habits_progress` table)

| Field | Type | Constraints |
|---|---|---|
| id | BIGINT | PK, AUTO_INCREMENT |
| habit_id | BIGINT | FK → habits(id), NOT NULL |
| date | DATE | NOT NULL |
| streak_value | INT | NOT NULL |
| created_at | DATETIME | NOT NULL |
| | | UNIQUE(habit_id, date) |

### Entity Relationships

```
User 1──* Habit (creator)
User 1──* Note (creator)
Habit 1──* Note (parent habit)
Habit 1──* HabitProgress (daily entries)
Habit *──1 HabitTemplate (optional — templates are crowd-sourced)
```

---

## 4. Features Implemented

### User Management
- [x] Create account (name, email, password)
- [x] Get all users, get user by ID
- [x] Update user details
- [x] Delete user
- [x] Password encoded with BCrypt via Spring Security's `PasswordEncoderFactories`

### Authentication
- [x] JWT-based sign-in (`POST /auth/sign_in`)
- [x] Token contains email (subject) + userId (custom claim), expires in 24h
- [x] JWT extracted from `Authorization: Bearer <token>` header
- [x] Stateless sessions (no HTTP session)
- [x] CORS configured for frontend URL (via `frontend.url` env var)
- [x] Non-authenticated routes: `/auth/**`, `POST /users`, `/actuator/health`
- [x] Frontend auto-attaches JWT via Axios interceptor

### Habits
- [x] Create habit from template (select a pre-existing template)
- [x] Create custom habit (free-form name + description)
- [x] List all habits for the authenticated user (`GET /habits/my`)
- [x] Delete habit (with ownership check via `@PreAuthorize`)
- [x] Update streak — increments by 1, prevents double-counting same day, auto-creates a HabitProgress entry
- [x] Duplicate habit name detection per user
- [x] Name normalization (trim, lowercase, capitalize first letter)

### Habit Templates
- [x] List all templates, sorted by popularity descending
- [x] List top 10 popular templates
- [x] Auto-create template when >3 users create a habit with the same name (via `CreateHabitTemplateUseCaseImpl`)
- [x] Popularity counter incremented on template-based habit creation

### Notes
- [x] Create note with title, content (markdown), personal feeling score, public/private flag, linked to a habit
- [x] List user's own notes
- [x] Update note (title, content, feeling, visibility)
- [x] Delete note (with ownership check)
- [x] Markdown rendering using `@uiw/react-md-editor`

### "For You" Page (FYP)
- [x] Shows public notes from other users
- [x] **Strategy**: If user has habits → filter notes by habits with matching names (habit-based recommendation)
- [x] **Strategy**: If user has no habits → show all public notes (default recommendation)
- [x] Doesn't show user's own notes
- [x] Paginated navigation (prev/next with counter)

### Progress Tracking
- [x] Daily habit progress entries created automatically when streak is updated
- [x] Timeline builder fills in gaps (days without progress carry forward the streak; resets to 0 if gap exceeds `thresholdDays`)
- [x] Line chart per habit showing streak values over time (Recharts)
- [x] Activity calendar heatmap showing overall activity (Nivo Calendar)
- [x] Data grouped by habit for individual visualization

### Frontend Pages
| Route | Component | Description |
|---|---|---|
| `/` | TitlePage | Landing page with feature overview |
| `/sign-up` | SignUpPage | Registration + onboarding habit selection |
| `/sign-in` | SignInPage | Login form |
| `/dashboard` | DashboardPage | Habit cards, create habit, note creation after streak update |
| `/notes` | NotesPage | Note CRUD with modals |
| `/fyp` | ForYouPage | Public note recommendations |
| `/progress` | ProgressPage | Charts + activity calendar |

### DevOps
- [x] Docker Compose for MySQL + backend
- [x] Dockerfile (multi-stage commented out, single-stage active)
- [x] GitLab CI config
- [x] SonarQube integration with JaCoCo coverage

---

## 5. API Endpoints

### Authentication
| Method | Path | Auth | Description |
|---|---|---|---|
| `POST` | `/auth/sign_in` | No | Sign in, returns JWT token |

### Users
| Method | Path | Auth | Description |
|---|---|---|---|
| `POST` | `/users` | No | Create user account |
| `GET` | `/users` | Yes | List all users |
| `GET` | `/users/{id}` | Yes | Get user by ID |
| `PUT` | `/users/{id}` | Yes | Update user (name, email, password) |
| `DELETE` | `/users/{id}` | Yes | Delete user |

### Habits
| Method | Path | Auth | Description |
|---|---|---|---|
| `POST` | `/habits` | Yes | Create habit (custom or from template) |
| `GET` | `/habits/my` | Yes | Get current user's habits |
| `PUT` | `/habits/{id}` | Yes | Update streak (+1, ownership check) |
| `DELETE` | `/habits/{id}` | Yes | Delete habit (ownership check) |

### Habit Templates
| Method | Path | Auth | Description |
|---|---|---|---|
| `GET` | `/habit_templates` | Yes | List all templates (sorted by popularity) |
| `GET` | `/habit_templates/popular` | Yes | List top 10 popular templates |

### Habit Progress
| Method | Path | Auth | Description |
|---|---|---|---|
| `GET` | `/habits_progress` | Yes | Get progress for current user (dates optional but unused) |

### Notes
| Method | Path | Auth | Description |
|---|---|---|---|
| `POST` | `/notes` | Yes | Create note |
| `GET` | `/notes/my` | Yes | Get current user's notes |
| `PUT` | `/notes/{id}` | Yes | Update note (ownership check) |
| `DELETE` | `/notes/{id}` | Yes | Delete note (ownership check) |
| `GET` | `/notes/fyp` | Yes | Get "For You" public notes |

### Health
| Method | Path | Auth | Description |
|---|---|---|---|
| `GET` | `/actuator/health` | No | Spring Actuator health check |

---

## 6. Key Design Decisions

### Strategy Pattern for Habit Creation
`HabitCreationStrategyService` inspects whether `CreateHabitRequest.habitTemplateId` is null. If null, it uses `CustomHabitCreationStrategy` (free-form name). If present, it uses `TemplateHabitCreationStrategy` (name from template, auto-increments popularity). This avoids a single monolithic creation method with conditionals.

### Strategy Pattern for FYP Recommendations
`RecommendationService` checks whether the user has habits. If they do, `HabitBasedRecommendation` filters public notes to those linked to habits the user also tracks. If they don't, `DefaultRecommendationStrategy` returns all public notes. This provides personalized vs. generic content based on onboarding state.

### Separate Domain and Persistence Models
Domain POJOs (`domain/`) have no JPA annotations. JPA entities (`persistence/entities/`) are framework-coupled. Converters (`persistence/converters/`) map between them. This keeps the business layer clean and testable, though the current domain objects are nearly identical to entities (no rich behavior beyond getters/setters).

### Ownership-Based Security
Spring method-level security with `@PreAuthorize` uses custom security beans (`HabitSecurity`, `NotesSecurity`) that check ownership via `existsByHabitIdAndCreatorEmail` / `existsByNoteIdAndCreatorEmail`. This prevents users from modifying/deleting others' data at the controller level.

### Flyway Migrations
Database schema is version-controlled via Flyway (V1-V6). DDL auto-generation (`ddl-auto=none`) is disabled — schema changes require explicit migration scripts. This is production-friendly but adds overhead during development.

### Threshold-Based Streak Decay
`thresholdDays` on each habit determines how many days can pass before the streak resets to 0. The `HabitProgressTimelineBuilder` fills in gaps in the timeline and resets streak when the gap exceeds the threshold. Currently hardcoded to 1 in both creation strategies.

---

## 7. Known Gaps / TODOs

### Backend
1. **`@NotBlank` on `boolean isAdmin`** (`CreateUserRequest.java:27`) — `@NotBlank` is a string annotation and won't compile/work on a boolean.
2. **Commented-out multi-stage Docker build** — The Dockerfile has an older multi-stage build commented out (lines 1-10), with only the single-stage version active.
3. **`from`/`to` parameters never wired** — `HabitProgressController.getProgressForUser()` passes `null, null` for `from`/`to` even though the use case supports filtering. The endpoint always returns all progress.
4. **`StreakValidator` exists but is never called** — The `StreakValidator` class has logic to reset streaks when gap exceeds threshold, but it's not injected anywhere in the use cases. The streak-reset logic only exists in the timeline builder (read-only).
5. **`TemplateNotFoundException` handler uses wrong type** — `GlobalExceptionHandler.java:23` catches `TemplateNotFoundException` but declares parameter as `UserNotFoundByEmailException`.
6. **`NoteNotFoundByIdException` not handled** — The exception class exists but there's no `@ExceptionHandler` in `GlobalExceptionHandler`.
7. **No pagination on any list endpoint** — All list endpoints return unbounded results.
8. **No request validation on most endpoints** — `CreateUserRequest` has `@NotBlank`/`@Email` annotations but controller doesn't have `@Valid`.
9. **`sign.jsx` unused** — Old/deprecated component still in the frontend `pages/` directory.
10. **No `.env` file in the repository** — The app reads from `.env[.properties]` but none is committed (expected for security).
11. **Progress controller returns timeline without IDs for generated gaps** — The timeline builder creates `HabitProgress` objects for days without entries; these have `id = null` and aren't persisted.
12. **`Dockerrun.aws.json`** exists but no other AWS deployment config.

### Frontend
1. **Multiple toast implementations** — `DashboardPage` and `SignInPage`/`SignUpPage` render inline error/success divs. `Toasts.jsx` component exists but isn't consistently used.
2. **Console log left in** — `UserActivityCalendar.jsx` has `console.log`, `DashboardPage` logs habits data.
3. **`handleUpdateStreak` creates notes after streak** — The frontend automatically opens the note modal after updating a streak. This is UX-coupled behavior enforced at the UI layer.
4. **Derived state stored alongside raw data** — `ProgressPage` stores both `progress` and derived `progressPerHabit`/`contribution` in separate state variables instead of computing via `useMemo`.
5. **Loading state is just a text "Loading..."** — no spinner or skeleton.

---

## 8. What's Missing

### Essential Habit Tracker Features

| Feature | Notes |
|---|---|
| **Edit habit** | No endpoint or UI to edit habit name/description/threshold |
| **Habit frequency / scheduling** | `thresholdDays` exists but always defaults to 1; no UI for configuring it; no concept of weekly/monthly habits |
| **Streak freeze / grace days** | No "days off" or vacation mode |
| **Notifications / reminders** | No push, email, or in-app reminders |
| **Email verification** | No email confirmation on sign-up |
| **Password reset** | No "forgot password" flow |
| **Date range filtering for progress** | Backend use case supports `from`/`to` params but controller ignores them |
| **Habit completion status** | No way to mark a day as "missed" vs. "not yet done" |
| **Multiple habit categories/groups** | No tagging or grouping |
| **Admin dashboard** | `isAdmin` field exists but no admin-specific features or endpoints |
| **Data export** | No CSV/JSON export of habits or notes |
| **Social features** | No following, likes, or comments on public notes |
| **Mobile responsiveness** | Tailwind classes suggest responsive intent, but no dedicated mobile layout testing |
| **Error boundaries** | No React error boundaries |
| **Offline support** | No PWA or offline caching |
| **Rate limiting** | No API rate limiting |
| **Input sanitization** | No HTML escaping on note content (markdown is rendered as-is) |
| **Automated tests (backend)** | Only test dependencies are declared; no test source files found |
| **CI/CD pipeline** | GitLab CI config exists but may not be fully wired |

### Nice-to-Have

- Habit streaks with milestones / achievements
- Weekly/monthly reports (PDF or in-app)
- Dark mode
- Habit reordering / pinning
- Search / filter for notes
- Note tags or categories
- Profile picture upload
- OAuth login (Google, GitHub)
- API documentation (Swagger/OpenAPI)
