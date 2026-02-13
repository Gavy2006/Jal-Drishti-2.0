


package com.example.jaldrishti20

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.jaldrishti20.ui.theme.fontFamily
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Home


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InstallationDetailsPage(navController: NavController) {
    val blue = Color(0xFF2158D1)
    val lightBlue = Color(0xFFF0F8FF)
    val white = Color.White
    val gray = Color(0xFF727272)

    val installationSteps = listOf(
        InstallationStep(
            "Step 1: Gutter Installation",
            "Securely attach gutters along the edges of your roof. Ensure a slight slope towards the downpipe to allow water to flow easily. Use brackets every 3 feet for support.",
            Icons.Default.Build
        ),
        InstallationStep(
            "Step 2: First-Flush Diverter",
            "Install a first-flush diverter in the pipeline after the gutters. This essential device will wash away debris and contaminants from the first spell of rain, ensuring cleaner water enters the tank.",
            Icons.Default.Home
        ),
        InstallationStep(
            "Step 3: Filtration System",
            "Connect the pipe from the diverter to a filtration unit. A simple sand and gravel filter is effective for removing suspended impurities. Ensure the filter has an outlet for clean water and a way to backwash for cleaning.",
            Icons.Default.Home
        ),
        InstallationStep(
            "Step 4: Storage Tank Connection",
            "Route the filtered water pipe to the top inlet of your storage tank. Make sure the tank is placed on a stable, flat surface. Install an overflow pipe near the top of the tank to safely drain excess water.",
            Icons.Default.Home
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Installation Guide", fontFamily = fontFamily, color = white, fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = blue),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = white)
                    }
                }
            )
        },
        containerColor = lightBlue
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    "RWH System Setup",
                    fontFamily = fontFamily,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = blue,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
            items(installationSteps.size) { index ->
                InstallationStepCard(step = installationSteps[index], blue = blue, gray = gray)
            }
        }
    }
}

@Composable
fun InstallationStepCard(step: InstallationStep, blue: Color, gray: Color) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            Surface(
                shape = CircleShape,
                color = blue.copy(alpha = 0.1f),
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = step.icon,
                    contentDescription = null,
                    tint = blue,
                    modifier = Modifier.padding(8.dp)
                )
            }
            Spacer(Modifier.width(16.dp))
            Column {
                Text(
                    text = step.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = blue
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = step.description,
                    fontSize = 14.sp,
                    color = gray,
                    lineHeight = 20.sp
                )
            }
        }
    }
}

data class InstallationStep(
    val title: String,
    val description: String,
    val icon: ImageVector
)


