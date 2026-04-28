<p align="center">
  <img
    src="https://readme-typing-svg.demolab.com?font=JetBrains+Mono&size=26&duration=2800&pause=800&color=A855F7&center=true&vCenter=true&width=800&lines=SkillArcade%3A+Gamify+Your+Growth;Quests%2C+XP%2C+and+Trophies"
    />
  <br/>
  <b/>SkillArcade</b><br>
  <i>Gamify Your Growth.</i>
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Kotlin-2.2.10-purple?logo=kotlin" alt="Kotlin" />
  <img src="https://img.shields.io/badge/Compose-2026.02.01-green?logo=jetpackcompose" alt="Compose" />
  <img src="https://img.shields.io/badge/Hilt-2.59.2-blue?logo=dagger" alt="Hilt" />
  <img src="https://img.shields.io/badge/Room-2.8.4-brightgreen?logo=sqlite" alt="Room" />
</p>

---

## 🕹️ Summary
SkillArcade is a mobile learning platform designed to bridge the gap between education and entertainment. Built with a **Neo-Boutique Arcade** aesthetic, it provides a highly interactive environment where users track progress through XP, unlock trophies, and complete goals via a localized, offline-first architecture.

> [!WARNING]
> This project is currently in a "Local-First" phase. Persistence is handled via Room, but no cloud sync is active. Your data stays on your device.

## 🎙️ Narrative
SkillArcade was born out of frustration with dry, uninspiring learning platforms. I wanted something that felt like playing a game—where every lesson is a quest and every goal achieved is a high score. Traditional LMS apps feel like homework; SkillArcade feels like a reward. It's about turning the cognitive load of learning into the dopamine hit of an arcade victory.

## 🛠️ Technical Stack

| Category | Technology | Version |
| :--- | :--- | :--- |
| **Language** | Kotlin | 2.2.10 |
| **UI Framework** | Jetpack Compose (BOM) | 2026.02.01 |
| **Dependency Injection** | Hilt | 2.59.2 |
| **Database** | Room | 2.8.4 |
| **Navigation** | Navigation Compose | 2.9.8 |
| **Image Loading** | Coil | 3.0.0-rc01 |
| **Architecture** | Clean Architecture / MVI-ish | - |

## 📐 Document Structure & Architecture
The project follows a strict separation of concerns to ensure efficiency and maintainability.

![SkillArcade System Architecture](docs/architecture/skillarcade-system-architecture.png)

Additional architecture diagrams are stored in `docs/architecture/`:

* [User Journey / App Flow](docs/architecture/skillarcade-user-journey-app-flow.png)
* [MVVM Data Flow](docs/architecture/skillarcade-mvvm-data-flow.png)
* [Lesson Completion Sequence](docs/architecture/skillarcade-lesson-completion-sequence.png)

## 🚀 Setup Instructions
Follow these steps to deploy the arcade environment to your local machine:

1. **Clone the repository:**
   ```bash
   git clone https://github.com/Inaolol/SkillArcade.git
   ```
2. **Open in Android Studio:**
   Use an Android Studio version that supports the project AGP version (**9.0.0**). If sync fails on an older IDE, see [`gradle/libs.versions.toml`](gradle/libs.versions.toml) and set `agp` to a version supported by that Android Studio installation.
3. **Sync Gradle:**
   Allow the IDE to download dependencies defined in `libs.versions.toml`.
4. **Run Build:**
   ```bash
   ./gradlew assembleDebug
   ```

## 🎮 Usage
The user remains in total control of their learning path. 

*   **Navigate** through the `Course Catalog` to find new quests.
*   **Track** progress in the `Home Dashboard`.
*   **Complete** goals to trigger the `GoalEvaluator` logic.
*   **Collect** trophies in the `Trophy Room`.

**Key Metrics:**
*   **100% Offline Capability**: All data resides in the local Room database.
*   **Zero Latency UI**: State management ensures immediate feedback for XP updates.
*   **Type-Safe Navigation**: Utilizing Kotlin Serialization for robust routing.

> [!NOTE]
> Efficiency is our priority. Composable functions are kept small and modular to ensure a **60 FPS** UI performance even on entry-level devices.

---
