# Rick and Morty Characters

<div align="center">
  <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/b/b1/Rick_and_Morty.svg/1200px-Rick_and_Morty.svg.png" width="300"/>
  
  ![Kotlin](https://img.shields.io/badge/Kotlin-1.9.23-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)
  ![Android](https://img.shields.io/badge/Android-24+-3DDC84?style=for-the-badge&logo=android&logoColor=white)
  ![Jetpack Compose](https://img.shields.io/badge/Jetpack_Compose-BOM_2024.05-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white)
  ![License](https://img.shields.io/badge/License-MIT-yellow?style=for-the-badge)
</div>

---

## About

Android application displaying characters from the **Rick and Morty** universe. Built with modern Android development stack following Clean Architecture principles.

---

## Screenshots

> *Screenshots will be added after final release build*

---

## Features

- **Character List** — browse all characters with status filter (All / Alive / Dead / Unknown)
- **Character Details** — full biography with image, origin, location and episode count
- **Favourites** — mark characters as favourite, they float to the top of the list
- **Offline support** — data cached in SQLDelight database on first launch
- **Pull-to-refresh** — update data from API at any time
- **Dark theme** — Rick and Morty inspired dark colour palette

---

## Architecture

The project follows **Clean Architecture** with **MVVM** pattern, organized as a multi-module project.

```
RickAndMorty/
├── app/                    # Single Activity, Navigation, DI, Theme
├── core/
│   ├── model/              # Domain models (Character, CharacterStatus)
│   ├── network/            # Retrofit + OkHttp, DTOs, NetworkModule
│   └── database/           # SQLDelight schema, DatabaseModule
└── feature/
    ├── list/               # Character list screen (data / domain / presentation)
    └── details/            # Character details screen (data / domain / presentation)
```

### Layer breakdown

| Layer | Responsibility |
|---|---|
| `data` | Repository implementations, network/DB mappers |
| `domain` | Repository interfaces, Use Cases |
| `presentation` | ViewModel, State, UI (Compose) |

---

## Tech Stack

| Category | Technology |
|---|---|
| Language | Kotlin 1.9.23 |
| UI | Jetpack Compose + Material 3 |
| Architecture | MVVM + Clean Architecture + State Hoisting |
| DI | Hilt 2.51.1 |
| Navigation | Compose Navigation |
| Network | Retrofit 2.11 + OkHttp 4.12 |
| Database | SQLDelight 2.0.2 |
| Image Loading | Coil 2.6.0 |
| Async | Kotlin Coroutines + Flow |
| Build | Gradle 8.9 + AGP 8.3.2 + Version Catalog |

---

## API

Uses the free [Rick and Morty API](https://rickandmortyapi.com/) — no API key required.

| Endpoint | Description |
|---|---|
| `GET /api/character?page={n}` | Paginated character list |
| `GET /api/character/{id}` | Single character details |

---

## Data Flow

```
API (Retrofit) ──► Repository ──► SQLDelight DB ──► Flow ──► ViewModel ──► UI
                        │
                   Cache on first launch
                   isFavorite preserved on refresh
```

On first launch data is fetched from the API and cached. On subsequent launches the cache is shown immediately, with pull-to-refresh available for updates.

---

## Getting Started

### Prerequisites
- Android Studio Hedgehog or newer
- JDK 17+ (bundled with Android Studio)
- Android device or emulator with API 24+

### Build & Run

```bash
git clone https://github.com/cringe/RickAndMorty.git
cd RickAndMorty
./gradlew assembleDebug
```

Or simply open the project in Android Studio and press **Run**.

---

## Module Dependencies

```
app
 ├── feature:list
 │    ├── core:model
 │    ├── core:network
 │    └── core:database
 ├── feature:details
 │    ├── core:model
 │    ├── core:network
 │    └── core:database
 ├── core:model
 ├── core:network
 └── core:database
```

---

## Favourite System

Favourites are stored as an `isFavorite` flag in the SQLDelight database. Toggling a favourite on the details screen immediately reorders the character list — favourited characters always appear at the top, sorted alphabetically within each group.

---

<div align="center">
  Made with ❤️ and a portal gun
</div>
