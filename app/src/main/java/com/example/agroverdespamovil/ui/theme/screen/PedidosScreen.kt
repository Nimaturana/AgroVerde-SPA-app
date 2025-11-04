package com.example.agroverdespamovil.ui.theme.screen



import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

data class Pedido(
    val id: Int,
    val producto: String,
    val fecha: String,
    val estado: String,
    val total: Double
)

private val PrimaryGreen = Color(0xFF4CAF50)
private val BackgroundLight = Color(0xFFF1F8E9)
private val OrangeAccent = Color(0xFFFF9800)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PedidoScreen(navController: NavController) {
    val pedidos = remember {
        mutableStateOf(
            listOf(
                Pedido(1, "Tomates Orgánicos", "02/11/2025", "Entregado", 5000.0),
                Pedido(2, "Lechugas Hidropónicas", "28/10/2025", "En camino", 3600.0),
                Pedido(3, "Manzanas Orgánicas", "21/10/2025", "Pendiente", 6400.0)
            )
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Mis Pedidos",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = PrimaryGreen
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundLight)
                .padding(paddingValues)
        ) {
            if (pedidos.value.isEmpty()) {
                // Mensaje si no hay pedidos
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        Icons.Default.ShoppingBag,
                        contentDescription = null,
                        tint = Color.Gray,
                        modifier = Modifier.size(80.dp)
                    )
                    Text(
                        text = "Aún no tienes pedidos",
                        color = Color.Gray,
                        fontSize = 16.sp
                    )
                }
            } else {
                // Lista de pedidos
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(pedidos.value) { pedido ->
                        PedidoCard(pedido = pedido)
                    }
                }
            }
        }
    }
}

@Composable
fun PedidoCard(pedido: Pedido) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = pedido.producto,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = pedido.fecha,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Estado: ${pedido.estado}",
                    color = when (pedido.estado) {
                        "Entregado" -> PrimaryGreen
                        "En camino" -> OrangeAccent
                        else -> MaterialTheme.colorScheme.error
                    },
                    fontWeight = FontWeight.Medium,
                    fontSize = 13.sp
                )

                Text(
                    text = "$${String.format("%.0f", pedido.total)}",
                    color = OrangeAccent,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
