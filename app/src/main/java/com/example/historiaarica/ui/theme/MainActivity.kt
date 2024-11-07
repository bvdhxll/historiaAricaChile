package com.example.historiaarica.ui.theme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.historiaarica.Data.NombreEntity
import com.example.historiaarica.Data.NombreViewModel
import com.example.historiaarica.ui.theme.HistoriaAricaTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Obtener el ViewModel
        val viewModel: NombreViewModel by viewModels()

        // Configurar el contenido de la UI
        setContent {
            HistoriaAricaTheme() {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = { BottomNavigationBar() }
                ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AgregarNombre(viewModel = viewModel)
                        Spacer(modifier = Modifier.height(16.dp))
                        NombreList(viewModel = viewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar() {
    BottomAppBar(
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        Button(onClick = { /* Acción para Inicio */ }, modifier = Modifier.weight(1f)) {
            Text("Inicio")
        }
        Spacer(modifier = Modifier.width(8.dp))
        Button(onClick = { /* Acción para Mapa */ }, modifier = Modifier.weight(1f)) {
            Text("Mapa")
        }
        Spacer(modifier = Modifier.width(8.dp))
        Button(onClick = { /* Acción para Mi Cuenta */ }, modifier = Modifier.weight(1f)) {
            Text("Mi Cuenta")
        }
    }
}

@Composable
fun NombreList(viewModel: NombreViewModel) {
    val nombres by viewModel.getUltimosNombres().collectAsState(initial = emptyList())
    ListContent(nombres = nombres)
}

@Composable
fun ListContent(nombres: List<NombreEntity>) {
    Column {
        Spacer(modifier = Modifier.height(40.dp))
        Text("Prueba de base de datos")
        nombres.forEach { nombre ->
            Text(nombre.name)
        }
    }
}

@Composable
fun AgregarNombre(viewModel: NombreViewModel) {
    var texto by remember { mutableStateOf("") }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        TextField(
            value = texto,
            onValueChange = { texto = it },
            label = { Text("Ingrese nombre:") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            viewModel.insertName(NombreEntity(name = texto))
            texto = ""
        }) {
            Text("Agregar nombre")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HistoriaAricaTheme() {
        Scaffold(bottomBar = { BottomNavigationBar() }) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Bienvenido Usuario")
            }
        }
    }
}
