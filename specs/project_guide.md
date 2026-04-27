# SkillArcade Agent Guide

This file is for coding agents and report-writing agents working on the Android Studio implementation of **SkillArcade: Gamified Learning Platform**.

The goal is to keep implementation work aligned with the existing design and to make report writing easier later.

## 1. Required References

Before writing code, read these files and folders:

```text
skillarcade_design/report-guide.md
skillarcade_design//DESIGN.md
skillarcade_design/skillarcade_ux_flowchart.png
```

The design folder "skillarcade_design" contains:

- screen mockups,
- HTML reference files,
- visual style guide,
- Screenshots for each planned screen.

Important references screen folders to inspect:

```text
splash_screen/
onboarding_slide_1/
onboarding_slide_2/
onboarding_slide_3/
onboarding_slide_4/
home_dashboard/
course_catalog/
course_progress/
lesson_player/
goals_screen/
trophy_room/
```

Each screen folder contains:

```text
code.html
screen.png
```

Use the screenshots as the visual target.
Use the HTML files as layout references, not as code to copy directly.

## 2. Product Scope

Build a mobile learning app with gamified progress.

Core screens:

- Splash Screen
- Onboarding Slides
- Home Dashboard
- Course Catalog
- Course Progress
- Lesson Player
- Goals Screen
- Trophy Room

Core features:

- view learning dashboard,
- browse courses,
- view course progress,
- open lesson player which are embedded YouTube links,
- complete goals or lessons through local state,
- update XP/progress locally,
- view trophies and achievements.

Do not add:

- backend,
- login/signup,
- push notifications,
- complex database unless explicitly requested.

## 3. Repository Setup we have

The user already set up the repo with basic structure .

I already Run the empty app once.

Actual Kotlin package structure (as built):

```text
app/src/main/java/com/example/skillarcade/
├── MainActivity.kt
├── SkillArcadeApplication.kt
├── data/
│   └── local/
│       ├── SkillArcadeDatabase.kt
│       ├── entities/          (Room @Entity classes)
│       ├── dao/               (Room @Dao interfaces)
│       ├── Mappers.kt
│       └── seed/
│           └── SampleDataSeeder.kt
│   └── repository/
│       └── SkillArcadeRepositoryImpl.kt
├── di/
│   ├── DatabaseModule.kt
│   └── RepositoryModule.kt
├── domain/
│   ├── model/                 (Course, Lesson, Goal, Trophy, UserProgress + enums)
│   ├── repository/            (SkillArcadeRepository interface)
│   └── usecase/               (ProgressCalculator, GoalEvaluator)
├── ui/
│   ├── theme/
│   ├── components/
│   ├── navigation/
│   └── screens/
```

## 5. Design Alignment Rules

The app should follow the **Neo-Boutique Arcade** design system from:

```text
skillarcade_design/DESIGN.md
```

Match the screenshots as closely as reasonable.
## 6. Implementation Order

Work screen by screen.

Recommended order:

1. Theme colors, typography, shapes.
2. Reusable components: ArcadeCard, ArcadeButton, ArcadeProgressBar, CourseCard, GoalCard, TrophyCard.
3. Data models and sample repository.
4. Navigation routes.
5. Splash and onboarding.
6. Home dashboard.
7. Course catalog.
8. Course progress.
9. Lesson player.
10. Goals screen.
11. Trophy room.
12. Local state/persistence polish.
13. Final screenshots and report evidence.

## 7. Report Logging Requirement

Every implementation task must update:

```text
specs/implementation-log.md
```

Create it if it does not exist.

Use this template:

~~~markdown
## YYYY-MM-DD - Feature Name

### What Was Implemented
- 

### Why This Was Implemented
- 

### Problem It Solves
- 

### Files Changed
- 

### Important Code Snippet
```kotlin
// short snippet only
```

### UI/UX Decision
- 

### Screenshot To Capture
- 

### Before State
- 

### After State
- 

### Challenge Faced
- 

### Solution
- 

### Report Notes
- 
~~~

Keep the log short but specific. The final report agent will use this file.

## 8. What To Record For The Report

For every feature, record:

- what feature was implemented,
- why it was implemented,
- what user problem it solves,
- key files/components changed,
- short code snippet,
- screenshot needed,
- before/after state when relevant,
- UI/UX decision,
- challenge faced,
- how it was solved.

Use teacher-style language:

- short definition,
- why this matters,
- what was implemented,
- result,
- final takeaway.

Example report note:

```text
Before adding ArcadeCard, the course section looked like simple flat content.
After adding ArcadeCard, the section had a black border, rounded corners, and hard shadow.
This made the screen more consistent with the gamified arcade design.
```

## 9. Screenshot Requirements

When a screen is completed, request or capture screenshots for:

- Splash Screen
- Onboarding
- Home Dashboard
- Course Catalog
- Course Progress
- Lesson Player
- Goals Screen
- Trophy Room

Before/after screenshots are valuable when:

- spacing is improved,
- borders and shadows are added,
- progress indicators are added,
- navigation is added,
- a screen changes from static to interactive.

## 10. Verification

Before saying a feature is complete:

- build the app if possible,
- run relevant tests if present,
- check Compose previews if available,
- compare the implemented screen with the reference screenshot,
- confirm `docs/implementation-log.md` was updated.

If a command cannot run, explain why.

## 11. Constraints

Do not:

- change the project idea,
- remove existing Android Studio generated files without reason,
- add unrelated advanced features,
- replace the design system,
- write the final report before implementation evidence exists,
- invent screenshots or implementation details.

Do:

- keep code organized,
- use readable names,
- keep composables small,
- keep the UI close to the design,
- preserve report evidence while coding.

## 12. Agent Final Response Format

When finishing a coding task, report:

- files changed,
- feature implemented,
- verification result,
- screenshots still needed,
- implementation-log entry added.

Keep the response short and factual.