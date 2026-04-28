# SkillArcade Implementation Log

## 2026-04-28 - Foundation Slice

### What Was Implemented
- Build configuration: KSP, Hilt 2.59.2, Room 2.8.4, Navigation Compose, Coroutines, kotlinx-serialization
- Domain layer: Course, Lesson, Goal, Trophy, UserProgress models; SkillArcadeRepository interface; ProgressCalculator and GoalEvaluator business logic
- UI Theme: ArcadeColors (InkBlack, PrimaryYellow, AccentPink, AccentMint, Cream); ClashDisplay and Epilogue typography; arcadeBorderShadow modifier for consistent arcade styling
- Data layer: Room entities, DAOs, SkillArcadeDatabase with type-safe queries; SampleDataSeeder for 3 courses, 10 lessons, 5 goals, 8 trophies
- UI Components: ArcadeButton (Primary/Success/Destructive), ArcadeCard, ArcadeProgressBar, ArcadeChip, BottomNavBar with type-safe navigation
- DI: Hilt modules for Room database singleton and repository bindings
- Navigation: Type-safe Navigation Compose routes (Splash, Onboarding, Home, CourseCatalog, CourseProgress, LessonPlayer, Goals, TrophyRoom); full route graph with MainScaffold
- Unit tests: 28 JVM tests covering ProgressCalculator, GoalEvaluator, and repository patterns (all passing)

### Why This Was Implemented
Foundation work establishes the architectural backbone (MVVM + Hilt + Room + Jetpack Compose) needed for all subsequent UI features. Type-safe navigation, reusable components, and business logic are prerequisites for building individual screens.

### Problem It Solves
Without foundation layers, screen-building work would require repeated setup, inconsistent styling, and unreliable data management. This foundation enables rapid, consistent feature development.

### Files Changed
- `gradle/libs.versions.toml`, `app/build.gradle.kts`: Dependencies and plugins
- `domain/model/`, `domain/repository/`, `domain/usecase/`: Business models and logic
- `ui/theme/`: Theme, colors, typography, tokens, extensions
- `data/local/entities/`, `data/local/dao/`, `data/local/SkillArcadeDatabase.kt`, `data/local/Mappers.kt`, `data/seed/SampleDataSeeder.kt`: Room database layer
- `data/repository/SkillArcadeRepositoryImpl.kt`: Data access implementation
- `ui/components/`: ArcadeButton, ArcadeCard, ArcadeProgressBar, ArcadeChip, BottomNavBar
- `di/DatabaseModule.kt`, `di/RepositoryModule.kt`: Dependency injection
- `SkillArcadeApplication.kt`, `MainActivity.kt`: App entry points with Hilt and theme
- `ui/navigation/AppRoute.kt`, `ui/navigation/SkillArcadeNavHost.kt`: Navigation graph
- `ui/screens/`: 8 stub composables (Splash, Onboarding, Home, CourseCatalog, CourseProgress, LessonPlayer, Goals, TrophyRoom)
- `domain/ProgressCalculatorTest.kt`, `domain/GoalEvaluatorTest.kt`, `data/SkillArcadeRepositoryTest.kt`: Unit tests

### Important Code  
```kotlin
// arcadeBorderShadow modifier — core of arcade design system
fun Modifier.arcadeBorderShadow(
    borderColor: Color = ArcadeColors.InkBlack,
    backgroundColor: Color = ArcadeColors.Cream
): Modifier = this
    .graphicsLayer { clip = false }
    .drawBehind {
        drawRect(
            color = borderColor,
            topLeft = Offset(-4.dp.toPx(), -4.dp.toPx()),
            size = Size(size.width + 8.dp.toPx(), size.height + 8.dp.toPx())
        )
    }
    .border(3.dp, borderColor, RoundedCornerShape(12.dp))
    .background(backgroundColor, RoundedCornerShape(12.dp))
```

### UI/UX Decision
- Hard shadows (graphicsLayer.clip = false + drawBehind) for authentic arcade cabinet look rather than Material elevation
- Uppercase ClashDisplay for headlines, Epilogue Bold for body, reinforcing arcade aesthetic
- BottomNavBar active state uses PrimaryYellow for high visual affordance; inactive tabs use muted InkBlack
- arcadeBorderShadow applied uniformly to cards, buttons, and containers for visual consistency

### Screenshot To Capture
- App launch showing navigation structure and BottomNavBar
- ArcadeButton variants (Primary, Success, Destructive)
- ArcadeCard and ArcadeProgressBar in context (once Home/Course screens fully implemented)

### Before State
- Empty Android project with basic MainActivity and no architecture

### After State
- Fully functional MVVM + Hilt + Room architecture with type-safe navigation
- Reusable component library matching Neo-Boutique Arcade design
- Sample data seeded on first launch
- 28 passing unit tests validating business logic
- 8 screen stubs ready for UI implementation

### Challenge Faced
- AGP 9.2.0 + KSP compatibility: Build would fail without `android.disallowKotlinSourceSets=false`
- arcadeBorderShadow shadows invisible: graphicsLayer clip was obscuring drawBehind output
- SampleDataSeeder race condition: Coroutines executing before Room tables ready
- BottomNavBar top-only border: RectangleShape border drew on all sides

### Solution
- Added `android.disallowKotlinSourceSets=false` to gradle.properties
- Used graphicsLayer{ clip = false } before drawBehind to allow shadows outside layout bounds
- Used runBlocking in SampleDataSeeder (safe because Room invokes onCreate on background thread)
- Implemented BottomNavBar top border via drawBehind{ drawLine() } on scope, not Modifier.border
- ArcadeProgressBar: moved clip to inner fill Box only, leaving outer container unclipped for shadow visibility

### Report Notes
The foundation slice establishes type-safe navigation, consistent arcade styling via arcadeBorderShadow, atomic database transactions via db.withTransaction, and one-way goal ratcheting to prevent progress regression. All 28 unit tests pass, validating ProgressCalculator (courseCompletionFraction, levelFromXp, xpToNextLevel, totalXpFromLessons) and GoalEvaluator (isGoalComplete, goalProgressFraction, evaluateGoals across all goal types). Screen stubs are ready for UI implementation.

---

## Task K — Splash + Onboarding Screens

### What was implemented
Replaced the splash stub with the Neo-Boutique Arcade gradient, logo, flame tile, loading pill, and two-second auto-navigation. Replaced onboarding with four page-driven slides using shared arcade cards, copy, progress dots, skip action, and full-width CTA.

### Why
Replaces stub screens with pixel-faithful Neo-Boutique Arcade layouts matching the design references.

### Key files changed
- app/src/main/java/com/example/skillarcade/ui/screens/SplashScreen.kt
- app/src/main/java/com/example/skillarcade/ui/screens/OnboardingScreen.kt

### Code snippet
```kotlin
LaunchedEffect(Unit) {
    delay(2_000)
    onNavigateToOnboarding()
}
```

### Screenshots needed
- Splash screen (auto-navigate animation)
- Onboarding slide 1, 2, 3, 4

### UI/UX decisions
Used local emoji/text placeholders inside bordered illustration cards instead of network artwork or new image dependencies, keeping the UI offline and within the existing dependency set. Used existing Material color-scheme container tokens where equivalent `ArcadeColors` container fields are not defined in this project.

### Challenges
The current `ArcadeColors` object does not expose `PrimaryContainer`, `TertiaryContainer`, or `SurfaceContainerLowest`, so the implementation references the existing Material3 color scheme values that are already wired to those exact design colors.

---

## Task L — Home Dashboard Screen

### What was implemented
Replaced the Home Dashboard stub with a full LazyColumn feed: sticky-style header with streak badge, welcome section, "Jump Back In" hero card for the first in-progress course, scrollable category chip row, and a Continue Learning section showing remaining in-progress courses — all wired to a new `HomeDashboardViewModel` via Hilt.

### Why
Brings the Home Dashboard to life with real data from the repository and a layout faithful to the Neo-Boutique Arcade HTML reference.

### Key files changed
- app/src/main/java/com/example/skillarcade/ui/screens/HomeDashboardScreen.kt
- app/src/main/java/com/example/skillarcade/ui/viewmodel/HomeDashboardViewModel.kt (new)

### Code snippet
```kotlin
val uiState: StateFlow<HomeDashboardUiState> = combine(
    repository.getCourses(),
    repository.getUserProgress()
) { courses, userProgress ->
    HomeDashboardUiState(
        userProgress = userProgress,
        inProgressCourses = courses
            .filter { it.completedLessons > 0 && it.completedLessons < it.totalLessons }
            .take(3),
        isLoading = false
    )
}.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), HomeDashboardUiState(isLoading = true))
```

### Screenshots needed
- Home Dashboard with in-progress courses visible
- Home Dashboard empty state (no started courses)

### UI/UX decisions
- Hero card uses `tertiaryContainer` (mint) background to match the HTML reference's `bg-tertiary-container`.
- Course thumbnail placeholders use difficulty-mapped container colors (mint/EASY, yellow/MEDIUM, pink/HARD) instead of network images — keeps the app offline and dependency-free.
- Category chips are visually interactive (selected state) but do not filter data; filtering belongs in the Catalog screen.
- Empty state shown only when inProgressCourses is completely empty; hero card shown separately from the "remaining" list to avoid duplication.

### Challenges
- `UserProgress.streak` (not `streakDays`) — read from actual domain model before coding.
- `collectAsStateWithLifecycle` confirmed available via `lifecycle.runtime.compose` in build.gradle.kts.

---

## Task M — Course Catalog Screen

### What was implemented
Replaced the Course Catalog stub with a repository-backed LazyColumn showing the catalog header, subtitle, difficulty filters, course count, arcade-styled course cards, progress, and filtered empty state. Added `CourseCatalogViewModel` to expose filtered courses through `StateFlow`.

### Why
Provides the main course-browsing experience so learners can filter by difficulty and open a course from real seeded repository data.

### Key files changed
- app/src/main/java/com/example/skillarcade/ui/screens/CourseCatalogScreen.kt
- app/src/main/java/com/example/skillarcade/ui/viewmodel/CourseCatalogViewModel.kt

### Code snippet
```kotlin
fun filterByDifficulty(difficulty: Difficulty?) {
    selectedDifficulty.value = difficulty
}
```

### Screenshots needed
- Course Catalog with all courses
- Course Catalog filtered empty state

### UI/UX decisions
- Difficulty filters use the existing `ArcadeChip` component with yellow active state and `surfaceContainerHigh` inactive state.
- Course thumbnails are local color-block placeholders mapped by difficulty instead of network images.
- Course cards use the shared hard-shadow/border treatment and the existing `ArcadeProgressBar` and `ArcadeButton`.

### Challenges
- The task scope did not allow adding test files, so verification was limited to the required Kotlin compile check.
- The new screen uses the moved `androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel` import to avoid introducing a deprecation warning.

---

## Task N — Course Progress Screen

### What was implemented
Replaced the Course Progress stub with a repository-backed detail screen showing a back action, course header, difficulty chip, progress bar, XP badge, and clickable lesson rows. Added `CourseProgressViewModel` to load the course and its ordered lessons from the navigation `courseId`.

### Why
Gives learners a course-specific progress view and lesson launcher that matches the Neo-Boutique Arcade design system.

### Key files changed
- app/src/main/java/com/example/skillarcade/ui/screens/CourseProgressScreen.kt
- app/src/main/java/com/example/skillarcade/ui/viewmodel/CourseProgressViewModel.kt

### Code snippet
```kotlin
val uiState: StateFlow<CourseProgressUiState> = combine(
    repository.getCourse(courseId),
    repository.getLessons(courseId)
) { course, lessons ->
    CourseProgressUiState(course = course, lessons = lessons.sortedBy { it.orderIndex })
}.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), CourseProgressUiState(isLoading = true))
```

### Screenshots needed
- Course Progress screen with completed and incomplete lessons
- Course Progress loading state

### UI/UX decisions
- Header uses a primary yellow arcade panel with black progress fill to match the course progress reference.
- Lesson rows use mint for completed lessons and white for available/incomplete lessons, with numbered circles and check/arrow affordances.
- Lesson duration uses the actual `durationMinutes` field from `Lesson.kt`.

### Challenges
- `SavedStateHandle` is used with the serialized navigation argument key `courseId`, matching the `AppRoute.CourseProgress` property name.
- The task scope did not allow adding test files, so verification was limited to the required Kotlin compile check.

---

## Task O — Lesson Player Screen

### What was implemented
Replaced the Lesson Player stub with a Neo-Boutique Arcade lesson view: back action, black 16:9 video placeholder, optional YouTube intent link, lesson metadata, XP badge, complete action, and completion banner. Added `LessonPlayerViewModel` to load the lesson by `lessonId` and mark it complete through the repository.

### Why
Provides the learner-facing lesson playback and completion flow without adding video-player dependencies.

### Key files changed
- app/src/main/java/com/example/skillarcade/ui/screens/LessonPlayerScreen.kt
- app/src/main/java/com/example/skillarcade/ui/viewmodel/LessonPlayerViewModel.kt

### Code snippet
```kotlin
fun completeLesson() {
    viewModelScope.launch {
        repository.completeLesson(lessonId)
    }
}
```

### Screenshots needed
- Lesson Player before completion
- Lesson Player after marking complete
- Lesson Player with YouTube link visible

### UI/UX decisions
- Used a black arcade video placeholder and external YouTube intent instead of embedding a player dependency.
- Completion reuses repository state, so the banner appears from the Room flow update after `completeLesson`.
- The screen stays blank below the back row while loading, matching the simple loading approach requested for this slice.

### Challenges
- The implementation uses only real `Lesson` fields: `title`, `youtubeUrl`, `durationMinutes`, `orderIndex`, `isCompleted`, and `xpReward`.
- The task scope did not allow adding test files, so verification was limited to the required Kotlin compile check.

---

## Task P — Goals Screen

### What was implemented
Replaced the Goals stub with a repository-backed goals screen showing the streak hero, completed/XP summary chips, daily quest cards, progress bars, empty state, and course navigation CTA. Added `GoalsViewModel` to combine goals and user progress into `StateFlow`.

### Why
Turns goals into a live quest board that updates reactively as lessons and progress change elsewhere in the app.

### Key files changed
- app/src/main/java/com/example/skillarcade/ui/screens/GoalsScreen.kt
- app/src/main/java/com/example/skillarcade/ui/viewmodel/GoalsViewModel.kt

### Code snippet
```kotlin
val uiState: StateFlow<GoalsUiState> = combine(
    repository.getGoals(),
    repository.getUserProgress()
) { goals, userProgress ->
    GoalsUiState(goals = goals, userProgress = userProgress)
}.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), GoalsUiState())
```

### Screenshots needed
- Goals screen with active and completed quests
- Goals screen empty state

### UI/UX decisions
- Completed quests use mint backgrounds, reduced opacity, a check mark, and line-through titles to create a clear finished state.
- Goal type icons use local emoji only, keeping the screen dependency-free.
- Progress fractions guard against zero target values before rendering the shared `ArcadeProgressBar`.

### Challenges
- `UserProgress.streak` is the available streak field, not `streakDays`.
- The task scope did not allow adding test files, so verification was limited to the required Kotlin compile check.

---

## Task Q — Trophy Room Screen

### What was implemented
Replaced the Trophy Room stub with a repository-backed profile, level/XP header, 2x2 stats grid, horizontal trophy rail, full trophy detail cards, and Home CTA. Added `TrophyRoomViewModel` to combine trophies and user progress into `StateFlow`.

### Why
Provides a complete achievement/profile view using local progress and trophy data.

### Key files changed
- app/src/main/java/com/example/skillarcade/ui/screens/TrophyRoomScreen.kt
- app/src/main/java/com/example/skillarcade/ui/viewmodel/TrophyRoomViewModel.kt

### Code snippet
```kotlin
val uiState: StateFlow<TrophyRoomUiState> = combine(
    repository.getTrophies(),
    repository.getUserProgress()
) { trophies, userProgress ->
    TrophyRoomUiState(trophies = trophies, userProgress = userProgress)
}.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), TrophyRoomUiState())
```

### Screenshots needed
- Trophy Room with unlocked and locked trophies
- Trophy Room stats/profile header

### UI/UX decisions
- Trophy icons are mapped from `iconKey` to emoji instead of using network images.
- Trophy rarity controls card and circle color: common neutral, rare mint, epic pink, legendary yellow.
- Locked trophies are dimmed with opacity while still showing their title and detail context.

### Challenges
- The app has no user profile name/image model, so the header intentionally uses static `PLAYER` and an emoji avatar per the task.
- The task scope did not allow adding test files, so verification was limited to the required Kotlin compile check.

---

## Post-Implementation: Visual Review Bug Fixes & Improvements

### What Was Fixed / Added
After all eight screens were built, a visual review on a physical device revealed five bugs and two missing features. All were diagnosed and resolved in one pass.

---

### Bug 1 — Splash screen text clipped ("SKILLARCAD" visible, D cut off)

**Root cause:** `softWrap = false` + `maxLines = 1` + 24 dp horizontal padding + 48 sp font = layout overflow on a 393 dp-wide screen.

**Files changed:** `ui/screens/SplashScreen.kt`

**Before:**
```kotlin
Text(
    text = "SKILLARCADE",
    style = MaterialTheme.typography.displayLarge,   // 48 sp
    softWrap = false,
    maxLines = 1,
    modifier = Modifier.padding(24.dp)
)
```

**After:**
```kotlin
Text(
    text = "SKILLARCADE",
    style = MaterialTheme.typography.displayLarge.copy(fontSize = 40.sp),
    // softWrap and maxLines removed — text can wrap if needed
    modifier = Modifier.padding(horizontal = 12.dp, vertical = 24.dp)
)
```

---

### Bug 2 — Flame icon rendered as empty tofu box □

**Root cause:** Agent used `""` (a Material Symbols private-use codepoint). No Material Symbols font exists in `app/src/main/res/font/`, so Android fell back to the system .notdef glyph.

**Files changed:** `ui/screens/SplashScreen.kt` line 87

**Before:**
```kotlin
Text(text = "", fontSize = 40.sp)   // Material Symbols codepoint — no font loaded
```

**After:**
```kotlin
Text(text = "🔥", fontSize = 40.sp)       // system emoji — always available
```

---

### Bug 3 — All screens dark/olive (design colors not rendering)

**Root cause:** `SkillArcadeTheme` defaulted `darkTheme` to `isSystemInDarkTheme()`. The test device was in system dark mode, so Material3 mapped Neo-Boutique tokens to their dark-mode variants, producing olive greens and muddy creams instead of the intended bright cream/yellow/mint palette.

**Files changed:** `ui/theme/Theme.kt`

**Before:**
```kotlin
fun SkillArcadeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
```

**After:**
```kotlin
fun SkillArcadeTheme(
    darkTheme: Boolean = false,    // force light — Neo-Boutique palette is light-only
    content: @Composable () -> Unit
) {
```

---

### Bug 4 — 0 courses / 0 goals / 0 trophies shown on every screen

**Root cause (first layer):** `Room.Callback.onCreate` only fires when the database file is created for the first time. The device already had the app installed (version 1 schema), so `onCreate` never fired again and `SampleDataSeeder` never ran.

**Fix 1** — forced schema recreation by bumping version and adding destructive migration:

`data/local/SkillArcadeDatabase.kt`
```kotlin
// Before
@Database(entities = [...], version = 1, exportSchema = false)

// After
@Database(entities = [...], version = 2, exportSchema = false)
```

`di/DatabaseModule.kt`
```kotlin
Room.databaseBuilder(context, SkillArcadeDatabase::class.java, "skillarcade.db")
    .fallbackToDestructiveMigration()   // ← added; drops DB on version mismatch
    .addCallback(seederCallback)
    .build()
```

**Root cause (second layer):** After the version bump, if the device had ALREADY been at version 2 (from any prior build), `fallbackToDestructiveMigration` is a no-op, `onCreate` still doesn't fire, and the tables remain empty.

**Fix 2** — added `onOpen` guard that re-seeds whenever the courses table is empty, regardless of DB version history:

`di/DatabaseModule.kt`
```kotlin
override fun onOpen(db: SupportSQLiteDatabase) {
    super.onOpen(db)
    val cursor = db.query("SELECT COUNT(*) FROM courses")
    cursor.moveToFirst()
    val count = cursor.getInt(0)
    cursor.close()
    if (count == 0) {
        val database = checkNotNull(roomDb)
        Log.d("DatabaseModule", "DB empty on open; seeding...")
        seed(db, database)           // delegates to SampleDataSeeder.onCreate
    }
}
```

This makes seeding idempotent: it runs on every cold open until data exists, then becomes a single fast COUNT query on all subsequent opens.

---

### Bug 5 — Difficulty chip invisible on course catalog thumbnail cards

**Root cause:** The chip's `color` parameter was set to `thumbnailColor`, which is the same colour as the containing thumbnail box, making the chip invisible against the background.

**Files changed:** `ui/screens/CourseCatalogScreen.kt`

**Before:**
```kotlin
ArcadeChip(
    text = course.difficulty.name,
    color = thumbnailColor   // same as thumbnail background — invisible
)
```

**After:**
```kotlin
ArcadeChip(
    text = course.difficulty.name,
    color = MaterialTheme.colorScheme.surfaceContainerLowest   // white — always visible
)
```

---

### Feature 1 — Immersive mode (system bars hidden, swipe to reveal)

The system status bar and navigation bar were visible inside the app, breaking the full-screen arcade aesthetic.

**Files changed:** `MainActivity.kt`

```kotlin
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    WindowCompat.getInsetsController(window, window.decorView).apply {
        hide(WindowInsetsCompat.Type.systemBars())
        systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    }
    setContent { SkillArcadeTheme { SkillArcadeNavHost() } }
}

// Re-hide on focus regain (e.g. after a dialog or notification panel)
override fun onWindowFocusChanged(hasFocus: Boolean) {
    super.onWindowFocusChanged(hasFocus)
    if (hasFocus) {
        WindowCompat.getInsetsController(window, window.decorView).apply {
            hide(WindowInsetsCompat.Type.systemBars())
            systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }
}
```

The `BottomNavBar` already uses `Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.navigationBars))` so the layout adapts correctly when the user swipes the system nav bar back in.

---

## Task R — Course Catalog Redesign & Data Refresh

### What was implemented
- **Redesigned Course Catalog**: Replaced the previous basic list with a pixel-faithful implementation of the provided design. Added a hard-shadowed search bar, scrollable category filters (UI/UX, Frontend, Marketing), and detailed course cards featuring category-specific colors, "NEW/POPULAR" tags, duration hours, and "ENROLL NOW" buttons.
- **Data Model Update**: Expanded `Course` domain model and `CourseEntity` to include `durationHours` and optional `tag`. Updated mappers accordingly.
- **Database Refresh**: Bumped `SkillArcadeDatabase` version to 3 and updated `SampleDataSeeder` with actual course data from the design (Advanced UI/UX Masterclass, React & Tailwind Bootcamp, Guerilla Marketing Tactics).
- **Navigation Update**: Refreshed `BottomNavBar` labels (HOME, LEARN, GOALS, ME) and adjusted layout/height to match the design.
- **Bug Fix**: Added missing `androidx.compose.material:material-icons-core` and `androidx.compose.material:material-icons-extended` dependencies to resolve "Unresolved reference 'icons'" compilation errors in the new search bar.

### Why
To bring the app's primary discovery interface in line with the high-fidelity design references and ensure the data displayed is relevant and complete.

### Key files changed
- `app/src/main/java/com/example/skillarcade/ui/screens/CourseCatalogScreen.kt`
- `app/src/main/java/com/example/skillarcade/ui/viewmodel/CourseCatalogViewModel.kt`
- `app/src/main/java/com/example/skillarcade/domain/model/Course.kt`
- `app/src/main/java/com/example/skillarcade/data/local/entities/CourseEntity.kt`
- `app/src/main/java/com/example/skillarcade/data/local/SkillArcadeDatabase.kt`
- `app/src/main/java/com/example/skillarcade/data/seed/SampleDataSeeder.kt`
- `app/src/main/java/com/example/skillarcade/ui/components/BottomNavBar.kt`
- `gradle/libs.versions.toml`
- `app/build.gradle.kts`

### Code snippet
```kotlin
// Multi-category filtering in ViewModel
val filteredByCategory = if (category == "ALL") courses 
                         else courses.filter { it.category.uppercase() == category.uppercase() }

// Search logic
val filteredBySearch = if (query.isBlank()) filteredByCategory
                       else filteredByCategory.filter { 
                           it.title.contains(query, ignoreCase = true) || 
                           it.description.contains(query, ignoreCase = true) 
                       }
```

### Screenshots needed
- Updated Course Catalog with Search bar and Category filters
- Redesigned Course Cards with "ENROLL NOW" buttons
- Updated Bottom Navigation Bar labels

### UI/UX decisions
- Used category-based filtering instead of difficulty-based filtering to match the design's primary organization.
- Implemented search-on-type logic in the ViewModel for immediate user feedback.
- Added a dotted separator in course cards using `drawBehind` to match the "arcade ticket" aesthetic.
- Category background colors in thumbnails are hardcoded for "UI/UX", "FRONTEND", and "MARKETING" to ensure visual harmony with the design.

### Challenges
- Missing Icons Dependency: The new `SearchBar` implementation used `Icons.Default.Search`, which required the full Material Icons extended library not previously present in the project.
- Data Persistence: Seeding new courses required a database version bump to ensure the `courses` table was cleared and repopulated with the updated schema (new fields).
- State Synchronization: The `CourseCatalogViewModel` was updated to also fetch `UserProgress` to drive the `TopBar` streak display, ensuring consistency across screens.

---

### Feature 2 — BottomNavBar redesign: arcade card active tab + icons

The original bottom nav showed only uppercase text labels with a faint yellow tint on the active tab. The redesign matches the HTML reference: active tab = yellow arcade card with hard shadow + border, inactive tabs = emoji icon + label in muted grey.

**Files changed:** `ui/components/BottomNavBar.kt`

Key changes:
1. Added `emoji` field to `NavTab` enum:
```kotlin
enum class NavTab(val label: String, val route: String, val emoji: String) {
    Home("Home", "home", "🏠"),
    Courses("Courses", "course_catalog", "📚"),
    Goals("Goals", "goals", "🎯"),
    Trophies("Trophies", "trophy_room", "🏆")
}
```

2. Active tab uses `arcadeBorderShadow` with `PrimaryYellow` fill:
```kotlin
Box(
    modifier = Modifier
        .weight(1f)
        .padding(horizontal = 4.dp)
        .arcadeBorderShadow(
            cornerRadius = 12.dp,
            shadowOffset = 2.dp,
            backgroundColor = ArcadeColors.PrimaryYellow
        )
        .clickable { onNavigate(tab.route) }
        .padding(vertical = 6.dp),
    contentAlignment = Alignment.Center
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = tab.emoji, fontSize = 18.sp)
        Text(text = tab.label, style = labelSmall.copy(fontWeight = Bold, color = InkBlack))
    }
}
```

3. Inactive tab uses muted `onSurfaceVariant` for both emoji and label with no card background.

4. Bar height increased from 64 dp to 72 dp. Navigation-bar inset handled by `Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.navigationBars))`.

---

### Feature 3 — YouTube WebView embed in Lesson Player

Replaced the static black video placeholder with a live YouTube embed using Android `WebView`. No external library added.

**Files changed:** `ui/screens/LessonPlayerScreen.kt`, `AndroidManifest.xml`

`AndroidManifest.xml` — INTERNET permission (required for WebView):
```xml
<uses-permission android:name="android.permission.INTERNET" />
```

`LessonPlayerScreen.kt` — video ID extraction + WebView composable:
```kotlin
private fun extractVideoId(youtubeUrl: String): String =
    youtubeUrl.substringAfter("v=").substringBefore("&")

@Composable
private fun YouTubePlayerCard(lesson: Lesson) {
    val videoId = extractVideoId(lesson.youtubeUrl)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .aspectRatio(16f / 9f)
            .arcadeBorderShadow(cornerRadius = ArcadeTokens.CornerRadius,
                                backgroundColor = ArcadeColors.InkBlack)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(ArcadeTokens.CornerRadius))
                .background(ArcadeColors.InkBlack)
        ) {
            AndroidView(
                factory = { ctx ->
                    WebView(ctx).apply {
                        settings.javaScriptEnabled = true
                        settings.domStorageEnabled = true
                        settings.mediaPlaybackRequiresUserGesture = false
                        webChromeClient = WebChromeClient()
                        loadUrl("https://www.youtube.com/embed/$videoId")
                    }
                },
                modifier = Modifier.fillMaxSize()
            )
        }
        ArcadeChip(
            text = "${lesson.durationMinutes} min",
            color = ArcadeColors.PrimaryYellow,
            modifier = Modifier.align(Alignment.BottomStart).padding(8.dp)
        )
    }
}
```

The video ID is extracted from the `youtubeUrl` already stored per lesson in `SampleDataSeeder` (e.g. `https://www.youtube.com/watch?v=F9UC9DY-vIU` → `F9UC9DY-vIU`). The embed URL `https://www.youtube.com/embed/{id}` keeps the user inside the app and allows the arcade card border and duration chip to frame the player.

---

### Screenshots captured during visual review
- Course Catalog: 0 COURSES / "No filtered courses yet" empty state (before seed fix)
- Goals: 0 DAY STREAK, 0/0 DONE, 0 XP, "No quests today" (before seed fix)
- BottomNavBar: new arcade card active tab with emoji icons (after redesign)

---

## Task S — Startup Seed Fix for Empty Screens

### What was found
Screenshots showed the app shell and visual design were rendering correctly, but every data-backed screen was in an empty state:
- Home: no started courses
- Learn: no courses found
- Goals: no daily quests and 0/0 progress
- Me: 0 XP, 0 lessons, 0 trophies

Codebase search showed the screens were wired to repository flows correctly:
- `CourseCatalogViewModel` reads `repository.getCourses()`
- `GoalsViewModel` reads `repository.getGoals()`
- `TrophyRoomViewModel` reads `repository.getTrophies()`
- `HomeDashboardViewModel` reads courses plus user progress

That made the likely failure the Room seed path, not Compose rendering or filtering.

### Search and docs used
- Searched the codebase for `SampleDataSeeder`, `Room.databaseBuilder`, `addCallback`, `onCreate`, `onOpen`, and DAO insert paths.
- Used Context7 for AndroidX/Room guidance around `RoomDatabase.Callback` and database prepopulation.
- Used Android CLI docs:
  - `android docs search "Room database callback prepopulate data onCreate DAO"`
  - `android docs fetch kb://android/training/data-storage/room/prepopulate`
  - `android docs search "Hilt inject Application onCreate Android"`
  - `android docs fetch kb://android/training/dependency-injection/hilt-android`

The docs helped confirm two implementation points:
- Room's official prepopulate APIs are `createFromAsset()` / `createFromFile()` for packaged database files.
- For this app's programmatic sample rows, seeding should happen after the Room instance is built/opened, through normal injected dependencies, rather than re-entering Room DAOs from the callback that is opening the same DB.
- Hilt supports injecting application-level dependencies from `@HiltAndroidApp`, so startup seeding can live in `SkillArcadeApplication`.

### Root cause
`DatabaseModule` created a Room database and attached a callback that called `SampleDataSeeder(...).onCreate(db)`. That seeder then used Room DAOs from inside the Room open/create callback while Room was opening the same database instance. This can fail or race during database initialization and leave all tables empty, which matches the screenshots.

### Fix implemented
- Converted `SampleDataSeeder` from a `RoomDatabase.Callback` subclass into a `@Singleton` Hilt-injected class.
- Added `seedIfNeeded()` that checks `courseDao.countCourses()` and seeds only when the course table is empty.
- Wrapped all inserts in `db.withTransaction` so courses, lessons, goals, trophies, and user progress are created atomically.
- Injected `SampleDataSeeder` into `SkillArcadeApplication` and launched `seedIfNeeded()` from `Application.onCreate()` on `Dispatchers.IO`.
- Removed callback seeding and the captured `roomDb` variable from `DatabaseModule`.
- Added DAO count queries for courses, lessons, goals, and trophies.
- Updated `fallbackToDestructiveMigration()` to the non-deprecated `fallbackToDestructiveMigration(true)` overload.

### Key files changed
- `app/src/main/java/com/example/skillarcade/SkillArcadeApplication.kt`
- `app/src/main/java/com/example/skillarcade/di/DatabaseModule.kt`
- `app/src/main/java/com/example/skillarcade/data/seed/SampleDataSeeder.kt`
- `app/src/main/java/com/example/skillarcade/data/local/dao/CourseDao.kt`
- `app/src/main/java/com/example/skillarcade/data/local/dao/LessonDao.kt`
- `app/src/main/java/com/example/skillarcade/data/local/dao/GoalDao.kt`
- `app/src/main/java/com/example/skillarcade/data/local/dao/TrophyDao.kt`
- `app/src/androidTest/java/com/example/skillarcade/data/seed/SampleDataSeederTest.kt`

### Code snippet
```kotlin
@Singleton
class SampleDataSeeder @Inject constructor(
    private val db: SkillArcadeDatabase,
    private val courseDao: CourseDao,
    private val lessonDao: LessonDao,
    private val goalDao: GoalDao,
    private val trophyDao: TrophyDao,
    private val userProgressDao: UserProgressDao
) {
    suspend fun seedIfNeeded() {
        db.withTransaction {
            if (courseDao.countCourses() > 0) return@withTransaction
            seedData()
        }
    }
}
```

### Verification
- TDD red step: `./gradlew assembleDebugAndroidTest --no-daemon` failed first because `seedIfNeeded()` and DAO count queries did not exist yet.
- `./gradlew testDebugUnitTest --no-daemon` passed.
- `./gradlew assembleDebugAndroidTest --no-daemon` passed.
- `./gradlew assembleDebug --no-daemon` passed.
- Device instrumentation execution was not run because `adb` was not on PATH and `android emulator list` returned no configured emulators.

---

## Task U — Resilient YouTube Lesson Player

### What was found
The Lesson Player already attempted to show YouTube content in a `WebView`, and `AndroidManifest.xml` already had `INTERNET`, so the blank white rectangle was not caused by a missing permission.

The fragile parts were in `LessonPlayerScreen.kt`:
- The WebView had no `WebViewClient`, so redirects, navigation, and load failures were not handled.
- The WebView background was not forced to black, so failure or initial load appeared as an empty white rectangle.
- There was no loading state, error state, or fallback action.
- `extractVideoId()` only handled `youtube.com/watch?v=...`, not `youtu.be`, `/embed/`, or `/shorts/` links.

### Search and docs used
- Searched the codebase for `youtubeUrl`, `WebView`, `AndroidView`, `WebChromeClient`, `loadUrl`, and `INTERNET`.
- Used Android CLI docs:
  - `android docs search "Android WebView loadUrl WebViewClient JavaScript settings WebChromeClient"`
  - `android docs fetch kb://android/develop/ui/views/layout/webapps/webview`
- Used Context7 for AndroidX WebKit/WebView references around `WebViewClient` request handling.
- Checked YouTube embed guidance for the `/embed/{videoId}` URL format and inline playback parameters.

The docs confirmed the right direction: keep JavaScript enabled for YouTube, add a `WebViewClient` for navigation/error handling, keep `WebChromeClient`, and retain an external browser/app fallback for links the embedded player cannot handle reliably.

### Fix implemented
- Added `ui/video/YouTubeVideo.kt` with:
  - `extractYouTubeVideoId()`
  - `buildYouTubeEmbedUrl()`
- Added JVM tests for watch, short, embed, shorts, invalid URLs, and embed URL generation.
- Updated `YouTubePlayerCard` to:
  - use `https://www.youtube.com/embed/{id}?playsinline=1&rel=0`
  - force the WebView background to black
  - enable JavaScript, DOM storage, wide viewport, and inline media playback
  - attach a `WebViewClient`
  - show `LOADING VIDEO...` while loading
  - show `VIDEO UNAVAILABLE` plus `OPEN IN YOUTUBE` on invalid URLs or main-frame load failures
  - send non-player external URLs to `Intent.ACTION_VIEW`

### Key files changed
- `app/src/main/java/com/example/skillarcade/ui/screens/LessonPlayerScreen.kt`
- `app/src/main/java/com/example/skillarcade/ui/video/YouTubeVideo.kt`
- `app/src/test/java/com/example/skillarcade/ui/video/YouTubeVideoTest.kt`

### Code snippet
```kotlin
val videoId = remember(lesson.youtubeUrl) { extractYouTubeVideoId(lesson.youtubeUrl) }
val embedUrl = remember(videoId) { videoId?.let(::buildYouTubeEmbedUrl) }

WebView(ctx).apply {
    setBackgroundColor(android.graphics.Color.BLACK)
    settings.javaScriptEnabled = true
    settings.domStorageEnabled = true
    settings.mediaPlaybackRequiresUserGesture = false
    settings.loadWithOverviewMode = true
    settings.useWideViewPort = true
    webChromeClient = WebChromeClient()
}
```

### Verification
- TDD red step: `./gradlew testDebugUnitTest --no-daemon` failed first because `extractYouTubeVideoId()` and `buildYouTubeEmbedUrl()` did not exist yet.
- `./gradlew testDebugUnitTest --no-daemon` passed after implementation.
- `./gradlew assembleDebug --no-daemon` passed.

### Next step
Seeded lesson URLs were intentionally not expanded in this task. Next pass should replace the repeated sample YouTube URL with course-relevant YouTube links and expand the catalog to roughly 7-8 total courses.

---

## Task T — Profile Personalization (Me Screen)

### What was implemented
- **Personalized Profile**: Updated the "Me" screen (Trophy Room) to replace the generic "PLAYER" label with "ABDIRIZAK" in the profile header.
- **Dynamic Avatar**: Integrated **Coil** for image loading and added a pixel-art profile photo generated via the **DiceBear API** with the seed "Abdirizak".
- **Build Configuration**: Added `coil-compose` and `coil-network-okhttp` to `libs.versions.toml` and `app/build.gradle.kts` to support network image loading.

### Why
To improve the user experience by providing a personalized profile identity and a unique visual avatar, moving away from generic placeholders.

### Key files changed
- `app/src/main/java/com/example/skillarcade/ui/screens/TrophyRoomScreen.kt`: Updated profile header and added `AsyncImage`.
- `gradle/libs.versions.toml`: Added Coil version and library aliases.
- `app/build.gradle.kts`: Added Coil dependencies.

### Code snippet
```kotlin
AsyncImage(
    model = "https://api.dicebear.com/7.x/pixel-art/png?seed=Abdirizak",
    contentDescription = "Profile Photo",
    modifier = Modifier
        .fillMaxSize()
        .padding(4.dp)
        .clip(CircleShape),
    contentScale = ContentScale.Crop
)
```

### Verification
- Ran `./gradlew assembleDebug` successfully.
- Verified that the `AsyncImage` correctly points to the personalized DiceBear URL.

---

## Task V — Expanded Catalog with YouTube Video Cards

### What was implemented
- Expanded the seeded catalog from 3 courses to 8 courses.
- Replaced repeated placeholder lesson video URLs with course-relevant public YouTube links.
- Added YouTube thumbnail URLs to `Course.thumbnailUrl` using `https://img.youtube.com/vi/{videoId}/hqdefault.jpg`.
- Updated Course Catalog cards to render real thumbnails with a dark overlay and play button, turning the previous abstract placeholder area into a video-card preview.
- Bumped `SkillArcadeDatabase` from version 3 to 4 so existing installs destructively refresh into the new seed catalog.
- Updated the seeder instrumentation test expectations from 3 courses / 44 lessons to 8 courses / 32 lessons.

### Search and video sources used
Web search was used to pick public, watchable YouTube videos and verify video IDs:
- UI/UX design tutorial: `c9Wg6Cb_YlU`
- Figma UI essentials: `kbZejnPXyLM`
- JavaScript full course: `EerdGm-ehJQ`
- React 19 app course: `dCLhUialKPQ`
- Tailwind CSS v4 course: `6biMWgD6_JY`
- Android beginners course: `fis26HvvDII`
- React Native full stack app: `f8Z9JyB2EIE`
- Digital marketing roadmap: `KZLroOQKT-g`

Useful references found during search:
- Class Central pages for freeCodeCamp UI/UX and Figma free video courses.
- GitHub/SuperSimpleDev course repo linking the JavaScript course video.
- Glasp summary pages for React, React Native, Tailwind, Android, and digital marketing videos.
- Search snippets and mirrored references confirming exact YouTube video IDs for UI/UX, React, React Native, Kotlin/Android-related videos, and marketing courses.

Context7 was used to confirm the library implementation details:
- Coil docs (`/coil-kt/coil`) confirmed `coil3.compose.AsyncImage` supports URL models, `ContentScale.Crop`, and normal Compose modifiers for remote thumbnail rendering.
- Android Developers docs (`/websites/developer_android_guide`) confirmed WebView needs JavaScript enabled for embedded web content and standard `loadUrl`/WebView configuration for remote pages.

### Why
The course list now had data, but the cards still looked visually empty because `CourseThumbnail` ignored `thumbnailUrl` and drew only a flat abstract placeholder. Using YouTube thumbnails makes each catalog item visibly connected to the video lesson experience and gives the Learn screen useful visual content.

### Key files changed
- `app/src/main/java/com/example/skillarcade/data/seed/SampleDataSeeder.kt`
- `app/src/main/java/com/example/skillarcade/data/local/SkillArcadeDatabase.kt`
- `app/src/main/java/com/example/skillarcade/ui/screens/CourseCatalogScreen.kt`
- `app/src/androidTest/java/com/example/skillarcade/data/seed/SampleDataSeederTest.kt`

### Code snippet
```kotlin
private fun youtubeThumbnail(videoId: String): String =
    "https://img.youtube.com/vi/$videoId/hqdefault.jpg"

AsyncImage(
    model = course.thumbnailUrl,
    contentDescription = "${course.title} video thumbnail",
    contentScale = ContentScale.Crop,
    modifier = Modifier.fillMaxSize()
)
```

### Verification
- `./gradlew testDebugUnitTest --no-daemon` passed.
- `./gradlew assembleDebug assembleDebugAndroidTest --no-daemon` passed.
- `android emulator list` returned no configured emulators, so no on-device visual run was available in this pass.

---

## Task W — Lesson Player Blank White WebView Fix

### What was found
The lesson screen correctly received a lesson and a YouTube URL, and the black WebView initially showed `LOADING VIDEO...`. After the WebView main page finished, the loader was hidden while the nested YouTube iframe had not actually reported readiness yet. On-device this appeared as a blank white video area.

### Search and docs used
- Web search found Google's YouTube IFrame Player API docs. The relevant guidance was to use an iframe URL with `enablejsapi=1`, include an `origin`, and listen for player events such as `onReady` and `onError`.
- Context7 Android Developers docs confirmed the WebView setup path: JavaScript must be enabled for embedded web content, a `WebViewClient` should handle navigation/error behavior, and HTML can be loaded into a WebView instead of only loading a remote URL directly.

### Fix implemented
- Added `buildYouTubePlayerHtml(videoId)` in `YouTubeVideo.kt`.
- The WebView now loads a controlled black HTML wrapper via `loadDataWithBaseURL(...)` instead of loading the raw YouTube embed URL directly.
- Added `enablejsapi=1` and an encoded SkillArcade origin to the YouTube embed URL.
- The HTML wrapper loads `https://www.youtube.com/iframe_api` and reports readiness through internal `skillarcade://player-ready` navigation.
- The WebView no longer hides the loader on `onPageFinished`; it hides it only when the YouTube iframe reports `onReady`.
- If YouTube reports `onError`, or if the iframe never reports ready within 10 seconds, the existing `OPEN IN YOUTUBE` fallback appears.
- Added unit coverage for the generated black iframe wrapper and readiness/error hooks.

### Key files changed
- `app/src/main/java/com/example/skillarcade/ui/video/YouTubeVideo.kt`
- `app/src/main/java/com/example/skillarcade/ui/screens/LessonPlayerScreen.kt`
- `app/src/test/java/com/example/skillarcade/ui/video/YouTubeVideoTest.kt`

### Code snippet
```kotlin
webView.loadDataWithBaseURL(
    YOUTUBE_PLAYER_BASE_URL,
    playerHtml,
    "text/html",
    "UTF-8",
    null
)
```

### Verification
- `./gradlew testDebugUnitTest --no-daemon` passed.
- `./gradlew assembleDebug --no-daemon` passed.
- No fresh on-device screenshot pass was run in this turn. The Android CLI emulator check could not be rerun because the environment rejected that escalated command.

---

## Task Y — Custom Splash Screen Image

### What was implemented
- Replaced the programmatic `SplashScreen` (gradient + text + emoji) with a custom full-screen image (`splash.png`).
- Moved the user-provided `splash.png` from `res/` to `res/drawable/` for proper resource management.
- Updated `SplashScreen.kt` to use `Image` with `painterResource` and `ContentScale.Crop`.

### Why
To match the specific visual branding requested by the user, replacing the foundation-slice placeholder with the final production asset.

### Key files changed
- `app/src/main/res/drawable/splash.png` (added/moved)
- `app/src/main/java/com/example/skillarcade/ui/screens/SplashScreen.kt`

### Code snippet
```kotlin
Image(
    painter = painterResource(id = R.drawable.splash),
    contentDescription = "SkillArcade Splash Screen",
    modifier = Modifier.fillMaxSize(),
    contentScale = ContentScale.Crop
)
```

### Verification
- Verified that `SplashScreen.kt` compiles and correctly references `R.drawable.splash`.
- Checked that the 3-second delay and navigation to Onboarding are preserved.

## Task X — YouTube Player Playback Fix

### What was fixed
Resolved an issue where the lesson player would display "LOADING VIDEO..." followed by a persistent black screen instead of playing the video.

### Root cause
The previous implementation used a complex HTML wrapper that attempted to initialize the `YouTube IFrame Player API` manually. This setup was fragile due to:
1.  **Origin Mismatches**: The API frequently failed to initialize because the `WebView` origin (`skillarcade.local`) didn't consistently match what the YouTube API expected for JS communication.
2.  **Script Loading Races**: The `onYouTubeIframeAPIReady` callback was not consistently firing within the `WebView` environment.
3.  **Navigation Interception**: The `WebViewClient` was over-eagerly intercepting internal YouTube resource requests (like those from `gstatic.com`), causing the player to fail silently.
4.  **User Agent Issues**: Some YouTube embed features require a modern mobile user agent to render controls correctly.

### Fix implemented
1.  **Simplified HTML Wrapper**: Replaced the JS-heavy API initialization with a standard, high-reliability `<iframe>` embed. This relies on YouTube's native handling of controls and playback, which is much more stable in a `WebView`.
2.  **Allow-list Expansion**: Added `gstatic.com` to the list of allowed hosts in `shouldOverrideUrlLoading` to ensure supporting assets (fonts, scripts) load correctly.
3.  **Updated User Agent**: Set a specific Pixel 7 / Chrome 116 user agent string to ensure YouTube serves the correct mobile-optimized player.
4.  **Consolidated Loading Logic**: Cleaned up the `WebViewClient` lifecycle handling. `isLoading` is now reset on `onPageFinished` instead of waiting for a fragile JS hook, ensuring the user sees the player once it's ready to render.

### Key files changed
- `app/src/main/java/com/example/skillarcade/ui/video/YouTubeVideo.kt`
- `app/src/main/java/com/example/skillarcade/ui/screens/LessonPlayerScreen.kt`

### Verification
- Verified that the video now loads and displays native YouTube controls (Play/Pause, Seek, Fullscreen).
- Verified that the "LOADING VIDEO..." overlay dismisses correctly once the iframe content begins to render.
- Confirmed external links still correctly open the YouTube app/browser.

