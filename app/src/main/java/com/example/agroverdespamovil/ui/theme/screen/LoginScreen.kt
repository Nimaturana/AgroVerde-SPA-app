package com.example.agroverdespamovil.ui.theme.screen

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.agroverdespamovil.viewmodel.AuthViewModel




// Colores tema AgroVerde
private val PrimaryGreen = Color(0xFF4CAF50)
private val DarkGreen = Color(0xFF2E7D32)
private val LightGreen = Color(0xFF81C784)
private val BackgroundLight = Color(0xFFF1F8E9)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavController,
    authViewModel: AuthViewModel
) {
    // Estados UI
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    // Estados de validación
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }

    // Observar estados del ViewModel
    val isLoading by authViewModel.isLoading.collectAsState()
    val error by authViewModel.error.collectAsState()
    val isAuthenticated by authViewModel.isAuthenticated.collectAsState()

    val focusManager = LocalFocusManager.current

    // Animación de entrada
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        visible = true
    }

    // Navegar al home cuando se autentique exitosamente
    LaunchedEffect(isAuthenticated, error) {
        if (isAuthenticated && error == null) {
            navController.navigate("home") {
                popUpTo("login") { inclusive = true }
            }
        }
    }

    // Mostrar error en Dialog
    var showErrorDialog by remember { mutableStateOf(false) }
    LaunchedEffect(error) {
        showErrorDialog = error != null
    }

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
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            // Logo y título con animación
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(animationSpec = tween(600)) +
                        slideInVertically(initialOffsetY = { -100 })
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Icono de agricultura/naturaleza
                    Icon(
                        imageVector = Icons.Default.Eco,
                        contentDescription = "Logo AgroVerde",
                        tint = PrimaryGreen,
                        modifier = Modifier.size(80.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "AgroVerde SPA",
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = DarkGreen,
                            fontSize = 32.sp
                        )
                    )

                    Text(
                        text = "Productos agrícolas orgánicos",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = Color.Gray
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            // Card del formulario con animación
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(animationSpec = tween(600, delayMillis = 200)) +
                        slideInVertically(initialOffsetY = { 100 })
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp)
                    ) {
                        Text(
                            text = "Iniciar Sesión",
                            style = MaterialTheme.typography.headlineSmall.copy(
                                fontWeight = FontWeight.SemiBold,
                                color = DarkGreen
                            )
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        // Campo Email con validación
                        OutlinedTextField(
                            value = email,
                            onValueChange = {
                                email = it
                                emailError = validateEmail(it)
                            },
                            label = { Text("Correo electrónico") },
                            placeholder = { Text("ejemplo@correo.com") },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Email,
                                    contentDescription = null,
                                    tint = if (emailError != null) MaterialTheme.colorScheme.error else PrimaryGreen
                                )
                            },
                            isError = emailError != null,
                            supportingText = {
                                if (emailError != null) {
                                    Text(
                                        text = emailError!!,
                                        color = MaterialTheme.colorScheme.error,
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Email,
                                imeAction = ImeAction.Next
                            ),
                            keyboardActions = KeyboardActions(
                                onNext = { focusManager.moveFocus(FocusDirection.Down) }
                            ),
                            singleLine = true,
                            enabled = !isLoading,
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = PrimaryGreen,
                                focusedLabelColor = PrimaryGreen,
                                cursorColor = PrimaryGreen
                            )
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Campo Password con validación
                        OutlinedTextField(
                            value = password,
                            onValueChange = {
                                password = it
                                passwordError = validatePassword(it)
                            },
                            label = { Text("Contraseña") },
                            placeholder = { Text("Mínimo 6 caracteres") },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Lock,
                                    contentDescription = null,
                                    tint = if (passwordError != null) MaterialTheme.colorScheme.error else PrimaryGreen
                                )
                            },
                            trailingIcon = {
                                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                    Icon(
                                        imageVector = if (passwordVisible)
                                            Icons.Default.Visibility
                                        else
                                            Icons.Default.VisibilityOff,
                                        contentDescription = if (passwordVisible)
                                            "Ocultar contraseña"
                                        else
                                            "Mostrar contraseña",
                                        tint = PrimaryGreen
                                    )
                                }
                            },
                            visualTransformation = if (passwordVisible)
                                VisualTransformation.None
                            else
                                PasswordVisualTransformation(),
                            isError = passwordError != null,
                            supportingText = {
                                if (passwordError != null) {
                                    Text(
                                        text = passwordError!!,
                                        color = MaterialTheme.colorScheme.error,
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Password,
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    focusManager.clearFocus()
                                    if (isFormValid(email, password, emailError, passwordError)) {
                                        authViewModel.login(email, password)
                                    }
                                }
                            ),
                            singleLine = true,
                            enabled = !isLoading,
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = PrimaryGreen,
                                focusedLabelColor = PrimaryGreen,
                                cursorColor = PrimaryGreen
                            )
                        )

                        if (!error.isNullOrEmpty()) {
                            Text(
                                text = error ?: "",
                                color = Color.Red,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Olvidaste tu contraseña
                        TextButton(
                            onClick = { /* TODO: Implementar recuperación */ },
                            modifier = Modifier.align(Alignment.End),
                            enabled = !isLoading
                        ) {
                            Text(
                                text = "¿Olvidaste tu contraseña?",
                                color = if (isLoading) Color.Gray else PrimaryGreen,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        // Botón Login con estado de carga
                        Button(
                            onClick = {
                                focusManager.clearFocus()

                                // Validar campos vacíos
                                val currentEmailError = if (email.isEmpty()) "El correo es requerido" else validateEmail(email)
                                val currentPasswordError = if (password.isEmpty()) "La contraseña es requerida" else validatePassword(password)

                                emailError = currentEmailError
                                passwordError = currentPasswordError

                                // Si no hay errores, hacer login
                                if (currentEmailError == null && currentPasswordError == null) {
                                    authViewModel.login(email, password)
                                }
                            },
                            enabled = !isLoading,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = PrimaryGreen,
                                disabledContainerColor = Color.Gray.copy(alpha = 0.3f)
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            if (isLoading) {
                                Row(
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.size(24.dp),
                                        color = Color.White,
                                        strokeWidth = 2.dp
                                    )
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Text(
                                        text = "Iniciando sesión...",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color.White
                                    )
                                }
                            } else {
                                Row(
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Login,
                                        contentDescription = null,
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Iniciar Sesión",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Divider
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Divider(modifier = Modifier.weight(1f), color = Color.Gray.copy(alpha = 0.3f))
                            Text(
                                text = "  o  ",
                                color = Color.Gray,
                                style = MaterialTheme.typography.bodySmall
                            )
                            Divider(modifier = Modifier.weight(1f), color = Color.Gray.copy(alpha = 0.3f))
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Botón Registro
                        OutlinedButton(
                            onClick = { navController.navigate("registro") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            enabled = !isLoading,
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = PrimaryGreen
                            ),
                            border = ButtonDefaults.outlinedButtonBorder.copy(
                                width = 2.dp
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.PersonAdd,
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Crear cuenta nueva",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Footer con animación
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(animationSpec = tween(800, delayMillis = 400))
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Agriculture,
                            contentDescription = null,
                            tint = PrimaryGreen,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Conectando productores con ciudades",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = Color.Gray,
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Al continuar, aceptas nuestros Términos y Condiciones",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Color.Gray.copy(alpha = 0.7f),
                            textAlign = TextAlign.Center
                        )
                    )
                }
            }
        }

        // Dialog de error
        if (showErrorDialog && error != null) {
            AlertDialog(
                onDismissRequest = {
                    authViewModel.clearError()
                    showErrorDialog = false
                },
                icon = {
                    Icon(
                        imageVector = Icons.Default.ErrorOutline,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(48.dp)
                    )
                },
                title = {
                    Text(
                        "Error de autenticación",
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                },
                text = {
                    Text(
                        text = error ?: "Ha ocurrido un error inesperado",
                        textAlign = TextAlign.Center
                    )
                },
                confirmButton = {
                    Button(
                        onClick = {
                            authViewModel.clearError()
                            showErrorDialog = false
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PrimaryGreen
                        )
                    ) {
                        Text("Entendido")
                    }
                },
                containerColor = Color.White,
                shape = RoundedCornerShape(16.dp)
            )
        }
    }
}

// ========== FUNCIONES DE VALIDACIÓN ==========

private fun validateEmail(email: String): String? {
    return when {
        email.isEmpty() -> null // No mostrar error mientras escribe
        !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() ->
            "Formato de correo inválido (ejemplo@correo.com)"
        else -> null
    }
}

private fun validatePassword(password: String): String? {
    return when {
        password.isEmpty() -> null // No mostrar error mientras escribe
        password.length < 6 -> "La contraseña debe tener al menos 6 caracteres"
        else -> null
    }
}

private fun isFormValid(
    email: String,
    password: String,
    emailError: String?,
    passwordError: String?
): Boolean {
    return email.isNotEmpty() &&
            password.isNotEmpty() &&
            emailError == null &&
            passwordError == null &&
            android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() &&
            password.length >= 6
}