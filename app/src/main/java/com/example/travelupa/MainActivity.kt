package com.example.travelupa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.travelupa.ui.theme.TravelupaTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TravelupaTheme {
                TravelupaApp()
            }
        }
    }
}

@Composable
fun TravelupaApp() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "greeting",
        // Menambahkan animasi transisi antar halaman
        enterTransition = { slideInHorizontally(initialOffsetX = { 1000 }, animationSpec = tween(500)) },
        exitTransition = { fadeOut(animationSpec = tween(500)) },
        popEnterTransition = { fadeIn(animationSpec = tween(500)) },
        popExitTransition = { slideOutHorizontally(targetOffsetX = { 1000 }, animationSpec = tween(500)) }
    ) {
        composable("greeting") {
            GreetingScreen(navController = navController)
        }
        composable("rekomendasi") {
            RekomendasiTempatScreen(navController = navController)
        }
    }
}

@Composable
fun GreetingScreen(navController: NavController) {
    var titleVisible by remember { mutableStateOf(false) }
    var subtitleVisible by remember { mutableStateOf(false) }
    var buttonVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(300)
        titleVisible = true
        delay(500)
        subtitleVisible = true
        delay(500)
        buttonVisible = true
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.welcome_background),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        // Gradient lebih dramatis
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.9f)),
                        startY = 600f
                    )
                )
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 64.dp)
                .systemBarsPadding(), // Menambahkan padding untuk status/navigation bar
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(1f))

            // Animasi yang terpisah untuk setiap elemen (staggered animation)
            AnimatedVisibility(
                visible = titleVisible,
                enter = fadeIn(animationSpec = tween(1000)) + slideInVertically(animationSpec = tween(1000))
            ) {
                Text(
                    text = "Selamat Datang di Travelupa!",
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineLarge
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            AnimatedVisibility(
                visible = subtitleVisible,
                enter = fadeIn(animationSpec = tween(1000, delayMillis = 200)) + slideInVertically(
                    initialOffsetY = { it / 2 },
                    animationSpec = tween(1000, delayMillis = 200)
                )
            ) {
                Text(
                    text = "Solusi buat kamu yang lupa kemana-mana.",
                    color = Color.White.copy(alpha = 0.8f),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            AnimatedVisibility(
                visible = buttonVisible,
                enter = fadeIn(animationSpec = tween(1000)) + slideInVertically(initialOffsetY = { it })
            ) {
                // Button dengan gaya baru dan ikon
                Button(
                    onClick = { navController.navigate("rekomendasi") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(50), // Membuat button lebih bulat
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    contentPadding = PaddingValues(horizontal = 24.dp)
                ) {
                    Text(
                        text = "Mulai Menjelajahi",
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Lanjutkan",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    }
}

// Menambahkan 'lokasi' untuk data yang lebih kaya
data class TempatWisata(val nama: String, val lokasi: String, val deskripsi: String, val gambarResId: Int)

val daftarTempatWisata = listOf(
    TempatWisata("Air Terjun Tumpak Sewu", "Lumajang, Jawa Timur", "Air terjun tercantik di Jawa Timur.", R.drawable.tumpak_sewu),
    TempatWisata("Gunung Bromo", "Probolinggo, Jawa Timur", "Matahari terbitnya bagus banget.", R.drawable.gunung_bromo),
    TempatWisata("Pantai Klayar", "Pacitan, Jawa Timur", "Punya seruling samudra yang unik.", R.drawable.pantai_klayar)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RekomendasiTempatScreen(navController: NavController) {
    // Menggunakan Scaffold untuk struktur halaman (TopAppBar, dll)
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Rekomendasi Destinasi", style = MaterialTheme.typography.titleLarge) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                    navigationIconContentColor = MaterialTheme.colorScheme.onBackground
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            itemsIndexed(daftarTempatWisata) { index, tempat ->
                // Animasi untuk setiap item saat muncul
                var visible by remember { mutableStateOf(false) }
                LaunchedEffect(Unit) {
                    delay(index * 150L) // Delay berdasarkan index item
                    visible = true
                }
                AnimatedVisibility(
                    visible = visible,
                    enter = fadeIn(animationSpec = tween(500)) + slideInVertically(
                        initialOffsetY = { it / 2 },
                        animationSpec = tween(500)
                    )
                ) {
                    TempatItem(tempat = tempat)
                }
            }
        }
    }
}

@Composable
fun TempatItem(tempat: TempatWisata) {
    // Desain Card yang کاملاً baru dengan teks overlay
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = tempat.gambarResId),
                contentDescription = "Gambar ${tempat.nama}",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            // Gradient Scrim untuk keterbacaan teks
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.8f)),
                            startY = 400f
                        )
                    )
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Bottom // Teks berada di bawah
            ) {
                Text(
                    text = tempat.nama,
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = tempat.lokasi,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.9f)
                )
            }
        }
    }
}