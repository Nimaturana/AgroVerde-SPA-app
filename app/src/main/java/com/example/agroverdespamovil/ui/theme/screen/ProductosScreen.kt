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
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
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

@Composable
fun ProductosScreen(navController: NavController) {
    val authViewModel: AuthViewModel = androidx.lifecycle.viewmodel.compose.viewModel()

    // Estados UI
    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    // Estados de validación
    var nombreError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var telefonoError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var confirmPasswordError by remember { mutableStateOf<String?>(null) }

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

    // Navegar al home cuando se registre exitosamente
    LaunchedEffect(isAuthenticated) {
        if (isAuthenticated) {
            navController.navigate("productos") {
                popUpTo("registro") { inclusive = true }
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
            Spacer(modifier = Modifier.height(20.dp))

            // Header con animación
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(animationSpec = tween(600)) +
                        slideInVertically(initialOffsetY = { -100 })
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Botón atrás
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = { navController.popBackStack() },
                            enabled = !isLoading
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Volver",
                                tint = DarkGreen
                            )
                        }
                        Spacer(modifier = Modifier.weight(1f))
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Icono
                    Icon(
                        imageVector = Icons.Default.PersonAdd,
                        contentDescription = "Registro",
                        tint = PrimaryGreen,
                        modifier = Modifier.size(60.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Crear Cuenta",
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = DarkGreen,
                            fontSize = 28.sp
                        )
                    )

                    Text(
                        text = "Únete a nuestra comunidad orgánica",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = Color.Gray
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

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
                        // Campo Nombre
                        OutlinedTextField(
                            value = nombre,
                            onValueChange = {
                                nombre = it
                                nombreError = validateNombre(it)
                            },
                            label = { Text("Nombre *") },
                            placeholder = { Text("Ingresa tu nombre") },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Person,
                                    contentDescription = null,
                                    tint = if (nombreError != null) MaterialTheme.colorScheme.error else PrimaryGreen
                                )
                            },
                            isError = nombreError != null,
                            supportingText = {
                                if (nombreError != null) {
                                    Text(
                                        text = nombreError!!,
                                        color = MaterialTheme.colorScheme.error,
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            },
                            keyboardOptions = KeyboardOptions(
                                capitalization = KeyboardCapitalization.Words,
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

                        Spacer(modifier = Modifier.height(12.dp))

                        // Campo Apellido (opcional)
                        OutlinedTextField(
                            value = apellido,
                            onValueChange = { apellido = it },
                            label = { Text("Apellido (opcional)") },
                            placeholder = { Text("Ingresa tu apellido") },
                            leadingIcon = {
                                Icon(Icons.Default.Person, contentDescription = null, tint = PrimaryGreen)
                            },
                            keyboardOptions = KeyboardOptions(
                                capitalization = KeyboardCapitalization.Words,
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

                        Spacer(modifier = Modifier.height(12.dp))

                        // Campo Email
                        OutlinedTextField(
                            value = email,
                            onValueChange = {
                                email = it.lowercase()
                                emailError = validateEmail(it)
                            },
                            label = { Text("Correo electrónico *") },
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

                        Spacer(modifier = Modifier.height(12.dp))

                        // Campo Teléfono (opcional)
                        OutlinedTextField(
                            value = telefono,
                            onValueChange = {
                                if (it.length <= 12) {
                                    telefono = it
                                    telefonoError = validateTelefono(it)
                                }
                            },
                            label = { Text("Teléfono (opcional)") },
                            placeholder = { Text("+56912345678") },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Phone,
                                    contentDescription = null,
                                    tint = if (telefonoError != null) MaterialTheme.colorScheme.error else PrimaryGreen
                                )
                            },
                            isError = telefonoError != null,
                            supportingText = {
                                if (telefonoError != null) {
                                    Text(
                                        text = telefonoError!!,
                                        color = MaterialTheme.colorScheme.error,
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Phone,
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

                        Spacer(modifier = Modifier.height(12.dp))

                        // Campo Contraseña
                        OutlinedTextField(
                            value = password,
                            onValueChange = {
                                password = it
                                passwordError = validatePassword(it)
                                // Revalidar confirmación si ya se escribió algo
                                if (confirmPassword.isNotEmpty()) {
                                    confirmPasswordError = validateConfirmPassword(it, confirmPassword)
                                }
                            },
                            label = { Text("Contraseña *") },
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

                        Spacer(modifier = Modifier.height(12.dp))

                        // Campo Confirmar Contraseña
                        OutlinedTextField(
                            value = confirmPassword,
                            onValueChange = {
                                confirmPassword = it
                                confirmPasswordError = validateConfirmPassword(password, it)
                            },
                            label = { Text("Confirmar contraseña *") },
                            placeholder = { Text("Repite tu contraseña") },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Lock,
                                    contentDescription = null,
                                    tint = if (confirmPasswordError != null) MaterialTheme.colorScheme.error else PrimaryGreen
                                )
                            },
                            trailingIcon = {
                                IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                                    Icon(
                                        imageVector = if (confirmPasswordVisible)
                                            Icons.Default.Visibility
                                        else
                                            Icons.Default.VisibilityOff,
                                        contentDescription = if (confirmPasswordVisible)
                                            "Ocultar contraseña"
                                        else
                                            "Mostrar contraseña",
                                        tint = PrimaryGreen
                                    )
                                }
                            },
                            visualTransformation = if (confirmPasswordVisible)
                                VisualTransformation.None
                            else
                                PasswordVisualTransformation(),
                            isError = confirmPasswordError != null,
                            supportingText = {
                                if (confirmPasswordError != null) {
                                    Text(
                                        text = confirmPasswordError!!,
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
                                    if (isFormValid(nombre, email, password, confirmPassword,
                                            nombreError, emailError, passwordError, confirmPasswordError)) {
                                        authViewModel.signup(
                                            email = email,
                                            password = password,
                                            nombre = nombre,
                                            apellido = apellido.ifEmpty { null },
                                            telefono = telefono.ifEmpty { null }
                                        )
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

                        Spacer(modifier = Modifier.height(24.dp))

                        // Botón Registrar
                        Button(
                            onClick = {
                                focusManager.clearFocus()

                                // Validar todos los campos requeridos
                                val currentNombreError = if (nombre.isEmpty()) "El nombre es requerido" else validateNombre(nombre)
                                val currentEmailError = if (email.isEmpty()) "El correo es requerido" else validateEmail(email)
                                val currentPasswordError = if (password.isEmpty()) "La contraseña es requerida" else validatePassword(password)
                                val currentConfirmPasswordError = if (confirmPassword.isEmpty()) "Confirma tu contraseña" else validateConfirmPassword(password, confirmPassword)

                                nombreError = currentNombreError
                                emailError = currentEmailError
                                passwordError = currentPasswordError
                                confirmPasswordError = currentConfirmPasswordError

                                // Si no hay errores, registrar
                                if (currentNombreError == null && currentEmailError == null &&
                                    currentPasswordError == null && currentConfirmPasswordError == null) {
                                    authViewModel.signup(
                                        email = email,
                                        password = password,
                                        nombre = nombre,
                                        apellido = apellido.ifEmpty { null },
                                        telefono = telefono.ifEmpty { null }
                                    )
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
                                        text = "Creando cuenta...",
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
                                        imageVector = Icons.Default.Check,
                                        contentDescription = null,
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Crear Cuenta",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Link a Login
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "¿Ya tienes cuenta?",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = Color.Gray
                                )
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            TextButton(
                                onClick = { navController.popBackStack() },
                                enabled = !isLoading
                            ) {
                                Text(
                                    text = "Iniciar Sesión",
                                    color = if (isLoading) Color.Gray else PrimaryGreen,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Footer
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(animationSpec = tween(800, delayMillis = 400))
            ) {
                Text(
                    text = "Al registrarte, aceptas nuestros Términos y Condiciones",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = Color.Gray.copy(alpha = 0.7f),
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier.padding(16.dp)
                )
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
                        "Error al registrar",
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

private fun validateNombre(nombre: String): String? {
    return when {
        nombre.isEmpty() -> null
        nombre.length < 2 -> "El nombre debe tener al menos 2 caracteres"
        !nombre.matches(Regex("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$")) -> "El nombre solo puede contener letras"
        else -> null
    }
}

private fun validateEmail(email: String): String? {
    return when {
        email.isEmpty() -> null
        !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() ->
            "Formato de correo inválido"
        else -> null
    }
}

private fun validateTelefono(telefono: String): String? {
    if (telefono.isEmpty()) return null
    return when {
        !telefono.matches(Regex("^[+]?[0-9]{8,12}$")) ->
            "Formato inválido (ej: +56912345678)"
        else -> null
    }
}

private fun validatePassword(password: String): String? {
    return when {
        password.isEmpty() -> null
        password.length < 6 -> "Mínimo 6 caracteres"
        else -> null
    }
}

private fun validateConfirmPassword(password: String, confirmPassword: String): String? {
    return when {
        confirmPassword.isEmpty() -> null
        password != confirmPassword -> "Las contraseñas no coinciden"
        else -> null
    }
}

private fun isFormValid(
    nombre: String,
    email: String,
    password: String,
    confirmPassword: String,
    nombreError: String?,
    emailError: String?,
    passwordError: String?,
    confirmPasswordError: String?
): Boolean {
    return nombre.isNotEmpty() &&
            email.isNotEmpty() &&
            password.isNotEmpty() &&
            confirmPassword.isNotEmpty() &&
            nombreError == null &&
            emailError == null &&
            passwordError == null &&
            confirmPasswordError == null &&
            android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() &&
            password.length >= 6 &&
            password == confirmPassword

}

