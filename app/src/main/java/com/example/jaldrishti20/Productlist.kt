package com.example.jaldrishti20


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.jaldrishti20.ui.theme.fontFamily



private val PrimaryBlue = Color(0xFF2158D1)


data class ProductItem(
    val title: String,
    val price: String,
    val oldPrice: String? = null,
    val imageRes: Int
)

val featuredProducts = listOf(
    ProductItem("Stainless Steel Filters, For Water Filter", "₹2,000", "₹1,134", R.drawable.img_4),
    ProductItem("Downspout First Flush Diverter Kit", "₹12,000", imageRes =R.drawable.img_5),
    ProductItem(" Leaf Guard Gutters Cover", "₹1500", imageRes = R.drawable.img_6),
    ProductItem("Rain Barrel Water Storage Container", "₹4,999", imageRes = R.drawable.img_7),
    ProductItem("Water Diversion System", "₹3539", imageRes =R.drawable.img_10),
    ProductItem("Downspout Extended Drain Pipe ", "₹2761", imageRes =R.drawable.img_9)
)


@Composable
fun FeaturedProductsScreen(navController: NavController) {
    Scaffold(
        topBar = { FeaturedTopBar(navController) },
        bottomBar = { BottomNavBar1(navController) },
        containerColor = Color.White
    ) { padding ->

        FeaturedProductsSection(
            modifier = Modifier
                .padding(padding)
                .background(Color.White)
        )
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeaturedTopBar(navController: NavController) {
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo1),
                    contentDescription = "Logo",
                    modifier = Modifier.size(28.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Jal Drishti",
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        },
        actions = {

            IconButton(
                onClick = {},
                modifier = Modifier.padding(horizontal = 2.dp)
            ) {
                Icon(Icons.Default.Search, contentDescription = "Search")
            }


            IconButton(
                onClick = {navController.navigate("cart")},
                modifier = Modifier.padding(horizontal = 4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = "cart",
                    tint = Color.White,
                    modifier = Modifier.padding(8.dp)
                )
            }

        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = PrimaryBlue,
            titleContentColor = Color.White,
            actionIconContentColor = Color.White
        )
    )
}



@Composable
fun FeaturedProductsSection(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF5F7FB))
            .padding(10.dp )
    ) {

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Featured Products",
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )

            }

            Spacer(modifier = Modifier.height(22.dp))
        }

        items(featuredProducts.chunked(2)) { rowItems ->

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                rowItems.forEach { product ->
                    FeaturedProductCard(
                        product = product,
                        modifier = Modifier.weight(1f)
                    )
                }

                if (rowItems.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}


@Composable
fun FeaturedProductCard(
    product: ProductItem,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(Color.White, RoundedCornerShape(16.dp))
            .padding(12.dp)
    ) {

        Box(
            modifier = Modifier
                .height(90.dp)
                .fillMaxWidth()
                , contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(product.imageRes),
                contentDescription = product.title,
                modifier = Modifier.size(115.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = product.title,
            fontFamily = fontFamily,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 2
        )

        Spacer(modifier = Modifier.height(4.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            if (product.oldPrice != null) {
                Text(
                    text = product.oldPrice,
                    fontSize = 12.sp,
                    color = Color.Gray,
                    textDecoration = TextDecoration.LineThrough
                )
                Spacer(modifier = Modifier.width(6.dp))
            }

            Text(
                text = product.price,
                fontSize = 12.sp ,
                fontWeight = FontWeight.SemiBold,
                color = PrimaryBlue
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedButton (
            onClick = {},
            modifier = Modifier.padding(start = 12.dp)
                .height(36.dp),
           // modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(50),
            border = BorderStroke(1.dp, Color(0xFF2F68ED))
        ) {
            Text("Add to cart", fontSize = 12.sp)
        }
    }
}

@Composable
fun BottomNavBar1(navController: NavController) {

    var selectedIndex by remember { mutableStateOf(3) }

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
fun RowScope.BottomNavItem1(
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
                    .size(36.dp)
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
                    tint = if (selected) PrimaryBlue else Color.White
                )
            }
        },
        label = {
            Text(label, fontSize = 10.sp, color = Color.White)
        },
        colors = NavigationBarItemDefaults.colors(
            indicatorColor = Color.Transparent
        )
    )
}
