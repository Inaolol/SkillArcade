---
name: Neo-Boutique Arcade
colors:
  surface: '#fff8ef'
  surface-dim: '#e2d9c7'
  surface-bright: '#fff8ef'
  surface-container-lowest: '#ffffff'
  surface-container-low: '#fcf3e0'
  surface-container: '#f6edda'
  surface-container-high: '#f0e7d5'
  surface-container-highest: '#eae2cf'
  on-surface: '#1f1b10'
  on-surface-variant: '#4d4632'
  inverse-surface: '#343024'
  inverse-on-surface: '#f9f0dd'
  outline: '#7f775f'
  outline-variant: '#d0c6ab'
  surface-tint: '#705d00'
  primary: '#705d00'
  on-primary: '#ffffff'
  primary-container: '#ffd500'
  on-primary-container: '#705c00'
  inverse-primary: '#eac300'
  secondary: '#ac2a5e'
  on-secondary: '#ffffff'
  secondary-container: '#fe6b9e'
  on-secondary-container: '#6e0035'
  tertiary: '#006c46'
  on-tertiary: '#ffffff'
  tertiary-container: '#32f5a7'
  on-tertiary-container: '#006c46'
  error: '#ba1a1a'
  on-error: '#ffffff'
  error-container: '#ffdad6'
  on-error-container: '#93000a'
  primary-fixed: '#ffe174'
  primary-fixed-dim: '#eac300'
  on-primary-fixed: '#221b00'
  on-primary-fixed-variant: '#554500'
  secondary-fixed: '#ffd9e1'
  secondary-fixed-dim: '#ffb1c6'
  on-secondary-fixed: '#3f001b'
  on-secondary-fixed-variant: '#8c0a46'
  tertiary-fixed: '#4dffb2'
  tertiary-fixed-dim: '#00e297'
  on-tertiary-fixed: '#002112'
  on-tertiary-fixed-variant: '#005234'
  background: '#fff8ef'
  on-background: '#1f1b10'
  surface-variant: '#eae2cf'
typography:
  display-xl:
    fontFamily: Clash Display
    fontSize: 48px
    fontWeight: '700'
    lineHeight: '1.1'
    letterSpacing: 0.02em
  headline-lg:
    fontFamily: Clash Display
    fontSize: 32px
    fontWeight: '700'
    lineHeight: '1.2'
    letterSpacing: 0.02em
  headline-md:
    fontFamily: Clash Display
    fontSize: 24px
    fontWeight: '600'
    lineHeight: '1.2'
  body-lg:
    fontFamily: Epilogue
    fontSize: 18px
    fontWeight: '500'
    lineHeight: '1.6'
  body-md:
    fontFamily: Epilogue
    fontSize: 16px
    fontWeight: '400'
    lineHeight: '1.6'
  label-bold:
    fontFamily: Epilogue
    fontSize: 14px
    fontWeight: '700'
    lineHeight: '1.2'
  label-sm:
    fontFamily: Epilogue
    fontSize: 12px
    fontWeight: '600'
    lineHeight: '1.2'
rounded:
  sm: 0.25rem
  DEFAULT: 0.5rem
  md: 0.75rem
  lg: 1rem
  xl: 1.5rem
  full: 9999px
spacing:
  unit: 4px
  xs: 4px
  sm: 8px
  md: 16px
  lg: 24px
  xl: 32px
  gutter: 16px
  margin: 20px
---

## Brand & Style
This design system embodies a **Playful Neo-Boutique / Brutalist Pop-Art** aesthetic. It targets a modern, skill-oriented gaming audience that values both competitive intensity and high-fashion editorial flair. The personality is unapologetically bold, tactile, and energetic. 

The UI should feel like a physical arcade machine merged with a high-end streetwear lookbook. It leverages high-contrast visuals, heavy structural lines, and a "thick" interactive language to evoke feelings of excitement, reliability, and tangible achievement.

## Colors
The palette is built on a "Neo-Boutique" base of warm Cream and crisp White, punctured by high-octane Pop-Art accents. 

- **Primary Yellow (#FFD500):** Used for main actions, high-priority highlights, and win states.
- **Background Cream (#FDFBF7):** The foundation for all screens to reduce optical fatigue while maintaining a retro-premium feel.
- **Accent Pink & Mint:** Used for secondary categories, difficulty tiers (Easy/Hard), or decorative flourishes.
- **Ink Black (#121212):** Used for all structural borders, hard shadows, and typography to ensure maximum legibility and "Brutalist" definition.

## Typography
The typographic system creates a tension between geometric structure and expressive weight.

- **Headings:** Use **Clash Display**. All headlines must be uppercase with a tight line height. This creates a "blocky" feel that aligns with the arcade aesthetic.
- **Body:** Use **Epilogue**. It provides a contemporary, slightly quirky character that remains highly readable for instructions and descriptions.
- **Hierarchy:** Use extreme weight contrasts. Large, chunky headlines should be paired with medium-weight body text to maintain the "Pop-Art" poster feel.

## Layout & Spacing
The layout follows a rigid, structural grid that mimics comic book panels or classic arcade interfaces. 

- **Grid Model:** A 4-column fluid mobile grid with 16px gutters. 
- **Rhythm:** Spacing increments are strictly based on 4px units.
- **Padding:** Elements should have generous internal padding (min 16px) to ensure the 3px borders do not feel cramped. 
- **Alignment:** Use heavy vertical stacks. Group related items in bordered containers rather than using whitespace alone to separate content.

## Elevation & Depth
Depth is created through physical geometry rather than light simulation.

- **Hard Shadows:** No blurs. Use a "drop-block" style: `4px 4px 0px #121212`. This gives every element a heavy, 2.5D physical presence.
- **Borders:** Every interactive or containing element must have a `3px solid #121212` border.
- **Layering:** When elements overlap, the shadow of the top element remains hard and opaque. 
- **Press State:** Upon interaction, elements should translate `2px` down and `2px` right, while the shadow shrinks to `2px 2px 0px`, simulating a physical mechanical press.

## Shapes
The shape language is consistently "Soft-Brutalist." While the borders and shadows are aggressive, the corners are rounded to maintain the "Playful" and "Boutique" nature of the design system.

- **Corner Radius:** A standard 12px radius is applied to buttons, cards, and input fields.
- **Containers:** Large layout sections or background cards may use a 20px radius.
- **Consistency:** Do not mix sharp corners with rounded ones; the 12px radius is the signature curve of the interface.

## Components

### Buttons
- **Style:** Chunky, 3px border, 4px hard shadow. 
- **Typography:** Clash Display, Uppercase, Bold.
- **Interaction:** On click, use a transform `translate(2px, 2px)` and reduce shadow size to create a "mechanical" click effect.
- **Variants:** Primary (Yellow), Success (Mint), Destructive (Pink).

### Cards
- **Style:** White or Cream background, 3px black border, 12px corner radius.
- **Shadow:** Always include the 4px hard black shadow to lift cards off the background.

### Input Fields
- **Style:** 3px black border, 12px radius, White background. 
- **Focus State:** Background shifts to Primary Yellow or adds an additional Pink offset outline.
- **Text:** Epilogue for user input, Clash Display for labels.

### Icons
- **Style:** Bold, thick strokes (min 3px to match borders). 
- **Shape:** Arcade-style (simplified, slightly chunky silhouettes).
- **Enclosure:** Often placed inside a small square or circle with a 3px border.

### Chips & Tags
- **Style:** 3px border, no shadow (to keep them secondary to buttons).
- **Colors:** Use Accent Mint or Pink for status indicators.
- **Typography:** Epilogue, Uppercase, 12px Bold.

### Progress Bars
- **Style:** 3px black border, 12px radius. 
- **Fill:** Solid Primary Yellow or Mint. No gradients.
- **Track:** Cream or light grey background.