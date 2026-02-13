package com.example.jaldrishti20

import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.jaldrishti20.ui.theme.fontFamily
data class NewsItem(
    val title: String,
    val source: String,
    val imageRes: Int,
    val webUrl: String
)
private val PrimaryBlue = Color(0xFF2158D1)
val newsList = listOf(
    NewsItem(
        title = "Nationwide campaign: Catch rain where, when it falls—build RWHS now",
        source = "Jal Shakti",
        imageRes = R.drawable.img_12,
        webUrl = "https://mowr.gov.in/jsa"
    ),
    NewsItem(
        title = "₹6,000cr World Bank scheme: Community groundwater management in 7 water-stressed states.",
        source = "Ataljal",
        imageRes = R.drawable.img_16,
        webUrl = "https://mowr.gov.in/jsa"
    ),
    NewsItem(
        title = "Rejuvenate water bodies, recharge ground water via Aquifer Plans for water-secure cities..",
        source = "amrut.gov",
        imageRes = R.drawable.img_13,
        webUrl = "https://ataljal.mowr.gov.in"
    ),
    NewsItem(
        title = "Builds rural check dams, ponds, rooftops for water harvesting, conservation.",
        source = "nrega ",
        imageRes = R.drawable.img_14,
        webUrl = "https://amrut.gov.in"
    ),
    NewsItem(
        title = "Har Khet Ko Pani: Jal Sanchay harvesting, Jal Shinchan recharge for farms",
        source = "pmksy",
        imageRes = R.drawable.img_15,
        webUrl = "https://nrega.nic.in"
    )
)

@Composable
fun NewsScreen(navController: NavController) {
    val context = LocalContext.current

    Scaffold(
        topBar = { NewsTopBar() },
        bottomBar = { BottomNavBar2(navController) },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            items(newsList) { news ->
                NewsCard(news = news) {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(news.webUrl))
                    context.startActivity(intent)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsTopBar() {
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo1),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .size(28.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Schemes",
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        },
        actions = {
            IconButton(onClick = {}) {
                Icon(Icons.Default.Search, contentDescription = null)
            }
            IconButton(onClick = {}) {
                Icon(Icons.Default.Notifications, contentDescription = null)
            }
            IconButton(onClick = {}) {
                Icon(Icons.Default.ShoppingCart, contentDescription = null)
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF2158D1),
            titleContentColor = Color.White,
            actionIconContentColor = Color.White
        )
    )
}

@Composable
fun NewsCard(
    news: NewsItem,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor =  Color.White
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column {
            Image(
                painter = painterResource(news.imageRes),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = news.title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 12.dp)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = news.source,
                fontSize = 13.sp,
                color = Color.Gray,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
            )
        }
    }
}
@Composable
fun BottomNavBar2(navController: NavController) {

    var selectedIndex by remember { mutableStateOf(2) }

    NavigationBar(
        containerColor = PrimaryBlue,
        modifier = Modifier.height(82.dp)
    ) {

        BottomNavItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home",
                    tint = if (selectedIndex == 0) PrimaryBlue else Color.White
                )
            },
            label = "Home",
            selected = selectedIndex == 0
        ) {
            selectedIndex = 0
            navController.navigate("home")
        }


        BottomNavItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.vendor211),
                    contentDescription = "Vendor",
                    tint = if (selectedIndex == 1) PrimaryBlue else Color.White
                )
            },
            label = "Vendor",
            selected = selectedIndex == 1
        ) {
            selectedIndex = 1
            navController.navigate("vendor")
        }



        BottomNavItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.scheme),
                    contentDescription = "Scheme",
                    tint = if (selectedIndex == 2) PrimaryBlue else Color.White
                )
            },
            label = "Scheme",
            selected = selectedIndex == 2
        ) {
            selectedIndex = 2
            navController.navigate("news")
        }

        BottomNavItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.product),
                    contentDescription = "Products",
                    tint = if (selectedIndex == 3) PrimaryBlue else Color.White
                )
            },
            label = "Products",
            selected = selectedIndex == 3
        ) {
            selectedIndex = 3
            navController.navigate("list")
        }



        BottomNavItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = "Cart",
                    tint = if (selectedIndex == 4) PrimaryBlue else Color.White
                )
            },
            label = "Cart",
            selected = selectedIndex == 4
        ) {
            selectedIndex = 4
            navController.navigate("cart")
        }

    }
}

@Composable
fun RowScope.BottomNavItem2(
    icon: ImageVector,
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = {
            Box(
                modifier = Modifier
                    .size(36.dp) // 👈 CONTROL WIDTH HERE
                    .background(
                        if (selected) Color.White.copy(alpha = 0.9f)
                        else Color.Transparent,
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    icon,
                    contentDescription = label,
                    tint = if (selected) Color(0xFF2F68ED) else Color.White
                )
            }
        },
        label = {
            Text(
                label,
                fontFamily = fontFamily,
                fontSize = 10.sp,
                color = Color.White
            )
        },
        colors = NavigationBarItemDefaults.colors(
            indicatorColor = Color.Transparent
        )
    )
}
