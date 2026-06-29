💧 Jal Drishti 2.0
Care Before It Becomes Rare
> A smart, AI-powered Rooftop Rainwater Harvesting Feasibility App — helping families, farmers, and governments make data-driven water conservation decisions.
[![Hackathon](https://img.shields.io/badge/Hackathon-Code%20Slayer%202K25-blue.svg)]()
[![Team](https://img.shields.io/badge/Team-Code%20Titans-purple.svg)]()
[![Platform](https://img.shields.io/badge/Platform-Web%20App-brightgreen.svg)]()
[![Backend](https://img.shields.io/badge/Backend-Firebase%20%7C%20GEE-orange.svg)]()
![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)
> 
📌 What is Jal Drishti?
India receives abundant rainfall every year — yet millions face water shortage. The problem isn't lack of water; it's poor management and wasted runoff.
Jal Drishti is an app that helps any user — homeowner, farmer, builder, or government body — understand exactly how much rainwater their rooftop or land can harvest, how to set up the system, and what it will cost.
> Backed by research with the **Civil Engineering department at NIT Kurukshetra**, consultations with **KUK Construction branch experts**, and a field visit to **Pabnawa village** where real harvesting systems are in use. 
🎯 Problem Statement
Groundwater levels across India are continuously depleting
Most rooftop rainwater flows away unused
People lack accessible, location-specific data to plan harvesting systems
Rooftop Rainwater Harvesting (RWH) is government-promoted but adoption is low due to complexity
Jal Drishti removes that barrier — making feasibility analysis instant, free, and visual.
> 
✨ Key Features
Feature	Description
📍 Location-based Analysis	Auto-fetch rainfall & soil data for any location
🧮 Capacity Calculator	Formula-based: `V = A × I × K`
🛰️ Satellite Data Integration	Google Earth Engine for real spatial & rainfall data
🗺️ QGIS Geospatial Processing	Advanced mapping and catchment area analysis
🤖 AI/ML Rooftop Detection	Computer vision models estimate rooftop area automatically
🥽 AR/VR Visualization	Preview your harvesting system virtually before building
📄 PDF Report Generation	Downloadable feasibility report with charts and cost estimates
🔔 WhatsApp Reminders	Maintenance alerts for filter cleaning, first-flush checks
💰 Cost Estimator	Breakdown by house size (Small / Medium / Large)
🏗️ Step-by-Step Setup Guide	Gutters, first-flush diverter, storage installation steps

🧮 Core Formula
```
V = A × I × K
```
Variable	Meaning	Example
`V`	Volume of water collected (litres/year)	1,71,044 L
`A`	Catchment / Roof Area (m²)	317.12 m²
`I`	Annual Effective Rainfall (mm)	674.2 mm
`K`	Runoff Coefficient (0.7–0.9)	0.8 (standard RCC roof)

👥 Target Users
🏠 Families & individuals — reduce water bills, save at home
🌾 Farmers — plan irrigation using harvested rainwater
🏛️ Government & local bodies — large-scale water planning
🏫 Schools & institutions — adopt as sustainability initiative
🏗️ Builders & contractors — integrate RWH in new construction
🏢 Corporates — CSR-driven water conservation programs

🛠️ Tech Stack
Layer	Technology
Frontend	HTML / CSS / JavaScript / React
Satellite & Rainfall Data	Google Earth Engine (GEE) API
Geospatial Analysis	QGIS (cloud-based microservices)
AI / ML	Computer Vision for rooftop detection
Visualization	AR/VR virtual system preview
Backend / Database	Firebase (Firestore + Authentication)
Notifications	WhatsApp API
PDF Reports	Chart.js + jsPDF
Hosting	Firebase Hosting

📊 Sample Feasibility Report Output

Location (Lat, Lon)          : 28.4089, 77.3178
Rooftop Rainfall (Effective) : 674.2 mm
Roof Area                    : 317.12 m²
Soil Condition               : Sand: 45% | Clay: 30%
Total Capacity (Potential)   : 1,71,044 liters/year
Design Suggestion            : Storage Tank
```
Report includes:
📊 Monthly rainfall bar chart (Jan–Dec)
🥧 Cost breakdown pie chart (Filters / Storage Tank / Labor)
📋 4-step installation guide
💸 Cost range: ₹20,000 – ₹1,20,000+
---
💰 Cost Estimation
House Size	Roof Area	Estimated Cost
Small House	100–150 m²	₹20,000 – ₹40,000
Medium House	200–300 m²	₹40,000 – ₹70,000
Large House	300 m²+	₹70,000 – ₹1,20,000+
Cost split (approx): Filters ~20% · Storage Tank ~50% · Labor ~30%
---
💼 Business Model

Free Tier           → Basic feasibility analysis + PDF reports
Premium Tier        → Advanced analytics, custom reports, multi-property
Vendor Marketplace  → Connect users with verified RWH contractors (commission)
Govt. Licensing     → White-label solution for municipal water planning
CSR Partnerships    → Corporate water conservation programs

---
📁 Project Structure
```
Jal-Drishti-2.0/
│
├── public/
│   └── index.html
│
├── src/
│   ├── components/            # Map, Chart, ReportCard, ARViewer
│   ├── pages/                 # Home, Analyzer, Report, Marketplace
│   ├── services/
│   │   ├── geeService.js      # Google Earth Engine rainfall data
│   │   ├── firebase.js        # Firebase config & Firestore
│   │   ├── qgisService.js     # Geospatial analysis pipeline
│   │   ├── mlService.js       # Rooftop detection AI model
│   │   └── reportGenerator.js # PDF report generation
│   └── utils/
│       ├── rainfallCalc.js    # V = A × I × K formula
│       └── costEstimator.js   # Cost breakdown logic
│
├── functions/                 # Firebase Cloud Functions
│   └── index.js
│
├── .env.example
├── .gitignore
├── package.json
└── README.md
```
---
🚀 Getting Started
Prerequisites
Node.js v16+
Firebase CLI: `npm install -g firebase-tools`
Google Earth Engine account
1. Clone the Repository
```bash
git clone https://github.com/Gavy2006/Jal-Drishti-2.0.git
cd Jal-Drishti-2.0
```
2. Install Dependencies
```bash
npm install
```
3. Configure Environment Variables
```bash
cp .env.example .env
```
```env
REACT_APP_FIREBASE_API_KEY=your_key
REACT_APP_FIREBASE_PROJECT_ID=jaldristi-backend
REACT_APP_GOOGLE_MAPS_API_KEY=your_key
GEE_SERVICE_ACCOUNT=jaldristi-gee-sa@jaldristi-backend.iam.gserviceaccount.com
GEE_KEY_FILE=path/to/service-account.json   # NEVER commit this file
```
4. Run Locally
```bash
npm start
```
5. Deploy
```bash
firebase login
firebase deploy
```
---
🔐 Security Notice
The GEE service account key (`jaldristi-backend-*.json`) must never be committed to GitHub.
Add to `.gitignore`:
```gitignore
# Service account keys
jaldristi-backend-*.json

# Environment variables
.env
.env.local
```
Store keys securely using Firebase Cloud Functions config or GitHub Actions Secrets.

🗺️ Roadmap
[x] Location-based rainfall & soil analysis
[x] Capacity calculator (V = A × I × K)
[x] PDF feasibility report generation
[x] Monthly rainfall chart
[x] Cost estimation breakdown
[ ] AI/ML rooftop auto-detection
[ ] AR/VR system preview
[ ] WhatsApp reminder integration
[ ] Vendor marketplace
[ ] Mobile app (React Native)
[ ] Hindi & regional language support
[ ] Government licensing portal

🏆 Built For
Code Slayer 2K25 Hackathon — presented by Team Code Titans
Research supported by:
Civil Engineering Department, NIT Kurukshetra
Construction Branch Experts, Kurukshetra University
Field visit: Pabnawa Village, Haryana

🤝 Contributing
Fork the repo
Create a branch: `git checkout -b feature/your-feature`
Commit: `git commit -m "Add your feature"`
Push: `git push origin feature/your-feature`
Open a Pull Request

👤 Author
Manisha Dhankhar,Devika,Gavy

🙏 Acknowledgements
Google Earth Engine for satellite rainfall data
Firebase for backend infrastructure
Central Ground Water Board (CGWB) for RWH guidelines
Ministry of Jal Shakti, Government of India
Civil Engineering Department, NIT Kurukshetra

> *"Jal Drishti — Care Before It Becomes Rare."*
