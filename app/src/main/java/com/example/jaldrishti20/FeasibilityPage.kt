package com.example.jaldrishti20

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Color as AndroidColor
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.FileProvider
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import com.example.jaldrishti20.ui.theme.fontFamily
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolygonOptions
import com.google.maps.android.SphericalUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import kotlin.math.roundToInt

// Data class to hold the detailed feasibility results
data class FeasibilityResult(
    val rainfall: Double = 0.0,
    val potential: Double = 0.0,
    val cost: Double = 0.0,
    val feasibility: String = "Low",
    val tankSize: Double = 0.0,
    val breakdown: Map<String, Double> = emptyMap()
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeasibilityPage(navController: NavController) {
    val blue = Color(0xFF2158D1)
    val red = Color(0xFFD32F2F)
    val yellow = Color(0xFFFFC107)
    val green = Color(0xFF4CAF50)
    val lightBlue = Color(0xFFF0F8FF)
    val white = Color.White
    val gray = Color(0xFF727272)

    // General state
    var currentStage by remember { mutableStateOf("input") }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    // Input states
    var city by remember { mutableStateOf("") }
    var state by remember { mutableStateOf("") }
    var roofArea by remember { mutableStateOf("") }
    var roofType by remember { mutableStateOf("Concrete") }
    var usage by remember { mutableStateOf("Domestic") }
    var isRoofDropdownExpanded by remember { mutableStateOf(false) }

    // Dialog states
    var showGoogleMapDialog by remember { mutableStateOf(false) }
    var polygonPoints by remember { mutableStateOf(listOf<LatLng>()) }
    var initialMapLocation by remember { mutableStateOf(LatLng(28.6139, 77.2090)) } // Default to Delhi

    // Output state
    var feasibilityResult by remember { mutableStateOf(FeasibilityResult()) }
    var analysisError by remember { mutableStateOf<String?>(null) }


    when (currentStage) {
        "input" -> {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("Feasibility Input", fontFamily = fontFamily, color = white, fontWeight = FontWeight.Bold) },
                        colors = TopAppBarDefaults.topAppBarColors(containerColor = blue)
                    )
                },
                containerColor = lightBlue
            ) { padding ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(horizontal = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item {
                        Spacer(Modifier.height(24.dp))
                        Text("Enter Project Details", fontFamily = fontFamily, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = blue)
                        Spacer(Modifier.height(16.dp))
                    }

                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(22.dp),
                            elevation = CardDefaults.cardElevation(10.dp),
                            colors = CardDefaults.cardColors(containerColor = white)
                        ) {
                            Column(Modifier.padding(24.dp)) {
                                OutlinedTextField(value = city, onValueChange = { city = it }, label = { Text("District/City", fontFamily = fontFamily) }, leadingIcon = { Icon(Icons.Default.LocationOn, null) }, modifier = Modifier.fillMaxWidth())
                                Spacer(Modifier.height(16.dp))
                                OutlinedTextField(value = state, onValueChange = { state = it }, label = { Text("State", fontFamily = fontFamily) }, leadingIcon = { Icon(Icons.Default.Place, null) }, modifier = Modifier.fillMaxWidth())
                                Spacer(Modifier.height(16.dp))
                                OutlinedTextField(value = roofArea, onValueChange = { roofArea = it }, label = { Text("Roof Area (sqm)", fontFamily = fontFamily) }, leadingIcon = { Icon(Icons.Default.Home, null) }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.fillMaxWidth())
                                Spacer(Modifier.height(16.dp))

                                //  Buttons for Satellite and AR
                                Row(
                                    Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    Button(
                                        onClick = {
                                            if (city.isNotBlank() && state.isNotBlank()) {
                                                coroutineScope.launch {
                                                    try {
                                                        val (lat, lon) = getCoordinates(city, state, "eea82c7e02064f76a8dc57b73937297f")
                                                        initialMapLocation = LatLng(lat, lon)
                                                        showGoogleMapDialog = true
                                                    } catch (e: Exception) {
                                                        Toast.makeText(context, e.message ?: "Location not found.", Toast.LENGTH_LONG).show()
                                                    }
                                                }
                                            } else {
                                                Toast.makeText(context, "Please enter City and State first.", Toast.LENGTH_LONG).show()
                                            }
                                        },
                                        modifier = Modifier.weight(1f),
                                        colors = ButtonDefaults.buttonColors(containerColor = blue),
                                        shape = RoundedCornerShape(12.dp)
                                    ) {
                                        Text("Satellite View", fontFamily = fontFamily, color = Color.White, fontSize = 14.sp, maxLines = 1)
                                    }
                                    Button(
                                        onClick = {
                                            Toast.makeText(context, "AR Camera feature coming soon!", Toast.LENGTH_SHORT).show()
                                        },
                                        modifier = Modifier.weight(1f),
                                        colors = ButtonDefaults.buttonColors(containerColor = blue),
                                        shape = RoundedCornerShape(12.dp)
                                    ) {
                                        Text("AR Camera", fontFamily = fontFamily, color = Color.White, fontSize = 14.sp)
                                    }
                                }
                                Spacer(Modifier.height(16.dp))

                                ExposedDropdownMenuBox(expanded = isRoofDropdownExpanded, onExpandedChange = { isRoofDropdownExpanded = it }) {
                                    OutlinedTextField(
                                        value = roofType,
                                        onValueChange = {},
                                        readOnly = true,
                                        label = { Text("Roof Type", fontFamily = fontFamily) },
                                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(isRoofDropdownExpanded) },
                                        modifier = Modifier
                                            .menuAnchor()
                                            .fillMaxWidth()
                                    )
                                    ExposedDropdownMenu(expanded = isRoofDropdownExpanded, onDismissRequest = { isRoofDropdownExpanded = false }) {
                                        listOf("Concrete", "Metal", "Tiles", "Plastic").forEach { type ->
                                            DropdownMenuItem(text = { Text(type) }, onClick = { roofType = type; isRoofDropdownExpanded = false })
                                        }
                                    }
                                }
                                Spacer(Modifier.height(16.dp))
                                OutlinedTextField(value = usage, onValueChange = { usage = it }, label = { Text("Primary Usage (e.g., Domestic)", fontFamily = fontFamily) }, leadingIcon = { Icon(Icons.Default.Info, null) }, modifier = Modifier.fillMaxWidth())
                            }
                        }
                    }

                    item {
                        Spacer(Modifier.height(24.dp))
                        Button(
                            onClick = {
                                if (city.isNotBlank() && state.isNotBlank() && roofArea.isNotBlank()) {
                                    analysisError = null
                                    currentStage = "loading"
                                } else {
                                    Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = blue),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                        ) {
                            Text("Analyze Feasibility", color = white, fontFamily = fontFamily, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        }
                        Spacer(Modifier.height(20.dp))
                    }
                }
            }
        }
        "loading" -> {
            FeasibilityLoadingScreen()
            LaunchedEffect(Unit) {
                coroutineScope.launch {
                    try {
                        val result = performFeasibilityAnalysis(context, city, state, roofArea, roofType, usage)
                        feasibilityResult = result
                        currentStage = "result"
                    } catch (e: Exception) {
                        analysisError = e.message ?: "An unknown error occurred."
                        currentStage = "input"
                    }
                }
            }
        }
        "result" -> {
            val resultColor = when (feasibilityResult.feasibility) {
                "High" -> green
                "Moderate" -> yellow
                else -> red
            }
            val resultIcon = when (feasibilityResult.feasibility) {
                "High" -> Icons.Default.CheckCircle
                "Moderate" -> Icons.Default.Info
                else -> Icons.Default.Warning
            }

            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("Feasibility Report", fontFamily = fontFamily, color = white, fontWeight = FontWeight.Bold) },
                        colors = TopAppBarDefaults.topAppBarColors(containerColor = blue),
                        navigationIcon = {
                            IconButton(onClick = { currentStage = "input" }) {
                                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = white)
                            }
                        }
                    )
                },
                containerColor = lightBlue
            ) { padding ->
                LazyColumn(modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 16.dp)) {
                    item {
                        Spacer(Modifier.height(16.dp))
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(20.dp),
                            elevation = CardDefaults.cardElevation(8.dp),
                            colors = CardDefaults.cardColors(containerColor = white)
                        ) {
                            Column(
                                Modifier
                                    .padding(20.dp)
                                    .fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(resultIcon, contentDescription = null, tint = resultColor, modifier = Modifier.size(48.dp))
                                Spacer(Modifier.height(12.dp))
                                Text("Feasibility: ${feasibilityResult.feasibility}", fontWeight = FontWeight.Bold, color = blue, fontSize = 22.sp, textAlign = TextAlign.Center)
                                Spacer(Modifier.height(8.dp))
                                Text("Location: $city, $state", color = gray, fontSize = 16.sp)
                            }
                        }
                    }

                    item { Spacer(Modifier.height(16.dp)) }

                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(20.dp),
                            elevation = CardDefaults.cardElevation(8.dp),
                            colors = CardDefaults.cardColors(containerColor = white)
                        ) {
                            Column(Modifier.padding(20.dp)) {
                                ResultRow("Annual Rainfall:", "${feasibilityResult.rainfall.roundToInt()} mm")
                                ResultRow("Water Potential:", "${feasibilityResult.potential.roundToInt()} Liters/Year")
                                ResultRow("Recommended Tank:", "${feasibilityResult.tankSize.roundToInt()} Liters")
                                Divider(Modifier.padding(vertical = 12.dp))
                                Text("Predicted Cost: ₹${"%,.0f".format(feasibilityResult.cost)}", fontWeight = FontWeight.Bold, color = blue, fontSize = 20.sp)
                                Spacer(Modifier.height(8.dp))
                                feasibilityResult.breakdown.forEach { (item, price) ->
                                    ResultRow("• $item:", "₹${"%,.0f".format(price)}")
                                }
                            }
                        }
                    }

                    item {
                        Spacer(Modifier.height(24.dp))
                        Button(
                            onClick = {
                                val sceneViewerIntent = Intent(Intent.ACTION_VIEW)
                                val intentUri = Uri.parse("https://arvr.google.com/scene-viewer/1.0").buildUpon()
                                    .appendQueryParameter("file", "https://storage.googleapis.com/rainwater-harvesting/rain_water_collector_idea_model%20(1)%20(1).glb")
                                    .appendQueryParameter("mode", "ar_only")
                                    .appendQueryParameter("title", "Rainwater Collector Model")
                                    .build()
                                sceneViewerIntent.data = intentUri
                                sceneViewerIntent.setPackage("com.google.android.googlequicksearchbox")

                                try {
                                    context.startActivity(sceneViewerIntent)
                                } catch (e: ActivityNotFoundException) {
                                    Toast.makeText(context, "Google App is required for VR Preview.", Toast.LENGTH_LONG).show()
                                    try {
                                        val playStoreIntent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.google.android.googlequicksearchbox"))
                                        context.startActivity(playStoreIntent)
                                    } catch (e: ActivityNotFoundException) {
                                        val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.googlequicksearchbox"))
                                        context.startActivity(webIntent)
                                    }
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = blue),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Icon(painter = painterResource(id = R.drawable.scheme), contentDescription = "VR Preview", tint = Color.White)
                            Spacer(Modifier.width(8.dp))
                            Text("VR Preview", color = Color.White, fontWeight = FontWeight.Bold)
                        }

                        Spacer(Modifier.height(12.dp))

                        OutlinedButton(
                            onClick = {
                                val reportFile = createDetailedPdfReport(context, city, state, roofArea, roofType, feasibilityResult)
                                sharePdf(context, reportFile)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Icon(Icons.Default.Share, contentDescription = null, tint = blue)
                            Spacer(Modifier.width(8.dp))
                            Text("Share Detailed Report", color = blue, fontWeight = FontWeight.Bold)
                        }
                        Spacer(Modifier.height(20.dp))
                    }
                }
            }
        }
    }

    if (showGoogleMapDialog) {
        PolygonGoogleMapDialog(
            initialLocation = initialMapLocation,
            onAreaSelected = { areaSqMeters ->
                roofArea = "%.1f".format(areaSqMeters)
                showGoogleMapDialog = false
            },
            onDismiss = {
                showGoogleMapDialog = false
            }
        )
    }

    if (analysisError != null) {
        AlertDialog(
            onDismissRequest = { analysisError = null },
            title = { Text("Analysis Failed") },
            text = { Text(analysisError!!) },
            confirmButton = {
                Button(onClick = { analysisError = null }) {
                    Text("OK")
                }
            }
        )
    }
}

fun createDetailedPdfReport(context: Context, city: String, state: String, area: String, roofType: String, result: FeasibilityResult): File {
    val pdfDocument = PdfDocument()
    val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create()
    val page = pdfDocument.startPage(pageInfo)
    val canvas = page.canvas
    var yPosition = 40f

    val titlePaint = Paint().apply {
        color = AndroidColor.parseColor("#1565C0")
        textSize = 22f
        isFakeBoldText = true
        textAlign = Paint.Align.CENTER
    }
    val headerPaint = Paint().apply {
        color = AndroidColor.parseColor("#1565C0")
        textSize = 16f
        isFakeBoldText = true
    }
    val bodyPaint = Paint().apply {
        color = AndroidColor.BLACK
        textSize = 12f
    }
    val labelPaint = Paint().apply {
        color = AndroidColor.DKGRAY
        textSize = 12f
        isFakeBoldText = true
    }
    val feasibilityPaint = Paint().apply {
        textSize = 18f
        isFakeBoldText = true
        textAlign = Paint.Align.CENTER
        color = when(result.feasibility) {
            "High" -> AndroidColor.parseColor("#4CAF50")
            "Moderate" -> AndroidColor.parseColor("#FFC107")
            else -> AndroidColor.parseColor("#D32F2F")
        }
    }

    canvas.drawText("Rainwater Harvesting Feasibility Report", pageInfo.pageWidth / 2f, yPosition, titlePaint)
    yPosition += 40

    canvas.drawText("Project Details", 40f, yPosition, headerPaint)
    yPosition += 25
    canvas.drawText("Location: $city, $state", 40f, yPosition, bodyPaint)
    yPosition += 20
    canvas.drawText("Roof Details: $area sqm, $roofType", 40f, yPosition, bodyPaint)
    yPosition += 40

    canvas.drawText("Feasibility Score: ${result.feasibility}", pageInfo.pageWidth / 2f, yPosition, feasibilityPaint)
    yPosition += 40

    canvas.drawText("Analysis Summary", 40f, yPosition, headerPaint)
    yPosition += 25
    canvas.drawText("Estimated Annual Rainfall: ${result.rainfall.roundToInt()} mm", 40f, yPosition, labelPaint)
    yPosition += 20
    canvas.drawText("Total Harvestable Water Potential: ${"%,.0f".format(result.potential)} Liters/Year", 40f, yPosition, bodyPaint.apply { isFakeBoldText = true })
    yPosition += 40

    canvas.drawText("System Recommendation & Cost", 40f, yPosition, headerPaint)
    yPosition += 25
    canvas.drawText("Recommended Storage Tank Size: ${"%,.0f".format(result.tankSize)} Liters", 40f, yPosition, labelPaint)
    yPosition += 30
    canvas.drawText("Total Estimated Cost:", 40f, yPosition, labelPaint)
    yPosition += 30
    canvas.drawText("₹${"%,.0f".format(result.cost)}", 40f, yPosition, titlePaint.apply { textAlign = Paint.Align.LEFT; textSize = 28f })
    yPosition += 45

    canvas.drawText("Cost Breakdown:", 40f, yPosition, headerPaint)
    yPosition += 25
    result.breakdown.forEach { (item, price) ->
        canvas.drawText("• $item:", 40f, yPosition, labelPaint)
        canvas.drawText("₹${"%,.0f".format(price)}", 200f, yPosition, bodyPaint)
        yPosition += 20
    }

    pdfDocument.finishPage(page)
    val file = File(context.cacheDir, "RWH_Report.pdf")
    try {
        FileOutputStream(file).use { out ->
            pdfDocument.writeTo(out)
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    pdfDocument.close()
    return file
}


fun sharePdf(context: Context, file: File) {
    if (!file.exists()) {
        Toast.makeText(context, "Error: Report file could not be created.", Toast.LENGTH_SHORT).show()
        return
    }

    val authority = "${context.packageName}.fileprovider"
    val uri = FileProvider.getUriForFile(context, authority, file)

    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "application/pdf"
        putExtra(Intent.EXTRA_STREAM, uri)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }
    try {
        context.startActivity(Intent.createChooser(shareIntent, "Share Detailed Report"))
    } catch (e: Exception) {
        Toast.makeText(context, "No application available to share PDF.", Toast.LENGTH_LONG).show()
    }
}


data class CheckingItem(
    val title: String,
    val icon: ImageVector,
    val description: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeasibilityLoadingScreen() {
    val blue = Color(0xFF1565C0)
    val white = Color.White
    val lightBlue = Color(0xFFF0F8FF)
    val checkingItems = listOf(
        CheckingItem("Geocoding Location", Icons.Default.LocationOn, "Converting city and state to coordinates"),
        CheckingItem("Fetching Rainfall Data", Icons.Default.LocationOn, "Analyzing precipitation patterns for your area"),
        CheckingItem("Calculating Water Potential", Icons.Default.LocationOn, "Estimating harvestable water based on roof size"),
        CheckingItem("Predicting System Cost", Icons.Default.LocationOn, "Forecasting filter, tank, and labor expenses"),
        CheckingItem("Assessing Feasibility Score", Icons.Default.LocationOn, "Determining the overall project viability")
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Feasibility Analysis",
                        color = white,
                        fontFamily = fontFamily ,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = blue)
            )
        },
        containerColor = lightBlue
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Card(
                    colors = CardDefaults.cardColors(containerColor = white),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(4.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "Analyzing Your Location", fontFamily = fontFamily,
                            fontWeight = FontWeight.Bold,
                            color = blue,
                            fontSize = 22.sp,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "Comprehensive rainwater harvesting potential assessment", fontFamily = fontFamily,
                            fontWeight = FontWeight.Medium,
                            color = Color.Gray,
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        CircularProgressIndicator(
                            color = blue,
                            strokeWidth = 6.dp,
                            modifier = Modifier.size(80.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            "Processing data...", fontFamily = fontFamily,
                            color = blue,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        LinearProgressIndicator(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .height(8.dp),
                            color = blue,
                            trackColor = lightBlue
                        )
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    "What we're analyzing:", fontFamily = fontFamily,
                    fontWeight = FontWeight.Bold,
                    color = blue,
                    fontSize = 18.sp,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
            items(checkingItems) { item ->
                FeasibilityCheckItemCard(item, blue)
                Spacer(modifier = Modifier.height(12.dp))
            }
            item { Spacer(modifier = Modifier.height(20.dp)) }
        }
    }
}


@Composable
fun FeasibilityCheckItemCard(item: CheckingItem, iconColor: Color) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = iconColor.copy(alpha = 0.1f),
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    item.icon,
                    contentDescription = item.title,
                    tint = iconColor,
                    modifier = Modifier
                        .padding(12.dp)
                        .size(24.dp)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    item.title,
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    item.description,
                    color = Color.Gray,
                    fontSize = 13.sp
                )
            }
        }
    }
}

@Composable
fun rememberMapViewWithLifecycle(): MapView {
    val context = LocalContext.current
    val mapView = remember { MapView(context) }
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    DisposableEffect(lifecycle, mapView) {
        val observer = object : DefaultLifecycleObserver {
            override fun onCreate(owner: LifecycleOwner) = mapView.onCreate(null)
            override fun onStart(owner: LifecycleOwner) = mapView.onStart()
            override fun onResume(owner: LifecycleOwner) = mapView.onResume()
            override fun onPause(owner: LifecycleOwner) = mapView.onPause()
            override fun onStop(owner: LifecycleOwner) = mapView.onStop()
            override fun onDestroy(owner: LifecycleOwner) = mapView.onDestroy()
        }
        lifecycle.addObserver(observer)
        onDispose { lifecycle.removeObserver(observer) }
    }
    return mapView
}


@Composable
fun PolygonGoogleMapDialog(
    initialLocation: LatLng,
    onAreaSelected: (Double) -> Unit,
    onDismiss: () -> Unit
) {
    val mapView = rememberMapViewWithLifecycle()
    var polygonPoints by remember { mutableStateOf(listOf<LatLng>()) }
    val coroutineScope = rememberCoroutineScope()

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Mark Roof Corners on Map", fontFamily = fontFamily) },
        text = {
            Column {
                Box(Modifier
                    .fillMaxWidth()
                    .height(350.dp)) {
                    AndroidView(
                        factory = { mapView },
                        update = { view ->
                            coroutineScope.launch {
                                view.getMapAsync { map ->
                                    map.mapType = GoogleMap.MAP_TYPE_SATELLITE
                                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(initialLocation, 19f))
                                    map.uiSettings.isZoomControlsEnabled = true

                                    map.setOnMapClickListener { latLng ->
                                        polygonPoints = polygonPoints + latLng

                                        map.clear()
                                        if (polygonPoints.size > 2) {
                                            map.addPolygon(
                                                PolygonOptions()
                                                    .addAll(polygonPoints)
                                                    .strokeColor(AndroidColor.parseColor("#FF2158D1"))
                                                    .fillColor(AndroidColor.parseColor("#442158D1"))
                                            )
                                        }
                                        polygonPoints.forEach { point ->
                                            map.addMarker(MarkerOptions().position(point))
                                        }
                                    }
                                }
                            }
                        },
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Spacer(Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()){
                    Text(
                        "Points: ${polygonPoints.size}",
                        fontFamily = fontFamily,
                        lineHeight = 18.sp
                    )
                    TextButton(onClick = { polygonPoints = emptyList() }){
                        Text("Clear")
                    }
                }
                Text("Tap on map to add corners. Min 3 required.", fontFamily = fontFamily, fontSize = 12.sp, color = Color.Gray)
            }
        },
        confirmButton = {
            Button(
                enabled = polygonPoints.size > 2,
                onClick = {
                    val area = SphericalUtil.computeArea(polygonPoints)
                    onAreaSelected(area)
                }
            ) {
                Text("Use This Area", fontFamily = fontFamily)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel", fontFamily = fontFamily) }
        }
    )
}

@Composable
fun ResultRow(label: String, value: String) {
    Row(Modifier
        .fillMaxWidth()
        .padding(vertical = 4.dp), verticalAlignment = Alignment.CenterVertically) {
        Text(label, fontWeight = FontWeight.Bold, color = Color.Black, modifier = Modifier.weight(1f))
        Text(value, color = Color.Gray, textAlign = TextAlign.End)
    }
}


suspend fun performFeasibilityAnalysis(context: Context, city: String, state: String, roofAreaStr: String, roofType: String, usage: String): FeasibilityResult {
    return withContext(Dispatchers.IO) {
        val apiKey = "eea82c7e02064f76a8dc57b73937297f"
        val area = roofAreaStr.toDoubleOrNull() ?: throw Exception("Invalid roof area entered.")

        val (lat, lon) = getCoordinates(city, state, apiKey)
        val annualRain = fetchRainfall(lat, lon)

        val efficiencies = mapOf("concrete" to 0.75, "metal" to 0.90, "tiles" to 0.80, "plastic" to 0.85)
        val coeff = efficiencies[roofType.lowercase()] ?: 0.70

        val potentialLiters = area * annualRain * coeff
        val tankCapacityNeeded = potentialLiters * 0.08
        val filterCost = if (area < 200) 6500.0 else 15000.0
        val tankCost = tankCapacityNeeded * 11
        val pipingExtras = mapOf("tiles" to area * 45, "concrete" to area * 25, "metal" to area * 35)
        val pipeCost = pipingExtras[roofType.lowercase()]?.toDouble() ?: (area * 30.0)
        val laborCost = 8000 + (area * 15)
        val totalCost = filterCost + tankCost + pipeCost + laborCost

        val score = when {
            annualRain < 400 -> "Low"
            potentialLiters < 20000 -> "Low"
            potentialLiters > 100000 && annualRain > 600 && coeff >= 0.8 -> "High"
            else -> "Moderate"
        }

        FeasibilityResult(
            rainfall = annualRain,
            potential = potentialLiters,
            cost = totalCost,
            feasibility = score,
            tankSize = tankCapacityNeeded,
            breakdown = mapOf(
                "Filter" to filterCost,
                "Storage Tank" to tankCost,
                "Piping & Gutters" to pipeCost,
                "Labor" to laborCost
            )
        )
    }
}

suspend fun getCoordinates(city: String, state: String, apiKey: String): Pair<Double, Double> {
    return withContext(Dispatchers.IO) {
        val query = "$city, $state".replace(" ", "+")
        val url = "https://api.opencagedata.com/geocode/v1/json?q=$query&key=$apiKey"
        try {
            val response = URL(url).readText()
            val json = JSONObject(response)
            val results = json.getJSONArray("results")
            if (results.length() == 0) throw Exception("Location not found. Please check city/state spelling.")
            val geometry = results.getJSONObject(0).getJSONObject("geometry")
            val lat = geometry.getDouble("lat")
            val lon = geometry.getDouble("lng")
            Pair(lat, lon)
        } catch (e: Exception) {
            throw Exception("Failed to connect to geocoding service. Check your internet connection.")
        }
    }
}

suspend fun fetchRainfall(lat: Double, lon: Double): Double {
    return withContext(Dispatchers.IO) {
        val url = "https://archive-api.open-meteo.com/v1/archive?latitude=$lat&longitude=$lon&start_date=2023-01-01&end_date=2023-12-31&daily=precipitation_sum&timezone=auto&precipitation_unit=mm"
        try {
            val response = URL(url).readText()
            val json = JSONObject(response)
            if (json.has("daily")) {
                val dailyData = json.getJSONObject("daily").getJSONArray("precipitation_sum")
                var total = 0.0
                for (i in 0 until dailyData.length()) {
                    if (!dailyData.isNull(i)) {
                        total += dailyData.getDouble(i)
                    }
                }
                if (total == 0.0) 700.0 else total // Fallback if API returns 0 data
            } else {
                700.0 // Fallback rainfall
            }
        } catch (e: Exception) {
            700.0 // Fallback rainfall on any error
        }
    }
}
