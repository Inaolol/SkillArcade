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
