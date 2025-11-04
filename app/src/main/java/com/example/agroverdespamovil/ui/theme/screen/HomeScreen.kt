package com.example.agroverdespamovil.ui.theme.screen

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.animation.core.*
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.agroverdespamovil.model.Producto
import com.example.agroverdespamovil.viewmodel.AuthViewModel
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import com.example.agroverdespamovil.util.ClimaUtils

// Colores tema AgroVerde
private val PrimaryGreen = Color(0xFF4CAF50)
private val DarkGreen = Color(0xFF2E7D32)
private val LightGreen = Color(0xFF81C784)
private val BackgroundLight = Color(0xFFF1F8E9)
private val OrangeAccent = Color(0xFFFF9800)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    authViewModel: AuthViewModel
) {
    // Estados
    val usuario by authViewModel.usuario.collectAsState()
    var isLoading by remember { mutableStateOf(false) }
    var showMenu by remember { mutableStateOf(false) }
    var climaData by remember { mutableStateOf<String?>(null) }
    var showClimaDialog by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    // Productos de ejemplo
    val productos = remember {
        listOf(
            Producto(
                id = 1,
                nombre = "Tomates Orgánicos",
                descripcion = "Tomates frescos cultivados sin pesticidas",
                categoria = "Verduras",
                precio = 2500.0,
                unidadMedida = "kg",
                stock = 50,
                productorId = 1,
                nombreProductor = "Huerto Verde",
                certificacionOrganica = true,
                region = "Región Metropolitana",
                imagenUrl = null,
                calificacion = 4.5,
                estado = "DISPONIBLE"
            ),
            Producto(
                id = 2,
                nombre = "Lechugas Hidropónicas",
                descripcion = "Lechugas cultivadas en sistemas hidropónicos",
                categoria = "Verduras",
                precio = 1800.0,
                unidadMedida = "unidad",
                stock = 30,
                productorId = 2,
                nombreProductor = "AgroTech",
                certificacionOrganica = true,
                region = "Valparaíso",
                imagenUrl = null,
                calificacion = 4.8,
                estado = "DISPONIBLE"
            ),
            Producto(
                id = 3,
                nombre = "Manzanas Orgánicas",
                descripcion = "Manzanas rojas de producción local",
                categoria = "Frutas",
                precio = 3200.0,
                unidadMedida = "kg",
                stock = 40,
                productorId = 3,
                nombreProductor = "Frutales del Sur",
                certificacionOrganica = true,
                region = "La Araucanía",
                imagenUrl = null,
                calificacion = 4.7,
                estado = "DISPONIBLE"
            ),
            Producto(
                id = 4,
                nombre = "Zanahorias Orgánicas",
                descripcion = "Zanahorias frescas sin químicos",
                categoria = "Verduras",
                precio = 2200.0,
                unidadMedida = "kg",
                stock = 25,
                productorId = 1,
                nombreProductor = "Huerto Verde",
                certificacionOrganica = true,
                region = "Región Metropolitana",
                imagenUrl = null,
                calificacion = 4.3,
                estado = "DISPONIBLE"
            )
        )
    }

    // Animación de entrada
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        visible = true
    }

    // Función para obtener el clima
    fun obtenerClima() {
        isLoading = true
        coroutineScope.launch {
            climaData = ClimaUtils.obtenerClima("Santiago,CL")
            showClimaDialog = true
            isLoading = false
        }
    }

    // Dialog para mostrar el clima
    if (showClimaDialog) {
        AlertDialog(
            onDismissRequest = { showClimaDialog = false },
            title = { Text("Información del Clima", fontWeight = FontWeight.Bold) },
            text = {
                Text(climaData ?: "No hay datos disponibles")
            },
            confirmButton = {
                Button(
                    onClick = { showClimaDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen)
                ) {
                    Text("Cerrar")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "AgroVerde SPA",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                        Text(
                            text = "Productos Orgánicos",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                },
                actions = {
                    // Botón del clima
                    IconButton(
                        onClick = { obtenerClima() },
                        enabled = !isLoading
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                strokeWidth = 2.dp,
                                color = Color.White
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.WbSunny,
                                contentDescription = "Consultar clima",
                                tint = Color.White
                            )
                        }
                    }

                    // Perfil del usuario
                    Box {
                        IconButton(onClick = { showMenu = !showMenu }) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(PrimaryGreen),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = usuario?.nombre?.firstOrNull()?.uppercaseChar()?.toString() ?: "U",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp
                                )
                            }
                        }

                        DropdownMenu(
                            expanded = showMenu,
                            onDismissRequest = { showMenu = false }
                        ) {
                            // Nombre del usuario
                            DropdownMenuItem(
                                text = {
                                    Column {
                                        Text(
                                            text = usuario?.nombre ?: "Usuario",
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = usuario?.email ?: "",
                                            fontSize = 12.sp,
                                            color = Color.Gray
                                        )
                                    }
                                },
                                onClick = { },
                                leadingIcon = {
                                    Icon(Icons.Default.Person, contentDescription = null)
                                }
                            )

                            Divider()

                            DropdownMenuItem(
                                text = { Text("Mi Perfil") },
                                onClick = {
                                    showMenu = false
                                    navController.navigate("perfil")
                                },
                                leadingIcon = {
                                    Icon(Icons.Default.AccountCircle, contentDescription = null)
                                }
                            )

                            DropdownMenuItem(
                                text = { Text("Mis Pedidos") },
                                onClick = {
                                    showMenu = false
                                    navController.navigate("pedidos")
                                },
                                leadingIcon = {
                                    Icon(Icons.Default.ShoppingBag, contentDescription = null)
                                }
                            )

                            Divider()

                            DropdownMenuItem(
                                text = {
                                    Text(
                                        "Cerrar Sesión",
                                        color = MaterialTheme.colorScheme.error
                                    )
                                },
                                onClick = {
                                    showMenu = false
                                    authViewModel.logout()
                                    navController.navigate("login") {
                                        popUpTo(0) { inclusive = true }
                                    }
                                },
                                leadingIcon = {
                                    Icon(
                                        Icons.Default.ExitToApp,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.error
                                    )
                                }
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = DarkGreen
                )
            )
        },
        floatingActionButton = {
            // Botón flotante alternativo para el clima
            FloatingActionButton(
                onClick = { obtenerClima() },
                containerColor = PrimaryGreen,
                contentColor = Color.White
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        strokeWidth = 2.dp,
                        color = Color.White
                    )
                } else {
                    Icon(Icons.Default.WbSunny, contentDescription = "Consultar clima")
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(BackgroundLight)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Header con bienvenida
                item {
                    AnimatedVisibility(
                        visible = visible,
                        enter = fadeIn() + slideInVertically(initialOffsetY = { -50 })
                    ) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = PrimaryGreen
                            )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(20.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "¡Bienvenido/a!",
                                        color = Color.White,
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = usuario?.nombre ?: "Usuario",
                                        color = Color.White.copy(alpha = 0.9f),
                                        fontSize = 18.sp
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "Productos frescos y orgánicos",
                                        color = Color.White.copy(alpha = 0.8f),
                                        fontSize = 14.sp
                                    )

                                    // Botón de clima en el header
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Button(
                                        onClick = { obtenerClima() },
                                        enabled = !isLoading,
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = Color.White,
                                            contentColor = PrimaryGreen
                                        ),
                                        modifier = Modifier.fillMaxWidth(0.7f)
                                    ) {
                                        if (isLoading) {
                                            CircularProgressIndicator(
                                                modifier = Modifier.size(16.dp),
                                                strokeWidth = 2.dp,
                                                color = PrimaryGreen
                                            )
                                        } else {
                                            Icon(
                                                Icons.Default.WbSunny,
                                                contentDescription = null,
                                                modifier = Modifier.size(16.dp)
                                            )
                                        }
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text("Consultar Clima")
                                    }
                                }
                                Icon(
                                    imageVector = Icons.Default.Eco,
                                    contentDescription = null,
                                    tint = Color.White.copy(alpha = 0.3f),
                                    modifier = Modifier.size(80.dp)
                                )
                            }
                        }
                    }
                }

                // Categorías rápidas
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        CategoryChip(
                            icon = Icons.Default.Grain,
                            label = "Verduras",
                            modifier = Modifier.weight(1f)
                        ) { /* TODO: Filtrar */ }

                        CategoryChip(
                            icon = Icons.Default.Eco,
                            label = "Frutas",
                            modifier = Modifier.weight(1f)
                        ) { /* TODO: Filtrar */ }
                    }
                }

                // Título de productos
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Productos Disponibles",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = DarkGreen
                        )
                        Text(
                            text = "${productos.size} productos",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    }
                }

                // Lista de productos
                items(productos) { producto ->
                    ProductoCard(
                        producto = producto,
                        onClick = {
                            navController.navigate("detalle_producto/${producto.id}")
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun CategoryChip(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .height(80.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = PrimaryGreen,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = label,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewHomeScreen() {
    HomeScreen(
        navController = rememberNavController(),
        authViewModel = AuthViewModel()
    )
}

@Composable
private fun ProductoCard(
    producto: Producto,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Imagen placeholder
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(BackgroundLight),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (producto.categoria == "Frutas")
                        Icons.Default.Eco
                    else
                        Icons.Default.Grain,
                    contentDescription = null,
                    tint = PrimaryGreen,
                    modifier = Modifier.size(40.dp)
                )
            }

            // Información
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = producto.nombre,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = producto.descripcion ?: "",
                    fontSize = 13.sp,
                    color = Color.Gray,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (producto.certificacionOrganica) {
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = LightGreen.copy(alpha = 0.2f)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Eco,
                                    contentDescription = null,
                                    tint = DarkGreen,
                                    modifier = Modifier.size(12.dp)
                                )
                                Text(
                                    text = "Orgánico",
                                    fontSize = 10.sp,
                                    color = DarkGreen,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }

                    Text(
                        text = producto.region ?: "",
                        fontSize = 11.sp,
                        color = Color.Gray
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "$${String.format("%.0f", producto.precio)} / ${producto.unidadMedida}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = OrangeAccent
                    )

                    Text(
                        text = "Stock: ${producto.stock}",
                        fontSize = 12.sp,
                        color = if (producto.stock > 10) PrimaryGreen else MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}