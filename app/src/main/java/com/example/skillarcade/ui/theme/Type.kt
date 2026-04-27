package com.example.skillarcade.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.skillarcade.R

// ─── Font families ───────────────────────────────────────────────────────────

val ClashDisplay = FontFamily(
    Font(R.font.clash_display_bold, FontWeight.Bold),
    Font(R.font.clash_display_semibold, FontWeight.SemiBold)
)

val Epilogue = FontFamily(
    Font(R.font.epilogue_regular, FontWeight.Normal),
    Font(R.font.epilogue_medium, FontWeight.Medium),
    Font(R.font.epilogue_bold, FontWeight.Bold)
)

// ─── Typography scale ────────────────────────────────────────────────────────
//
// Design spec → Material3 slot mapping:
//   display-xl   (Clash Display 48sp Bold)       → displayLarge
//   headline-lg  (Clash Display 32sp Bold)        → headlineLarge
//   headline-md  (Clash Display 24sp SemiBold)    → headlineMedium
//   body-lg      (Epilogue 18sp Medium)           → bodyLarge
//   body-md      (Epilogue 16sp Normal)           → bodyMedium
//   label-bold   (Epilogue 14sp Bold)             → labelLarge
//   label-sm     (Epilogue 12sp SemiBold)         → labelSmall
//
// Note: All Clash Display styles should be rendered UPPERCASE at the call-site
// (e.g. text.uppercase() or using TextStyle.platformStyle). Do NOT force
// uppercase inside the Typography definition.

val SkillArcadeTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = ClashDisplay,
        fontWeight = FontWeight.Bold,
        fontSize = 48.sp,
        lineHeight = (48 * 1.1).sp,   // lineHeight: 1.1
        letterSpacing = 0.96.sp // letterSpacing: 0.02em
    ),
    headlineLarge = TextStyle(
        fontFamily = ClashDisplay,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = (32 * 1.2).sp,
        letterSpacing = 0.64.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = ClashDisplay,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        lineHeight = (24 * 1.2).sp,
        letterSpacing = 0.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = Epilogue,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
        lineHeight = (18 * 1.6).sp,
        letterSpacing = 0.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = Epilogue,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = (16 * 1.6).sp,
        letterSpacing = 0.sp
    ),
    labelLarge = TextStyle(
        fontFamily = Epilogue,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        lineHeight = (14 * 1.2).sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = Epilogue,
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp,
        lineHeight = (12 * 1.2).sp,
        letterSpacing = 0.sp
    )
)
