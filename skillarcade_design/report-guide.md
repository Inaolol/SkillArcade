# SkillArcade Report Guide

Altinbas University  
Department of Software Engineering  
Introduction to Mobile Application Development  

Project:

**SkillArcade: Gamified Learning Platform**

This guide explains how to write the final printed report in a style that feels familiar to the course lectures.

The report should be written in English and should be about **1000-2000 words**.

## 1. Assignment Requirements

The project PDF requires:

- a simple mobile application,
- a real-life problem,
- interface design,
- navigation,
- user input,
- simple data handling,
- clear screenshots,
- GitHub repository link,
- printed hard copy,
- student signature.

The grading rubric gives points for:

- project idea and usefulness,
- technology choice,
- planning and design,
- user interface quality,
- main functionality,
- navigation and interaction,
- data handling,
- code organization,
- report quality and screenshots,
- GitHub submission.

## 2. Teacher Writing Style

The lecture slides usually explain topics in a simple step-by-step structure.

Use this style in the report.

### 2.1 Common Pattern

A good section should follow this pattern:

1. Start with a short definition.
2. Explain why the feature is needed.
3. Show what was implemented.
4. Add a screenshot.
5. Explain the screenshot with bullet points.
6. Mention the result.

Example structure:

```text
What Is the Home Dashboard?
The Home Dashboard is the main screen of SkillArcade.

Why This Screen Matters
It gives the learner a quick overview of progress, current lesson, XP, and next actions.

What Was Implemented
- progress card,
- continue learning section,
- course cards,
- bottom navigation.

Figure 1
Home dashboard screen.

Result
The user can understand what to do next without opening many different screens.
```

### 2.2 Words and Headings to Use

Use headings similar to the lecture slides:

- Main Idea
- Why This Matters
- Step 1
- Step 2
- What Was Implemented
- What This Does
- Result
- Key Point
- Important
- Final Takeaway
- Summary

### 2.3 Sentence Style

Use short and clear sentences.

Good style:

```text
Jetpack Compose was selected because it allows the UI to be written directly in Kotlin.
This reduces XML boilerplate and makes the screen easier to update when state changes.
```

Avoid:

```text
This application leverages a highly sophisticated declarative interface implementation paradigm.
```

## 3. Report Story

The report should include the brainstorming, planning, and design stages because the rubric gives points for planning and design.

### 3.1 Brainstorming Story

Use this honest project evolution story:

```text
At the beginning, several mobile application ideas were considered. The first idea was a student planning application that could organize tasks and study time. This idea was useful, but it was close to a normal to-do list. Another idea was a study challenge application where students could compare study progress with friends. This was more interactive, but it did not fully show enough screens and learning flow.

After comparing these ideas, the final idea became SkillArcade, a gamified learning platform. This idea was selected because it combines learning, progress tracking, goals, trophies, and interaction in one mobile application. It also gives more opportunity to demonstrate interface design, navigation, reusable components, and data handling.
```

### 3.2 Final Project Idea

Define the final idea like this:

```text
SkillArcade is a mobile learning application that makes skill development feel like an arcade game. The user can browse courses, continue lessons, complete goals, earn XP, and view trophies. The main purpose is to make learning more motivating by adding progress, rewards, and visual feedback.
```

### 3.3 Problem Statement

Use this:

```text
Many learning applications show lessons in a plain list. This can make learning feel repetitive. Some students lose motivation because progress is not clearly visible. SkillArcade solves this problem by turning learning activities into small missions with XP, streaks, trophies, and clear progress indicators.
```

## 4. Suggested Report Sections

Use the exact sections from the project PDF.

### 4.1 Introduction

Purpose:

- introduce the project,
- mention the real-life problem,
- briefly state the solution.

Include:

- project name,
- mobile platform,
- main goal.

Suggested paragraph:

```text
This project is called SkillArcade. It is a Kotlin Android mobile application designed as a gamified learning platform. The main goal is to help users follow courses, continue lessons, complete daily goals, and see their learning progress in a more motivating way.
```

### 4.2 Project Idea

Purpose:

- explain what the app does,
- explain why it is useful,
- mention the brainstorming process.

Include:

- previous ideas considered,
- reason for final selection,
- target user.

Screenshot to include:

- splash screen,
- onboarding screen.

### 4.3 Technology Choice

Purpose:

- explain why Kotlin and Android Studio were selected.

Mention:

- Kotlin is officially supported for Android,
- Jetpack Compose allows declarative UI,
- Android Studio provides emulator, preview, debugging, and project tools.

Suggested paragraph:

```text
Kotlin was selected because it is the modern programming language for Android development. It has a cleaner syntax than Java and supports null safety. Android Studio was selected because it is the official IDE for Android development. Jetpack Compose was used because it allows UI screens to be built directly with Kotlin functions.
```

Code snippet to include:

```kotlin
@Composable
fun HomeScreen() {
    // Home dashboard UI
}
```

Explain:

- `@Composable` means the function describes part of the UI.
- The screen is built with Kotlin instead of XML.

### 4.4 Planning Stage

Purpose:

- show that the project was planned before coding.

Include:

- screen list,
- navigation plan,
- data model plan,
- design system plan.

Screens planned:

- Splash Screen,
- Onboarding Slides,
- Home Dashboard,
- Course Catalog,
- Course Progress,
- Lesson Player,
- Goals Screen,
- Trophy Room.

Screenshot to include:

- design mockup or screen image from the design folder.

Write in this style:

```text
The application was planned as a multi-screen mobile app. Each screen has one clear responsibility. This makes the app easier to understand and easier to implement.
```

### 4.5 Development Stage

Purpose:

- explain how the app was built.

Use step-by-step subsections.

Recommended subsections:

```text
Step 1: Creating the Android Project
Step 2: Creating the Theme
Step 3: Building Reusable Components
Step 4: Creating the Screens
Step 5: Adding Navigation
Step 6: Adding State and Data
Step 7: Testing and Improving the UI
```

For each step, include:

- what was built,
- why it was needed,
- important code snippet,
- screenshot if visual.

Example:

```text
Step 3: Building Reusable Components

Reusable components were created for cards, progress bars, buttons, and navigation items.

Why This Matters
The same design style appears on many screens. Reusable components reduce repeated code and keep the interface consistent.
```

### 4.6 Final Version

Purpose:

- show final app screens and explain functionality.

Include screenshots:

- Home Dashboard,
- Course Catalog,
- Course Progress,
- Lesson Player,
- Goals Screen,
- Trophy Room.

For every screenshot, write:

```text
Figure X shows the [screen name].

This screen includes:
- first important UI element,
- second important UI element,
- third important UI element.

Result
The user can [main benefit].
```

### 4.7 Challenges and Solutions

Purpose:

- show what was difficult and how it was solved.

Use before/after style when possible.

Teacher-style example:

```text
Challenge: The first version of the home screen looked too flat.

Before Improvement
The cards had only background color and did not clearly look interactive.

Solution
I added a strong border, rounded corners, and hard shadow to match the arcade design system.

After Improvement
The cards became easier to recognize as clickable elements.

Why This Matters
Visual feedback helps the user understand which parts of the UI are interactive.
```

Other possible challenges:

- matching the HTML design in Jetpack Compose,
- keeping text readable on small screens,
- building bottom navigation,
- saving progress locally,
- creating reusable components,
- making progress bars and trophy cards consistent.

### 4.8 Conclusion

Purpose:

- summarize what was achieved.

Mention:

- project goal,
- main features,
- technology learned,
- possible future improvements.

Suggested paragraph:

```text
In conclusion, SkillArcade successfully demonstrates a gamified learning mobile application. The app includes onboarding, course browsing, lesson progress, goals, trophies, and navigation between multiple screens. During development, I practiced Kotlin, Jetpack Compose, UI design, state handling, and basic data organization.
```

### 4.9 GitHub Information

Include:

- GitHub username,
- repository link,
- short note that the project was completed individually.

Example:

```text
GitHub Username: [your username]
Repository Link: [repository URL]
```

## 5. Screenshot Checklist

Capture clear emulator screenshots.

Required screenshots:

- Splash Screen,
- Onboarding Slide 1,
- Onboarding Slide 2,
- Onboarding Slide 3,
- Onboarding Slide 4,
- Home Dashboard,
- Course Catalog,
- Course Progress,
- Lesson Player,
- Goals Screen,
- Trophy Room.

Optional before/after screenshots:

- before applying arcade border and shadow,
- after applying arcade border and shadow,
- before improving spacing,
- after improving spacing,
- before adding bottom navigation,
- after adding bottom navigation.

Important:

- Screenshots should be clear.
- Do not crop important UI.
- Use consistent emulator size.
- Add captions under screenshots in the report.

## 6. Code Snippet Checklist

Include only short code snippets.

Good snippets:

- one data class,
- one composable screen,
- one reusable component,
- one navigation route,
- one state update example,
- one local data example.

Do not paste full files.

### 6.1 Data Model Snippet

Example:

```kotlin
data class Course(
    val title: String,
    val progress: Float,
    val xp: Int
)
```

Explain:

- This model stores course information.
- The UI reads this data to display progress and XP.

### 6.2 Reusable Component Snippet

Example:

```kotlin
@Composable
fun ArcadeCard(content: @Composable () -> Unit) {
    Card {
        content()
    }
}
```

Explain:

- The card keeps the design consistent.
- It can be reused on the dashboard, course list, and trophy screen.

### 6.3 State Snippet

Example:

```kotlin
var currentXp by remember { mutableStateOf(1200) }
```

Explain:

- `mutableStateOf` stores a value that can change.
- When the value changes, Compose recomposes the UI.

## 7. Design Explanation Guide

SkillArcade uses a **Neo-Boutique Arcade** design style.

Explain it simply:

```text
The design uses a light cream background, strong black borders, rounded cards, and hard shadows. This creates an arcade-like visual style while keeping the app readable.
```

Mention:

- yellow for primary actions,
- pink and mint for accents,
- black borders for structure,
- rounded cards for friendly appearance,
- hard shadows for arcade effect.

### 7.1 Before and After Examples

Use this format:

```text
Before Using the Arcade Card Style
The course cards looked like simple flat boxes.

After Using the Arcade Card Style
The cards had a black border, rounded corners, and hard shadow.

Result
The interface became more playful and more consistent with the game-like learning concept.
```

```text
Before Adding Progress Feedback
The user could see the course name but not how much progress was completed.

After Adding Progress Feedback
The screen displayed XP, progress bars, and lesson completion status.

Result
The user could understand learning progress quickly.
```

## 8. Data Handling Explanation

The app should show simple data handling.

Possible data examples:

- course list,
- lesson progress,
- XP amount,
- goals,
- trophies,
- current streak.

Explain it like this:

```text
The application uses structured Kotlin data classes to represent courses, lessons, goals, and trophies. These objects are displayed in Jetpack Compose screens. This keeps the UI separate from the data structure and makes the code easier to organize.
```

If local storage is implemented, add:

```text
The app stores progress locally so the user can keep XP and completed lessons after reopening the application.
```

## 9. Navigation Explanation

Explain navigation with a simple flow.

Example:

```text
The app starts with the splash screen and onboarding screens. After onboarding, the user enters the home dashboard. From the dashboard, the user can open courses, view lesson progress, start a lesson, check daily goals, or open the trophy room.
```

Possible flow:

```text
Splash Screen
↓
Onboarding
↓
Home Dashboard
↓
Course Catalog / Course Progress / Lesson Player / Goals / Trophy Room
```

## 10. Final Report Tone

The report should sound like a course explanation.

Use:

- clear definitions,
- numbered steps,
- short bullet lists,
- small code examples,
- figure captions,
- summary paragraphs.

Avoid:

- very long paragraphs,
- exaggerated marketing language,
- copying the design document directly,
- unexplained screenshots,
- large code blocks.

Final takeaway:

```text
The report should show not only the final app, but also how the idea was selected, planned, designed, implemented, tested, and improved.
```
