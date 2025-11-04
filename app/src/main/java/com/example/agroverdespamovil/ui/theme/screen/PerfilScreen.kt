package com.example.agroverdespamovil.ui.theme.screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.agroverdespamovil.components.ImagePicker
import com.example.agroverdespamovil.data.local.LocalImageStorage
import com.example.agroverdespamovil.util.*
import com.example.agroverdespamovil.viewmodel.PerfilViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

// Colores tema AgroVerde
private val PrimaryGreen = Color(0xFF4CAF50)
private val DarkGreen = Color(0xFF2E7D32)
private val LightGreen = Color(0xFF81C784)
private val BackgroundLight = Color(0xFFF1F8E9)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilScreen(
    navController: NavController,
    authViewModel: com.example.agroverdespamovil.viewmodel.AuthViewModel
) {
    val context = LocalContext.current

    // Crear ViewModel con Context
    val perfilViewModel: PerfilViewModel = viewModel(
        factory = object : androidx.lifecycle.ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return PerfilViewModel(context) as T
            }
        }
    )

    // Estados

    val usuario by authViewModel.usuario.collectAsState()
    val isLoading by perfilViewModel.isLoading.collectAsState()
    val error by perfilViewModel.error.collectAsState()
    val avatarUri by perfilViewModel.avatarUri.collectAsState()

    var showImagePicker by remember { mutableStateOf(false) }
    var showPermissionDialog by remember { mutableStateOf(false) }
    var permissionDeniedMessage by remember { mutableStateOf("") }
    var tempCameraUri by remember { mutableStateOf<Uri?>(null) }

    // Animación de entrada
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        visible = true
    }

    // ========== LAUNCHERS ==========

    // Launcher para cámara
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success && tempCameraUri != null) {
            perfilViewModel.actualizarAvatar(tempCameraUri!!)
        }
    }

    // Launcher para galería
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            perfilViewModel.actualizarAvatar(it)
        }
    }

    // Launcher de permisos de cámara
    val cameraPermissionLauncher = rememberCameraPermissionLauncher { isGranted ->
        if (isGranted) {
            // Crear archivo temporal y abrir cámara
            val imageFile = CameraUtils.createImageFile(context)
            val uri = CameraUtils.getImageUri(context, imageFile)  // Variable local
            tempCameraUri = uri  // Guardar para después
            cameraLauncher.launch(uri)  //  Usa la variable local
        } else {
            permissionDeniedMessage = "Se necesita permiso de cámara para tomar fotos"
            showPermissionDialog = true
        }
    }

    // Launcher de permisos de galería
    val galleryPermissionLauncher = rememberGalleryPermissionLauncher { isGranted ->
        if (isGranted) {
            galleryLauncher.launch("image/*")
        } else {
            permissionDeniedMessage = "Se necesita permiso para acceder a la galería"
            showPermissionDialog = true
        }
    }

    // ========== UI ==========

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(BackgroundLight, Color.White)
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // TopAppBar
            TopAppBar(
                title = { Text("Mi Perfil", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = PrimaryGreen,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )

            // Contenido con animación
            AnimatedVisibility(
                visible = visible && !isLoading,
                enter = fadeIn() + slideInVertically(initialOffsetY = { 50 })
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(16.dp))

                    // ========== FOTO DE PERFIL ==========
                    Box(
                        contentAlignment = Alignment.BottomEnd
                    ) {
                        // Avatar
                        Box(
                            modifier = Modifier
                                .size(140.dp)
                                .clip(CircleShape)
                                .border(4.dp, PrimaryGreen, CircleShape)
                                .background(Color.White),
                            contentAlignment = Alignment.Center
                        ) {
                            if (avatarUri != null) {
                                // Mostrar imagen guardada
                                Image(
                                    painter = rememberAsyncImagePainter(avatarUri),
                                    contentDescription = "Foto de perfil",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            } else {
                                // Icono por defecto
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = "Sin foto",
                                    modifier = Modifier.size(80.dp),
                                    tint = Color.Gray.copy(alpha = 0.5f)
                                )
                            }
                        }

                        // Botón de cámara flotante
                        FloatingActionButton(
                            onClick = { showImagePicker = true },
                            modifier = Modifier.size(48.dp),
                            containerColor = PrimaryGreen,
                            contentColor = Color.White
                        ) {
                            Icon(
                                imageVector = Icons.Default.CameraAlt,
                                contentDescription = "Cambiar foto",
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // ========== INFORMACIÓN DEL USUARIO ==========
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp)
                        ) {
                            Text(
                                text = "Información Personal",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = DarkGreen
                                )
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            // Nombre
                            InfoRow(
                                icon = Icons.Default.Person,
                                label = "Nombre",
                                value = "${usuario?.nombre ?: ""} ${usuario?.apellido ?: ""}".trim()
                            )

                            Divider(modifier = Modifier.padding(vertical = 12.dp))

                            // Email
                            InfoRow(
                                icon = Icons.Default.Email,
                                label = "Correo",
                                value = usuario?.email ?: "No disponible"
                            )

                            Divider(modifier = Modifier.padding(vertical = 12.dp))

                            // Teléfono
                            InfoRow(
                                icon = Icons.Default.Phone,
                                label = "Teléfono",
                                value = usuario?.telefono ?: "No registrado"
                            )

                            Divider(modifier = Modifier.padding(vertical = 12.dp))

                            // Dirección
                            InfoRow(
                                icon = Icons.Default.LocationOn,
                                label = "Dirección",
                                value = usuario?.direccion ?: "No registrada"
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // ========== BOTÓN CERRAR SESIÓN ==========
                    OutlinedButton(
                        onClick = {
                            // TODO: Implementar logout
                            navController.navigate("login") {
                                popUpTo(0) { inclusive = true }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color.Red
                        ),
                        border = ButtonDefaults.outlinedButtonBorder.copy(
                            width = 2.dp,
                            brush = Brush.horizontalGradient(listOf(Color.Red, Color.Red))
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Default.Logout, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Cerrar Sesión", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                    }

                    Spacer(modifier = Modifier.height(32.dp))
                }
            }

            // Loading
            if (isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = PrimaryGreen)
                }
            }
        }

        // ========== DIALOGS ==========

        // Dialog de selección de imagen
        if (showImagePicker) {
            ImagePicker(
                onCameraClick = {
                    if (CameraUtils.hasCameraPermission(context)) {
                        val imageFile = CameraUtils.createImageFile(context)
                        val uri = CameraUtils.getImageUri(context, imageFile)  // Variable local
                        tempCameraUri = uri  // Guardar para después
                        cameraLauncher.launch(uri)  // Usa variable local
                    } else {
                        cameraPermissionLauncher.launch(android.Manifest.permission.CAMERA)
                    }
                },
                onGalleryClick = {
                    if (CameraUtils.hasGalleryPermission(context)) {
                        galleryLauncher.launch("image/*")
                    } else {
                        val permission = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                            android.Manifest.permission.READ_MEDIA_IMAGES
                        } else {
                            android.Manifest.permission.READ_EXTERNAL_STORAGE
                        }
                        galleryPermissionLauncher.launch(permission)
                    }
                },
                onDismiss = { showImagePicker = false }
            )
        }
        // Dialog de permiso denegado
        if (showPermissionDialog) {
            AlertDialog(
                onDismissRequest = { showPermissionDialog = false },
                icon = {
                    Icon(
                        Icons.Default.Warning,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(48.dp)
                    )
                },
                title = { Text("Permiso requerido") },
                text = { Text(permissionDeniedMessage) },
                confirmButton = {
                    TextButton(onClick = { showPermissionDialog = false }) {
                        Text("Entendido", color = PrimaryGreen)
                    }
                }
            )
        }

        // Dialog de error
        error?.let {
            AlertDialog(
                onDismissRequest = { perfilViewModel.clearError() },
                icon = {
                    Icon(
                        Icons.Default.ErrorOutline,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error
                    )
                },
                title = { Text("Error") },
                text = { Text(it) },
                confirmButton = {
                    TextButton(onClick = { perfilViewModel.clearError() }) {
                        Text("OK", color = PrimaryGreen)
                    }
                }
            )
        }
    }
}

// ========== COMPONENTE DE FILA DE INFORMACIÓN ==========
@Composable
private fun InfoRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = PrimaryGreen,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Color.Gray,
                    fontWeight = FontWeight.Medium
                )
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.SemiBold
                )
            )
        }
    }
}