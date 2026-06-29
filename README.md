<p align="center">
  <h1 align="center">💧 Jal Drishti 2.0</h1>
  <p align="center"><em>Care Before It Becomes Rare</em></p>
  <p align="center">
    <img src="https://img.shields.io/badge/Platform-Android-brightgreen?logo=android" />
    <img src="https://img.shields.io/badge/Language-Kotlin-orange?logo=kotlin" />
    <img src="https://img.shields.io/badge/UI-Jetpack%20Compose-blue?logo=jetpackcompose" />
    <img src="https://img.shields.io/badge/Backend-Firebase-yellow?logo=firebase" />
    <img src="https://img.shields.io/badge/Hackathon-Code%20Slayer%202K25-purple" />
    <img src="https://img.shields.io/badge/License-MIT-blue" />
  </p>
</p>

---

## 📌 Table of Contents

- [What is Jal Drishti?](#-what-is-jal-drishti)
- [The Problem](#-the-problem)
- [Key Features](#-key-features)
- [Core Formula](#-core-formula)
- [Tech Stack](#️-tech-stack)
- [Project Structure](#-project-structure)
- [Getting Started](#-getting-started)
- [Sample Output](#-sample-feasibility-output)
- [Cost Estimation](#-cost-estimation)
- [Business Model](#-business-model)
- [Roadmap](#️-roadmap)
- [Target Users](#-target-users)
- [Team](#-team)
- [Acknowledgements](#-acknowledgements)

---

## 💧 What is Jal Drishti?

**Jal Drishti 2.0** is a smart, AI-powered **Android application** for Rooftop Rainwater Harvesting (RWH) feasibility analysis. It helps families, farmers, builders, and government bodies make data-driven decisions on water conservation — by combining satellite rainfall data, geospatial analysis, AI-based rooftop detection, and cost estimation into one mobile-first tool.

> India receives abundant rainfall every year — yet millions face water shortage. The problem isn't lack of water; it's poor management and wasted runoff.

---

## ❗ The Problem

- Rooftop Rainwater Harvesting (RWH) is government-promoted, yet adoption remains low
- People lack **accessible, location-specific data** to plan harvesting systems
- Existing tools are complex, expensive, or inaccessible to common users
- There is no single platform that goes from **location → analysis → report → vendor**

**Jal Drishti removes that barrier** — making feasibility analysis instant, free, and visual, right from your Android phone.

---

## ✨ Key Features

| Feature | Description |
|---|---|
| 📍 **Location-Based Analysis** | Auto-fetch rainfall & soil data for any entered location |
| 🧮 **Capacity Calculator** | Formula-driven: `V = A × I × K` for accurate volume estimation |
| 🛰️ **Satellite Data Integration** | Google Earth Engine for real spatial & annual rainfall data |
| 🗺️ **Geospatial Analysis** | QGIS-based mapping and catchment area analysis |
| 🤖 **AI/ML Rooftop Detection** | Computer vision model to estimate rooftop area automatically |
| 🥽 **AR/VR Visualization** | Preview your harvesting system virtually before building |
| 📄 **PDF Report Generation** | Downloadable feasibility report with charts and cost estimates |
| 🔔 **WhatsApp Reminders** | Maintenance alerts for filter cleaning, first-flush checks |
| 💰 **Cost Estimator** | Breakdown by house size (Small / Medium / Large) |
| 🏗️ **Step-by-Step Setup Guide** | Gutters, first-flush diverter, and storage installation steps |

---

## 🧮 Core Formula

The water harvesting capacity is calculated using the standard RWH formula:

```
V = A × I × K
```

| Variable | Meaning | Example Value |
|---|---|---|
| `V` | Volume of water collected (litres/year) | 1,71,044 L |
| `A` | Catchment / Roof Area (m²) | 317.12 m² |
| `I` | Annual Effective Rainfall (mm) | 674.2 mm |
| `K` | Runoff Coefficient (0.7–0.9 for RCC roofs) | 0.8 |

> Runoff coefficient varies by roof material: RCC (0.80–0.90), Tiled (0.75–0.85), Metal Sheet (0.70–0.80)

---

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| **Language** | Kotlin |
| **UI Framework** | Jetpack Compose |
| **Build System** | Gradle (Kotlin DSL — `build.gradle.kts`) |
| **Backend & Auth** | Firebase (Firestore + Firebase Authentication) |
| **Satellite & Rainfall Data** | Google Earth Engine (GEE) API |
| **Geospatial Analysis** | QGIS (cloud-based microservices) |
| **AI / ML** | Computer Vision for rooftop area detection |
| **Visualization** | AR/VR virtual system preview |
| **Notifications** | WhatsApp API |
| **PDF Reports** | Chart.js + jsPDF |
| **Hosting** | Firebase Hosting |

---

## 📁 Project Structure

```
Jal-Drishti-2.0/
│
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/jaldrishti/
│   │   │   │   ├── ui/               # Jetpack Compose screens & components
│   │   │   │   ├── viewmodel/        # ViewModels for each screen
│   │   │   │   ├── repository/       # Data layer (Firebase, GEE, ML)
│   │   │   │   ├── model/            # Data models (Location, Report, User)
│   │   │   │   └── utils/
│   │   │   │       ├── RainfallCalc.kt     # V = A × I × K formula
│   │   │   │       └── CostEstimator.kt    # Cost breakdown logic
│   │   │   └── res/                  # Layouts, drawables, strings
│   │   └── google-services.json      # Firebase config (DO NOT commit)
│   └── build.gradle.kts              # App-level Gradle config
│
├── gradle/
│   └── libs.versions.toml            # Version catalog
│
├── build.gradle.kts                  # Root-level Gradle config
├── gradle.properties
├── settings.gradle.kts
├── .gitignore
└── README.md
```

---

## 🚀 Getting Started

### Prerequisites

- Android Studio Hedgehog (2023.1.1) or later
- Android SDK 26+
- A Firebase project (with Firestore and Authentication enabled)
- Google Earth Engine account (for satellite rainfall data)
- Google Maps API key

### Steps

**1. Clone the Repository**

```bash
git clone https://github.com/Gavy2006/Jal-Drishti-2.0.git
cd Jal-Drishti-2.0
```

**2. Connect Firebase**

- Go to [Firebase Console](https://console.firebase.google.com) → Create/select your project
- Add an Android app with your package name
- Download `google-services.json` and place it inside `app/`

**3. Add API Keys**

Create a `local.properties` file (already gitignored) and add:

```properties
MAPS_API_KEY=your_google_maps_api_key
GEE_SERVICE_ACCOUNT=your_gee_service_account_email
```

> ⚠️ **Never commit `google-services.json` or any API keys to GitHub.**

**4. Build & Run**

- Open the project in Android Studio
- Let Gradle sync complete
- Run on an emulator or physical Android device (API 26+)

---

## 📊 Sample Feasibility Output

```
Location (Lat, Lon)       : 28.4089, 77.3178
Rooftop Rainfall (Annual) : 674.2 mm
Roof Area (Detected)      : 317.12 m²
Soil Condition            : Sand 45% | Clay 30%
Total Harvest Potential   : 1,71,044 litres/year
Recommended System        : Underground Storage Tank

Report includes:
  📊 Monthly rainfall bar chart (Jan–Dec)
  🥧 Cost breakdown pie chart (Filters / Tank / Labour)
  📋 4-step installation guide
  💸 Estimated cost: ₹40,000 – ₹70,000
```

---

## 💰 Cost Estimation

| House Size | Roof Area | Estimated Cost |
|---|---|---|
| Small House | 100–150 m² | ₹20,000 – ₹40,000 |
| Medium House | 200–300 m² | ₹40,000 – ₹70,000 |
| Large House | 300 m²+ | ₹70,000 – ₹1,20,000+ |

**Cost split (approximate):** Filters ~20% · Storage Tank ~50% · Labour ~30%

---

## 💼 Business Model

```
Free Tier          →  Basic feasibility analysis + PDF report
Premium Tier       →  Advanced analytics, custom reports, multi-property tracking
Vendor Marketplace →  Connect users with verified RWH contractors (commission-based)
Govt. Licensing    →  White-label solution for municipal water planning bodies
CSR Partnerships   →  Corporate water conservation programs
```

---

## 🗺️ Roadmap

- [x] Location-based rainfall & soil data analysis
- [x] Capacity calculator (V = A × I × K)
- [x] PDF feasibility report generation
- [x] Monthly rainfall chart (Jan–Dec)
- [x] Cost estimation breakdown
- [x] Firebase Authentication & Firestore integration
- [ ] AI/ML rooftop area auto-detection
- [ ] AR/VR harvesting system preview
- [ ] WhatsApp reminder integration
- [ ] Vendor marketplace
- [ ] Hindi & regional language support
- [ ] Government licensing portal

---

## 👥 Target Users

| User | Use Case |
|---|---|
| 🏠 Families & Individuals | Reduce water bills, harvest at home |
| 🌾 Farmers | Plan irrigation using harvested rainwater |
| 🏛️ Government & Local Bodies | Large-scale water planning and policy |
| 🏫 Schools & Institutions | Adopt as a sustainability initiative |
| 🏗️ Builders & Contractors | Integrate RWH into new construction |
| 🏢 Corporates | CSR-driven water conservation programs |

---

## 👤 Team

**Team Code Titans** — Built for **Code Slayer 2K25 Hackathon**

- Manisha Dhankhar
- Devika Sharma
- Gavy

Research supported by:
- Civil Engineering Department, NIT Kurukshetra
- Construction Branch Experts, Kurukshetra University
- Field visit: Pabnawa Village, Haryana

---

## 🙏 Acknowledgements

- [Google Earth Engine](https://earthengine.google.com/) — for satellite rainfall data
- [Firebase](https://firebase.google.com/) — for backend infrastructure
- [Central Ground Water Board (CGWB)](https://cgwb.gov.in/) — for RWH guidelines
- [Ministry of Jal Shakti, Government of India](https://jalshakti-ddws.gov.in/) — for policy reference
- Civil Engineering Department, NIT Kurukshetra

---

## 📄 License

This project is licensed under the **MIT License** — see the [LICENSE](LICENSE) file for details.

---

> *"Jal Drishti — Care Before It Becomes Rare."* 💧
