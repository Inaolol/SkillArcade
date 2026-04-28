# SkillArcade — Agent Index

This file is intentionally light. It points to where things live; load detail on demand.

## Project

Android Studio app. For scope, package structure, implementation order, constraints, and conventions, read `specs/project_guide.md` when starting work.

## UI / Design (lazy-load before any UI work)

- `skillarcade_design/<screen>/code.html` — layout reference (do not copy verbatim)
- `skillarcade_design/<screen>/screen.png` — visual target to match
- `skillarcade_design/DESIGN.md` — Neo-Boutique Arcade tokens: colors, typography, spacing, borders, shadows

Screen folders: `splash_screen`, `onboarding_slide_1..4`, `home_dashboard`, `course_catalog`, `course_progress`, `lesson_player`, `goals_screen`, `trophy_room`.

## Fonts

Custom fonts live in `app/src/main/res/font/` (lowercase + underscores only). Required: **Clash Display** (headlines) and **Epilogue** (body). If missing, stop and ask the user to download:
- Clash Display → https://www.fontshare.com/fonts/clash-display
- Epilogue → https://fonts.google.com/specimen/Epilogue

## Architecture

MVVM + Jetpack Compose + Hilt + Room, per `specs/project_guide.md` §3. Defer details to that file and to `Codex-android-skill`.

## Skills to use

Invoke via the `Skill` tool when the situation matches:

- `Codex-android-skill` — any Android/Kotlin/Compose/architecture/Hilt/Room work
- `frontend-design` and `high-end-visual-design` — building or polishing UI to match the Neo-Boutique aesthetic
- `superpowers:brainstorming` — before starting any new feature
- `superpowers:writing-plans` / `superpowers:executing-plans` — multi-step work
- `superpowers:test-driven-development` — adding logic with testable behavior
- `superpowers:systematic-debugging` — any bug or test failure
- `superpowers:verification-before-completion` — before claiming a task is done

## Implementation log

Every implementation task appends an entry to `specs/implementation-log.md` using the template in `specs/project_guide.md` §7.

## Editing this file

Keep it light. Do not paste product scope, package trees, design tokens, or per-screen rules here — those live in the guide and design folder and are read on demand.
